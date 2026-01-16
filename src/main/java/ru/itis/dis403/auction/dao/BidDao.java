package ru.itis.dis403.auction.dao;

import ru.itis.dis403.auction.config.DatabaseConfig;
import ru.itis.dis403.auction.model.Bid;
import java.sql.*;

public class BidDao {
    public void save(Bid bid) {
        String sql = "INSERT INTO bids (user_id, plate_id, amount) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = DatabaseConfig.prepare(conn, sql, bid.getUserId(), bid.getPlateId(), bid.getPrice());
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) bid.setId(rs.getInt(1));
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}