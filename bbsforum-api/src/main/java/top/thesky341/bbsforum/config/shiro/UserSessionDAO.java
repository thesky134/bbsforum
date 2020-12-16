package top.thesky341.bbsforum.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.InitializingBean;
import top.thesky341.bbsforum.config.RedisManager;

import java.io.Serializable;

/**
 * @author thesky
 * @date 2020/12/15
 */
public class UserSessionDAO extends CachingSessionDAO implements InitializingBean {


    private RedisManager redisManager;


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
//        System.out.println("doCreate");
//        System.out.println(sessionId.toString());

        redisManager.set(sessionId.toString(), session, RedisManager.DEFAULT_EXPIRE);
        return sessionId;
    }


    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
//            System.out.println("adsf" + session);
            return; //如果会话过期/停止 没必要再更新了
        }
//        System.out.println("doUpdate");
//        System.out.println(session.getId().toString());
        redisManager.set(session.getId().toString(), session, RedisManager.DEFAULT_EXPIRE);
    }

    @Override
    protected void doDelete(Session session) {
//        System.out.println("doDelete");
        redisManager.delete(session.getId().toString());
    }


    @Override
    protected Session doReadSession(Serializable sessionId) {
//        System.out.println("doReadSession");
//        System.out.println(sessionId.toString());
        return redisManager.get(sessionId.toString(), Session.class);
    }


    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == this.redisManager) {
            System.out.println("StringRedisTemplate should be not null.");
        }

    }


}

