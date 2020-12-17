package top.thesky341.bbsforum.entity;

/**
 * @author thesky
 * @date 2020/12/8
 */
public class UserCommentState {
    private int id;
    private User user;
    private Comment comment;
    private int state;

    public UserCommentState() {
    }

    public UserCommentState(User user, Comment comment, int state) {
        this.user = user;
        this.comment = comment;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserCommentState{" +
                "id=" + id +
                ", user=" + user +
                ", comment=" + comment +
                ", state=" + state +
                '}';
    }
}
