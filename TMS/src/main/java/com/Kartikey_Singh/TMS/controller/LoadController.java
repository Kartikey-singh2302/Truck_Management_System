package com.Kartikey_Singh.TMS.controller;

import com.Kartikey_Singh.TMS.dto.LoadDto;
import com.Kartikey_Singh.TMS.entity.Load;
import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;
import com.Kartikey_Singh.TMS.service.LoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/load")
public class LoadController {
    private final LoadService loadService;

    @PostMapping
    public ResponseEntity<Load> createLoad(@RequestBody LoadDto loadDto) {
        Load load = dtoToEntity(loadDto);
        return ResponseEntity.ok(loadService.createLoad(load));
    }

    @GetMapping
    public ResponseEntity<List<Load>> getLoads(
            @RequestParam(required = false) String shipperId,
            @RequestParam(required = false) LoadStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(loadService.getLoads(shipperId, status, page, size));
    }

    @GetMapping("/{loadId}")
    public ResponseEntity<Load> getLoadById(@PathVariable UUID loadId) {
        Load load = loadService.getLoadById(loadId);
        return ResponseEntity.ok(load);
    }

    @PatchMapping("/{loadId}")
    public ResponseEntity<Void> cancelLoad(@PathVariable UUID loadId) {
        loadService.cancelLoad(loadId);
        return ResponseEntity.noContent().build();
    }

    private Load dtoToEntity(LoadDto dto) {
        Load load = new Load();
        load.setShipperId(dto.getShipperId());
        load.setLoadingCity(dto.getLoadingCity());
        load.setUnloadingCity(dto.getUnloadingCity());
        load.setLoadingDate(dto.getLoadingDate());
        load.setProductType(dto.getProductType());
        load.setWeight(dto.getWeight());
        load.setWeightUnit(dto.getWeightUnit());
        load.setTruckType(dto.getTruckType());
        load.setNoOfTrucks(dto.getNoOfTrucks());
        return load;
    }
}

