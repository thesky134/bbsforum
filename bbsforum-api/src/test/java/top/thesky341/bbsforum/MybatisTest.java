package top.thesky341.bbsforum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.thesky341.bbsforum.entity.Chara;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.mapper.UserMapper;
import top.thesky341.bbsforum.util.encrypt.MD5SaltEncryption;

import javax.annotation.Resource;

/**
 * @author thesky
 * @date 2020/12/9
 */
@SpringBootTest
public class MybatisTest {
    @Resource
    UserMapper userMapper;

    @Test
    public void mapperTest() {
        User user = new User();
        user.setUsername("test1");
        user.setEmail("123456@qq.com");
        user.setPasswd(MD5SaltEncryption.encrypt("abcd1234", "1234567890abcdef"));
        user.setSalt("1234567890abcdef");
        Chara chara = new Chara();
        chara.setId(3);
        user.setChara(chara);
        userMapper.addUser(user);
        System.out.println(userMapper.getUserById(user.getId()));
    }
}
