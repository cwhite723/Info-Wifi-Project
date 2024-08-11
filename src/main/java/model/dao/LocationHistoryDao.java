package model.dao;

import model.vo.LocationHistory;
import util.DataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationHistoryDao {
    private DataSourceManager dataSourceManager;

    public void saveLocationHistory(double latitude, double longitude) {
        String INSERT_LOCATION_SQL = "INSERT INTO location_history (latitude, longitude) VALUES (?, ?)";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOCATION_SQL)) {
            preparedStatement.setDouble(1, latitude);
            preparedStatement.setDouble(2, longitude);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LocationHistory> getLocationHistory() {
        List<LocationHistory> locationHistoryList = new ArrayList<>();
        String SELECT_LOCATION_SQL = "SELECT * FROM location_history ORDER BY id DESC";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOCATION_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                LocationHistory locationHistory = new LocationHistory();
                locationHistory.setId(rs.getInt("id"));
                locationHistory.setLatitude(rs.getDouble("latitude"));
                locationHistory.setLongitude(rs.getDouble("longitude"));
                locationHistory.setCreatedAt(rs.getTimestamp("created_at"));
                locationHistoryList.add(locationHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationHistoryList;
    }

    public void deleteLocationHistory(int id) {
        String DELETE_HISTORY_SQL = "DELETE FROM location_history WHERE id = ?";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HISTORY_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
