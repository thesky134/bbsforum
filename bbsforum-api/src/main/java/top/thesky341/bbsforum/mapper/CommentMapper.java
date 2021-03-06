package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.entity.Comment;
import top.thesky341.bbsforum.entity.Pagination;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Repository
@Mapper
public interface CommentMapper {
    void addComment(Comment comment);
    void setCommentDeletedByPostId(int postId);
    void setCommentDeleted(int id);
    void updateContent(Comment comment);
    Comment getCommentById(int id);
    List<Comment> getCommentByUserId(int userId);
    List<Comment> getCommentByPostId(int postId);
    int getCommentSum(int postId, int userId);
    int getCommentSumByPostId(int postId);
    List<Comment> getCommentListByPagination(Pagination pagination);
}
