package model.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Bookmark {
    private Integer id;
    private Integer wifiId;
    private Integer bookmarkGroupId;
    private String bookmarkGroupName;
    private String wifiName;
    private Timestamp createdAt;
}
