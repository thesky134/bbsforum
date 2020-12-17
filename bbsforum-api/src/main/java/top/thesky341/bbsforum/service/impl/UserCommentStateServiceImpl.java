package top.thesky341.bbsforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.entity.UserCommentState;
import top.thesky341.bbsforum.mapper.UserCommentStateMapper;
import top.thesky341.bbsforum.service.UserCommentStateService;

/**
 * @author hyn
 * @date 2020/12/17
 */
@Service
public class UserCommentStateServiceImpl implements UserCommentStateService {
    @Autowired
    private UserCommentStateMapper userCommentStateMapper;

    @Override
    public int getCommentStateSum(int commentId, int state) {
        return getUserCommentStateSum(commentId, -1, state);
    }

    @Override
    public int getUserCommentStateSum(int commentId, int userId, int state) {
        return userCommentStateMapper.getUserCommentStateSum(commentId, userId, state);
    }

    @Override
    public UserCommentState addUserCommentState(UserCommentState userCommentState) {
        userCommentStateMapper.addUserCommentState(userCommentState);
        userCommentState = userCommentStateMapper.getUserCommentStateById(userCommentState.getId());
        return userCommentState;
    }
}
