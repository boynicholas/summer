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
import org.apache.shiro.cache.Cache;
import org.springframework.web.socket.WebSocketSession;

/**
 * @ClassName: WebSocketService
 * @author: cnlyml
 * @date: 2016/9/9 21:00
 */
public class WebSocketService {

    private Cache<ShiroUser, WebSocketSession> cache;

    public WebSocketService(SpringCacheManagerWrapper springCacheManagerWrapper) {
         cache = springCacheManagerWrapper.getCache("websocketCache");
    }

    public void sendMessage(ShiroUser user) {
        WebSocketSession session = cache.get(user);
        if(session != null) {
            /*session.sendMessage();*/
        }

    }
}
