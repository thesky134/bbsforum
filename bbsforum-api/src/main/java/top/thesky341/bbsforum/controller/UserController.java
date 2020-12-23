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
import top.thesky341.bbsforum.entity.*;
import top.thesky341.bbsforum.dto.PasswdDto;
import top.thesky341.bbsforum.entity.groups.*;
import top.thesky341.bbsforum.service.*;
import top.thesky341.bbsforum.util.common.RandomGenerateString;
import top.thesky341.bbsforum.util.common.UserUtil;
import top.thesky341.bbsforum.util.encrypt.MD5SaltEncryption;
import top.thesky341.bbsforum.util.encrypt.RandomGenerateSalt;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;
import top.thesky341.bbsforum.vo.CommentVo;
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
    /**
     * 存储头像的物理路径
     */
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
    @Resource(name = "userCommentStateServiceImpl")
    UserCommentStateService userCommentStateService;
    @Resource(name = "requestAnswerServiceImpl")
    RequestAnswerService requestAnswerService;



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
        Map<String, Object> data = new HashMap<>();
        data.put(UserSessionManager.OAUTH_TOKEN, subject.getSession().getId());
        data.put("id", user.getId());
        return Result.success(data);
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
            data.put("userId", user.getId());
            data.put("username", user.getUsername());
            return Result.success(data);
        } else {
            return Result.success("loginState", false);
        }
    }

    /**
     * 用户注销退出
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/user/manage/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

    /**
     * 更新用户名
     * 有唯一性要求
     */
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

    /**
     * 更新邮箱
     * 有唯一性要求
     */
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

    /**
     * 更新联系方式
     */
    @RequiresAuthentication
    @PostMapping("/user/manage/update/phone")
    public Result updatePhone(@Validated(UpdatePhone.class) @RequestBody User newUser) {
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setPhone(newUser.getPhone());
        userService.updatePhone(user);
        return Result.success();
    }

    /**
     * 更新工作类型
     */
    @RequiresAuthentication
    @PostMapping("/user/manage/update/jobtype")
    public Result updateJobType(@Validated(UpdateJobType.class) @RequestBody User newUser) {
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setJobType(newUser.getJobType());
        userService.updateJobType(user);
        return Result.success();
    }

    /**
     * 更新工作位置
     */
    @RequiresAuthentication
    @PostMapping("/user/manage/update/joblocation")
    public Result updateJobLocation(@Validated(UpdateJobLocation.class) @RequestBody User newUser) {
        User user = userService.getUserById(UserUtil.getCurrentUserId());
        user.setJobLocation(newUser.getJobLocation());
        userService.updateJobLocation(user);
        return Result.success();
    }

    /**
     * 更新个人签名
     */
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
        //判断后缀
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

    /**
     * 用户更新密码
     * 会要求原密码和新密码
     * 注销原用户token，返回新token
     */
    @RequiresAuthentication
    @PostMapping("/user/manage/update/passwd")
    public Result updatePasswd(@Valid @RequestBody PasswdDto passwdDto) {
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserById((int)subject.getPrincipal());
        String encryptedOldPasswd = MD5SaltEncryption.encrypt(passwdDto.getOldPasswd(),
                user.getSalt());
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
        user = userService.getUserByUsername(user.getUsername());
        //检查是否每天第一次登录，第一次登录积分加5
        userService.checkIsTodayFirstLogin(user);
        userService.updateLastLoginTime(user);
        return Result.success(UserSessionManager.OAUTH_TOKEN, subject.getSession().getId());
    }


    /**
     * 获取用户个人信息
     * 该信息不包含敏感信息
     */
    @PostMapping("/user/manage/info/{userId}")
    public Result getUserInfo(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        user.setOther(null);
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

    /**
     * 获取某用户个人帖子数量
     */
    @RequiresAuthentication
    @PostMapping("/user/post/sum/{userId}")
    public Result getUserPostSum(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        int sum = postService.getPostSum(-1, userId, -1);
        return Result.success("sum", sum);
    }

    /**
     * 根据用户id获取用户个人的帖子列表
     * @param paginationDto 包含用户id，分页大小，分页位置
     */
    @PostMapping("/user/post/list")
    public Result getUserPostList(@Valid @RequestBody PaginationDto paginationDto) {
        int userId = paginationDto.getUserId();
        User user = userService.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        pagination.setUserId(userId);
        return Result.success("posts", getPostInfoListByPagination(pagination));
    }

    /**
     * 根据用户id获取对应用户的评论数量
     */
    @PostMapping("/user/comment/sum/{userId}")
    public Result getCommentSum(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        int sum = commentService.getCommentSum(-1, userId);
        return Result.success("sum", sum);
    }

    /**
     * 获取某用户个人的评论列表
     * @param paginationDto 包含用户id, 分页大小，分页位置
     */
    @PostMapping("/user/comment/list")
    public Result getCommentList(@Valid @RequestBody PaginationDto paginationDto) {
        int userId = paginationDto.getUserId();
        User user = this.userService.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        System.out.println(user);
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1), paginationDto.getPageSize());
        pagination.setUserId(userId);
        List<Comment> comments = this.commentService.getCommentListByPagination(pagination);
        List<CommentVo> commentVos = new ArrayList();
        for(int i = 0; i < comments.size(); ++i) {
            CommentVo commentVo = new CommentVo();
            Comment comment = (Comment)comments.get(i);
            commentVo.setContent(comment.getContent());
            commentVo.setId(comment.getId());
            commentVo.setPostTitle(comment.getPost().getTitle());
            commentVo.setPostId(comment.getPost().getId());
            commentVo.setCreateTime(comment.getCreateTime());
            commentVo.setModifyTIme(comment.getModifyTime());
            commentVo.setUser(comment.getUser().getUsername());
            commentVo.setUserId(comment.getUser().getId());
            commentVo.setGoodSum(this.userCommentStateService.getCommentStateSum(comment.getId(), 1));
            commentVo.setBadSum(this.userCommentStateService.getCommentStateSum(comment.getId(), 2));
            commentVo.setLikeSum(this.userCommentStateService.getCommentStateSum(comment.getId(), 3));
            commentVo.setPicture(comment.getUser().getPicture());
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                commentVo.setGood(this.userCommentStateService.getUserCommentStateSum(comment.getId(), userId, 1) == 1);
                commentVo.setBad(this.userCommentStateService.getUserCommentStateSum(comment.getId(), userId, 2) == 1);
                commentVo.setLike(this.userCommentStateService.getUserCommentStateSum(comment.getId(), userId, 3) == 1);
            }
            commentVos.add(commentVo);
        }

        return Result.success("comments", commentVos);
    }

    /**
     * 被用于获取postInfos
     */
    public List<PostInfoVo> getPostInfoListByPagination(Pagination pagination) {
        List<Post> posts = postService.getPostListByPagination(pagination);
        List<PostInfoVo> postInfoVos = new ArrayList<>();
        for (Post post : posts) {
            int postId = post.getId();
            int commentSum = commentService.getCommentSum(postId, -1);
            int visitSum = userPostStateService.getPostStateSum(postId, 4);
            boolean answered = false;
            if(post.getCategory().getName().equals("积分悬赏") && requestAnswerService.getRequestAnswerByPostId(postId) != null) {
                answered = true;
            }
            postInfoVos.add(new PostInfoVo(post, commentSum, visitSum, answered));
        }
        return postInfoVos;
    }


}
