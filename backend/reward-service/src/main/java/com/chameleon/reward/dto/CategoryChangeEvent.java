package com.chameleon.reward.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryChangeEvent {
    private String customerId;
    private String oldCategory;
    private String newCategory;
    private LocalDateTime changedAt;
    private Double confidence;
}
