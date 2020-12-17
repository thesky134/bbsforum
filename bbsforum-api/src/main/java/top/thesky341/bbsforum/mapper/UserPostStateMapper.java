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
    void deleteUserPostStateById(int id);
    UserPostState getUserPostStateById(int id);

    /**
     * 查询某个帖子处于某种状态的数量
     * 当userId字段不为-1时，表示查询单个用户的状态
     * state字段：1表示赞，2表示踩，3表示喜欢，4表示浏览
     */
    int getUserPostStateSum(@Param("postId") int postId, @Param("userId") int userId, @Param("state") int state);

    /**
     * 添加用户关于帖子处于某种状态
     * state字段：1表示赞，2表示踩，3表示喜欢，4表示浏览
     */
    void addUserPostState(UserPostState userPostState);

    UserPostState getUserPostStateByPostIdAndUserIdAndState(@Param("postId") int postId, @Param("userId") int userId, @Param("state") int state);
}
