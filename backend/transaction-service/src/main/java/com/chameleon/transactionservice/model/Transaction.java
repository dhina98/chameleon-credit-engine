package com.chameleon.transactionservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String customerId;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String merchantName;
    @Column(nullable = false)
    private String merchantCode;
    @Column(nullable = false)
    private LocalDateTime transactionTime;

    private String cardId;
    private String status;
}
