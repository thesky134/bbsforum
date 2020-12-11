package top.thesky341.bbsforum.service;

import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.entity.User;

/**
 * @author thesky
 * @date 2020/12/9
 */
public interface UserService {
    User addUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserById(int id);
    void checkIsTodayFirstLogin(User user);
    void updateLastLoginTime(User user);
    void updateUsername(User user);
    void updateEmail(User user);
    void updatePhone(User user);
    void updateJobType(User user);
    void updateJobLocation(User user);
    void updatePersonalSignal(User user);
    void updatePicture(User user);
}
