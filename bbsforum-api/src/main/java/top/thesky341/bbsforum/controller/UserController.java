package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.thesky341.bbsforum.entity.Register;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.mapper.UserMapper;
import top.thesky341.bbsforum.service.UserService;
import top.thesky341.bbsforum.util.encrypt.MD5SaltEncryption;
import top.thesky341.bbsforum.util.encrypt.RandomGenerateSalt;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author thesky
 * @date 2020/12/9
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户注册，会生成16位的随机字符串作为盐值
     * 加密使用 MD5盐值加密，加密次数为3
     * @param user
     * @return
     */
    @PostMapping("/user/manage/register")
    public Result register(@Validated(Register.class) @RequestBody User user) {
        System.out.println(user);
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return new Result(ResultCode.UsernameAlreadyExist);
        }
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return new Result(ResultCode.EmailAlreadyExist);
        }
        String passwd = user.getPasswd();
        String salt = RandomGenerateSalt.generateSalt();
        String encryptedPasswd = MD5SaltEncryption.encrypt(passwd, salt);
        user.setSalt(salt);
        user.setPasswd(encryptedPasswd);
        userService.addUser(user);
        return Result.success();
    }

    @PostMapping("/user/manage/login")
    public Result login(@Valid @RequestBody User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPasswd());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return Result.success();
    }

    @PostMapping("/user/manage/checkloginstate")
    public Result checkLoginState() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Map<String, Object> data = new HashMap<>();
            data.put("loginState", true);
            data.put("username", subject.getPrincipal().toString());
            return Result.success(data);
        } else {
            return Result.success("loginState", false);
        }
    }

    /**
     * 由于使用了前后端分离，
     * 因此需要堵塞 Shiro 原有的登录接口
     */
    @RequestMapping("/shiro/login")
    public Result shiroLogin() {
        return new Result(ResultCode.NeedLogin);
    }
}
