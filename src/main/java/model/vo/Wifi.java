package model.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

@Setter
@Getter
public class Wifi {
    private Integer id;
    private String managementNumber;
    private String city;
    private String wifiName;
    private String roadAddress;
    private String subAddress;
    private String installFloor;
    private String installType;
    private String installAgency;
    private String serviceType;
    private String networkType;
    private Integer installYear;
    private String LocationType;
    private String connectionEnvironment;
    private Double latitude;
    private Double longitude;
    private Timestamp createdAt;

    public double calculateDistance(double lat2, double lon2) {
        double lat1 = this.latitude;
        double lon1 = this.longitude;
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        BigDecimal bd = new BigDecimal(Double.toString(distance));
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
