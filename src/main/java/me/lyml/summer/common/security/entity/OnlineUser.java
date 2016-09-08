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

package me.lyml.summer.common.security.entity;

import me.lyml.summer.base.entity.ShiroUser;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: OnlineUser
 * @author: cnlyml
 * @date: 2016/9/7 20:24
 */
public class OnlineUser extends ShiroUser implements Serializable {

    private static final long serialVersionUID = 6372243818227970944L;

    // sessionID
    private String sessionId;

    // session host
    private String host;

    // session 创建时间
    private Date startTime;

    // session 最后交互时间
    private Date lastAccessTime;

    // session timeout
    private long timeout;

    // session状态(是否踢出)
    private boolean sessionStatus = Boolean.TRUE;

    public OnlineUser() {

    }

    public OnlineUser(ShiroUser shiroUser) {
        super(shiroUser);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(boolean sessionStatus) {
        this.sessionStatus = sessionStatus;
    }
}
