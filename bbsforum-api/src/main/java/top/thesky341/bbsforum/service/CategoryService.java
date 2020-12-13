package top.thesky341.bbsforum.service;

import top.thesky341.bbsforum.entity.Category;

import java.util.List;

/**
 * @author hz
 * @date 2020/12/12
 */
public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategoryById(int id);
}
