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
    private Integer categoryId;
    private boolean top;

    public Pagination() {
    }

    public Pagination(int from, int num, Integer categoryId, boolean top) {
        this.from = from;
        this.num = num;
        this.categoryId = categoryId;
        this.top = top;
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

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "from=" + from +
                ", num=" + num +
                ", categoryId=" + categoryId +
                ", top=" + top +
                '}';
    }
}
