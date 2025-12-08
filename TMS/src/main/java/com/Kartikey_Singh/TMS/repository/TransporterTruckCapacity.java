package com.Kartikey_Singh.TMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransporterTruckCapacity extends JpaRepository<TransporterTruckCapacity, Long> {

}
