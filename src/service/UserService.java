package service;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserService {

    private final Connection connection;

    private PreparedStatement statement;
    private ResultSet resultSet;

    public UserService(Connection connection) {
        this.connection = connection;
    }

    public boolean isValidUser(String id, String pw) {
        return getUser(id, pw) != null;
    }

    public User createUser(String name, String id, String pw) {
        try {
            statement = connection.prepareStatement("insert into `user` values (?,?,?,?)");
            statement.setString(1, id);
            statement.setString(2, pw);
            statement.setString(3, name);
            String lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/ MM/ dd, HH:mm:ss"));
            statement.setString(4, lastLogin);
            statement.executeUpdate();

            return new User(id, pw, name, lastLogin);
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                if (!statement.isClosed()) statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public User getUser(String id, String pw) {
        try {
            statement = connection.prepareStatement("select * from `user` " +
                    "where `user_id`=? and `password`=?");
            statement.setString(1, id);
            statement.setString(2, pw);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            } else return null;
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

    public boolean isDuplicatedUserId(String id) {
        return isValidUserId(id);
    }

    public boolean isValidUserId(String id) {
        try {
            statement = connection.prepareStatement("select * from `user` where `user_id`=?");
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            return false;
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
