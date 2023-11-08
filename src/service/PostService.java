package service;

import entity.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostService {

    private final Connection connection;

    private PreparedStatement statement;
    private ResultSet resultSet;

    public PostService(Connection connection) {
        this.connection = connection;
    }

    public void writePost(String writerId, String content) {
        try {
            String s1 = "insert into `post` (`content`, `writer_id`) values (" + content + ", " + writerId + ")";
            statement = connection.prepareStatement(s1);
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

    public List<Post> getPostList() {
        try {
            statement = connection.prepareStatement("select * from `post`");
            resultSet = statement.executeQuery();
            List<Post> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Post(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3)
                        )
                );
            }
            return list;
        } catch (SQLException ignored) {
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

    public Post readPost(String postId) {
        try {
            statement = connection.prepareStatement("select * from `post` " +
                    "where `post_id`=?");
            statement.setString(1, postId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Post(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
            } else return null;
        } catch (SQLException ignored) {
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
