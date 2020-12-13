package top.thesky341.bbsforum.service;

import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.Post;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/11
 */
public interface PostService {
    int getAllPostSum();
    int getPostSumWithCategory(int categoryId);
    List<Post> getPostListByPagination(Pagination pagination);
    List<Post> getPostListByPaginationWithCategory(Pagination pagination);
    Post getPostById(int id);
}
