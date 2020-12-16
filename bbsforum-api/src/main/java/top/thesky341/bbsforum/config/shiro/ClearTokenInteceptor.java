package top.thesky341.bbsforum.config.shiro;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.thesky341.bbsforum.config.RedisManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author thesky
 * @date 2020/12/15
 */
public class ClearTokenInteceptor extends HandlerInterceptorAdapter {

    private static final String SESSION_TIME_OUT_K = "SESSION_TIME_OUT";
    private static final String SESSION_TIME_OUT_V = "timeout";

    @Autowired
    private RedisManager redisManager;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = request.getHeader(UserSessionManager.OAUTH_TOKEN);

        if (null != token) {
            Session s = redisManager.get(token, Session.class);

            if (null == s || null == s.getId()) {
                response.setHeader(SESSION_TIME_OUT_K, SESSION_TIME_OUT_V);
            }
        }

        return super.preHandle(request, response, handler);
    }
}