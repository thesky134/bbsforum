package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.entity.UserCommentState;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Mapper
@Repository
public interface UserCommentStateMapper {
    void addUserCommentState(UserCommentState userCommentState);
    void deleteUserCommentState(int id);
    UserCommentState getUserCommentState(int id);

}
