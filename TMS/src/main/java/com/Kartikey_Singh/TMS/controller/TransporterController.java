package com.Kartikey_Singh.TMS.controller;

import com.Kartikey_Singh.TMS.dto.TransporterDto;
import com.Kartikey_Singh.TMS.dto.TruckCapacitydto;
import com.Kartikey_Singh.TMS.entity.Transporter;
import com.Kartikey_Singh.TMS.service.TransporterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transporter")
public class TransporterController {
    private final TransporterService transporterService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Transporter> registerTransporter(@RequestBody TransporterDto transporterDto) {
        Transporter transporter = dtoToEntity(transporterDto);
        return ResponseEntity.ok(transporterService.registerTransporter(transporter));
    }

    @GetMapping("/{transporterId}")
    public ResponseEntity<TransporterDto> getTransporterById(@PathVariable UUID transporterId) {
        Transporter transporter = transporterService.getTransporterById(transporterId);
        return ResponseEntity.ok(modelMapper.map(transporter, TransporterDto.class));
    }

    @PutMapping("/{transporterId}/trucks")
    public ResponseEntity<Transporter> updateTrucks(@PathVariable UUID transporterId,
                                                    @RequestParam String truckType,
                                                    @RequestParam int count) {
        return ResponseEntity.ok(transporterService.updateTrucks(transporterId, truckType, count));
    }

    private Transporter dtoToEntity(TransporterDto dto) {
        Transporter transporter = new Transporter();
        transporter.setCompanyName(dto.getCompanyName());
        transporter.setRating(dto.getRating());
        if (dto.getAvailableTrucks() != null) {
            Map<String, Integer> trucksMap = dto.getAvailableTrucks()
                    .stream()
                    .collect(Collectors.toMap(TruckCapacitydto::getTruckType, TruckCapacitydto::getCount));
            transporter.setAvailableTrucks(trucksMap);
        }
        return transporter;
    }
}
