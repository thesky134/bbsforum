package top.thesk341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesk341.bbsforum.entity.Category;

/**
 * @author thesky
 * @date 2020/12/8
 */
@Repository
@Mapper
public interface CategoryMapper {
    void addCategory(Category category);
    void deleteCategoryById(int id);
    Category getCategoryById(int id);
}
