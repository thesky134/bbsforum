package top.thesky341.bbsforum.dto;

/**
 * @author thesky
 * @date 2020/12/22
 */
public class RequestAnswerDto {
    int postId;
    int commentId;

    public RequestAnswerDto() {
    }

    public RequestAnswerDto(int postId, int commentId) {
        this.postId = postId;
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "PostAnswerDto{" +
                "postId=" + postId +
                ", commentId=" + commentId +
                '}';
    }
}
