package com.Kartikey_Singh.TMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.Instant;
import java.util.UUID;

@Data
public class LoadDto {
    private UUID shipperId;
    private String loadingCity;
    private String unloadingCity;
    private Instant loadingDate;
    private String productType;
    private double weight;
    private String weightUnit; // KG | TON
    private String truckType;
    private int noOfTrucks;
}
