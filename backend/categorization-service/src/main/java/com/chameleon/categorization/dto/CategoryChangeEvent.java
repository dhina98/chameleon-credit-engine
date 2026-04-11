package com.chameleon.categorization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryChangeEvent {
    private String customerId;
    private String oldCategory;
    private String newCategory;
    private LocalDateTime changedAt;
    private Double confidence;
}
