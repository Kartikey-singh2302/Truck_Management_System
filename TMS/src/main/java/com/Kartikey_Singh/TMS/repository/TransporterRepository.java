package com.Kartikey_Singh.TMS.repository;

import com.Kartikey_Singh.TMS.entity.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, UUID> {
    //List<TransporterTruckCapacity> findByTransporterId(UUID transporterId);
    TransporterTruckCapacity findByTransporterIdAndTruckType(UUID transporterId, String truckType);
}
