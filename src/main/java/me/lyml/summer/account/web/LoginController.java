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

package me.lyml.summer.account.web;

import me.lyml.summer.base.entity.ShiroUser;
import me.lyml.summer.base.web.BaseController;
import me.lyml.summer.common.security.realm.AuthorizationRealm;
import me.lyml.summer.common.utils.Logs;
import me.lyml.summer.common.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Security;

/**
 * @ClassName: LoginController
 * @author: cnlyml
 * @date: 2016/9/3 14:19
 */
@Controller
public class LoginController extends BaseController {
    @Resource
    private FormAuthenticationFilter formAuthenticationFilter;

    private Logger logger = Logs.get();

    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
    public String login(ModelMap map) {
        if(ShiroUtils.isAuthenticated()) {
            return "redirect:/dashboard/index";
        }else if(ShiroUtils.isRemembered()) {
            ShiroUser shiroUser = ShiroUtils.getShiroUser();
            map.put("isRemember", true);
            map.put("user", shiroUser);
            return "login";
        }else{
            return "login";
        }
    }

    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
    public String fail(HttpServletRequest request, ModelMap map) {
        if(ShiroUtils.isAuthenticated()) {
            return "redirect:${adminPath}/index";
        }

        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            map.put("message", "用户名或密码错误");
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            map.put("message", "用户名或密码错误");
            logger.info("用户{}登录失败，密码错误", WebUtils.getCleanParam(request, formAuthenticationFilter.getUsernameParam()));
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            map.put("message", "登陆失败次数过多，请1小时后再试");
            logger.info("用户{}登陆失败，失败次数过多", WebUtils.getCleanParam(request, formAuthenticationFilter.getUsernameParam()));
        }

        return "login";
    }

}
