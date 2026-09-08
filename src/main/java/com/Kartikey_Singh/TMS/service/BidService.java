package com.Kartikey_Singh.TMS.service;

import com.Kartikey_Singh.TMS.entity.Bid;
import com.Kartikey_Singh.TMS.entity.enums.BidStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface BidService {
    Bid submitBid(Bid bid);
    List<Bid> getBidsByFilter(UUID loadId, UUID transporterId, BidStatus status);
    Bid getBidById(UUID bidId);
    void rejectBid(UUID bidId);
    List<Bid> BestBids(UUID loadId );
}
