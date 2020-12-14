package top.thesky341.bbsforum.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author thesky
 * @date 2020/12/14
 */
public class PostDto {
    private int id;
    @NotNull(message = "标题必须存在")
    @Length(min = 3, max = 60, message = "标题长度应该在3至60之间")
    private String title;
    @NotNull(message = "帖子内容必须存在")
    @Length(min = 1, max = 3000, message = "帖子内容长度应该在1至3000之间")
    private String content;
    @NotNull(message = "帖子分类必须存在")
    private String category;
    private boolean hidden = false;
    private int reward = 0;

    public PostDto() {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}
