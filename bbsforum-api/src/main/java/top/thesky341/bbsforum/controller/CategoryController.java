package top.thesky341.bbsforum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.thesky341.bbsforum.entity.Category;
import top.thesky341.bbsforum.service.CategoryService;
import top.thesky341.bbsforum.service.PostService;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.vo.CategoryVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hz
 * @date 2020/12/12
 */
@RestController
public class CategoryController {
    @Resource(name = "categoryServiceImpl")
    CategoryService categoryService;
    @Resource(name = "postServiceImpl")
    PostService postService;

    @PostMapping("/category/all")
    public Result getAllCategory() {
        List<Category> categories =  categoryService.getAllCategory();
        List<CategoryVo> categoryVos = new ArrayList<>();
        for(Category category : categories) {
            CategoryVo categoryVo = new CategoryVo(category, postService.getPostSum(category.getId(), -1, 0));
            categoryVos.add(categoryVo);
        }
        return Result.success("categorys", categoryVos);
    }


}
