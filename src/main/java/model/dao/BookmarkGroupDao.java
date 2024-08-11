package model.dao;

import model.vo.BookmarkGroup;
import util.DataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupDao {
    private DataSourceManager dataSourceManager;

    public void addGroup(String groupName, int sequence) {
        String sql = "INSERT INTO bookmark_group (group_name, sequence) VALUES (?, ?)";
        try (Connection conn = dataSourceManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            pstmt.setInt(2, sequence);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGroup(int id, String groupName, int sequence) {
        String sql = "UPDATE bookmark_group SET group_name = ?, sequence = ?, updated_at = NOW() WHERE id = ?";
        try (Connection conn = dataSourceManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            pstmt.setInt(2, sequence);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGroup(int id) {
        String sql = "DELETE FROM bookmark_group WHERE id = ?";
        try (Connection conn = dataSourceManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BookmarkGroup getGroupById(int id) {
        BookmarkGroup group = null;
        String sql = "SELECT * FROM bookmark_group WHERE id = ?";

        try (Connection conn = dataSourceManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    group = new BookmarkGroup();
                    group.setId(rs.getInt("id"));
                    group.setGroupName(rs.getString("group_name"));
                    group.setSequence(rs.getInt("sequence"));
                    group.setCreatedAt(rs.getTimestamp("created_at"));
                    group.setUpdatedAt(rs.getTimestamp("updated_at"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return group;
    }

    public void addBookmarkToGroup(int groupId, int wifiId) {
        String sql = "INSERT INTO bookmark (group_id, wifi_id) VALUES (?, ?)";
        try (Connection conn = dataSourceManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, groupId);
            pstmt.setInt(2, wifiId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BookmarkGroup> getAllGroups() {
        List<BookmarkGroup> groupList = new ArrayList<>();
        String sql = "SELECT * FROM bookmark_group ORDER BY sequence ASC";
        try (Connection conn = dataSourceManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                BookmarkGroup group = new BookmarkGroup();
                group.setId(rs.getInt("id"));
                group.setGroupName(rs.getString("group_name"));
                group.setSequence(rs.getInt("sequence"));
                group.setCreatedAt(rs.getTimestamp("created_at"));
                group.setUpdatedAt(rs.getTimestamp("updated_at"));
                groupList.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }
}
