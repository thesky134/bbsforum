package top.thesky341.bbsforum.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.thesky341.bbsforum.entity.Category;

import java.util.List;

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
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
}
