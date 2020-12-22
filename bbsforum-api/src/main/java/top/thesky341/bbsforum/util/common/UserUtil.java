package top.thesky341.bbsforum.util.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;

import java.util.Calendar;
import java.util.Date;

/**
 * @author thesky
 * @date 2020/12/9
 */
public class UserUtil {
    /**
     * 检查传入时间是否为今天之前的时间
     * 用于判断今天是否登录过
     * @param date 待检查的时间
     */
    public static boolean checkIsBeforeToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar now = Calendar.getInstance();
        if(calendar.get(Calendar.YEAR) < now.get(Calendar.YEAR)) {
            return true;
        } else if(calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) < now.get(Calendar.MONTH)) {
            return true;
        } else if(calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && calendar.get(Calendar.DAY_OF_MONTH) < now.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    /**
     * 得到当前用户的id
     */
    @RequiresAuthentication
    public static int getCurrentUserId() {
        Subject subject = SecurityUtils.getSubject();
        String principal = subject.getPrincipal().toString();
        return Integer.parseInt(principal);
    }
}
