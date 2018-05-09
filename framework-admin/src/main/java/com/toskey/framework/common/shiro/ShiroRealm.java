package com.toskey.framework.common.shiro;

import com.google.common.collect.Lists;
import com.toskey.framework.modules.admin.model.Menu;
import com.toskey.framework.modules.admin.model.Role;
import com.toskey.framework.modules.admin.model.User;
import com.toskey.framework.modules.admin.service.IUserService;
import com.toskey.framework.modules.admin.util.LoginUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 系统权限安全认证
 *
 * @author toskey
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permission = Lists.newArrayList();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roleList = user.getRoleList();
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
        ShiroToken shiroToken = (ShiroToken) authenticationToken;
        String userName = shiroToken.getUsername();
        User user = userService.queryByUserName(userName);
        ByteSource credSalt = new Md5Hash(user.getSalt());
        return new SimpleAuthenticationInfo(user, user.getPassword(), credSalt, this.getClass().getName());
    }

}
