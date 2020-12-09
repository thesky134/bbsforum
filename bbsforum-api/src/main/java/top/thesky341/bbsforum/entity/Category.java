package top.thesky341.bbsforum.entity;

import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/8
 */
public class Category {
    private int id;
    private String name;
    private String introduction;
    private Date createTime;
    private Date modifyTime;

    public Category() {
    }

    public Category(int id, String name, String introduction, Date createTime, Date modifyTime) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
