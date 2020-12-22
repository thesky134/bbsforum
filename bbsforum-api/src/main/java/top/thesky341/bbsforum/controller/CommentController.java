package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.thesky341.bbsforum.dto.CommentDto;
import top.thesky341.bbsforum.dto.PaginationDto;
import top.thesky341.bbsforum.dto.groups.CommentList;
import top.thesky341.bbsforum.dto.groups.PaginationWithCategory;
import top.thesky341.bbsforum.entity.*;
import top.thesky341.bbsforum.service.CommentService;
import top.thesky341.bbsforum.service.PostService;
import top.thesky341.bbsforum.service.UserCommentStateService;
import top.thesky341.bbsforum.service.UserService;
import top.thesky341.bbsforum.util.common.CommentUtil;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;
import top.thesky341.bbsforum.vo.CommentVo;
import top.thesky341.bbsforum.vo.PostInfoVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hyn
 * @date 2020/12/16
 */
@RestController
public class CommentController {
    @Resource(name = "postServiceImpl")
    PostService postService;
    @Resource(name = "userServiceImpl")
    UserService userService;
    @Resource(name = "commentServiceImpl")
    CommentService commentService;
    @Resource(name = "userCommentStateServiceImpl")
    UserCommentStateService userCommentStateService;

    /**
     * 添加评论，评论是有关联帖子的
     */
    @RequiresAuthentication
    @PostMapping("/comment/manage/add")
    public Result addComment(@Valid @RequestBody CommentDto commentDto) {
        Post post = postService.getPostById(commentDto.getPostId());
        Assert.notNull(post, "帖子不存在");
        Assert.isTrue(!post.isHidden(), "帖子不存在");
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserById((int)subject.getPrincipal());
        Comment comment = new Comment(commentDto.getContent(), user, post);
        comment = commentService.addComment(comment);
        if(user.getTodayScore() < 30) {
            user.setScore(user.getScore() + 1);
            user.setTodayScore(user.getTodayScore() + 1);
            userService.updateDailyScore(user);
        }
        return Result.success();
    }

    /**
     * 修改评论
     */
    @RequiresAuthentication
    @PostMapping("/comment/manage/revise")
    public Result reviseComment(@Valid @RequestBody CommentDto commentDto) {
        Comment comment = commentService.getCommentById(commentDto.getId());
        Assert.notNull(comment, "评论不存在");
        comment.setContent(commentDto.getContent());
        commentService.updateContent(comment);
        return Result.success();
    }

    /**
     * 获取某个帖子的评论数量
     */
    @PostMapping("/comment/post/sum")
    public Result getCommentPostSum(@RequestBody CommentDto commentDto) {
        Post post = postService.getPostById(commentDto.getPostId());
        Assert.notNull(post, "关联帖子不存在");
        Assert.isTrue(!post.isHidden(), "帖子不存在");
        int sum = commentService.getCommentSum(commentDto.getPostId(), -1);
        return Result.success("sum", sum);
    }

    /**
     * 以分页的方式获取某个帖子的评论列表
     */
    @PostMapping("/comment/post/list")
    public Result getCommentList(@Validated(CommentList.class) @RequestBody PaginationDto paginationDto) {
        Post post = postService.getPostById(paginationDto.getPostId());
        Assert.notNull(post, "帖子不存在");
        Assert.isTrue(!post.isHidden(), "帖子不存在");
        Pagination pagination = new Pagination();
        pagination.setFrom(paginationDto.getPageSize() * (paginationDto.getPosition() - 1));
        pagination.setNum(paginationDto.getPageSize());
        pagination.setPostId(paginationDto.getPostId());

        List<Comment> comments = commentService.getCommentListByPagination(pagination);
        System.out.println(comments);
        List<CommentVo> commentVos = new ArrayList<>();
        for(int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            CommentVo commentVo = CommentUtil.parseCommentToCommentVo(comment, userCommentStateService);
            commentVos.add(commentVo);
        }
        return Result.success("comments", commentVos);
    }

    /**
     * 用户给评论点赞，踩，喜欢
     * 当赞或喜欢时，踩会被取消
     * 当踩时，赞和喜欢会取消
     * @param stateStr good, bad, like
     * @param operateStr add, delete
     * @param commentId
     */
    @RequiresAuthentication
    @PostMapping("/comment/manage/{stateStr}/{operateStr}/{commentId}")
    public Result changeCommentState(@PathVariable String stateStr, @PathVariable String operateStr, @PathVariable int commentId) {
        Comment comment = commentService.getCommentById(commentId);
        Assert.notNull(comment, "评论不存在");
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserById((int)subject.getPrincipal());
        if(operateStr.equals("add")) {
            UserCommentState userCommentState;
            if(stateStr.equals("good")) {
                userCommentState = new UserCommentState(user, comment, 1);
                userCommentStateService.addUserCommentState(userCommentState);
                userCommentState.setState(2);
                userCommentStateService.deleteUserCommentState(userCommentState);
            } else if(stateStr.equals("bad")) {
                userCommentState = new UserCommentState(user, comment, 2);
                userCommentStateService.addUserCommentState(userCommentState);
                userCommentState.setState(1);
                userCommentStateService.deleteUserCommentState(userCommentState);
                userCommentState.setState(3);
                userCommentStateService.deleteUserCommentState(userCommentState);
            } else if(stateStr.equals("like")) {
                userCommentState = new UserCommentState(user, comment, 3);
                userCommentStateService.addUserCommentState(userCommentState);
                userCommentState.setState(2);
                userCommentStateService.deleteUserCommentState(userCommentState);
            } else {
                return new Result(ResultCode.OperateNotExist);
            }
        } else if(operateStr.equals("delete")) {
            UserCommentState userCommentState;
            if(stateStr.equals("good")) {
                userCommentState = new UserCommentState(user, comment, 1);
                userCommentStateService.deleteUserCommentState(userCommentState);
            } else if(stateStr.equals("bad")) {
                userCommentState = new UserCommentState(user, comment, 2);
                userCommentStateService.deleteUserCommentState(userCommentState);
            } else if(stateStr.equals("like")) {
                userCommentState = new UserCommentState(user, comment, 3);
                userCommentStateService.deleteUserCommentState(userCommentState);
            } else {
                return new Result(ResultCode.OperateNotExist);
            }
        } else {
            return new Result(ResultCode.OperateNotExist);
        }
        return Result.success();
    }
}
