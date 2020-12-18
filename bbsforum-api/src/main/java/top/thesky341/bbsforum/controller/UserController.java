package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.thesky341.bbsforum.config.shiro.UserSessionManager;
import top.thesky341.bbsforum.dto.PaginationDto;
import top.thesky341.bbsforum.dto.PasswdDto;
import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.Post;
import top.thesky341.bbsforum.entity.groups.*;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.service.CommentService;
import top.thesky341.bbsforum.service.PostService;
import top.thesky341.bbsforum.service.UserPostStateService;
import top.thesky341.bbsforum.service.UserService;
import top.thesky341.bbsforum.util.common.RandomGenerateString;
import top.thesky341.bbsforum.util.common.UserUtil;
import top.thesky341.bbsforum.util.encrypt.MD5SaltEncryption;
import top.thesky341.bbsforum.util.encrypt.RandomGenerateSalt;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;
import top.thesky341.bbsforum.vo.PostInfoVo;
import top.thesky341.bbsforum.vo.UserVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author thesky
 * @date 2020/12/9
 */
@RestController
public class UserController {
    @Value("${imgAbsolutePathPrefix}")
    private String pathPrefix;

    @Resource( name = "userServiceImpl")
    UserService userService;
    @Resource(name = "postServiceImpl")
    PostService postService;
    @Resource(name = "commentServiceImpl")
    CommentService commentService;
    @Resource(name = "userPostStateServiceImpl")
    UserPostStateService userPostStateService;


