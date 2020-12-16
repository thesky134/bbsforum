package top.thesky341.bbsforum.entity;

/**
 * 封装了分页查询文章的开始位置和数量
 * 可以指定查询文章的类别
 * @author hz
 * @date 2020/12/11
 */
public class Pagination {
    private int from;
    private int num;
    private Integer categoryId = -1;
    private Integer userId = -1;
    private Integer postId = -1;
    private Integer top = -1;

    public Pagination() {
    }

    public Pagination(int from, int num) {
        this.from = from;
        this.num = num;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "from=" + from +
                ", num=" + num +
                ", categoryId=" + categoryId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", top=" + top +
                '}';
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
