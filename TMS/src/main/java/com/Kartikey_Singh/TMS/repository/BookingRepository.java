package com.Kartikey_Singh.TMS.repository;

import com.Kartikey_Singh.TMS.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    int sumAllocatedTrucksByLoadId(UUID loadId);
}
