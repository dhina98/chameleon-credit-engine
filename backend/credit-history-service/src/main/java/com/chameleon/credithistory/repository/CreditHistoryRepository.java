package com.chameleon.credithistory.repository;

import com.chameleon.credithistory.model.CreditHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Long> {
    List<CreditHistory> findByCustomerId(String customerId);

    List<CreditHistory> findByCustomerIdAndTransactionTimeAfter(String CustomerId, LocalDateTime after);

    List<CreditHistory> findByCustomerIdAndCategory(String Customerid, String category);
}
