import config.ConnectionConfig;
import entity.User;
import service.FollowService;
import service.LikeService;
import service.UserService;

import java.sql.*;
import java.util.*;

public class Main {

    private static final boolean DEBUG = true;

    //Connection
    private static final Connection connection = ConnectionConfig.getNewConnection();

    //Services
    private static final UserService userService = new UserService(connection);
    private static final FollowService followService = new FollowService(connection);
    private static final LikeService likeService = new LikeService(connection);

    //Scanner for keyboard input
    private static final Scanner scanner = new Scanner(System.in);

    //HashSet for online users Key: user(user_id)
    private static final Set<String> onlineUserSet = new HashSet<>();

    private static User user;

    public static void main(String[] args) throws SQLException {
        if (!DEBUG) {
            println("You have to sign-in before using!");

            //Sign-in
            String id, pw;

            while (true) {
                println("Enter your ID and PW");

                print("ID: ");
                id = scanner.nextLine();
                print("PW: ");
                pw = scanner.nextLine();

                if (userService.isValidUser(id, pw)) {
                    user = userService.getUser(id, pw);
                    break;
                }

                println("Wrong ID or PW! Enter correctly!\n");
            }
        } else {
            user = new User("1", "1q2w3e4r5t", "Kim", "2023-11-07");
        }

        Option[] options = Option.values();
        while (true) {
            println("Choose one of the options below");
            for (int i = 0; i < options.length; i++) {
                println((i + 1) + ". " + options[i].toString());
            }

            //User nextLine to prevent whitespace be left in keyboard buffer
            print("Choose: ");
            Option selectOption = Option.getOption(scanner.nextLine());
            if (selectOption == null) {
                println("Wrong option! Select correctly!\n");
                continue;
            }

            switch (selectOption) {
                case WRITE_POST -> {
                    print("Write content: ");
                    String content = scanner.nextLine();
                    //Write post
                }
                case READ_POST -> {
                    //Get all posts and show them to user

                    print("Select want to read: ");
                    String wantToRead = scanner.nextLine();
                    //Read post

                    //Check user want to read comments on this post.
                    println("Want to read comments?");

                    //Read comments
                }
                case LIKE_POST -> {
                    //Show posts
                    print("Select the post that want to leave a like: ");
                    try {
                        int wantToLike = Integer.parseInt(scanner.nextLine());

                        //Leave Like on post
                        likeService.leaveLikeOnPost(user.userId(), wantToLike);
                    } catch (NumberFormatException e) {
                        println("Incorrect post id!\n");
                    }
                }
                case LIKE_COMMENT -> {
                    //Show comments
                    print("Select the comment that want to leave a like: ");
                    String wantToLike = scanner.nextLine();

                    //Leave Like on post
                }
                case FOLLOW_USER -> {
                    println("Enter an user id you want to follow!");
                    println("(If you already follow he or she, it follow will be deleted.)");
                    print("User id: ");

                    String targetId = scanner.nextLine();
                    if (!userService.isValidUserId(targetId)) {
                        println("Incorrect user id!\n");
                        continue;
                    }

                    if (followService.isAlreadyFollowed(targetId, user.userId())) {
                        followService.removeFollower(targetId, user.userId());
                        println("Removed follow data.\n");
                    } else {
                        followService.addFollower(targetId, user.userId());
                        println("Add follow data.\n");
                    }
                }
                case BLOCK_USER -> {
                    //Same as a Follow mechanism
                }
                case SHOW_ACTIVITY -> {
                    //Show use his id to show all related data on this user
                }
                case EXIT -> System.exit(0);
            }
        }
    }

    private static void println(String s) {
        System.out.println(s);
    }

    private static void print(String s) {
        System.out.print(s);
    }
}