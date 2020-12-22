package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.thesky341.bbsforum.config.shiro.UserSessionManager;
import top.thesky341.bbsforum.dto.PaginationDto;
import top.thesky341.bbsforum.entity.*;
import top.thesky341.bbsforum.entity.groups.Login;
import top.thesky341.bbsforum.service.*;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;
import top.thesky341.bbsforum.vo.CategoryVo;
import top.thesky341.bbsforum.vo.PostInfoVo;
import top.thesky341.bbsforum.vo.UserVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cj
 * @date 2020/12/16
 */
@RestController
public class
AdminController {
    @Resource(name = "userServiceImpl")
    UserService userService;
    @Resource(name = "postServiceImpl")
    PostService postService;
    @Resource(name = "commentServiceImpl")
    CommentService commentService;
    @Resource(name = "userPostStateServiceImpl")
    UserPostStateService userPostStateService;
    @Resource(name = "categoryServiceImpl")
    CategoryService categoryService;
    @Resource(name = "charaServiceImpl")
    CharaService charaService;
    @Resource(name = "requestAnswerServiceImpl")
    RequestAnswerService requestAnswerService;

    /**
     * 用于管理员后台的登录
     */
    @PostMapping("/admin/manage/login")
    public Result adminUserLogin(@Validated(Login.class) @RequestBody User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPasswd());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        user = userService.getUserByUsername(user.getUsername());
        if (!(subject.hasRole("admin") || subject.hasRole("superadmin"))) {
            subject.logout();
            return new Result(ResultCode.PermissionDenied);
        }
        Map<String, Object> data = new HashMap<>();
        data.put(UserSessionManager.OAUTH_TOKEN, subject.getSession().getId());
        data.put("id", user.getId());
        return Result.success(data);
    }

    /**
     * 获取用户列表
     * 注意要有管理员权限才行
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/user/list")
    public Result getUserList(@Valid @RequestBody PaginationDto paginationDto) {
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        List<User> users = userService.getUserListByPagination(pagination);
        List<UserVo> userVos = new ArrayList<>();
        for(User user : users) {
            UserVo userVo = new UserVo(user);
            userVos.add(userVo);
        }
        return Result.success("users", userVos);
    }

    /**
     * 获取用户总数
     * @return
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/user/sum")
    public Result getUserSum() {
        return Result.success("sum", userService.getUserSum());
    }

    /**
     * 获取用户资料统计
     * @return
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/user/statistics")
    public Result getUserStatistics() {
        Map<String, Object> data = new HashMap<>();
        data.put("todayLoginSum", userService.sumOfLoginsInaFewDays(0));
        data.put("yearActiveSum",
                userService.sumOfLoginsInaFewDays(365));
        data.put("sum", userService.getUserSum());
        data.put("monthActiveSum", userService.sumOfLoginsInaFewDays(30));
        return Result.success(data);
    }

    /**
     * 设在账号封禁状态
     * @param state
     * @param userId
     * @return
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/user/disabled/{state}/{userId}")
    public Result setUserDisabled(@PathVariable String state, @PathVariable int userId) {
        User user = userService.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        Subject subject = SecurityUtils.getSubject();
        if(!subject.hasRole("superadmin") && (user.getChara().getName().equals("admin") || user.getChara().getName().equals("superadmin"))) {
            return new Result(ResultCode.PermissionDenied);
        }
        if(state.equals("true")) {
            userService.updateUserDisabledState(userId, 1);
        } else if(state.equals("false")) {
            userService.updateUserDisabledState(userId, 0);
        } else {
            return new Result(ResultCode.OperateNotExist);
        }
        return Result.success();
    }

    /**
     * 获取全部帖子数量
     * @return
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/post/sum")
    public Result getPostSum() {
        return Result.success("sum", postService.getPostSum(-1, -1, -1));
    }

    /**
     * 获取帖子列表
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/post/list")
    public Result getPostList(@Valid @RequestBody PaginationDto paginationDto) {
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        pagination.setAdmin(1);
        return Result.success("posts", getPostInfoListByPagination(pagination));
    }

    /**
     * 设置帖子置顶状态
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/post/top/{state}/{postId}")
    public Result setPostTop(@PathVariable String state, @PathVariable int postId) {
        Post post = postService.getPostById(postId);
        Assert.notNull(post, "帖子不存在");
        if(state.equals("true")) {
            postService.updatePostTopState(postId, 1);
        } else if(state.equals("false")) {
            postService.updatePostTopState(postId, 0);
        } else {
            return new Result(ResultCode.OperateNotExist);
        }
        return Result.success();
    }

    /**
     * 设置帖子加精状态
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/post/excellent/{state}/{postId}")
    public Result setPostExcellent(@PathVariable String state, @PathVariable int postId) {
        Post post = postService.getPostById(postId);
        Assert.notNull(post, "帖子不存在");
        if(state.equals("true")) {
            postService.updatePostExcellentState(postId, 1);
        } else if(state.equals("false")) {
            postService.updatePostExcellentState(postId, 0);
        } else {
            return new Result(ResultCode.OperateNotExist);
        }
        return Result.success();
    }

    /**
     * 添加分类
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/category/add")
    public Result addCategory(@Valid @RequestBody Category category) {
        System.out.println(category);
        Category oldCategory = categoryService.getCategoryByName(category.getName());
        if(oldCategory != null) {
            return new Result(ResultCode.CategoryAlreadyExist);
        }
        categoryService.addCategory(category);
        return Result.success();
    }

    /**
     * 修改分类
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/category/revise")
    public Result updateCategory(@Valid @RequestBody Category category) {
        System.out.println(category);
        Category oldCategory = categoryService.getCategoryById(category.getId());
        Assert.notNull(oldCategory, "分类不存在");
        categoryService.reviseCategory(category);
        return Result.success();
    }

    /**
     * 获取某个分类信息
     */
    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/category/info/{categoryId}")
    public Result getCategoryInfo(@PathVariable int categoryId) {
        System.out.println(categoryId);
        Category category = categoryService.getCategoryById(categoryId);
        Assert.notNull(category, "分类不存在");
        return Result.success("category", new CategoryVo(category, postService.getPostSum(category.getId(), -1, 0)));
    }

    /**
     * 用于超级管理员管理管理员
     * 获取管理员数量
     */
    @RequiresRoles("superadmin")
    @PostMapping("/admin/admin/sum")
    public Result getAdminSum() {
        return Result.success("sum", userService.getAdminSum());
    }

    /**
     * 以分页方式获取管理员列表
     */
    @PostMapping("/admin/admin/list")
    public Result getAdminList(@Valid @RequestBody PaginationDto paginationDto) {
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        List<User> users = userService.getAdminListByPagination(pagination);
        List<UserVo> userVos = new ArrayList<>();
        for(User user : users) {
            UserVo userVo = new UserVo(user);
            userVos.add(userVo);
        }
        return Result.success("users", userVos);
    }

    /**
     *
     */
    @RequiresRoles("superadmin")
    @PostMapping("/admin/chara/{userId}/{charaName}")
    public Result setUserChara(@PathVariable String charaName, @PathVariable int userId) {
        Chara chara = charaService.getCharaByName(charaName);
        Assert.notNull(chara, "角色不存在");
        User user = userService.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        user.setChara(chara);
        userService.updateChara(user);
        return Result.success();
    }

    /**
     * 被用于将posts封装成postVos
     * @param pagination
     * @return
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
