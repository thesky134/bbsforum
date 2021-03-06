package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.thesky341.bbsforum.dto.PaginationDto;
import top.thesky341.bbsforum.dto.RequestAnswerDto;
import top.thesky341.bbsforum.dto.PostDto;
import top.thesky341.bbsforum.dto.groups.PaginationWithCategory;
import top.thesky341.bbsforum.entity.*;
import top.thesky341.bbsforum.service.*;
import top.thesky341.bbsforum.util.common.CommentUtil;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;
import top.thesky341.bbsforum.vo.CategoryVo;
import top.thesky341.bbsforum.vo.CommentVo;
import top.thesky341.bbsforum.vo.PostInfoVo;
import top.thesky341.bbsforum.vo.PostVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource(name = "requestAnswerServiceImpl")
    RequestAnswerService requestAnswerService;
    @Resource(name = "userCommentStateServiceImpl")
    UserCommentStateService userCommentStateService;

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
        //判断用户是否有权限修改帖子
        if(userId != oldPost.getUser().getId() && !(subject.hasRole("admin") || subject.hasRole("superadmin"))) {
            return new Result(ResultCode.PermissionDenied);
        }
        if(oldPost.getCategory().getName().equals("积分悬赏")) {
            Assert.isNull(requestAnswerService.getRequestAnswerByPostId(oldPost.getId()),
                    "原贴子为积分悬赏，且已被回答，不允许修改");
        }
        User user = oldPost.getUser();
        Category category = categoryService.getCategoryByName(postDto.getCategory());
        Assert.notNull(category, "对应分类不存在");
        if(category.getName().equals("积分悬赏")) {
            if(postDto.getReward() <= 0) {
                return new Result(ResultCode.RewardNotGreater0);
            }
            int nowScore = user.getScore();
            if(oldPost.getCategory().getName().equals("积分悬赏")) {
                nowScore += oldPost.getReward();
            }
            if(postDto.getReward() > nowScore) {
                return new Result(ResultCode.ScoreNotEnough);
            }
        }
        Post post = new Post(postDto, user, category);
        postService.revisePost(post);
        //判断帖子以前与现在的分类是否为积分悬赏
        if (category.getName().equals("积分悬赏")) {
            if(oldPost.getCategory().getName().equals("积分悬赏")) {
                user.setScore(oldPost.getReward() + user.getScore() - postDto.getReward());
                userService.updateScore(user);
            } else {
                user.setScore(user.getScore() - postDto.getReward());
                userService.updateScore(user);
            }
        } else {
            if(oldPost.getCategory().getName().equals("积分悬赏")) {
                user.setScore(oldPost.getReward() + user.getScore());
                userService.updateScore(user);
            }
        }
        return Result.success();
    }

    /**
     * 获取所有帖子的数量
     * 用于主页分页
     */
    @PostMapping("/post/all/sum")
    public Result getAllPostSum() {
        return Result.success("sum", postService.getPostSum(-1, -1, 0));
    }

    /**
     * 获取某个分类下的帖子数量
     */
    @PostMapping("/post/category/sum")
    public Result getPostSumWithCategory(@RequestBody Category category) {
        category = categoryService.getCategoryById(category.getId());
        Assert.notNull(category, "分类不存在");
        return Result.success("sum", postService.getPostSum(category.getId(), -1, 0));
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
        pagination.setHidden(0);
        return Result.success("posts", getPostInfoListByPagination(pagination));
    }

    /**
     * 获取某个分类下的帖子列表
     * 同时会返回该分类的信息
     */
    @PostMapping("/post/category/list")
    public Result getPostListWithCategory(@Validated(PaginationWithCategory.class) @RequestBody PaginationDto paginationDto) {
        Category category = categoryService.getCategoryById(paginationDto.getCategoryId());
        Assert.notNull(category, "分类不存在");
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        pagination.setCategoryId(paginationDto.getCategoryId());
        pagination.setHidden(0);
        CategoryVo categoryVo = new CategoryVo(category, postService.getPostSum(category.getId(), -1, 0));
        Map<String, Object> data = new HashMap<>();
        data.put("category", categoryVo);
        data.put("posts", getPostInfoListByPagination(pagination));
        return Result.success(data);
    }

    /**
     * 查看某个具体的帖子
     */
    @PostMapping("/post/view/{id}")
    public Result postView(@PathVariable int id) {
        Post post = postService.getPostById(id);
        Assert.notNull(post, "帖子不存在");
        //当帖子被隐藏时，只有对应权限运行查看
        if (post.isHidden()) {
            Subject subject = SecurityUtils.getSubject();
            if(subject.isAuthenticated()) {
                int userId = (int)subject.getPrincipal();
                if(userId != post.getUser().getId()
                        && !subject.hasRole("admin")
                        && !subject.hasRole("superadmin")) {
                    return new Result(ResultCode.PermissionDenied);
                }
            } else {
                return new Result(ResultCode.PermissionDenied);
            }
        }
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

        if(post.getCategory().getName().equals("积分悬赏") && requestAnswerService.getRequestAnswerByPostId(post.getId()) != null) {
            Comment comment = requestAnswerService.getRequestAnswerByPostId(post.getId()).getComment();
            CommentVo commentVo = CommentUtil.parseCommentToCommentVo(comment, userCommentStateService);
            postVo.setComment(commentVo);
        }

        return Result.success("post", postVo);
    }

    /**
     * 操作帖子，设置帖子是否隐藏
     * @param stateStr
     * @param postId
     * @return
     */
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

    /**
     * 删除帖子
     * 删除后不能复原
     */
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

    /**
     * 被获取帖子列表的方法使用
     * 能将post封装成postVo
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

    /**
     * 用户给帖子点赞，踩，喜欢
     * 当赞或喜欢时，踩会被取消
     * 当踩时，赞和喜欢会取消
     * 帖子被隐藏时，无法被点赞，踩，喜欢
     * @param stateStr   good, bad, like
     * @param operateStr add, delete
     * @param postId
     * @return
     */
    @RequiresAuthentication
    @PostMapping("/post/manage/{stateStr}/{operateStr}/{postId}")
    public Result changePostState(@PathVariable String stateStr, @PathVariable String operateStr, @PathVariable int postId) {
        Post post = postService.getPostById(postId);
        Assert.notNull(post, "帖子不存在");
        Assert.isTrue(!post.isHidden(), "帖子不存在");
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserById((int) subject.getPrincipal());
        if (operateStr.equals("add")) {
            UserPostState userPostState;
            if (stateStr.equals("good")) {
                userPostState = new UserPostState(post, user, 1);
                userPostStateService.addUserPostState(userPostState);
                userPostState.setState(2);
                userPostStateService.deleteUserPostState(userPostState);
            } else if (stateStr.equals("bad")) {
                userPostState = new UserPostState(post, user, 2);
                userPostStateService.addUserPostState(userPostState);
                userPostState.setState(1);
                userPostStateService.deleteUserPostState(userPostState);
                userPostState.setState(3);
                userPostStateService.deleteUserPostState(userPostState);
            } else if (stateStr.equals("like")) {
                userPostState = new UserPostState(post, user, 3);
                userPostStateService.addUserPostState(userPostState);
                userPostState.setState(2);
                userPostStateService.deleteUserPostState(userPostState);
            } else {
                return new Result(ResultCode.OperateNotExist);
            }
        } else if (operateStr.equals("delete")) {
            UserPostState userPostState;
            if (stateStr.equals("good")) {
                userPostState = new UserPostState(post, user, 1);
                userPostStateService.deleteUserPostState(userPostState);
            } else if (stateStr.equals("bad")) {
                userPostState = new UserPostState(post, user, 2);
                userPostStateService.deleteUserPostState(userPostState);
            } else if (stateStr.equals("like")) {
                userPostState = new UserPostState(post, user, 3);
                userPostStateService.deleteUserPostState(userPostState);
            } else {
                return new Result(ResultCode.OperateNotExist);
            }
        } else {
            return new Result(ResultCode.OperateNotExist);
        }
        return Result.success();
    }

    @RequiresAuthentication
    @PostMapping("/post/answer")
    public Result answerPost(@RequestBody RequestAnswerDto requestAnswerDto) {
        Post post = postService.getPostById(requestAnswerDto.getPostId());
        Comment comment = commentService.getCommentById(requestAnswerDto.getCommentId());
        Assert.notNull(post, "帖子不存在");
        Assert.notNull(comment, "评论不存在");
        if(!post.getCategory().getName().equals("积分悬赏")) {
            return new Result(ResultCode.PostNotRewardRequest);
        }
        System.out.println(requestAnswerDto);
        Subject subject = SecurityUtils.getSubject();
        int userId = (int)subject.getPrincipal();
        if(post.getUser().getId() != userId && !subject.hasRole("admin") && !subject.hasRole("superadmin")) {
            return new Result(ResultCode.PermissionDenied);
        }
        RequestAnswer requestAnswer = requestAnswerService.getRequestAnswerByPostId(post.getId());
        Assert.isNull(requestAnswer, "该帖子已经存在答案");
        requestAnswer = new RequestAnswer(-1, post, comment);
        requestAnswerService.addRequestAnswer(requestAnswer);
        User answerUser = comment.getUser();
        answerUser.setScore(answerUser.getScore() + post.getReward());
        userService.updateScore(answerUser);
        return Result.success();
    }
}
