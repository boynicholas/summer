/*
 * Copyright 2016 Cnlyml
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lyml.summer.common.security.realm;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.lyml.summer.account.entity.Permission;
import me.lyml.summer.account.entity.Role;
import me.lyml.summer.account.entity.User;
import me.lyml.summer.account.service.RoleService;
import me.lyml.summer.account.service.UserService;
import me.lyml.summer.base.entity.ShiroUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName: AuthorizationRealm
 * @author: cnlyml
 * @date: 2016/9/1 9:46
 */
public class AuthorizationRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
        List<Role> roleList = userService.findRolesByUserID(shiroUser.getId());
        Set<String> permissions = Sets.newHashSet();
        Set<String> roles = Sets.newHashSet();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Role role : roleList) {
            List<Permission> permissionList = roleService.findPermissionsByRoleID(role.getId());
            permissions.addAll(permissionList.stream().map(Permission::getPermissionCode).collect(Collectors.toList()));
            roles.add(role.getId() + "");
        }

        info.addStringPermissions(permissions);
        info.addRoles(roles);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();

        User user = userService.findUserByUserName(userName);
        if(user == null) {
            throw new UnknownAccountException("用户名或密码不正确");
        }

        return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getUserName(), user.getRealName(),
                user.getEmail(), user.getMobileNo(), user.getAvatar()), user.getUserPass(), ByteSource.Util.bytes(user.generateCredentialsSalt()),getName());
    }

}
