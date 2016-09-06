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

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getUserName(), user.getRealName(),
                user.getEmail(), user.getMobileNo(), user.getAvatar()), user.getUserPass(), ByteSource.Util.bytes(user.generateCredentialsSalt()),getName());

        return authenticationInfo;
    }

    public static class ShiroUser implements Serializable {
        private Long id;
        private String userName;
        private String realName;
        private String email;
        private String mobileNo;
        private String avatar;

        public ShiroUser(Long id, String userName, String realName, String email, String mobileNo, String avatar) {
            this.id = id;
            this.userName = userName;
            this.realName = realName;
            this.email = email;
            this.mobileNo = mobileNo;
            this.avatar = avatar;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        /**
         * 本函数作为默认的<shiro:principal />输出
         * @return
         */
        @Override
        public String toString() {
            return realName;
        }

        /**
         * 重载equals,只计算userName
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (userName == null) {
                if (other.userName != null) {
                    return false;
                }
            } else if (!userName.equals(other.userName)) {
                return false;
            }
            return true;
        }

        /**
         * 重载hashCode，只计算userName
         * @return
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(userName);
        }

    }

}
