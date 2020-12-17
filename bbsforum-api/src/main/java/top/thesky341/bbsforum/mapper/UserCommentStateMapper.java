package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.entity.UserCommentState;
import top.thesky341.bbsforum.entity.UserPostState;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Mapper
@Repository
public interface UserCommentStateMapper {
    void deleteUserCommentStateById(int id);
    UserCommentState getUserCommentStateById(int id);
    int getUserCommentStateSum(@Param("commentId") int commentId, @Param("userId") int userId, @Param("state") int state);
    void addUserCommentState(UserCommentState userCommentState);
}
