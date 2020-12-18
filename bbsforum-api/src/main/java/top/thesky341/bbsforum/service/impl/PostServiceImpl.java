package top.thesky341.bbsforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.Post;
import top.thesky341.bbsforum.mapper.PostMapper;
import top.thesky341.bbsforum.service.PostService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thesky
 * @date 2020/12/11
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostMapper postMapper;

    @Override
    public int getPostSum(int categoryId, int userId) {
        return postMapper.getPostSum(categoryId, userId);
    }

    /**
     * 需要测试
     * 分页获取文章列表，因为涉及了是否置顶，分类等问题，因此弄得比较复杂
     * 直接将分类写在了另一个接口和服务中
     * 然后在服务中封装了置顶，接口中调用时不用关心置不置顶的问题
     * 向外表现为置顶文章在前，未置顶文章在后
     * 在内部则判断获取的文章是否包含置顶文章
     * 会出现获取的文章全为，部分为，一点也没有三种情况
     */
    @Override
    public List<Post> getPostListByPagination(Pagination pagination) {
        if(pagination.getUserId() == -1) {
            int postTopSum = postMapper.getPostTopSum(pagination.getCategoryId());
            System.out.println(postTopSum);
            if(postTopSum - 1 >= pagination.getFrom() + pagination.getNum() - 1) {
                pagination.setTop(1);
                return postMapper.getPostListByPagination(pagination);
            } else if(postTopSum - 1 >= pagination.getFrom()) {
                List<Post> result = new ArrayList<>();
                //需要测试
                pagination.setTop(1);
                result.addAll(postMapper.getPostListByPagination(pagination));
                pagination.setFrom(0);
                pagination.setNum(pagination.getNum() - result.size());
                pagination.setTop(0);
                result.addAll(postMapper.getPostListByPagination(pagination));
                return result;
            } else {
                pagination.setTop(0);
                pagination.setFrom(pagination.getFrom() - postTopSum);
                return postMapper.getPostListByPagination(pagination);
            }
        } else {
            return postMapper.getPostListByPagination(pagination);
        }
    }

    @Override
    public Post getPostById(int id) {
        return postMapper.getPostById(id);
    }

    @Override
    public Post addPost(Post post) {
        postMapper.addPost(post);
        return postMapper.getPostById(post.getId());
    }

    @Override
    public Post revisePost(Post post) {
        postMapper.revisePost(post);
        return postMapper.getPostById(post.getId());
    }

    @Override
    public void updatePostHiddenState(int postId, int state) {
        postMapper.updatePostHiddenState(postId, state);
    }

    @Override
    public void setPostDeleted(int postId) {
        postMapper.setPostDeleted(postId);
    }
}
