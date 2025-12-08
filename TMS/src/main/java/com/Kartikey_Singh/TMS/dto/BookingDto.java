package com.Kartikey_Singh.TMS.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class BookingDto {
    private UUID bookingId;
    private UUID loadId;
    private UUID transporterId;
    private UUID bidId;
    private int allocatedTrucks;
    private double finalRate;
    private String truckType;
    private String status;
    private Instant bookedAt;
}
