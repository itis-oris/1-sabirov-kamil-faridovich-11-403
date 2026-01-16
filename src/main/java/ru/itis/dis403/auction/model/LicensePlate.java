package ru.itis.dis403.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class LicensePlate {
    private Integer id;
    private String number;
    private String region;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private LocalDateTime endDate;
    private String status = "ACTIVE";
    private Integer userCreatorId;

    public void setUserCreatorId(Integer userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public Integer getUserCreatorId() {
        return userCreatorId;
    }

    public String getFullNumber() {
        return number + " " + region;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getRegion() {
        return region;
    }

    public String getNumber() {
        return number;
    }
}