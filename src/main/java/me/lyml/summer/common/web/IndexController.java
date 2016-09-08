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

package me.lyml.summer.common.web;

import me.lyml.summer.base.web.BaseController;
import me.lyml.summer.common.security.CustomSessionManager;
import me.lyml.summer.common.security.entity.OnlineUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: IndexController
 * @author: cnlyml
 * @date: 2016/9/6 19:27
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private CustomSessionManager customSessionManager;


    @RequestMapping("/dashboard/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/dashboard/verifyLogin", method = RequestMethod.GET)
    public String verifyLogin(HttpServletRequest request){
        if(request.getHeader("Referer").contains("login")) {

            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);

            OnlineUser onlineUser = customSessionManager.getByUserName(subject.getPrincipal().toString());
            if (onlineUser != null && !onlineUser.getSessionId().equals(session.getId())) {
                customSessionManager.getSessionDAO().readSession(onlineUser.getSessionId()).stop();
            }
        }

        return "redirect:/dashboard/index";
    }
}
