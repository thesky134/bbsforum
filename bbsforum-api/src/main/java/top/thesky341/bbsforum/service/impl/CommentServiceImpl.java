package top.thesky341.bbsforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.mapper.CommentMapper;
import top.thesky341.bbsforum.service.CommentService;

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
}
