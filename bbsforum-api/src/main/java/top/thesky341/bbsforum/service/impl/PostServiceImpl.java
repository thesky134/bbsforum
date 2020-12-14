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
    public int getAllPostSum() {
        return postMapper.getAllPostSum();
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
        int postTopSum = postMapper.getPostTopSum();
        if(postTopSum - 1 >= pagination.getFrom() + pagination.getNum() - 1) {
            return postMapper.getPostTopListByPagination(pagination);
        } else if(postTopSum - 1 >= pagination.getFrom()) {
            List<Post> result = new ArrayList<>();
            //需要测试
            result.addAll(postMapper.getPostTopListByPagination(pagination));
            System.out.println(result.size());
            pagination.setFrom(0);
            pagination.setNum(pagination.getNum() - result.size());
            result.addAll(postMapper.getPostNotTopListByPagination(pagination));
            return result;
        } else {
            pagination.setFrom(pagination.getFrom() - postTopSum);
            return postMapper.getPostNotTopListByPagination(pagination);
        }
    }

    @Override
    public List<Post> getPostListByPaginationWithCategory(Pagination pagination) {
        int postTopSum = postMapper.getPostTopSumWithCategory(pagination.getCategoryId());
        if(postTopSum - 1 >= pagination.getFrom() + pagination.getNum() - 1) {
            return postMapper.getPostTopListByPaginationWithCategory(pagination);
        } else if(postTopSum - 1 >= pagination.getFrom()) {
            List<Post> result = new ArrayList<>();
            //需要测试
            result.addAll(postMapper.getPostTopListByPaginationWithCategory(pagination));
            System.out.println(result.size());
            pagination.setFrom(0);
            pagination.setNum(pagination.getNum() - result.size());
            result.addAll(postMapper.getPostNotTopListByPaginationWithCategory(pagination));
            return result;
        } else {
            pagination.setFrom(pagination.getFrom() - postTopSum);
            return postMapper.getPostNotTopListByPaginationWithCategory(pagination);
        }
    }

    @Override
    public Post getPostById(int id) {
        return postMapper.getPostById(id);
    }

    @Override
    public int getPostSumWithCategory(int categoryId) {
        return postMapper.getPostSumWithCategory(categoryId);
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
}
