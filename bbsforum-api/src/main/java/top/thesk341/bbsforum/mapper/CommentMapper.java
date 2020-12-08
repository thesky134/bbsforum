package top.thesk341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesk341.bbsforum.entity.Comment;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Repository
@Mapper
public interface CommentMapper {
    void addComment(Comment comment);
    void deleteCommentById(int id);
    Comment getCommentById(int id);
    List<Comment> getCommentByUserId(int userId);
    List<Comment> getCommentByPostId(int postId);
}
