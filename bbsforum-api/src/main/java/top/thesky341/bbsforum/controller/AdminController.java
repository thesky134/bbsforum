package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.thesky341.bbsforum.config.shiro.UserSessionManager;
import top.thesky341.bbsforum.dto.PaginationDto;
import top.thesky341.bbsforum.entity.Pagination;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.entity.groups.Login;
import top.thesky341.bbsforum.service.UserService;
import top.thesky341.bbsforum.util.result.Result;
import top.thesky341.bbsforum.util.result.ResultCode;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author cj
 * @date 2020/12/16
 */
@RestController
public class AdminController {
    @Resource(name = "userServiceImpl")
    UserService userService;

    @PostMapping("/admin/manage/login")
    public Result adminUserLogin(@Validated(Login.class) @RequestBody User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPasswd());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        user = userService.getUserByUsername(user.getUsername());
        if(!(subject.hasRole("admin") || subject.hasRole("superadmin"))) {
            return new Result(ResultCode.PermissionDenied);
        }
        return Result.success(UserSessionManager.OAUTH_TOKEN, subject.getSession().getId());
    }

    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/user/list")
    public Result getUserList(@Valid @RequestBody PaginationDto paginationDto) {
        Pagination pagination = new Pagination(paginationDto.getPageSize() * (paginationDto.getPosition() - 1),
                paginationDto.getPageSize());
        List<User> users = userService.getUserListByPagination(pagination);
//        List<U>
    }

    @RequiresRoles(value = {"admin", "superadmin"}, logical = Logical.OR)
    @PostMapping("/admin/user/sum")
    public Result getUserSum() {
        return Result.success("sum", userService.getUserSum());
    }
}
