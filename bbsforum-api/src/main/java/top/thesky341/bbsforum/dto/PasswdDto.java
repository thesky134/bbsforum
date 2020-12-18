package top.thesky341.bbsforum.dto;

import org.hibernate.validator.constraints.Length;
import top.thesky341.bbsforum.entity.groups.Login;
import top.thesky341.bbsforum.entity.groups.Register;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author thesky
 * @date 2020/12/18
 */
public class PasswdDto {
    @NotNull(message = "原密码必须存在")
    private String oldPasswd;
    @NotNull(message = "新密码必须存在")
    @Length(min = 6, max = 26, message = "新密码长度应该在6至26之间", groups = {Register.class, Login.class})
    @Pattern(regexp = "^[^\\s]+$", message = "新密码不能包含空白字符", groups = {Register.class, Login.class})
    private String newPasswd;

    public PasswdDto() {
    }

    public PasswdDto(String oldPasswd, String newPasswd) {
        this.oldPasswd = oldPasswd;
        this.newPasswd = newPasswd;
    }

    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

    @Override
    public String toString() {
        return "PasswdDto{" +
                "oldPasswd='" + oldPasswd + '\'' +
                ", newPasswd='" + newPasswd + '\'' +
                '}';
    }
}
