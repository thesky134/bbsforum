package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.thesky341.bbsforum.dto.PaginationDto;
import top.thesky341.bbsforum.dto.PostDto;
import top.thesky341.bbsforum.dto.groups.PaginationWithCategory;
import top.thesky341.bbsforum.entity.*;
import top.thesky341.bbsforum.service.*;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;
import top.thesky341.bbsforum.vo.PostInfoVo;
import top.thesky341.bbsforum.vo.PostVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hz
 * @date 2020/12/11
 */
@RestController
public class PostController {
    @Resource(name = "postServiceImpl")
    PostService postService;
    @Resource(name = "commentServiceImpl")
    CommentService commentService;
    @Resource(name = "userPostStateServiceImpl")
    UserPostStateService userPostStateService;
    @Resource(name = "categoryServiceImpl")
    CategoryService categoryService;
    @Resource(name = "userServiceImpl")
    UserService userService;

    /**
     * 添加帖子, 注意帖子可能为积分悬赏分类的
     * 发布积分悬赏帖子，会扣除用户的积分，用户积分应该不少于悬赏的积分
     */
    @RequiresAuthentication
    @PostMapping("/post/manage/add")
    public Result addRewardPost(@Valid @RequestBody PostDto postDto) {
        int userId = (int)SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserById(userId);
        Category category = categoryService.getCategoryByName(postDto.getCategory());
        Assert.notNull(category, "对应分类不存在");
        if(category.getName().equals("积分悬赏")) {
            if(postDto.getReward() <= 0) {
                return new Result(ResultCode.RewardNotGreater0);
            }
            if(user.getScore() < postDto.getReward()) {
                return new Result(ResultCode.ScoreNotEnough);
            }
        }
        Post post = new Post(postDto, user, category);
        postService.addPost(post);
        if (category.getName().equals("积分悬赏")) {
            user.setScore(user.getScore() - postDto.getReward());
            userService.updateScore(user);
        }
        return Result.success();
    }

    /**
     * 修改帖子，当用户为帖子作者或者拥有管理员权限时才可以修改
     * @param postDto
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/post/manage/revise")
    public Result revisePost(@Valid @RequestBody PostDto postDto) {
        Subject subject = SecurityUtils.getSubject();
        int userId = (int) subject.getPrincipal();
        int postId = postDto.getId();
        Post oldPost = postService.getPostById(postId);
        Assert.notNull(oldPost, "帖子不存在");
        if(userId != oldPost.getUser().getId() && !(subject.hasRole("admin") || subject.hasRole("superadmin"))) {
            return new Result(ResultCode.PermissionDenied);
        }
        User user = oldPost.getUser();
        Category category = categoryService.getCategoryByName(postDto.getCategory());
        Assert.notNull(category, "对应分类不存在");
        if(category.getName().equals("积分悬赏")) {
            if(postDto.getReward() <= 0) {
                return new Result(ResultCode.RewardNotGreater0);
            }
            if(postDto.getReward() > oldPost.getReward() + user.getScore()) {
                return new Result(ResultCode.ScoreNotEnough);
            }
        }
        Post post = new Post(postDto, user, category);
        postService.revisePost(post);
        if (category.getName().equals("积分悬赏")) {
            user.setScore(oldPost.getReward() + user.getScore() - postDto.getReward());
            userService.updateScore(user);
        }
        return Result.success();
    }

    /**
     * 获取所有帖子的数量
     * @return
     */
    @PostMapping("/post/all/sum")
    public Result getAllPostSum() {
        return Result.success("sum", postService.getPostSum(-1, -1));
    }

    @PostMapping("/post/category/sum")
    public Result getPostSumWithCategory(@RequestBody Category category) {
        category = categoryService.getCategoryById(category.getId());
        Assert.notNull(category, "分类不存在");
        return Result.success("sum", postService.getPostSum(category.getId(), -1));
    }

