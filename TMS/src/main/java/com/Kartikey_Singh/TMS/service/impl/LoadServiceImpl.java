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

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
public class LoadServiceImpl implements LoadService {

    private final LoadRepository loadRepository;
    @Override
    public Load createLoad(Load load) {
      load.setStatus(LoadStatus.POSTED);
      return loadRepository.save(load);
    }

    @Override
    public List<Load> getLoads(String shipperId, LoadStatus status, int page, int size) {
        return loadRepository.findByShipperIdAndStatus(shipperId, status, PageRequest.of(page, size));
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
