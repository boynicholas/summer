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

package me.lyml.summer.common.security;

import me.lyml.summer.base.entity.ShiroUser;
import me.lyml.summer.common.security.entity.OnlineUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: CustomSessionManager
 * @author: cnlyml
 * @date: 2016/9/7 20:33
 */
public class CustomSessionManager {

    public static final String SESSION_STATUS = CustomSessionManager.class.getName() + "_ONLINE_STATUS";
    private SessionDAO sessionDAO;



    /**
     * 获取所有在线用户(包含remember)
     * @return
     */
    public List<OnlineUser> getAll() {
        Collection<Session> onlineSessionList = sessionDAO.getActiveSessions();
        List<OnlineUser> list = new ArrayList<>();

        for(Session session : onlineSessionList) {
            OnlineUser onlineUser = get(session);
            if(onlineUser != null) {
                list.add(onlineUser);
            }
        }

        return list;
    }

    public OnlineUser getByUserName(String userName) {
        List<OnlineUser> onlineUsers = getAll();
        for(OnlineUser onlineUser : onlineUsers) {
            if(onlineUser.getUserName().equals(userName)) {
                return onlineUser;
            }
        }

        return null;
    }

    public OnlineUser get(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        return session == null ? null : get(session);
    }

    private OnlineUser get(Session session) {
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

        if(obj == null) {
            return null;
        }
        if(obj instanceof SimplePrincipalCollection) {
            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
            obj = spc.getPrimaryPrincipal();

            if(obj != null && obj instanceof ShiroUser) {
                OnlineUser onlineUser = new OnlineUser((ShiroUser) obj);
                onlineUser.setHost(session.getHost());
                onlineUser.setSessionId(session.getId().toString());
                onlineUser.setLastAccessTime(session.getLastAccessTime());
                onlineUser.setTimeout(session.getTimeout());
                onlineUser.setStartTime(session.getStartTimestamp());


                return onlineUser;
            }

        }

        return null;
    }


    public void setSessionDAO(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public SessionDAO getSessionDAO() {
        return sessionDAO;
    }
}
