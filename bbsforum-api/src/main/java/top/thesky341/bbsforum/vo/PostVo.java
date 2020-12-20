package top.thesky341.bbsforum.vo;

import top.thesky341.bbsforum.entity.Post;

import java.util.Date;
//
/**
 * @author thesky
 * @date 2020/12/11
 */
public class PostVo {
    private int id;
    private String title;
    private String content;
    private Date createDate;
    private Date modifyDate;
    private String user;
    private String category;
    private boolean excellent;
    private boolean top;
    private int VisitSum;
    private int CommentSum;
    private int goodSum;
    private int badSum;
    private int likeSum;
    private boolean good;
    private boolean bad;
    private boolean like;
    private int reward;

    public PostVo() {
    }

    public void parsePost(Post post) {
        setId(post.getId());
        setTitle(post.getTitle());
        setContent(post.getContent());
        setCreateDate(post.getCreateTime());
        setModifyDate(post.getModifyTime());
        setUser(post.getUser().getUsername());
        setCategory(post.getCategory().getName());
        setExcellent(post.isExcellent());
        setTop(post.isTop());
        setReward(post.getReward());
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getVisitSum() {
        return VisitSum;
    }

    public void setVisitSum(int visitSum) {
        VisitSum = visitSum;
    }

    public int getCommentSum() {
        return CommentSum;
    }

    public void setCommentSum(int commentSum) {
        CommentSum = commentSum;
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
        return "PostVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", user='" + user + '\'' +
                ", category='" + category + '\'' +
                ", excellent=" + excellent +
                ", top=" + top +
                ", VisitSum=" + VisitSum +
                ", CommentSum=" + CommentSum +
                ", goodSum=" + goodSum +
                ", badSum=" + badSum +
                ", likeSum=" + likeSum +
                ", good=" + good +
                ", bad=" + bad +
                ", like=" + like +
                ", reward=" + reward +
                '}';
    }
}
