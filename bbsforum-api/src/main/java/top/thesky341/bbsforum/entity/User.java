package top.thesky341.bbsforum.entity;

import org.hibernate.validator.constraints.Length;
import top.thesky341.bbsforum.entity.groups.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/8
 */
public class User {
    private int id;
    @NotNull(message = "用户名必须存在", groups = {Register.class, Login.class, UpdateUsername.class})
    @Length(min = 1, max = 20, message = "用户名长度应该在1至20之间", groups = {Register.class, Login.class, UpdateUsername.class})
    @Pattern(regexp = "^[^\\s]+$", message = "用户名不能包含空白字符", groups = {Register.class, Login.class, UpdateUsername.class})
    private String username;
    @NotNull(message = "邮箱必须存在", groups = {Register.class, UpdateEmail.class})
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "邮箱格式错误", groups = {Register.class, UpdateEmail.class})
    private String email;
    @NotNull(message = "密码必须存在", groups = {Register.class, Login.class})
    @Length(min = 6, max = 26, message = "密码长度应该在6至26之间", groups = {Register.class, Login.class})
    @Pattern(regexp = "^[^\\s]+$", message = "密码不能包含空白字符", groups = {Register.class, Login.class})
    private String passwd;
    private String salt;
    @NotNull(message = "联系方式必须存在", groups = {UpdatePhone.class})
    @Length(min = 3, max = 20, message = "联系方式长度应该在3至20之间", groups = {UpdatePhone.class})
    @Pattern(regexp = "^[0-9]+$", message = "联系方式不能出现非数字字符", groups = {UpdatePhone.class})
    private String phone;
    @NotNull(message = "工作性质必须存在", groups = {UpdateJobType.class})
    @Length(min = 2, max = 30, message = "工作性质长度应该在2至30之间", groups = {UpdateJobType.class})
    private String jobType;
    @NotNull(message = "工作地点必须存在", groups = {UpdateJobLocation.class})
    @Length(min = 2, max = 60, message = "工作地点长度应该在2至60之间", groups = {UpdateJobLocation.class})
    private String jobLocation;
    @NotNull(message = "个性签名必须存在", groups = {UpdatePersonalSignal.class})
    @Length(max = 100, message = "个性签名长度应该不大于100个字符", groups = {UpdatePersonalSignal.class})
    private String personalSignal;
    private String picture;
    private Chara chara;
    private int score;
    private int todayScore;
    private Date createTime;
    private Date lastLoginTime;
    private boolean disabled;
    private String other;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getPersonalSignal() {
        return personalSignal;
    }

    public void setPersonalSignal(String personalSignal) {
        this.personalSignal = personalSignal;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Chara getChara() {
        return chara;
    }

    public void setChara(Chara chara) {
        this.chara = chara;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTodayScore() {
        return todayScore;
    }

    public void setTodayScore(int todayScore) {
        this.todayScore = todayScore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", salt='" + salt + '\'' +
                ", phone='" + phone + '\'' +
                ", jobType='" + jobType + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", personalSignal='" + personalSignal + '\'' +
                ", picture='" + picture + '\'' +
                ", chara=" + chara +
                ", score=" + score +
                ", todayScore=" + todayScore +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", disabled=" + disabled +
                ", other='" + other + '\'' +
                '}';
    }
}
