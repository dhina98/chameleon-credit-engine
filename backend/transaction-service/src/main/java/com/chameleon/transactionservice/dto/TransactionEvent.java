package com.chameleon.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEvent {
    private Long transactionId;
    private String customerId;
    private String category;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
    private String merchantName;
    private String merchantCode;
    private String status;
    private String cardId;
}
