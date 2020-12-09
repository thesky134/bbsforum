package top.thesky341.bbsforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.thesky341.bbsforum.entity.Chara;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.mapper.CharaMapper;
import top.thesky341.bbsforum.mapper.UserMapper;

/**
 * @author thesky
 * @date 2020/12/9
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CharaMapper charaMapper;

    public User addUser(User user) {
        Chara chara = charaMapper.getGeneralChara();
        Assert.notNull(chara, "普通用户角色不存在");
        user.setChara(chara);
        userMapper.addUser(user);
        return userMapper.getUserById(user.getId());
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }
}
