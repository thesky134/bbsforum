package top.thesky341.bbsforum.entity;

/**
 * @author thesky
 * @date 2020/12/22
 */
public class RequestAnswer {
    private int id;
    private Post post;
    private Comment comment;

    public RequestAnswer() {
    }

    public RequestAnswer(int id, Post post, Comment comment) {
        this.id = id;
        this.post = post;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "RequestAnswer{" +
                "id=" + id +
                ", post=" + post +
                ", comment=" + comment +
                '}';
    }
}
