package com.chameleon.categorization.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "spending_data_points")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpendingDataPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String customerId;
    private BigDecimal amount;
    private LocalDateTime spentAt;
    private String category;
}
