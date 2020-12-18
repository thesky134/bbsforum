package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.dto.PaginationDto;
import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.Post;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Repository
@Mapper
public interface PostMapper {
    void addPost(Post post);
    void batchAddPost(Post post);
    void revisePost(Post posr);
    void deletePostById(int id);
    void updatePostHiddenState(@Param("id") int id, @Param("state") int state);
    void setPostDeleted(int id);
    Post getPostById(int id);
    List<Post> getPostByUserId(int userId);

    /**
     * 获取帖子数量，可以用于主页，具体分类的帖子列表，用户帖子列表
     * @param categoryId 分类，不指定时置为为-1
     * @param userId 用户，不指定时置为-1
     * @return
     */
    int getPostSum(int categoryId, int userId);
    int getPostTopSum(int categoryId);

    /**
     * 根据分页获取文章列表
     * 在主页和具体分类的帖子列表里面需要考虑置顶的问题
     * 在用户个人信息里面就不需要考虑
     * @param pagination
     * @return
     */
    List<Post> getPostListByPagination(Pagination pagination);
}
