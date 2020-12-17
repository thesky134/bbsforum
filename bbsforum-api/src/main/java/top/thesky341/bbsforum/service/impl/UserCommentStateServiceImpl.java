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
    public void addUserCommentState(UserCommentState userCommentState) {
        if(userCommentStateMapper.getUserCommentStateSum(userCommentState.getComment().getId(),
                userCommentState.getUser().getId(), userCommentState.getState()) != 0) {
            return;
        }
        userCommentStateMapper.addUserCommentState(userCommentState);
    }

    @Override
    public void deleteUserCommentState(UserCommentState userCommentState) {
        if(userCommentStateMapper.getUserCommentStateSum(userCommentState.getComment().getId(),
                userCommentState.getUser().getId(), userCommentState.getState()) == 0) {
            return;
        }
        userCommentState = userCommentStateMapper.getUserCommentStateByCommentIdAndUserIdAndState(
                userCommentState.getComment().getId(),
                userCommentState.getUser().getId(),
                userCommentState.getState()
        );
        userCommentStateMapper.deleteUserCommentStateById(userCommentState.getId());
    }
}
