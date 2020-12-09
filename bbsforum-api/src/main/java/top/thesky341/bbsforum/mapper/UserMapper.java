package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.entity.User;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Repository
@Mapper
public interface UserMapper {
    void addUser(User user);
    void deleteUserById(User user);
    User getUserById(int id);
    User getUserByUsername(String name);
    User getUserByEmail(String email);
}
