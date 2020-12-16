package top.thesky341.bbsforum.entity;

/**
 * @author thesky
 * @date 2020/12/8
 */
public class UserPostState {
    private int id;
    private User user;
    private Post post;
    private int state;

    public UserPostState() {
    }

    public UserPostState(Post post, User user, int state) {
        this.id = id;
        this.user = user;
        this.post = post;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UserPostState{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                ", state=" + state +
                '}';
    }
}
