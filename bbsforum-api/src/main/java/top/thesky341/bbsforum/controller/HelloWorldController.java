package top.thesky341.bbsforum.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.thesky341.bbsforum.util.common.RandomGenerateString;
import top.thesky341.bbsforum.util.result.Result;

import java.io.*;

/**
 * @author thesky
 * @date 2020/12/8
 */
@RestController
public class HelloWorldController {
    @Value("${imgAbsolutePathPrefix}")
    private String pathPrefix;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World";
    }

    @GetMapping("/role")
    public Result roleTest() {
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.isAuthenticated());
        System.out.println(subject.hasRole("general"));
        System.out.println(subject.hasRole("admin"));
        return Result.success();
    }

    @PostMapping("/uploadimg")
    public Result uploadImg(MultipartFile file) throws IOException {
        System.out.println(file);
        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());
        String imgName = RandomGenerateString.generateString(3) + ".png";
        OutputStream os = new FileOutputStream(new File(pathPrefix + imgName));
        InputStream fis = file.getInputStream();
        try {
            os.write(fis.readAllBytes());
        } finally {
            os.close();
            fis.close();
        }
        return Result.success();
    }
}
