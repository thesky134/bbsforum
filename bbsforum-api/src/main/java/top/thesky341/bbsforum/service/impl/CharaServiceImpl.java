package top.thesky341.bbsforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thesky341.bbsforum.entity.Chara;
import top.thesky341.bbsforum.mapper.CharaMapper;
import top.thesky341.bbsforum.service.CharaService;

/**
 * @author thesky
 * @date 2020/12/21
 */
@Service
public class CharaServiceImpl implements CharaService {
    @Autowired
    CharaMapper charaMapper;

    @Override
    public Chara getCharaByName(String name) {
        return charaMapper.getCharaByName(name);
    }
}
