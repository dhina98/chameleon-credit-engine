package com.chameleon.categorization.repository;

import com.chameleon.categorization.model.SpendingDataPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpendingDataPointRepository extends JpaRepository<SpendingDataPoint, String> {
     List<SpendingDataPoint> findByCustomerId(String customerId);
     List<SpendingDataPoint> findByCustomerIdAndSpentAtAfter(String customerId, LocalDateTime from);
}
