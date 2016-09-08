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

import me.lyml.summer.account.entity.User;
import me.lyml.summer.account.service.UserService;
import me.lyml.summer.base.entity.ShiroUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Objects;

/**
 * @ClassName: AuthorizationRealm
 * @author: cnlyml
 * @date: 2016/9/1 9:46
 */
public class AuthorizationRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
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
