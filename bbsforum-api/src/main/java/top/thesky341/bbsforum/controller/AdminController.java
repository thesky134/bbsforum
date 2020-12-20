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
public class AdminController {
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

    @PostMapping("/admin/manage/login")
    public Result adminUserLogin(@Validated(Login.class) @RequestBody User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPasswd());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        user = userService.getUserByUsername(user.getUsername());
        if(!(subject.hasRole("admin") || subject.hasRole("superadmin"))) {
            return new Result(ResultCode.PermissionDenied);
        }
        return Result.success(UserSessionManager.OAUTH_TOKEN, subject.getSession().getId());
    }

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

    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/user/sum")
    public Result getUserSum() {
        return Result.success("sum", userService.getUserSum());
    }

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

    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/post/sum")
    public Result getPostSum() {
        return Result.success("sum", postService.getPostSum(-1, -1, -1));
    }

    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/post/list")
    public Result getPostList(@Valid @RequestBody PaginationDto paginationDto) {
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        pagination.setAdmin(1);
        return Result.success("posts", getPostInfoListByPagination(pagination));
    }

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

    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/category/revise")
    public Result updateCategory(@Valid @RequestBody Category category) {
        System.out.println(category);
        Category oldCategory = categoryService.getCategoryById(category.getId());
        Assert.notNull(oldCategory, "分类不存在");
        categoryService.reviseCategory(category);
        return Result.success();
    }

    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/category/info/{categoryId}")
    public Result getCategoryInfo(@PathVariable int categoryId) {
        System.out.println(categoryId);
        Category category = categoryService.getCategoryById(categoryId);
        Assert.notNull(category, "分类不存在");
        return Result.success("category", new CategoryVo(category, postService.getPostSum(category.getId(), -1, 0)));
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
