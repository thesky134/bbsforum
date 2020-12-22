package top.thesky341.bbsforum.service;

import top.thesky341.bbsforum.entity.RequestAnswer;

/**
 * @author thesky
 * @date 2020/12/22
 */
public interface RequestAnswerService {
    RequestAnswer getRequestAnswerByPostId(int postId);
    RequestAnswer addRequestAnswer(RequestAnswer requestAnswer);
}
