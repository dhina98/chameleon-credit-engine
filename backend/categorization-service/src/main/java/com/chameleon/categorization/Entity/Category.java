package com.chameleon.categorization.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category {
    @Id
    private String name;
    private String displayName;
    private double rewardsMultiplier;

}
