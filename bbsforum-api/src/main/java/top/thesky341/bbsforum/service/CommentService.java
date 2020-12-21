package top.thesky341.bbsforum.service;

import top.thesky341.bbsforum.entity.Comment;
import top.thesky341.bbsforum.entity.Pagination;

import java.util.List;

/**
 * @author hsc
 * @date 2020/12/11
 */
public interface CommentService {
    int getCommentSum(int postId, int userId);
    Comment getCommentById(int id);
    Comment addComment(Comment comment);
    void updateContent(Comment comment);
    List<Comment> getCommentListByPagination(Pagination pagination);
}