    /**
     * 用户注册，会生成16位的随机字符串作为盐值
     * 加密使用 MD5盐值加密，加密次数为3
     * @param user
     * @return
     */
    @PostMapping("/user/manage/register")
    public Result register(@Validated(Register.class) @RequestBody User user) {
        //用户名已经存在 151
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return new Result(ResultCode.UsernameAlreadyExist);
        }
        //邮箱已经存在 152
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return new Result(ResultCode.EmailAlreadyExist);
        }
        String passwd = user.getPasswd();
        //生成盐值，加密密码
        String salt = RandomGenerateSalt.generateSalt();
        String encryptedPasswd = MD5SaltEncryption.encrypt(passwd, salt);
        user.setSalt(salt);
        user.setPasswd(encryptedPasswd);
        userService.addUser(user);
        return Result.success();
    }

    /**
     * 用户登录，登录正确会返回 token
     * @param user 需要包含 username 和 passwd
     * @return 登录成功返回 token
     */
    @PostMapping("/user/manage/login")
    public Result login(@Validated(Login.class) @RequestBody User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPasswd());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        user = userService.getUserByUsername(user.getUsername());
        //检查是否每天第一次登录，第一次登录积分加5
        userService.checkIsTodayFirstLogin(user);
        userService.updateLastLoginTime(user);
        return Result.success(UserSessionManager.OAUTH_TOKEN, subject.getSession().getId());
    }

    /**
     * 检查用户登录状态
     */
    @PostMapping("/user/manage/checkloginstate")
    public Result checkLoginState() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = userService.getUserById(UserUtil.getCurrentUserId());
            Map<String, Object> data = new HashMap<>();
            data.put("loginState", true);
            data.put("username", user.getUsername());
            return Result.success(data);
        } else {
            return Result.success("loginState", false);
        }
    }

    @RequiresAuthentication
    @PostMapping("/user/manage/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/user/manage/update/username")
    public Result updateUsername(@Validated(UpdateUsername.class) @RequestBody User newUser) {
        if (userService.getUserByUsername(newUser.getUsername()) != null) {
            return new Result(ResultCode.UsernameAlreadyExist);
        }
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setUsername(newUser.getUsername());
        userService.updateUsername(user);
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/user/manage/update/email")
    public Result updateEmail(@Validated(UpdateEmail.class) @RequestBody User newUser) {
        if(userService.getUserByEmail(newUser.getEmail()) != null) {
            return new Result(ResultCode.EmailAlreadyExist);
        }
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setEmail(newUser.getEmail());
        userService.updateEmail(user);
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/user/manage/update/phone")
    public Result updatePhone(@Validated(UpdatePhone.class) @RequestBody User newUser) {
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setPhone(newUser.getPhone());
        userService.updatePhone(user);
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/user/manage/update/jobtype")
    public Result updateJobType(@Validated(UpdateJobType.class) @RequestBody User newUser) {
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setJobType(newUser.getJobType());
        userService.updateJobType(user);
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/user/manage/update/joblocation")
    public Result updateJobLocation(@Validated(UpdateJobLocation.class) @RequestBody User newUser) {
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setJobLocation(newUser.getJobLocation());
        userService.updateJobLocation(user);
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/user/manage/update/personalsignal")
    public Result updatePersonalSignal(@Validated(UpdatePersonalSignal.class) @RequestBody User newUser) {
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setPersonalSignal(newUser.getPersonalSignal());
        userService.updatePersonalSignal(user);
        return Result.success();
    }

    /**
     * 上传头像
     * @param picture 传入的头像，为 MultipartFile 类型
     * 首先判断了图片后缀是否为 .jpg 或 .png 或 .jpeg
     * 然后使用用户id 和随机生成的 3个字符 加上后缀生成新的图片名
     * 后面便是保存并且把图片名写入数据库
     */
    @RequiresAuthentication
    @PostMapping("/user/manage/update/picture")
    public Result updatePicture(MultipartFile picture) {
        Assert.notNull(picture, "图片不存在");
        String oldPictureName = picture.getOriginalFilename();
        System.out.println(oldPictureName);
        String[] nameParts = oldPictureName.split("\\.");
        if(nameParts.length <= 1) {
            return new Result(ResultCode.PictureFormatError);
        }
        String pictureNameSuffix = "." + nameParts[nameParts.length - 1];
        if(!(".jpg".equals(pictureNameSuffix.toLowerCase())) && !(".png".equals(pictureNameSuffix.toLowerCase()))
            && !(".jpeg".equals(pictureNameSuffix.toLowerCase()))) {
            return new Result(ResultCode.PictureFormatError);
        }
        String newPictureName = UserUtil.getCurrentUserId() + RandomGenerateString.generateString(3) + pictureNameSuffix;
        File savedPicture = new File(pathPrefix + newPictureName);
        while (savedPicture.exists()) {
            newPictureName = UserUtil.getCurrentUserId() + RandomGenerateString.generateString(3) + pictureNameSuffix;
            savedPicture = new File(pathPrefix + newPictureName);
        }
        try(OutputStream os = new FileOutputStream(savedPicture)) {
            try(InputStream is = picture.getInputStream()) {
                os.write(is.readAllBytes());
            } catch (IOException e) {
                return new Result(ResultCode.PictureSavingError);
            }
        } catch (IOException e) {
            return new Result(ResultCode.PictureSavingError);
        }
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setPicture(newPictureName);
        userService.updatePicture(user);
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/user/manage/update/passwd")
    public Result updatePasswd(@Valid @RequestBody PasswdDto passwdDto) {
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserById((int)subject.getPrincipal());
        String encryptedOldPasswd = MD5SaltEncryption.encrypt(passwdDto.getOldPasswd(),
                user.getSalt());
        System.out.println(user);
        System.out.println(encryptedOldPasswd);
        if(!encryptedOldPasswd.equals(user.getPasswd())) {
            return new Result(ResultCode.IncorrectCredentialsException);
        }
        String encryptedNewPasswd = MD5SaltEncryption.encrypt(passwdDto.getNewPasswd(),
                user.getSalt());
        user.setPasswd(encryptedNewPasswd);
        userService.updatePasswd(user);
        subject.logout();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), passwdDto.getNewPasswd());
        subject.login(token);
        System.out.println(subject);
        user = userService.getUserByUsername(user.getUsername());
        //检查是否每天第一次登录，第一次登录积分加5
        userService.checkIsTodayFirstLogin(user);
        userService.updateLastLoginTime(user);
        return Result.success(UserSessionManager.OAUTH_TOKEN, subject.getSession().getId());
    }


    @RequiresAuthentication
    @PostMapping("/user/manage/info")
    public Result getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        int userId = (int)subject.getPrincipal();
        User user = userService.getUserById(userId);
        user.setChara(null);
        user.setOther(null);
        user.setTodayScore(-1);
        user.setCreateTime(null);
        return Result.success("user", new UserVo(user));
    }

    /**
     * 由于使用了前后端分离，
     * 因此需要堵塞 Shiro 原有的登录接口
     */
    @RequestMapping("/shiro/login")
    public Result shiroLogin() {
        return new Result(ResultCode.NeedLogin);
    }

    @RequiresAuthentication
    @PostMapping("/user/post/sum")
    public Result getUserPostSum() {
        Subject subject = SecurityUtils.getSubject();
        int userId = (int)subject.getPrincipal();
        int sum = postService.getPostSum(-1, userId);
        return Result.success("sum", sum);
    }

    @RequiresAuthentication
    @PostMapping("/user/post/list")
    public Result getUserPostList(@Valid @RequestBody PaginationDto paginationDto) {
        Subject subject = SecurityUtils.getSubject();
        int userId = (int)subject.getPrincipal();
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        pagination.setUserId(userId);
        return Result.success("posts", getPostInfoListByPagination(pagination));
    }

    public List<PostInfoVo> getPostInfoListByPagination(Pagination pagination) {
        List<Post> posts = postService.getPostListByPagination(pagination);
        List<PostInfoVo> postInfoVos = new ArrayList<>();
        for (Post post : posts) {
            int postId = post.getId();
            int commentSum = commentService.getCommentSumByPostId(postId);
            int visitSum = userPostStateService.getPostStateSum(postId, 4);
            postInfoVos.add(new PostInfoVo(post, commentSum, visitSum));
        }
        return postInfoVos;
    }
}
