package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.entity.Chara;

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
    Chara getGeneralChara();
    Chara getCharaByName(String name);
}
