package com.Kartikey_Singh.TMS.repository;

import com.Kartikey_Singh.TMS.entity.Bid;
import com.Kartikey_Singh.TMS.entity.enums.BidStatus;
import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {
    List<Bid> findByLoadId(UUID loadId);
    List<Bid> findByLoadIdAndStatus(UUID loadId, BidStatus status);
    List<Bid> findByTransporterId(UUID transporterId);
    List<Bid>findByLoadIdAndTransporterIdAndStatus(UUID loadId, UUID transporterId, BidStatus status);
    List<Bid>findByLoadIdAndTransporterId(UUID loadId,UUID transporterId);
    List<Bid>findByTransporterIdAndStatus(UUID transporterId, BidStatus status);
    List<Bid>findByStatus(BidStatus status);
}
