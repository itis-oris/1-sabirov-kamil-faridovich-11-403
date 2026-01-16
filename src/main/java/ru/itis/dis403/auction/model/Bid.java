package ru.itis.dis403.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bid {
    private Integer id;
    private Integer userId;
    private Integer plateId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {}

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getPlateId() {
        return plateId;
    }

    public Integer getUserId() {
        return userId;
    }
}