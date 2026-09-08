package com.Kartikey_Singh.TMS.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CreateLoadRequest {

    private UUID shipperId;
    private String loadingCity;
    private String unloadingCity;
    private Instant loadingDate;
    private String productType;
    private double weight;
    private String weightUnit;
    private String truckType;
    private int noOfTrucks;
}