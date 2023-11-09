//For scalability
public enum Option {
    WRITE_POST,
    READ_POST,
    LIKE_POST,
    LIKE_COMMENT,
    FOLLOW_USER,
    SEE_MY_FOLLOWERS,
    BLOCK_USER,
    SHOW_ACTIVITY,
    EXIT;

    public static Option getOption(String value) {
        try {
            int idx = Integer.parseInt(value);
            return switch (idx) {
                case 1, 2, 3, 4, 5, 6, 7, 8 -> Option.values()[idx - 1];
                default -> null;
            };
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return switch (this) {
            case WRITE_POST -> "Write a post.";
            case READ_POST -> "Read a post.";
            case LIKE_POST -> "Leave a like on the post.";
            case LIKE_COMMENT -> "Leave a like on the comment.";
            case FOLLOW_USER -> "Follow the user.";
            case SEE_MY_FOLLOWERS -> "See my followers list.";
            case BLOCK_USER -> "Block the user.";
            case SHOW_ACTIVITY -> "Show my activity.";
            case EXIT -> "Exit.";
        };
    }
}
