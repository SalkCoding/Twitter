package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FollowService {

    private final Connection connection;

    public FollowService(Connection connection) {
        this.connection = connection;
    }

    public boolean isAlreadyFollowed(String targetId, String followerId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from `follow` " +
                            "where `target_id`=? and `follower_id`=?");
            statement.setString(1, targetId);
            statement.setString(2, followerId);
            statement.execute();

            ResultSet set = statement.getResultSet();
            return set.next();
        } catch (SQLException e) {
            return true;
        }
    }

    public void addFollower(String targetId, String followerId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `follow` values(?,?)"
            );
            statement.setString(1, targetId);
            statement.setString(2, followerId);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public void removeFollower(String targetId, String followerId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE from `follow` where `target_id`=?"
            );
            statement.setString(1, targetId);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public List<String> getFollowerList(String userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from `follow` where `target_id`=?"
            );
            statement.setString(1, userId);
            statement.execute();

            ResultSet set = statement.getResultSet();
            List<String> followerList = new ArrayList<>();
            while (set.next()) {
                followerList.add(set.getString(1));
            }
            return followerList;
        } catch (SQLException e) {
            return null;
        }
    }
}
