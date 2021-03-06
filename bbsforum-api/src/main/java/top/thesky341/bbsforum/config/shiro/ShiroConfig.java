package top.thesky341.bbsforum.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import top.thesky341.bbsforum.config.RedisManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author thesky
 * @date 2020/12/9
 */
@Configuration
public class ShiroConfig {
    /**
     * 设置凭证匹配时用的算法：MD5 盐值加密 3 次
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        String algorithmName = "MD5";
        int iterations = 3;
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(algorithmName);
        credentialsMatcher.setHashIterations(iterations);

        return credentialsMatcher;
    }

    /**
     * userReaml 设置凭证匹配方式
     */
    @Bean
    public UserRealm userRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }

    @Bean
    public UserSessionManager userSessionManager(@Qualifier("userSessionDAO") UserSessionDAO userSessionDAO) {
        UserSessionManager userSessionManager = new UserSessionManager();
        userSessionManager.setSessionDAO(userSessionDAO);
        return userSessionManager;
    }


    @Bean
    public UserSessionDAO userSessionDAO(RedisManager redisManager) {
        UserSessionDAO userSessionDAO = new UserSessionDAO();
        userSessionDAO.setRedisManager(redisManager);
        return userSessionDAO;
    }


    /**
     * securityManager 设置 userRealm
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm, @Qualifier("userSessionManager") UserSessionManager userSessionManager) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(userSessionManager);
        return securityManager;
    }

    /**
     * shiroFileterFactory 设置了 SecurityManager
     * 设置了权限，项目实际需要权限是通过注解方式声明的
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> m = new HashMap<>();
        m.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(m);
        shiroFilterFactoryBean.setLoginUrl("/shiro/login");
        return shiroFilterFactoryBean;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     　　* 开启shiro aop注解支持.
     　　* 使用代理方式;所以需要开启代码支持;
     　　*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);

        return authorizationAttributeSourceAdvisor;
    }
}
