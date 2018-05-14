package com.toskey.framework.common.shiro;

import com.google.common.collect.Lists;
import com.toskey.framework.core.util.Encodes;
import com.toskey.framework.modules.admin.dao.UserDao;
import com.toskey.framework.modules.admin.model.Menu;
import com.toskey.framework.modules.admin.model.Role;
import com.toskey.framework.modules.admin.model.User;
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

/**
 * 系统权限安全认证
 *
 * @author toskey
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permission = Lists.newArrayList();
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

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken shiroToken = (UsernamePasswordToken) authenticationToken;
        String userName = shiroToken.getUsername();
        User user = userDao.getByUserName(userName);
        if(null != user) {
            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
            return new SimpleAuthenticationInfo(user.getLoginName(),
                    user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
        }
        return null;
    }

    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }


}
