package top.thesky341.bbsforum.service;

import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.entity.Chara;
import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.User;

import java.util.List;

/**
 * @author thesky
 * @date 2020/12/9
 */
public interface UserService {
    User addUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User getUserById(int id);
    List<User> getUserListByPagination(Pagination pagination);
    List<User> getAdminListByPagination(Pagination pagination);
    int getUserSum();
    int getAdminSum();
    int sumOfLoginsInaFewDays(int days);
    void checkIsTodayFirstLogin(User user);
    void updateLastLoginTime(User user);
    void updateUsername(User user);
    void updateEmail(User user);
    void updatePhone(User user);
    void updateJobType(User user);
    void updateJobLocation(User user);
    void updatePersonalSignal(User user);
    void updatePicture(User user);
    void updateScore(User user);
    void updatePasswd(User user);
    void updateUserDisabledState(int userId, int state);
    void updateChara(User user);
    void updateDailyScore(User user);
}
