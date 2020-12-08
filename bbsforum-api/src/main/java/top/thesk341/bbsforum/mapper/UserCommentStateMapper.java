package top.thesk341.bbsforum.mapper;

import top.thesk341.bbsforum.entity.UserCommentState;

/**
 * @author thesky
 * @date 2020/12/8
 */
public interface UserCommentStateMapper {
    void addUserCommentState(UserCommentState userCommentState);
    void deleteUserCommentState(int id);
    void getUserCommentState(int id);
}
