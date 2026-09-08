package com.Kartikey_Singh.TMS.service.impl;

import com.Kartikey_Singh.TMS.entity.Load;
import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;
import com.Kartikey_Singh.TMS.exception.InvalidStatusTransitionException;
import com.Kartikey_Singh.TMS.exception.ResourceNotFoundException;
import com.Kartikey_Singh.TMS.repository.LoadRepository;
import com.Kartikey_Singh.TMS.service.LoadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class LoadServiceImpl implements LoadService {

    private final LoadRepository loadRepository;
    @Override
    public Load createLoad(Load load) {

        System.out.println("loadId before save = " + load.getLoadId());
        System.out.println("version before save = " + load.getVersion());

        load.setStatus(LoadStatus.POSTED);
        load.setDatePosted(java.time.Instant.now());

        return loadRepository.save(load);
    }

    @Override
    public List<Load> getLoads(UUID shipperId, LoadStatus status, int page, int size) {
            Pageable pageable = PageRequest.of(page, size);
            if (shipperId != null && status != null) {

                return loadRepository.findByShipperIdAndStatus(shipperId, status, pageable);

            } else if (shipperId != null) {

                return loadRepository.findByShipperId(shipperId, pageable).getContent();

            } else if (status != null) {
                return loadRepository.findByStatus(status, pageable).getContent();

            } else {
                return loadRepository.findAll(pageable).getContent();
            }
    }

    @Override
    public Load getLoadById(UUID loadId) {
        return loadRepository.findById(loadId)
                .orElseThrow(() -> new ResourceNotFoundException("Load not found with id: " + loadId));
    }

    @Override
    @Transactional
    public void cancelLoad(UUID loadId) {
        Load load = getLoadById(loadId);
        if (load.getStatus()== LoadStatus.BOOKED) {
            throw new InvalidStatusTransitionException("Cannot cancel a load that is already BOOKED");
        }
        load.setStatus(LoadStatus.CANCELLED);
        loadRepository.save(load);

    }
}
