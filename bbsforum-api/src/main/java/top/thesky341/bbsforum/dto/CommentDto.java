package top.thesky341.bbsforum.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author thesky
 * @date 2020/12/16
 */
public class CommentDto {
    private int id = -1;
    @NotNull(message = "评论内容必须存在")
    @Length(min = 1, max = 1000, message = "帖子内容长度应该在1至1000之间")
    private String content;
    private int postId = -1;
    private int userId = -1;

    public CommentDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", postId=" + postId +
                ", userId=" + userId +
                '}';
    }
}
