package top.thesky341.bbsforum.service;

import top.thesky341.bbsforum.entity.UserCommentState;
import top.thesky341.bbsforum.entity.UserPostState;

/**
 * @author hyn
 * @date 2020/12/17
 */
public interface UserCommentStateService {
    int getCommentStateSum(int commentId, int state);
    int getUserCommentStateSum(int commentId, int userId, int state);
    UserCommentState addUserCommentState(UserCommentState userCommentState);
}
