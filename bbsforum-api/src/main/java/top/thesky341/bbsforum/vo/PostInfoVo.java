package top.thesky341.bbsforum.vo;

import top.thesky341.bbsforum.entity.Post;

import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/11
 */
public class PostInfoVo {
    private int id;
    private String picture;
    private String title;
    private String category;
    private int commentSum;
    private int visitSum;
    private Date createTime;
    private boolean excellent;
    private boolean top;

    public PostInfoVo() {
    }

    public PostInfoVo(Post post, int commentSum, int visitSum) {
        id = post.getId();
        picture = post.getUser().getPicture();
        title = post.getTitle();
        category = post.getCategory().getName();
        this.commentSum = commentSum;
        this.visitSum = visitSum;
        createTime = post.getCreateTime();
        excellent = post.isExcellent();
        top = post.isTop();
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

    @Override
    public String toString() {
        return "PostInfoVo{" +
                "picture='" + picture + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", commentSum=" + commentSum +
                ", visitSum=" + visitSum +
                ", createTime=" + createTime +
                ", excellent=" + excellent +
                ", top=" + top +
                '}';
    }
}
