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

package me.lyml.summer.common.utils;

import me.lyml.summer.base.entity.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;

/**
 * @ClassName: ShiroUtils
 * @author: cnlyml
 * @date: 2016/9/6 19:50
 */
public class ShiroUtils {
    private Logger logger = Logs.get();

    /**
     * 获取当前登录用户
     * @return 登陆用户
     */
    public static ShiroUser getShiroUser() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated() || subject.isRemembered()) {
            return (ShiroUser) subject.getPrincipal();
        }

        return new ShiroUser();
    }

    /**
     * 是否已登陆
     * @return 登陆状态
     */
    public static boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static boolean isRemembered() {
        return SecurityUtils.getSubject().isRemembered();
    }

    /**
     * 是否有指定权限
     * @param permission 权限名称
     * @return boolean
     */
    public static boolean hasPermission(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    /**
     * 是否有指定角色
     * @param roleName 角色名称
     * @return boolean
     */
    public static boolean hasRole(String roleName) {
        return SecurityUtils.getSubject().hasRole(roleName);
    }

}
