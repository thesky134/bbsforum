package top.thesk341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesk341.bbsforum.entity.Chara;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Repository
@Mapper
public interface CharaMapper {
    void addChara(Chara chara);
    void deleteCharaById(int id);
    Chara getCharaById(int id);
}
