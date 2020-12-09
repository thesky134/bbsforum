package top.thesky341.bbsforum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.mapper.UserMapper;
import top.thesky341.bbsforum.service.UserService;
import top.thesky341.bbsforum.util.encrypt.MD5SaltEncryption;
import top.thesky341.bbsforum.util.encrypt.RandomGenerateSalt;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;

import javax.validation.Valid;

/**
 * @author thesky
 * @date 2020/12/9
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user/manage/register")
    public Result register(@Valid @RequestBody User user) {
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
        return null;
    }
}
