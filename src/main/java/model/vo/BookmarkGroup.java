package model.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BookmarkGroup {
    private Integer id;
    private String groupName;
    private Integer sequence;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
