package top.thesky341.bbsforum.dto;

import top.thesky341.bbsforum.dto.groups.CommentList;
import top.thesky341.bbsforum.dto.groups.PaginationWithCategory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author hz
 * @date 2020/12/11
 */
public class PaginationDto {
    @NotNull(message = "每页大小必须存在")
    @Min(value = 1, message = "每页大小不能小于1")
    private int pageSize;
    @NotNull(message = "所在页数必须存在")
    @Min(value = 1, message = "所在页数必须大于1")
    private int position;
    @NotNull(message = "分类不允许为空", groups = {PaginationWithCategory.class})
    private int categoryId = -1;
    @NotNull(message = "关联的帖子必须存在", groups = {CommentList.class})
    private int postId = -1;

    public PaginationDto() {
    }

    public PaginationDto(@NotNull(message = "每页大小必须存在") @Min(value = 1, message = "每页大小不能小于1") int pageSize, @NotNull(message = "所在页数必须存在") @Min(value = 1, message = "所在页数必须大于1") int position, @NotNull(message = "分类不允许为空", groups = {PaginationWithCategory.class}) int categoryId) {
        this.pageSize = pageSize;
        this.position = position;
        this.categoryId = categoryId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "PaginationDto{" +
                "pageSize=" + pageSize +
                ", position=" + position +
                ", categoryId=" + categoryId +
                ", postId=" + postId +
                '}';
    }
}
