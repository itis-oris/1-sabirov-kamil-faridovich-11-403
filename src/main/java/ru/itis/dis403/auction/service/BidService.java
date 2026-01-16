package ru.itis.dis403.auction.service;

import ru.itis.dis403.auction.dao.BidDao;
import ru.itis.dis403.auction.dao.LicensePlateDao;
import ru.itis.dis403.auction.model.Bid;
import ru.itis.dis403.auction.model.CompletedAuction;
import ru.itis.dis403.auction.model.LicensePlate;
import ru.itis.dis403.auction.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BidService {
    private BidDao bidDao;
    private LicensePlateDao plateDao;

    public BidService(BidDao bidDao, LicensePlateDao plateDao) {
        this.bidDao = bidDao;
        this.plateDao = plateDao;
    }

    public boolean placeBid(int plateId, BigDecimal amount, User user) {
        LicensePlate plate = plateDao.findById(plateId);

        if (!"ACTIVE".equals(plate.getStatus()) || plate.getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Аукцион завершен или лот неактивен");
        }

        if (amount.compareTo(plate.getCurrentPrice()) <= 0) {
            throw new IllegalArgumentException("Ставка должна быть выше текущей цены");
        }

        if (amount.compareTo(plate.getStartingPrice()) < 0) {
            throw new IllegalArgumentException("Ставка не может быть ниже начальной цены");
        }

        Bid bid = new Bid();
        bid.setUserId(user.getId());
        bid.setPlateId(plateId);
        bid.setAmount(amount);
        bid.setTimestamp(LocalDateTime.now());

        bidDao.save(bid);

        plateDao.updatePrice(plateId, amount);

        return true;
    }

    public List<Bid> getBidHistoryForPlate(int plateId) {
        return bidDao.findByPlateId(plateId);
    }

    public List<Bid> getUserBids(int userId) {
        return bidDao.findByUserId(userId);
    }

    public Bid getHighestBidForPlate(int plateId) {
        return bidDao.findHighestBidForPlate(plateId);
    }

    public List<CompletedAuction> getCompletedAuctions() {
        List<Object[]> results = bidDao.findCompletedPlatesWithWinners();
        List<CompletedAuction> completedAuctions = new ArrayList<>();

        for (Object[] result : results) {
            LicensePlate plate = (LicensePlate) result[0];
            String winnerUsername = (String) result[1];
            BigDecimal winningAmount = (BigDecimal) result[2];

            completedAuctions.add(new CompletedAuction(plate, winnerUsername, winningAmount));
        }

        return completedAuctions;
    }
}