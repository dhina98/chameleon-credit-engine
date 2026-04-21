package com.chameleon.reward.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
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
