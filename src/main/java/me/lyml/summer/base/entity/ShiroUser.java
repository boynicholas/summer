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

package me.lyml.summer.base.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @ClassName: ShiroUser
 * @author: cnlyml
 * @date: 2016/9/7 10:44
 */
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = -6287991482923724239L;

    private Long id;
    private String userName;
    private String realName;
    private String email;
    private String mobileNo;
    private String avatar;

    public ShiroUser() {}

    public ShiroUser(ShiroUser shiroUser) {
        this.id = shiroUser.id;
        this.userName = shiroUser.userName;
        this.realName = shiroUser.realName;
        this.email = shiroUser.email;
        this.mobileNo = shiroUser.mobileNo;
        this.avatar = shiroUser.avatar;
    }

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
        return userName;
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
