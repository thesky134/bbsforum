package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
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
    void deletePostById(int id);
    Post getPostById(int id);
    List<Post> getPostByUserId(int userId);
    int getAllPostSum();
    int getPostTopSum();
    int getPostSumWithCategory(int categoryId);
    int getPostTopSumWithCategory(int categoryId);
    List<Post> getPostTopListByPagination(Pagination pagination);
    List<Post> getPostNotTopListByPagination(Pagination pagination);
    List<Post> getPostTopListByPaginationWithCategory(Pagination pagination);
    List<Post> getPostNotTopListByPaginationWithCategory(Pagination pagination);
}
