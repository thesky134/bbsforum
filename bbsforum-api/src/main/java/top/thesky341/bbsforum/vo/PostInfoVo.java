package top.thesky341.bbsforum.vo;

import top.thesky341.bbsforum.entity.Post;

import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/11
 * 对帖子信息进行了封装，不包含帖子内容
 * 帖子信息和具体的帖子不一样
 * 前者用户主页或者具体分类下的帖子列表展示
 * 后者用于查看帖子
 */
public class PostInfoVo {
    private int id;
    /**
     * 帖子作者的头像路径
     */
    private String picture;
    private String user;
    private int userId;
    private String title;
    private String category;
    private int commentSum;
    private int visitSum;
    private Date createTime;
    private Date modifyTime;
    private boolean excellent;
    private boolean top;
    private boolean hidden;

    public PostInfoVo() {
    }

    public PostInfoVo(Post post, int commentSum, int visitSum) {
        id = post.getId();
        picture = post.getUser().getPicture();
        title = post.getTitle();
        category = post.getCategory().getName();
        this.commentSum = commentSum;
        this.visitSum = visitSum;
        this.user = post.getUser().getUsername();
        this.userId = post.getUser().getId();
        createTime = post.getCreateTime();
        modifyTime = post.getModifyTime();
        excellent = post.isExcellent();
        top = post.isTop();
        hidden = post.isHidden();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(int commentSum) {
        this.commentSum = commentSum;
    }

    public int getVisitSum() {
        return visitSum;
    }

    public void setVisitSum(int visitSum) {
        this.visitSum = visitSum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostInfoVo{" +
                "id=" + id +
                ", picture='" + picture + '\'' +
                ", user='" + user + '\'' +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", commentSum=" + commentSum +
                ", visitSum=" + visitSum +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", excellent=" + excellent +
                ", top=" + top +
                ", hidden=" + hidden +
                '}';
    }
}
