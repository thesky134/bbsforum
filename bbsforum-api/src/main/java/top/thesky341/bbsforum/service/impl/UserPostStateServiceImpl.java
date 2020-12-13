package top.thesky341.bbsforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
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
    public int getPostVisitSum(int postId) {
        return userPostStateMapper.getPostVisitSum(postId);
    }

    @Override
    public int getPostGoodSum(int postId) {
        return userPostStateMapper.getPostGoodSum(postId);

    }

    @Override
    public int getPostBadSum(int postId) {
        return userPostStateMapper.getPostBadSum(postId);
    }

    @Override
    public int getPostLikeSum(int postId) {
        return userPostStateMapper.getPostLikeSum(postId);
    }

    @Override
    public int getPostStateSum(int postId, int state) {
        return userPostStateMapper.getPostStateSum(postId, state);
    }

    @Override
    public boolean getUserPostGoodState(int postId) {
        return false;
    }

    @Override
    public boolean getUserPostStateState(int postId, int userId, int state) {
        return userPostStateMapper.getUserPostStateState(postId, userId, state);
    }
}
