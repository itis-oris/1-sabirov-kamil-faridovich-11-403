package ru.itis.dis403.auction.dao;

import ru.itis.dis403.auction.model.LicensePlate;
import ru.itis.dis403.auction.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LicensePlateDao {
    public List<LicensePlate> findAllActive() {
        List<LicensePlate> list = new ArrayList<>();
        String sql = "SELECT * FROM license_plates WHERE status = 'ACTIVE' AND end_date > NOW()";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public LicensePlate findById(int id) {
        String sql = "SELECT * FROM license_plates WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return null;
    }
    public boolean existsActiveByNumberAndRegion(String number, String region) {
        String sql = "SELECT COUNT(*) FROM license_plates WHERE number = ? AND region = ? AND status = 'ACTIVE' AND end_date > NOW()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, number);
            ps.setString(2, region);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки существования номера", e);
        }
        return false;
    }

    public void save(LicensePlate plate) {
        String sql = plate.getId() == null ?
                "INSERT INTO license_plates (number, region, description, starting_price, end_date, current_price, user_сreator_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id" :
                "UPDATE license_plates SET number=?, region=?, description=?, starting_price=?, end_date=? , user_сreator_id=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             ps.setString(1, plate.getNumber());
             ps.setString(2, plate.getRegion());
             ps.setString(3, plate.getDescription());
             ps.setBigDecimal(4, plate.getStartingPrice());
             LocalDateTime endDate = plate.getEndDate();
             if (endDate != null) {
                ps.setTimestamp(5, Timestamp.valueOf(endDate));
             } else {
                ps.setNull(5, Types.TIMESTAMP);
             }
             ps.setBigDecimal(6, plate.getCurrentPrice());
            ps.setInt(7, plate.getUserCreatorId());
             if (plate.getId() == null) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) plate.setId(rs.getInt(1));
                }
             } else {
                 ps.executeUpdate();
             }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения лота: " + e.getMessage());
        }
    }

    public void updatePrice(int id, java.math.BigDecimal price) {
        String sql = "UPDATE license_plates SET current_price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, price);
            ps.setInt(2, id);
             ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM license_plates WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    private LicensePlate map(ResultSet rs) throws SQLException {
        LicensePlate p = new LicensePlate();
        p.setId(rs.getInt("id"));
        p.setNumber(rs.getString("number"));
        p.setDescription(rs.getString("description"));
        p.setStartingPrice(rs.getObject("starting_price", java.math.BigDecimal.class));
        p.setCurrentPrice(rs.getObject("current_price", java.math.BigDecimal.class));
        Timestamp timestamp = rs.getTimestamp("end_date");
        if (timestamp != null) {
            p.setEndDate(timestamp.toLocalDateTime());
        } else {
            p.setEndDate(null);
        }
        p.setStatus(rs.getString("status"));
        p.setRegion(rs.getString("region"));
        p.setUserCreatorId(rs.getInt("user_сreator_id"));
        return p;
    }

    public List<LicensePlate> findAllActiveByRegion(String region) {
        List<LicensePlate> list = new ArrayList<>();
        String sql = "SELECT * FROM license_plates WHERE status = 'ACTIVE' AND end_date > NOW() AND region = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, region);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public List<LicensePlate> findByCreatorId(int id) {
        List<LicensePlate> list = new ArrayList<>();
        String sql = "SELECT * FROM license_plates WHERE user_сreator_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }

    public void update(LicensePlate plate) {
        String sql = "UPDATE license_plates SET number=?, region=?, description=?, starting_price=?, current_price=?, end_date=?, user_сreator_id=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plate.getNumber());
            ps.setString(2, plate.getRegion());
            ps.setString(3, plate.getDescription());
            ps.setBigDecimal(4, plate.getStartingPrice());
            ps.setBigDecimal(5, plate.getCurrentPrice());

            LocalDateTime endDate = plate.getEndDate();
            if (endDate != null) {
                ps.setTimestamp(6, Timestamp.valueOf(endDate));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }

            ps.setInt(7, plate.getUserCreatorId());
            ps.setInt(8, plate.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления лота: " + e.getMessage(), e);
        }
    }
}