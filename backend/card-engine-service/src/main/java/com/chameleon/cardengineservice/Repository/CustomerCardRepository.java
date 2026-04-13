package com.chameleon.cardengineservice.Repository;

import com.chameleon.cardengineservice.model.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {
    Optional<CustomerCard> findByCustomerId(String customerId);
}
