package top.thesky341.bbsforum;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author thesky
 * @date 2020/12/16
 */
@SpringBootTest
public class ShiroTest {
    @Test
    public void roleTest() {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("test14", "abcd1234");
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        System.out.println(subject.hasRole("normal"));
        System.out.println(subject.hasRole("admin"));
    }
}
