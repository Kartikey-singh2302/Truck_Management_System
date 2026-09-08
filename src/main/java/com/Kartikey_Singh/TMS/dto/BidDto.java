package com.Kartikey_Singh.TMS.dto;

import com.Kartikey_Singh.TMS.entity.enums.BidStatus;
import lombok.Data;

import java.util.UUID;
@Data
public class BidDto {
    private double proposedRate;
    private int trucksOffered;
    private UUID loadId;
    private UUID transporterId;
    private String truckType;
}
