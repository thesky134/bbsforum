package top.thesky341.bbsforum.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thesky
 * @date 2020/12/8
 */
@RestController
public class HelloWorldController {
    @RequiresRoles("superadmin")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World";
    }
}
