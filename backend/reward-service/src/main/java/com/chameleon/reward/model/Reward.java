package com.chameleon.reward.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name ="rewards")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String customerId;
    private double totalPoints;     // NEVER reset — always carry forward
    private String currentCategory; // current card type label
    private LocalDateTime lastUpdated;

}
