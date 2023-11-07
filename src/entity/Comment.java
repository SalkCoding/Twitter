package entity;

public record Comment(int commentId, int postId, String writerId, String content) {
}
