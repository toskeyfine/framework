package com.toskey.framework.common.shiro;

import com.google.common.collect.Lists;
import com.toskey.framework.core.util.Encodes;
import com.toskey.framework.modules.admin.dao.UserDao;
import com.toskey.framework.modules.admin.model.Menu;
import com.toskey.framework.modules.admin.model.Role;
import com.toskey.framework.modules.admin.model.User;
import com.toskey.framework.modules.admin.model.UserToken;
import com.toskey.framework.modules.admin.service.IUserService;
import com.toskey.framework.modules.admin.util.LoginUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 系统权限安全认证
 *
 * @author toskey
 */
@Component
public class ShiroRealm extends AuthorizingRealm {



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroUserToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();
        String userId = user.getId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roleList = LoginUtils.queryRoleByUser(user.getId());
        if(roleList.size() > 0) {
            for(Role role : roleList) {
                List<Menu> menuList = LoginUtils.queryMenuByRole(role.getId());
                for(Menu menu : menuList) {
                    info.addStringPermission(menu.getPermission());
                }
                info.addRole(role.getEnName());
            }
        }
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String accessToken = (String) authenticationToken.getPrincipal();

        //根据accessToken，查询用户信息
        UserToken token = LoginUtils.queryByUserToken(accessToken);
        //token失效
        if(token == null || token.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        //查询用户信息
        User user = LoginUtils.queryById(token.getUserId());

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, getName());
        return info;
    }
}
