package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.entity.RequestAnswer;

/**
 * @author thesky
 * @date 2020/12/22
 */
@Repository
@Mapper
public interface RequestAnswerMapper {
    void addRequestAnswer(RequestAnswer requestAnswer);
    RequestAnswer getRequestAnswerByPostId(int postId);
    RequestAnswer getRequestAnswerById(int id);
}
