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

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.io.Serializable;

/**
 * @ClassName: SpringUuidSessionIdGenerator
 * @author: cnlyml
 * @date: 2016/9/5 10:03
 */
public class SpringUuidSessionIdGenerator implements SessionIdGenerator {
    private AlternativeJdkIdGenerator alternativeJdkIdGenerator;

    public SpringUuidSessionIdGenerator() {
        alternativeJdkIdGenerator = new AlternativeJdkIdGenerator();
    }
    @Override
    public Serializable generateId(Session session) {

        return alternativeJdkIdGenerator.generateId().toString();
    }
}
