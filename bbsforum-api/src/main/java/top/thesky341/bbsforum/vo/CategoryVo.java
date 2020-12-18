package top.thesky341.bbsforum.vo;

import top.thesky341.bbsforum.entity.Category;

import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/18
 */
public class CategoryVo {
    private int id = -1;
    private String name;
    private String introduction;
    private Date createTime;
    private Date modifyTime;
    private int sum;

    public CategoryVo() {
    }

    public CategoryVo(Category category, int sum) {
        this.id = category.getId();
        this.name = category.getName();
        this.introduction = category.getIntroduction();
        this.createTime = category.getCreateTime();
        this.modifyTime = category.getModifyTime();
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "CategoryVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", sum=" + sum +
                '}';
    }
}
