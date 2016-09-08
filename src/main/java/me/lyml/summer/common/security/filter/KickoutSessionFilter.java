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

package me.lyml.summer.common.security.filter;

import me.lyml.summer.common.security.CustomSessionManager;
import me.lyml.summer.common.security.entity.OnlineUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: KickoutSessionFilter
 * @author: cnlyml
 * @date: 2016/9/7 21:33
 */
public class KickoutSessionFilter extends AccessControlFilter{
    private String kickoutUrl;

    private CustomSessionManager customSessionManager;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Subject subject = getSubject(request, response);
        Session session = subject.getSession(false);

        OnlineUser onlineUser = customSessionManager.getByUserName(subject.getPrincipal().toString());
        if(onlineUser != null && !onlineUser.getSessionId().equals(session.getId())) {
            subject.logout();
            /**
             * AjAx判断
             */
            if("XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With"))){

            } else {
                WebUtils.issueRedirect(request, response, kickoutUrl);
            }
            return false;

        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    public void setCustomSessionManager(CustomSessionManager customSessionManager) {
        this.customSessionManager = customSessionManager;
    }

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }
}
