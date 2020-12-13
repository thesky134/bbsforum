package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.entity.UserPostState;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Repository
@Mapper
public interface UserPostStateMapper {
    void addUserPostState(UserPostState userPostState);
    void deleteUserPostState(int id);
    UserPostState getUserPostState(int id);
    int getPostVisitSum(int postId);
    int getPostGoodSum(int postId);
    int getPostBadSum(int postId);
    int getPostLikeSum(int postId);

    /**
     * 查询某个帖子处于某种状态的数量
     * state字段：1表示赞，2表示踩，3表示喜欢，4表示浏览
     * @param postId 指定帖子的 id
     * @param state 指定某种状态
     */
    int getPostStateSum(@Param("postId") int postId, @Param("state") int state);
    boolean getUserPostGoodState(int postId);
    boolean getUserPostBadState(int postId);
    boolean getUserPostLikeState(int postId);

    /**
     * 查询用户关于帖子是否处于某种状态
     * state字段：1表示赞，2表示踩，3表示喜欢，4表示浏览
     * @param postId 帖子的id
     * @param userId 用户的id
     * @param state 状态
     */
    boolean getUserPostStateState(@Param("postId") int postId, @Param("userId") int userId, @Param("state") int state);
}
