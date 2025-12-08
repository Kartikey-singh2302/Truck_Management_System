package com.Kartikey_Singh.TMS.entity;

import com.Kartikey_Singh.TMS.entity.enums.BidStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        indexes = {
                @Index(name = "idx_bid_loadid",columnList = "loadId"),
                @Index(name = "idx_bid_transporterid", columnList = "transporterId")
        }
)
@Builder
@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID bidId;
    private UUID loadId;
    private UUID transporterId;
    private double proposedRate;
    private int trucksOffered;
    private String TruckType;

    @Enumerated(EnumType.STRING)
    public BidStatus bidStatus;
    private Instant submittedAt;
}

