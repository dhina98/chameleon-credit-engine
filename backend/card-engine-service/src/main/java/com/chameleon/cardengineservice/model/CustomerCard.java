package com.chameleon.cardengineservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name ="customer_cards")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String customerId;

    private String previousCategory;

    private String activeCategory;

    private String cardId;

    private double knnConfidence;

    private LocalDateTime lastUpdated;
}
