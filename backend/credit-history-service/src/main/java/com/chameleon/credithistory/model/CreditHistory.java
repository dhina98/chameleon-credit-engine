package com.chameleon.credithistory.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "credit_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    @Column(nullable = false)
    private String customerId;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private String category; // example : FOOD|TRAVEL|SHOPPING|FUEL|ENTERTAINMENT|UTILITIES
    @Column(nullable = false)
    private String merchantName;
    @Column(nullable = false)
    private LocalDateTime transactionTime;
    private String merchantCode;
    private String status; // SUCCESS | FAILED| PENDING
    private String cardId;
}
