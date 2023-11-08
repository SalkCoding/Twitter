package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FollowService {

    private final Connection connection;

    private PreparedStatement statement;
    private ResultSet resultSet;

    public FollowService(Connection connection) {
        this.connection = connection;
    }

    public boolean isAlreadyFollowed(String targetId, String followerId) {
        try {
            statement = connection.prepareStatement(
                    "select * from `follow` " +
                            "where `target_id`=? and `follower_id`=?");
            statement.setString(1, targetId);
            statement.setString(2, followerId);
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return true;
        } finally {
            try {
                if (!resultSet.isClosed()) resultSet.close();
                if (!statement.isClosed()) statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addFollower(String targetId, String followerId) {
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO `follow` values(?,?)"
            );
            statement.setString(1, targetId);
            statement.setString(2, followerId);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        } finally {
            try {
                if (!statement.isClosed()) statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeFollower(String targetId, String followerId) {
        try {
            statement = connection.prepareStatement(
                    "DELETE from `follow` where `target_id`=? and `follower_id`=?"
            );
            statement.setString(1, targetId);
            statement.setString(2, followerId);
            statement.executeUpdate();
        } catch (SQLException ignored) {
        } finally {
            try {
                if (!statement.isClosed()) statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> getFollowerList(String userId) {
        try {
            statement = connection.prepareStatement(
                    "select * from `follow` where `target_id`=?"
            );
            statement.setString(1, userId);
            resultSet = statement.executeQuery();
            List<String> followerList = new ArrayList<>();
            while (resultSet.next()) {
                followerList.add(resultSet.getString(1));
            }
            return followerList;
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                if (!resultSet.isClosed()) resultSet.close();
                if (!statement.isClosed()) statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
