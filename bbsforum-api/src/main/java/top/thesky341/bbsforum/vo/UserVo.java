package top.thesky341.bbsforum.vo;

import org.hibernate.validator.constraints.Length;
import top.thesky341.bbsforum.entity.Chara;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.entity.groups.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/18
 * 对个人信息进行了封装
 * 用户的敏感信息不会被返回
 */
public class UserVo {
    private int id;
    private String username;
    private String email;
    private String phone;
    private String jobType;
    private String jobLocation;
    private String personalSignal;
    private String picture;
    private String chara;
    private int score;
    private int todayScore;
    private Date createTime;
    private Date lastLoginTime;
    private boolean disabled;
    private String other;

    public UserVo() {
    }

    public UserVo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.jobType = user.getJobType();
        this.jobLocation = user.getJobLocation();
        if(user.getChara() != null) {
            this.chara = user.getChara().getName();
        }
        this.createTime = user.getCreateTime();
        this.lastLoginTime = user.getLastLoginTime();
        this.score = user.getScore();
        this.todayScore = user.getTodayScore();
        this.other = user.getOther();
        this.personalSignal = user.getPersonalSignal();
        this.picture = user.getPicture();
        this.disabled = user.isDisabled();
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

    public String getChara() {
        return chara;
    }

    public void setChara(String chara) {
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
        return "UserVo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", jobType='" + jobType + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", personalSignal='" + personalSignal + '\'' +
                ", picture='" + picture + '\'' +
                ", chara='" + chara + '\'' +
                ", score=" + score +
                ", todayScore=" + todayScore +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", disabled=" + disabled +
                ", other='" + other + '\'' +
                '}';
    }
}
