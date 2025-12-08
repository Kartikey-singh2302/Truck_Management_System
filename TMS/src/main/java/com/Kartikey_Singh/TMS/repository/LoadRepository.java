package com.Kartikey_Singh.TMS.repository;

import com.Kartikey_Singh.TMS.entity.Load;
import com.Kartikey_Singh.TMS.entity.enums.LoadStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoadRepository extends JpaRepository<Load, UUID> {
    List<Load> findByShipperIdAndStatus(String shipperId, LoadStatus status, Pageable pageable);
    Page<Load> findByShipperId(String shipperId, Pageable pageable);
    Page<Load> findByStatus(LoadStatus status, Pageable pageable);
}
