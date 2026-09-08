package com.Kartikey_Singh.TMS.entity;

import com.Kartikey_Singh.TMS.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
        (
                indexes = {@Index(name = "idx_booking_loadid", columnList = "loadId")}
        )
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookingId;
    private UUID loadId;
    private UUID bidId;
    private UUID transporterId;
    private int allocatedTrucks;
    private double finalRate;
    private String TruckType;


    @Enumerated(EnumType.STRING)
    private BookingStatus status;


    private Instant bookedAt;


}
