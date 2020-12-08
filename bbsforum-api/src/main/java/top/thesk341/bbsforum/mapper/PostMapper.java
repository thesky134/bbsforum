package top.thesk341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesk341.bbsforum.entity.Post;

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
}
