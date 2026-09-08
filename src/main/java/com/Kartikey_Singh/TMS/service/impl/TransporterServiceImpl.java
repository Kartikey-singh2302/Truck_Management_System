package com.Kartikey_Singh.TMS.service.impl;

import com.Kartikey_Singh.TMS.entity.Transporter;
import com.Kartikey_Singh.TMS.exception.ResourceNotFoundException;
import com.Kartikey_Singh.TMS.repository.TransporterRepository;
import com.Kartikey_Singh.TMS.service.TransporterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransporterServiceImpl implements TransporterService {
    private final TransporterRepository transporterRepository;
    @Override
    public Transporter registerTransporter(Transporter transporter) {
        return transporterRepository.save(transporter);
    }

    @Override
    public Transporter getTransporterById(UUID transporterId) {
        return transporterRepository.findById(transporterId)
                .orElseThrow(() -> new ResourceNotFoundException("Transporter not found with id: " + transporterId));
    }

    @Override
    @Transactional
    public Transporter updateTrucks(UUID transporterId, String truckType, int count) {
        Transporter transporter = getTransporterById(transporterId);


        Map<String,Integer> trucks = transporter.getAvailableTrucks();
        if (trucks == null) {
            throw new IllegalStateException("specified truck type is not available");
        }
        trucks.put(truckType, count);

        return transporterRepository.save(transporter);
    }
}
