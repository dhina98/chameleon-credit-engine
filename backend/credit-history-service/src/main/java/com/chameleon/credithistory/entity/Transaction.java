package com.chameleon.credithistory.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String category; // e.g., "food", "travel", "groceries", "fuel"
    private Double amount;
    private LocalDate date;
    private Boolean isSeasonal; // true during seasons like Big Billion Days, Thanksgiving

    // Constructors
    public Transaction() {}

    public Transaction(Long userId, String category, Double amount, LocalDate date, Boolean isSeasonal) {
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.isSeasonal = isSeasonal;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getIsSeasonal() {
        return isSeasonal;
    }

    public void setIsSeasonal(Boolean isSeasonal) {
        this.isSeasonal = isSeasonal;
    }
}
