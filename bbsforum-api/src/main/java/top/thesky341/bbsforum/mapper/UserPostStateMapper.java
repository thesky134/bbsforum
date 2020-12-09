package top.thesky341.bbsforum.mapper;

import top.thesky341.bbsforum.entity.UserPostState;

/**
 * @author thesky
 * @date 2020/12/8
 */
public interface UserPostStateMapper {
    void addUserPostState(UserPostState userPostState);
    void deleteUserPostState(int id);
    void getUserPostState(int id);
}