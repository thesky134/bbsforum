package top.thesky341.bbsforum.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author thesky
 * @date 2020/12/16
 */
public class CommentDto {
    private int id;
    @NotNull(message = "评论内容必须存在")
    @Length(min = 1, max = 1000, message = "帖子内容长度应该在1至1000之间")
    private String content;
    @NotNull(message = "被评论的帖子必须存在")
    private int postId;

    public CommentDto() {
    }

    public CommentDto(int id, @NotNull(message = "评论内容必须存在") @Length(min = 1, max = 1000, message = "帖子内容长度应该在1至1000之间") String content, @NotNull(message = "帖子id必须存在") int postId) {
        this.id = id;
        this.content = content;
        this.postId = postId;
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

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", postId=" + postId +
                '}';
    }
}
