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

package me.lyml.summer.common.application.websocket.entity;

import me.lyml.summer.base.entity.ShiroUser;

/**
 * @ClassName: SocketMessage
 * @author: cnlyml
 * @date: 2016/9/10 21:24
 */
public class SocketMessage {
    private Integer sendType;
    private ShiroUser shiroUser;
    private Integer messageType;
    private String title;
    private String message;

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }
}
