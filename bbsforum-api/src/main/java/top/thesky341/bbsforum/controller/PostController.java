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
import top.thesky341.bbsforum.entity.Category;
import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.Post;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.service.*;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;
import top.thesky341.bbsforum.vo.PostInfoVo;
import top.thesky341.bbsforum.vo.PostVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
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

    @PostMapping("/post/all/sum")
    public Result getAllPostSum() {
        return Result.success("sum", postService.getAllPostSum());
    }

    /**
     * 对需要返回的文章信息使用了 PostInfoVo 封装，并写在 posts 列表里
     * @param paginationDto 包含了要获取的页数和大小
     * @return 包含了获取的文章列表，元素为 PostInfoVo
     */
    @PostMapping("/post/all/list")
    public Result getPostList(@Valid @RequestBody PaginationDto paginationDto) {
        Pagination pagination = new Pagination();
        pagination.setFrom(paginationDto.getPageSize() * (paginationDto.getPosition() - 1));
        pagination.setNum(paginationDto.getPageSize());
        List<Post> posts = postService.getPostListByPagination(pagination);
        List<PostInfoVo> postInfoVos = new ArrayList<>();
        for(Post post : posts) {
            int postId = post.getId();
            int commentSum = commentService.getCommentSumByPostId(postId);
            int visitSum = userPostStateService.getPostStateSum(postId, 4);
            postInfoVos.add(new PostInfoVo(post, commentSum, visitSum));
        }
        return Result.success("posts", postInfoVos);
    }

    @PostMapping("/post/category/sum")
    public Result getPostSumWithCategory(int id) {
        Category category = categoryService.getCategoryById(id);
        Assert.notNull(category, "分类不存在");
        return Result.success("sum", postService.getPostSumWithCategory(id));
    }

    @PostMapping("/post/category/list")
    public Result getPostListWithCategory(@Validated(PaginationWithCategory.class) @RequestBody PaginationDto paginationDto) {
        Category category = categoryService.getCategoryById(paginationDto.getCategoryId());
        Assert.notNull(category, "分类不存在");
        Pagination pagination = new Pagination();
        pagination.setFrom(paginationDto.getPageSize() * (paginationDto.getPosition() - 1));
        pagination.setNum(paginationDto.getPageSize());
        pagination.setCategoryId(paginationDto.getCategoryId());



        List<Post> posts = postService.getPostListByPaginationWithCategory(pagination);
        List<PostInfoVo> postInfoVos = new ArrayList<>();
        for (Post post : posts) {
            int postId = post.getId();
            int commentSum = commentService.getCommentSumByPostId(postId);
            int visitSum = userPostStateService.getPostStateSum(postId, 4);
            postInfoVos.add(new PostInfoVo(post, commentSum, visitSum));
        }
        return Result.success("posts", postInfoVos);
    }

    @GetMapping("/post/view/{id}")
    public Result postView(@PathVariable int id) {
        Post post = postService.getPostById(id);
        Assert.notNull(post, "帖子不存在");
        PostVo postVo = new PostVo();
        postVo.parsePost(post);
        postVo.setVisitSum(userPostStateService.getPostStateSum(id, 4));
        postVo.setCommentSum(commentService.getCommentSumByPostId(id));
        postVo.setGoodSum(userPostStateService.getPostStateSum(id, 1));
        postVo.setBadSum(userPostStateService.getPostStateSum(id, 2));
        postVo.setLikeSum(userPostStateService.getPostStateSum(id, 3));
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            int userId = (int)subject.getPrincipal();
            postVo.setGood(userPostStateService.getUserPostStateState(id, userId, 1));
            postVo.setBad(userPostStateService.getUserPostStateState(id, userId, 2));
            postVo.setLike(userPostStateService.getUserPostStateState(id, userId, 3));
        }
        return Result.success("post", postVo);
    }

    /**
     * 添加帖子, 注意帖子可能为积分悬赏分类的
     */
    @RequiresAuthentication
    @PostMapping("/post/add")
    public Result addRewardPost(@Valid @RequestBody PostDto postDto) {
        int userId = (int)SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserById(userId);
        Category category = categoryService.getCategoryByName(postDto.getCategory());
        Assert.notNull(category, "对应分类不存在");
        if(category.getName().equals("积分悬赏")) {
            if(postDto.getReward() <= 0) {
                return new Result(ResultCode.RewardNotGreater0);
            }
        }
        Post post = new Post(postDto, user, category);
        postService.addPost(post);
        return Result.success();
    }

//    @RequiresAuthentication
//    @PostMapping("/post/revise/normal")
}
