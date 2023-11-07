package service;

import java.sql.*;
import java.util.Scanner;

public class LikeService {

    private final Connection connection;

    public LikeService(Connection connection) {
        this.connection = connection;
    }

    public void leaveLikeOnPost(String likerId, int postId) {
        Statement stmt;
        PreparedStatement pstm;
        ResultSet rs1;
        ResultSet rs2;

        try {
            String s3;
            String s4;

            stmt = connection.createStatement();
            String s2 = "select `liker_id` from `post_like` where `liker_id` = " + likerId + " and `post_id` = " + postId;
            rs1 = stmt.executeQuery(s2);

            if(rs1.next()) {
                //if you already liked that post, Delete function will be activated
                System.out.println("Already liked post. Delete like from the post");
                s3 = "delete from `Post_like` where `liker_id` =" + likerId + " and `post_id` = " + postId;

                pstm = connection.prepareStatement(s3);
                pstm.execute(s3);
            }
            else {
                //if you didn't make a like to the post, then your total likes count++
                //and insert new value for post_like table
                s3 = "insert into `Post_like` values ('" + likerId + "', '" + postId + "')";

                pstm = connection.prepareStatement(s3);
                pstm.executeUpdate();

                s4 = "select count(*) from `post_like` where `liker_id` = " + likerId;
                rs2 = stmt.executeQuery(s4);

                //here is showing the total likes that you made
                if(rs2.next()) {
                    int like_count = rs2.getInt(1);
                    if(like_count == 1)
                        System.out.println("Your total like is 1");
                    else
                        System.out.println("Your total likes are " + like_count);
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
