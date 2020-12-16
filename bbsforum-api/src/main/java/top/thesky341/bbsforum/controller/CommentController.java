package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
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
import top.thesky341.bbsforum.service.UserService;
import top.thesky341.bbsforum.util.result.Result;
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

    @RequiresAuthentication
    @PostMapping("/comment/manage/add")
    public Result addComment(@Valid @RequestBody CommentDto commentDto) {
        Post post = postService.getPostById(commentDto.getPostId());
        Assert.notNull(post, "帖子不存在");
        Subject subject = SecurityUtils.getSubject();
        User user = userService.getUserById((int)subject.getPrincipal());
        Comment comment = new Comment(commentDto.getContent(), user, post);
        comment = commentService.addComment(comment);
        System.out.println(comment);
        return Result.success();
    }

    @PostMapping("/comment/sum")
    public Result getCommentSum(int postId) {
        Post post = postService.getPostById(postId);
        Assert.notNull(post, "关联帖子不存在");
        int sum = commentService.getCommentSumByPostId(postId);
        return Result.success("sum", sum);
    }

    @PostMapping("/comment/list")
    public Result getCommentList(@Validated(CommentList.class) @RequestBody PaginationDto paginationDto) {
        Post post = postService.getPostById(paginationDto.getPostId());
        Assert.notNull(post, "分类不存在");
        Pagination pagination = new Pagination();
        pagination.setFrom(paginationDto.getPageSize() * (paginationDto.getPosition() - 1));
        pagination.setNum(paginationDto.getPageSize());
        pagination.setPostId(paginationDto.getPostId());

        List<Comment> comments = commentService.getCommentListByPagination(pagination);

        List<PostInfoVo> postInfoVos = new ArrayList<>();
//        for (Post post : posts) {
//            int postId = post.getId();
//            int commentSum = commentService.getCommentSumByPostId(postId);
//            int visitSum = userPostStateService.getPostStateSum(postId, 4);
//            postInfoVos.add(new PostInfoVo(post, commentSum, visitSum));
//        }
        return null;
    }
}
