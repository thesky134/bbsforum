package top.thesky341.bbsforum.service;

import top.thesky341.bbsforum.entity.Comment;
import top.thesky341.bbsforum.entity.Pagination;

import java.util.List;

/**
 * @author hsc
 * @date 2020/12/11
 */
public interface CommentService {
    int getCommentSumByPostId(int postId);
    Comment addComment(Comment comment);
    List<Comment> getCommentListByPagination(Pagination pagination);
}
