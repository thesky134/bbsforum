package top.thesky341.bbsforum.service;

import org.apache.ibatis.annotations.Param;

/**
 * @author thesky
 * @date 2020/12/11
 */
public interface UserPostStateService {
    int getPostVisitSum(int postId);
    int getPostGoodSum(int postId);
    int getPostBadSum(int postId);
    int getPostLikeSum(int postId);
    int getPostStateSum(int postId, int state);
    boolean getUserPostGoodState(int postId);
    boolean getUserPostStateState(int postId, int userId, int state);
}
