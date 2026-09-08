package com.Kartikey_Singh.TMS.repository;

import com.Kartikey_Singh.TMS.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    @Query("""
    SELECT COALESCE(SUM(b.allocatedTrucks), 0)
    FROM Booking b
    WHERE b.loadId = :loadId
""")
    int sumAllocatedTrucksByLoadId(UUID loadId);
}
