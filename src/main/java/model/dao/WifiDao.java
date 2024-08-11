package model.dao;

import model.vo.Wifi;
import util.DataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiDao {
    private DataSourceManager dataSourceManager;

    public void save(Wifi wifi) {
        String INSERT_WIFI_SQL = "INSERT INTO wifi (management_number, city, wifi_name, road_address, sub_address, install_floor, install_type, install_agency, service_type, network_type, install_year, location_type, connection_environment, latitude, longitude, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement((INSERT_WIFI_SQL), Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, wifi.getManagementNumber());
            preparedStatement.setString(2, wifi.getCity());
            preparedStatement.setString(3, wifi.getWifiName());
            preparedStatement.setString(4, wifi.getRoadAddress());
            preparedStatement.setString(5, wifi.getSubAddress());
            preparedStatement.setString(6, wifi.getInstallFloor());
            preparedStatement.setString(7, wifi.getInstallType());
            preparedStatement.setString(8, wifi.getInstallAgency());
            preparedStatement.setString(9, wifi.getServiceType());
            preparedStatement.setString(10, wifi.getNetworkType());
            preparedStatement.setInt(11, wifi.getInstallYear());
            preparedStatement.setString(12, wifi.getLocationType());
            preparedStatement.setString(13, wifi.getConnectionEnvironment());
            preparedStatement.setDouble(14, wifi.getLatitude());
            preparedStatement.setDouble(15, wifi.getLongitude());
            preparedStatement.setTimestamp(16, wifi.getCreatedAt());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    wifi.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating wifi entry failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Wifi getWifiById(Integer id) {
        String SELECT_WIFI_SQL = "SELECT * FROM wifi WHERE id = ?";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WIFI_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Wifi wifi = new Wifi();
                wifi.setId(rs.getInt("id"));
                wifi.setManagementNumber(rs.getString("management_number"));
                wifi.setCity(rs.getString("city"));
                wifi.setWifiName(rs.getString("wifi_name"));
                wifi.setRoadAddress(rs.getString("road_address"));
                wifi.setSubAddress(rs.getString("sub_address"));
                wifi.setInstallFloor(rs.getString("install_floor"));
                wifi.setInstallType(rs.getString("install_type"));
                wifi.setInstallAgency(rs.getString("install_agency"));
                wifi.setServiceType(rs.getString("service_type"));
                wifi.setNetworkType(rs.getString("network_type"));
                wifi.setInstallYear(rs.getInt("install_year"));
                wifi.setLocationType(rs.getString("location_type"));
                wifi.setConnectionEnvironment(rs.getString("connection_environment"));
                wifi.setLatitude(rs.getDouble("latitude"));
                wifi.setLongitude(rs.getDouble("longitude"));
                wifi.setCreatedAt(rs.getTimestamp("created_at"));
                return wifi;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Wifi> findAll() {
        List<Wifi> wifiList = new ArrayList<>();
        String SELECT_ALL_WIFI_SQL = "SELECT * FROM wifi";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WIFI_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Wifi wifi = new Wifi();
                wifi.setId(rs.getInt("id"));
                wifi.setManagementNumber(rs.getString("management_number"));
                wifi.setCity(rs.getString("city"));
                wifi.setWifiName(rs.getString("wifi_name"));
                wifi.setRoadAddress(rs.getString("road_address"));
                wifi.setSubAddress(rs.getString("sub_address"));
                wifi.setInstallFloor(rs.getString("install_floor"));
                wifi.setInstallType(rs.getString("install_type"));
                wifi.setInstallAgency(rs.getString("install_agency"));
                wifi.setServiceType(rs.getString("service_type"));
                wifi.setNetworkType(rs.getString("network_type"));
                wifi.setInstallYear(rs.getInt("install_year"));
                wifi.setLocationType(rs.getString("location_type"));
                wifi.setConnectionEnvironment(rs.getString("connection_environment"));
                wifi.setLatitude(rs.getDouble("latitude"));
                wifi.setLongitude(rs.getDouble("longitude"));
                wifi.setCreatedAt(rs.getTimestamp("created_at"));
                wifiList.add(wifi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wifiList;
    }

}
