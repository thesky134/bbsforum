package top.thesky341.bbsforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.entity.Comment;
import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.mapper.CommentMapper;
import top.thesky341.bbsforum.service.CommentService;

import java.util.List;

/**
 * @author hsc
 * @date 2020/12/11
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public int getCommentSumByPostId(int postId) {
        return commentMapper.getCommentSumByPostId(postId);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentMapper.getCommentById(id);
    }

    @Override
    public Comment addComment(Comment comment) {
        commentMapper.addComment(comment);
        return commentMapper.getCommentById(comment.getId());
    }

    @Override
    public void updateContent(Comment comment) {
        commentMapper.updateContent(comment);
    }

    @Override
    public List<Comment> getCommentListByPagination(Pagination pagination) {
        return commentMapper.getCommentListByPagination(pagination);
    }
}
