package top.thesky341.bbsforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.thesky341.bbsforum.entity.Chara;
import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.mapper.CharaMapper;
import top.thesky341.bbsforum.mapper.UserMapper;
import top.thesky341.bbsforum.service.UserService;
import top.thesky341.bbsforum.util.common.UserUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author thesky
 * @date 2020/12/9
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CharaMapper charaMapper;

    @Override
    public User addUser(User user) {
        Chara chara = charaMapper.getGeneralChara();
        Assert.notNull(chara, "普通用户角色不存在");
        user.setChara(chara);
        userMapper.addUser(user);
        return userMapper.getUserById(user.getId());
    }

    @Override
    public List<User> getUserListByPagination(Pagination pagination) {
        return userMapper.getUserListByPagination(pagination);
    }

    @Override
    public int getUserSum() {
        return userMapper.getUserSum();
    }

    @Override
    public List<User> getAdminListByPagination(Pagination pagination) {
        return userMapper.getAdminListByPagination(pagination);
    }

    @Override
    public int getAdminSum() {
        return userMapper.getAdminSum();
    }

    @Override
    public void updateUsername(User user) {
        userMapper.updateUsername(user);
    }

    @Override
    public void updateEmail(User user) {
        userMapper.updateEmail(user);
    }

    @Override
    public void updatePhone(User user) {
        userMapper.updatePhone(user);
    }

    @Override
    public void updateJobType(User user) {
        userMapper.updateJobType(user);
    }

    @Override
    public void updatePicture(User user) {
        userMapper.updatePicture(user);
    }

    @Override
    public void updateScore(User user) {
        userMapper.updateScore(user);
    }

    @Override
    public void updatePasswd(User user) {
        userMapper.updatePasswd(user);
    }

    @Override
    public void updateJobLocation(User user) {
        userMapper.updateJobLocation(user);
    }

    @Override
    public void updatePersonalSignal(User user) {
        userMapper.updatePersonalSignal(user);
    }

    @Override
    public void checkIsTodayFirstLogin(User user) {
        if (user.getLastLoginTime() == null
                || UserUtil.checkIsBeforeToday(user.getLastLoginTime())) {
            user.setScore(user.getScore() + 5);
            user.setTodayScore(0);
            userMapper.updateDailyScore(user);
        }
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public int sumOfLoginsInaFewDays(int days) {
        List<User> users = userMapper.getAllUser();
        int sum = 0;
        for(User user : users) {
            if(user.getLastLoginTime() == null) {
                continue;
            }
            Date date = user.getLastLoginTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar compareTime = Calendar.getInstance();
            System.out.println(calendar);
            System.out.println(compareTime);
            compareTime.add(Calendar.DAY_OF_YEAR, -days);
            if(calendar.get(Calendar.YEAR) < compareTime.get(Calendar.YEAR)) {
                continue;
            } else if(calendar.get(Calendar.YEAR) == compareTime.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) < compareTime.get(Calendar.MONTH)) {
                continue;
            } else if(calendar.get(Calendar.YEAR) == compareTime.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) == compareTime.get(Calendar.MONTH)
                    && calendar.get(Calendar.DAY_OF_MONTH) < compareTime.get(Calendar.DAY_OF_MONTH)) {
                continue;
            } else {
                sum++;
            }
        }
        return sum;
    }

    @Override
    public void updateLastLoginTime(User user) {
        userMapper.updateLastLoginTime(user);
    }

    @Override
    public void updateUserDisabledState(int userId, int state) {
        userMapper.updateUserDisabledState(userId, state);
    }

    @Override
    public void updateChara(User user) {
        userMapper.updateChara(user);
    }

    @Override
    public void updateDailyScore(User user) {
        userMapper.updateDailyScore(user);
    }
}
