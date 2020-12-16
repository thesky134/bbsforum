package top.thesky341.bbsforum.config.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import top.thesky341.bbsforum.entity.User;
import top.thesky341.bbsforum.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * @author thesky
 * @date 2020/12/9
 */
public class UserRealm extends AuthorizingRealm {
    @Resource
    UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 登录认证
     * @param token 保存有用户名、密码等信息
     * 这里用于获取 AuthenticationInfo, 之后还需要在另一个进行密码匹配
     * 注意 Principal 存储用户 Id
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String name = ((UsernamePasswordToken)token).getUsername();
        User user = userMapper.getUserByUsername(name);
        if(user == null) {
            throw new UnknownAccountException();
        } else if(user.isDisabled() == true) {
            throw new LockedAccountException();
        } else {
            ByteSource salt = ByteSource.Util.bytes(user.getSalt());
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getId(), user.getPasswd(), salt, getName());
            return authenticationInfo;
        }
    }
}
