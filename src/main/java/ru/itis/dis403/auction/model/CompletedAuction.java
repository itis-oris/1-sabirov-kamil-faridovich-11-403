package ru.itis.dis403.auction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompletedAuction {
    private LicensePlate plate;
    private String winnerUsername;
    private BigDecimal winningAmount;

    public CompletedAuction(LicensePlate plate, String winnerUsername, BigDecimal winningAmount) {
        this.plate = plate;
        this.winnerUsername = winnerUsername;
        this.winningAmount = winningAmount;
    }

    public LicensePlate getPlate() {
        return plate;
    }

    public String getWinnerUsername() {
        return winnerUsername;
    }

    public BigDecimal getWinningAmount() {
        return winningAmount;
    }

    public String getNumber() {
        return plate.getNumber();
    }

    public String getRegion() {
        return plate.getRegion();
    }

    public String getDescription() {
        return plate.getDescription();
    }

    public BigDecimal getCurrentPrice() {
        return plate.getCurrentPrice();
    }

    public LocalDateTime getEndDate() {
        return plate.getEndDate();
    }

    public Integer getId() {
        return plate.getId();
    }
}