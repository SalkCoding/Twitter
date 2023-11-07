package service;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    private final Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
    }

    public boolean isValidUser(String id, String pw) {
        return false;
    }

    public User getUser(String id, String pw) {
        return null;
    }


    public boolean isValidUserId(String id) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from `user` where `user_id`=?");
            statement.setString(1, id);
            statement.execute();

            ResultSet set = statement.getResultSet();
            return set.next();
        } catch (SQLException e) {
            return false;
        }
    }
}
