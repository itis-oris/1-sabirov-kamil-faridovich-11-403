package ru.itis.dis403.auction.dao;

import ru.itis.dis403.auction.model.Bid;
import ru.itis.dis403.auction.model.LicensePlate;
import ru.itis.dis403.auction.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidDao {
    public void save(Bid bid) {
        String sql = "INSERT INTO bids (user_id, plate_id, amount, timestamp) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bid.getUserId());
            ps.setInt(2, bid.getPlateId());
            ps.setBigDecimal(3, bid.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(bid.getTimestamp()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bid.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving bid: " + e.getMessage(), e);
        }
    }
    public List<Bid> findByPlateId(int plateId) {
        List<Bid> bids = new ArrayList<>();
        String sql = "SELECT b.*, u.username FROM bids b " +
                "JOIN users u ON b.user_id = u.id " +
                "WHERE b.plate_id = ? ORDER BY b.amount DESC, b.timestamp DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, plateId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bids.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bids;
    }

    public Bid findHighestBidForPlate(int plateId) {
        String sql = "SELECT b.*, u.username FROM bids b " +
                "JOIN users u ON b.user_id = u.id " +
                "WHERE b.plate_id = ? ORDER BY b.amount DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, plateId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Bid> findByUserId(int userId) {
        List<Bid> bids = new ArrayList<>();
        String sql = "SELECT b.*, u.username FROM bids b " +
                "JOIN users u ON b.user_id = u.id " +
                "WHERE b.user_id = ? ORDER BY b.timestamp DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bids.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bids;
    }

    private Bid map(ResultSet rs) throws SQLException {
        Bid bid = new Bid();
        bid.setId(rs.getInt("id"));
        bid.setUserId(rs.getInt("user_id"));
        bid.setPlateId(rs.getInt("plate_id"));
        bid.setAmount(rs.getBigDecimal("amount"));
        bid.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return bid;
    }

    public List<Object[]> findCompletedPlatesWithWinners() {
        List<Object[]> results = new ArrayList<>();
        String sql = "SELECT lp.*, u.username as winner_username, b.amount as winning_amount " +
                "FROM license_plates lp " +
                "LEFT JOIN bids b ON b.id = (" +
                "    SELECT id FROM bids WHERE plate_id = lp.id ORDER BY amount DESC LIMIT 1" +
                ") " +
                "LEFT JOIN users u ON b.user_id = u.id " +
                "WHERE lp.status = 'ACTIVE' AND lp.end_date <= NOW() " +
                "ORDER BY lp.end_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LicensePlate plate = mapLicensePlate(rs);
                String winnerUsername = rs.getString("winner_username");
                BigDecimal winningAmount = rs.getBigDecimal("winning_amount");
                results.add(new Object[]{plate, winnerUsername, winningAmount});
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding completed plates with winners", e);
        }
        return results;
    }

    private LicensePlate mapLicensePlate(ResultSet rs) throws SQLException {
        LicensePlate plate = new LicensePlate();
        plate.setId(rs.getInt("id"));
        plate.setNumber(rs.getString("number"));
        plate.setRegion(rs.getString("region"));
        plate.setDescription(rs.getString("description"));
        plate.setStartingPrice(rs.getBigDecimal("starting_price"));
        plate.setCurrentPrice(rs.getBigDecimal("current_price"));
        Timestamp timestamp = rs.getTimestamp("end_date");
        if (timestamp != null) {
            plate.setEndDate(timestamp.toLocalDateTime());
        }
        plate.setStatus(rs.getString("status"));
        return plate;
    }
}