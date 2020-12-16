package top.thesky341.bbsforum.entity;

import org.hibernate.validator.constraints.Length;
import top.thesky341.bbsforum.dto.PostDto;
import top.thesky341.bbsforum.entity.groups.Login;
import top.thesky341.bbsforum.entity.groups.Register;
import top.thesky341.bbsforum.entity.groups.UpdateUsername;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/8
 */
public class Post {
    private int id;

    private String title;
    private String content;
    private Date createTime;
    private Date modifyTime;
    private User user;
    private Category category;
    private boolean hidden;
    private boolean deleted;
    private boolean excellent;
    private boolean top;
    private int reward;

    public Post() {
    }

    public Post(PostDto postDto, User user, Category category) {
        this.id = postDto.getId();
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.user = user;
        this.category = category;
        this.reward = postDto.getReward();
        this.hidden = postDto.isHidden();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isExcellent() {
        return excellent;
    }

    public void setExcellent(boolean excellent) {
        this.excellent = excellent;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", user=" + user +
                ", category=" + category +
                ", hidden=" + hidden +
                ", deleted=" + deleted +
                ", excellent=" + excellent +
                ", top=" + top +
                ", reward=" + reward +
                '}';
    }
}
