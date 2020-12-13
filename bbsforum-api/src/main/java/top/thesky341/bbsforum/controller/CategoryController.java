package top.thesky341.bbsforum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.thesky341.bbsforum.service.CategoryService;
import top.thesky341.bbsforum.util.result.Result;

import javax.annotation.Resource;

/**
 * @author hz
 * @date 2020/12/12
 */
@RestController
public class CategoryController {
    @Resource(name = "categoryServiceImpl")
    CategoryService categoryService;

    @PostMapping("/category/all")
    public Result getAllCategory() {
        return Result.success("categorys", categoryService.getAllCategory());
    }


}
