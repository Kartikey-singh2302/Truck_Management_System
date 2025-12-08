package com.Kartikey_Singh.TMS.service.impl;

import com.Kartikey_Singh.TMS.entity.Bid;
import com.Kartikey_Singh.TMS.entity.Load;
import com.Kartikey_Singh.TMS.entity.Transporter;
import com.Kartikey_Singh.TMS.entity.enums.BidStatus;
import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;
import com.Kartikey_Singh.TMS.exception.InsufficientCapacityException;
import com.Kartikey_Singh.TMS.exception.InvalidStatusTransitionException;
import com.Kartikey_Singh.TMS.exception.ResourceNotFoundException;
import com.Kartikey_Singh.TMS.repository.BidRepository;
import com.Kartikey_Singh.TMS.repository.LoadRepository;
import com.Kartikey_Singh.TMS.repository.TransporterRepository;
import com.Kartikey_Singh.TMS.service.BidService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;
    private final LoadRepository loadRepository;
    private final TransporterRepository transporterRepository;
    @Override
    public Bid submitBid(Bid bid) {
        Load load = loadRepository.findById(bid.getLoadId())
                .orElseThrow(() -> new ResourceNotFoundException("Load not found with id: " + bid.getLoadId()));
        if ("CANCELLED".equals(load.getStatus()) || "BOOKED".equals(load.getStatus())) {
            throw new InvalidStatusTransitionException("Cannot bid on CANCELLED or BOOKED loads");
        }

        Transporter transporter = transporterRepository.findById(bid.getTransporterId())
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found with id: " + bid.getTransporterId()));

        // Capacity validation - Rule 1
        Integer availableTrucksCount = transporter.getAvailableTrucks().get(bid.getTruckType());
        if (availableTrucksCount == null || bid.getTrucksOffered() > availableTrucksCount) {
            throw new InsufficientCapacityException("Transporter does not have sufficient trucks available");
        }

        if ("POSTED".equals(load.getStatus())) {
            load.setStatus(LoadStatus.OPEN_FOR_BIDS);
            loadRepository.save(load);
        }

        return bidRepository.save(bid);
    }


    @Override
    public List<Bid> getBidsByFilter(UUID loadId, UUID transporterId, BidStatus status) {
        if (loadId != null && transporterId != null && status != null) {
            return bidRepository.findByLoadIdAndTransporterIdAndStatus(loadId, transporterId, status);
        } else if (loadId != null && transporterId != null) {
            return bidRepository.findByLoadIdAndTransporterId(loadId, transporterId);
        } else if (loadId != null && status != null) {
            return bidRepository.findByLoadIdAndStatus(loadId, status);
        } else if (transporterId != null && status != null) {
            return bidRepository.findByTransporterIdAndStatus(transporterId, status);
        } else if (loadId != null) {
            return bidRepository.findByLoadId(loadId);
        } else if (transporterId != null) {
            return bidRepository.findByTransporterId(transporterId);
        } else if (status != null) {
            return bidRepository.findByStatus(status);
        } else {
            return bidRepository.findAll();
        }
    }

    @Override
    public Bid getBidById(UUID bidId) {
        return bidRepository.findById(bidId)
                .orElseThrow(() -> new ResourceNotFoundException("Bid not found with id: " + bidId));
    }

    @Override
    @Transactional
    public void rejectBid(UUID bidId) {
        Bid bid = getBidById(bidId);
        bid.setBidStatus(BidStatus.REJECTED);
        bidRepository.save(bid);

    }
}
