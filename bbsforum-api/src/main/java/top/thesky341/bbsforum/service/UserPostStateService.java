package top.thesky341.bbsforum.service;

import org.apache.ibatis.annotations.Param;
import top.thesky341.bbsforum.entity.UserPostState;

/**
 * @author thesky
 * @date 2020/12/11
 */
public interface UserPostStateService {
    int getPostStateSum(int postId, int state);
    int getUserPostStateSum(int postId, int userId, int state);
    void addUserPostState(UserPostState userPostState);
    void deleteUserPostState(UserPostState userPostState);
}
