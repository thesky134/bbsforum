package top.thesky341.bbsforum.service.impl;

import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.entity.RequestAnswer;
import top.thesky341.bbsforum.mapper.RequestAnswerMapper;
import top.thesky341.bbsforum.service.RequestAnswerService;

import javax.annotation.Resource;

/**
 * @author thesky
 * @date 2020/12/22
 */
@Service
public class RequestAnswerServiceImpl implements RequestAnswerService {
    @Resource
    RequestAnswerMapper requestAnswerMapper;

    @Override
    public RequestAnswer getRequestAnswerByPostId(int postId) {
        return requestAnswerMapper.getRequestAnswerByPostId(postId);
    }

    @Override
    public RequestAnswer addRequestAnswer(RequestAnswer requestAnswer) {
        requestAnswerMapper.addRequestAnswer(requestAnswer);
        return requestAnswerMapper.getRequestAnswerById(requestAnswer.getId());
    }
}
