package top.thesky341.bbsforum.service;

import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.Post;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/11
 */
public interface PostService {
    int getPostSum(int categoryId, int userId, int hidden);
    List<Post> getPostListByPagination(Pagination pagination);
    Post getPostById(int id);
    Post addPost(Post post);
    Post revisePost(Post post);
    void updatePostHiddenState(int postId, int state);
    void setPostDeleted(int postId);
    void updatePostExcellentState(int postId, int state);
    void updatePostTopState(int postId, int state);
}
