package model.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LocationHistory {
    private Integer id;
    private Double latitude;
    private Double longitude;
    private Timestamp createdAt;
}
