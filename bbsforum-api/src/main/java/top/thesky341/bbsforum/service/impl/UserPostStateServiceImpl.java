package top.thesky341.bbsforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.entity.UserPostState;
import top.thesky341.bbsforum.mapper.UserPostStateMapper;
import top.thesky341.bbsforum.service.UserPostStateService;

/**
 * @author
 * @date 2020/12/11
 */
@Service
public class UserPostStateServiceImpl implements UserPostStateService {
    @Autowired
    UserPostStateMapper userPostStateMapper;

    @Override
    public int getUserPostStateSum(int postId, int userId, int state) {
        return userPostStateMapper.getUserPostStateSum(postId, userId, state);
    }

    @Override
    public int getPostStateSum(int postId, int state) {
        return getUserPostStateSum(postId, -1, state);
    }

    @Override
    public UserPostState addUserPostState(UserPostState userPostState) {
        userPostStateMapper.addUserPostState(userPostState);
        userPostState = userPostStateMapper.getUserPostStateById(userPostState.getId());
        return userPostState;
    }
}
