package model.dao;

import model.vo.Bookmark;
import util.DataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDao {
    private DataSourceManager dataSourceManager;

    public void addBookmark(int wifiId, int groupId) {
        String INSERT_BOOKMARK_SQL = "INSERT INTO bookmark (wifi_id, group_id) VALUES (?, ?)";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKMARK_SQL)) {
            preparedStatement.setInt(1, wifiId);
            preparedStatement.setInt(2, groupId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bookmark> getAllBookmarks() {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String SELECT_ALL_BOOKMARKS_SQL = "SELECT b.id, b.group_id, g.group_name, w.id as wifi_id, w.wifi_name, b.created_at " +
                "FROM bookmark b " +
                "JOIN bookmark_group g ON b.group_id = g.id " +
                "JOIN wifi w ON b.wifi_id = w.id";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOKMARKS_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getInt("id"));
                bookmark.setBookmarkGroupId(rs.getInt("group_id"));
                bookmark.setBookmarkGroupName(rs.getString("group_name"));
                bookmark.setWifiId(rs.getInt("wifi_id"));
                bookmark.setWifiName(rs.getString("wifi_name"));
                bookmark.setCreatedAt(rs.getTimestamp("created_at"));
                bookmarkList.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarkList;
    }

    public void deleteBookmark(int bookmarkId) {
        String DELETE_BOOKMARK_SQL = "DELETE FROM bookmark WHERE id = ?";
        try (Connection connection = dataSourceManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOKMARK_SQL)) {
            preparedStatement.setInt(1, bookmarkId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
