package com.Kartikey_Singh.TMS.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class TransporterDto {
    private String companyName;
    private double rating;
    private List<TruckCapacitydto> availableTrucks;
    private String truckType;
    private int count;
}