    /**
     * 对需要返回的文章信息使用了 PostInfoVo 封装，并写在 posts 列表里
     * @param paginationDto 包含了要获取的页数和大小
     * @return 包含了获取的文章列表，元素为 PostInfoVo
     */
    @PostMapping("/post/all/list")
    public Result getPostList(@Valid @RequestBody PaginationDto paginationDto) {
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        return Result.success("posts", getPostInfoListByPagination(pagination));
    }

    @PostMapping("/post/category/list")
    public Result getPostListWithCategory(@Validated(PaginationWithCategory.class) @RequestBody PaginationDto paginationDto) {
        Category category = categoryService.getCategoryById(paginationDto.getCategoryId());
        Assert.notNull(category, "分类不存在");
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        pagination.setCategoryId(paginationDto.getCategoryId());
        return Result.success("posts", getPostInfoListByPagination(pagination));
    }

    @PostMapping("/post/view/{id}")
    public Result postView(@PathVariable int id) {
        Post post = postService.getPostById(id);
        Assert.notNull(post, "帖子不存在");
        PostVo postVo = new PostVo();
        postVo.parsePost(post);
        postVo.setVisitSum(userPostStateService.getPostStateSum(id, 4));
        postVo.setCommentSum(commentService.getCommentSum(id, -1));
        postVo.setGoodSum(userPostStateService.getPostStateSum(id, 1));
        postVo.setBadSum(userPostStateService.getPostStateSum(id, 2));
        postVo.setLikeSum(userPostStateService.getPostStateSum(id, 3));
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            int userId = (int)subject.getPrincipal();
            postVo.setGood(userPostStateService.getUserPostStateSum(id, userId, 1) != 0);
            postVo.setBad(userPostStateService.getUserPostStateSum(id, userId, 2) != 0);
            postVo.setLike(userPostStateService.getUserPostStateSum(id, userId, 3) != 0);
            if (userPostStateService.getUserPostStateSum(id, userId, 4) == 0) {
                User user = userService.getUserById(userId);
                UserPostState userPostState = new UserPostState(post, user, 4);
                userPostStateService.addUserPostState(userPostState);
            }
        }
        return Result.success("post", postVo);
    }

    @RequiresAuthentication
    @PostMapping("/post/manage/hidden/{stateStr}/{postId}")
    public Result setPostHidden(@PathVariable String stateStr, @PathVariable int postId) {
        Subject subject = SecurityUtils.getSubject();
        int userId = (int) subject.getPrincipal();
        Post post = postService.getPostById(postId);
        Assert.notNull(post, "帖子不存在");
        if(userId != post.getUser().getId() && !(subject.hasRole("admin") || subject.hasRole("superadmin"))) {
            return new Result(ResultCode.PermissionDenied);
        }
        int state = -1;
        if(stateStr.equals("true")) {
            state = 1;
        } else if(stateStr.equals("false")) {
            state = 0;
        } else {
            return new Result(ResultCode.OperateNotExist);
        }
        postService.updatePostHiddenState(postId, state);
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/post/manage/delete/{postId}")
    public Result setPostDeleted(@PathVariable int postId) {
        Subject subject = SecurityUtils.getSubject();
        int userId = (int) subject.getPrincipal();
        Post post = postService.getPostById(postId);
        Assert.notNull(post, "帖子不存在");
        if(userId != post.getUser().getId() && !(subject.hasRole("admin") || subject.hasRole("superadmin"))) {
            return new Result(ResultCode.PermissionDenied);
        }
        postService.setPostDeleted(postId);
        return Result.success();
    }

    public List<PostInfoVo> getPostInfoListByPagination(Pagination pagination) {
        List<Post> posts = postService.getPostListByPagination(pagination);
        List<PostInfoVo> postInfoVos = new ArrayList<>();
        for (Post post : posts) {
            int postId = post.getId();
            int commentSum = commentService.getCommentSum(postId, -1);
            int visitSum = userPostStateService.getPostStateSum(postId, 4);
            postInfoVos.add(new PostInfoVo(post, commentSum, visitSum));
        }
        return postInfoVos;
    }
}
