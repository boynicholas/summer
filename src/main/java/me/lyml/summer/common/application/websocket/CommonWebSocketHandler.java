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

package me.lyml.summer.common.application.websocket;

import me.lyml.summer.base.entity.ShiroUser;
import me.lyml.summer.common.security.SpringCacheManagerWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.annotation.Resource;
import javax.swing.*;
import java.lang.reflect.Field;
import java.security.Principal;

/**
 * @ClassName: CommonWebSocketHandler
 * @author: cnlyml
 * @date: 2016/9/9 16:51
 */
public class CommonWebSocketHandler implements WebSocketHandler {

    private Cache<ShiroUser, WebSocketSession> websocketSessionCache;

    public CommonWebSocketHandler(SpringCacheManagerWrapper cacheManager) {
        websocketSessionCache = cacheManager.getCache("webSocketCache");
    }

    /**
     * 建立连接后
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

        // 这里获取到的是ShiroHttpServletRequest#ObjectPrincipal的内部类。从外界不知道怎么访问，直接反射获取
        Principal principal = webSocketSession.getPrincipal();
        Field field = principal.getClass().getDeclaredField("object");
        field.setAccessible(true);
        ShiroUser shiroUser = (ShiroUser) field.get(principal);

        if(websocketSessionCache.get(shiroUser) == null) {
            websocketSessionCache.put(shiroUser, webSocketSession);
        }

    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    /**
     * session连接关闭
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        if(webSocketSession.getPrincipal() instanceof ShiroUser) {
            ShiroUser shiroUser = (ShiroUser) webSocketSession.getPrincipal();
            websocketSessionCache.remove(shiroUser);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
