package com.Kartikey_Singh.TMS.entity;

import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Load {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID loadId;

    private UUID shipperId;
    private String loadingCity;
    private String unloadingCity;
    private Instant loadingDate;
    private String productType;
    private double weight;
    private String weightUnit; // KG | TON
    private String truckType;
    private int noOfTrucks;

    @Enumerated(EnumType.STRING)
    private LoadStatus status;
    private Instant datePosted;

    @Version
    private Long version;

}
