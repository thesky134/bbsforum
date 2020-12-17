package top.thesky341.bbsforum.vo;

import top.thesky341.bbsforum.entity.User;

import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/16
 */
public class CommentVo {
    private int id;
    private String content;
    private Date createTime;
    private Date modifyTIme;
    private String user;
    private int goodSum;
    private int badSum;
    private int likeSum;
    private boolean good;
    private boolean bad;
    private boolean like;

    public CommentVo() {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTIme() {
        return modifyTIme;
    }

    public void setModifyTIme(Date modifyTIme) {
        this.modifyTIme = modifyTIme;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getGoodSum() {
        return goodSum;
    }

    public void setGoodSum(int goodSum) {
        this.goodSum = goodSum;
    }

    public int getBadSum() {
        return badSum;
    }

    public void setBadSum(int badSum) {
        this.badSum = badSum;
    }

    public int getLikeSum() {
        return likeSum;
    }

    public void setLikeSum(int likeSum) {
        this.likeSum = likeSum;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public boolean isBad() {
        return bad;
    }

    public void setBad(boolean bad) {
        this.bad = bad;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "CommentVo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", modifyTIme=" + modifyTIme +
                ", user=" + user +
                ", goodSum=" + goodSum +
                ", badSum=" + badSum +
                ", likeSum=" + likeSum +
                ", good=" + good +
                ", bad=" + bad +
                ", like=" + like +
                '}';
    }
}
