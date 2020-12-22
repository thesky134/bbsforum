package top.thesky341.bbsforum.util.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import top.thesky341.bbsforum.entity.Comment;
import top.thesky341.bbsforum.service.UserCommentStateService;
import top.thesky341.bbsforum.vo.CommentVo;

import javax.annotation.Resource;

/**
 * @author thesky
 * @date 2020/12/22
 */
public class CommentUtil {
    /**
     * 将Comment封装成CommentVo
     * @param comment
     * @param userCommentStateService
     * @return
     */
    public static CommentVo parseCommentToCommentVo(Comment comment, UserCommentStateService userCommentStateService) {
        CommentVo commentVo = new CommentVo();
        commentVo.setContent(comment.getContent());
        commentVo.setId(comment.getId());
        commentVo.setPostTitle(comment.getPost().getTitle());
        commentVo.setPostId(comment.getPost().getId());
        commentVo.setCreateTime(comment.getCreateTime());
        commentVo.setModifyTIme(comment.getModifyTime());
        commentVo.setUser(comment.getUser().getUsername());
        commentVo.setUserId(comment.getUser().getId());
        commentVo.setGoodSum(userCommentStateService.getCommentStateSum(comment.getId(), 1));
        commentVo.setBadSum(userCommentStateService.getCommentStateSum(comment.getId(), 2));
        commentVo.setLikeSum(userCommentStateService.getCommentStateSum(comment.getId(), 3));
        commentVo.setPicture(comment.getUser().getPicture());
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            int userId = (int)subject.getPrincipal();
            commentVo.setGood(userCommentStateService.getUserCommentStateSum(comment.getId(),
                    userId, 1) == 1);
            commentVo.setBad(userCommentStateService.getUserCommentStateSum(comment.getId(),
                    userId, 2) == 1);
            commentVo.setLike(userCommentStateService.getUserCommentStateSum(comment.getId(),
                    userId, 3) == 1);
        }
        return commentVo;
    }
}
