package com.Kartikey_Singh.TMS.service.impl;

import com.Kartikey_Singh.TMS.entity.*;
import com.Kartikey_Singh.TMS.entity.enums.BookingStatus;
import com.Kartikey_Singh.TMS.entity.enums.BidStatus;
import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;
import com.Kartikey_Singh.TMS.exception.InsufficientCapacityException;
import com.Kartikey_Singh.TMS.exception.InvalidStatusTransitionException;
import com.Kartikey_Singh.TMS.exception.LoadAlreadyBookedException;
import com.Kartikey_Singh.TMS.exception.ResourceNotFoundException;
import com.Kartikey_Singh.TMS.repository.BidRepository;
import com.Kartikey_Singh.TMS.repository.BookingRepository;
import com.Kartikey_Singh.TMS.repository.LoadRepository;
import com.Kartikey_Singh.TMS.repository.TransporterRepository;
import com.Kartikey_Singh.TMS.service.BookingService;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BidRepository bidRepository;
    private final LoadRepository loadRepository;
    private final TransporterRepository transporterRepository;

    @Override
    @Transactional
    public Booking acceptBidAndBook(UUID bidId) {
        try {
            Bid bid = bidRepository.findById(bidId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bid not found with id: " + bidId));
            Load load = loadRepository.findById(bid.getLoadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Load not found with id: " + bid.getLoadId()));
            Transporter transporter = transporterRepository.findById(bid.getTransporterId())
                    .orElseThrow(() -> new ResourceNotFoundException("Transporter not found with id: " + bid.getTransporterId()));

            if (load.getStatus() == LoadStatus.CANCELLED ||
                    (load.getStatus() == LoadStatus.BOOKED && !loadHasRemainingTrucks(load))) {
                throw new InvalidStatusTransitionException("Cannot book on CANCELLED or fully BOOKED load");
            }

            // Capacity validation: Deduct trucks if available for the specific truckType
            Integer availableCount = transporter.getAvailableTrucks().get(bid.getTruckType());
            if (availableCount == null || availableCount < bid.getTrucksOffered()) {
                throw new InsufficientCapacityException("Transporter does not have enough available trucks");
            }

            transporter.getAvailableTrucks().put(bid.getTruckType(), availableCount - bid.getTrucksOffered());
            transporterRepository.save(transporter);

            Booking booking = new Booking();
            booking.setBidId(bid.getBidId());
            booking.setLoadId(bid.getLoadId());
            booking.setTransporterId(bid.getTransporterId());
            booking.setAllocatedTrucks(bid.getTrucksOffered());
            booking.setFinalRate(bid.getProposedRate());
            booking.setTruckType(bid.getTruckType());
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setBookedAt(java.time.Instant.now());

            Booking savedBooking = bookingRepository.save(booking);

            // Update Bid status to ACCEPTED
            bid.setBidStatus(BidStatus.ACCEPTED);
            bidRepository.save(bid);

            // Update load remaining trucks logic
            int remainingTrucks = calculateRemainingTrucks(load);
            if (remainingTrucks <= 0) {
                load.setStatus(LoadStatus.BOOKED);
            } else {
                load.setStatus(LoadStatus.OPEN_FOR_BIDS);
            }
            loadRepository.save(load);

            return savedBooking;
        } catch (OptimisticLockException e) {
            throw new LoadAlreadyBookedException();
        }
    }

    private boolean loadHasRemainingTrucks(Load load) {
        int remaining = calculateRemainingTrucks(load);
        return remaining > 0;
    }

    private int calculateRemainingTrucks(Load load) {
        Integer noOfTrucks = load.getNoOfTrucks();
        Integer sumAllocated = bookingRepository.sumAllocatedTrucksByLoadId(load.getLoadId());
        if (sumAllocated == null) sumAllocated = 0;
        return noOfTrucks - sumAllocated;
    }

    @Override
    public Booking getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }

    @Override
    @Transactional
    public void cancelBooking(UUID bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.COMPLETED) {
            throw new InvalidStatusTransitionException("Booking already cancelled or completed");
        }

        Load load = loadRepository.findById(booking.getLoadId())
                .orElseThrow(() -> new ResourceNotFoundException("Load not found with id: " + booking.getLoadId()));

        Transporter transporter = transporterRepository.findById(booking.getTransporterId())
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found with id: " + booking.getTransporterId()));

        // Restore trucks for the truckType
        Integer availableCount = transporter.getAvailableTrucks().get(booking.getTruckType());
        if (availableCount == null) availableCount = 0;
        transporter.getAvailableTrucks().put(booking.getTruckType().toString(), availableCount + booking.getAllocatedTrucks());
        transporterRepository.save(transporter);

        // Update booking status
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        // Update load status if it was BOOKED and now trucks are available
        int remainingTrucks = calculateRemainingTrucks(load);
        if (load.getStatus() == LoadStatus.BOOKED && remainingTrucks > 0) {
            load.setStatus(LoadStatus.OPEN_FOR_BIDS);
            loadRepository.save(load);
        }
    }
}
