package com.Kartikey_Singh.TMS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class BookingDto {
    @NotBlank(message = "please provide a valid BookingID")
    private UUID bookingId;
    private UUID loadId;
    private UUID transporterId;
    @NotBlank(message = "please provide a valid BidID")
    private UUID bidId;
    private int allocatedTrucks;
    private double finalRate;
    private String truckType;
    private String status;
    private Instant bookedAt;
}
