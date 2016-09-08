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

package me.lyml.summer.common.application;

import me.lyml.summer.common.security.SpringCacheManagerWrapper;
import me.lyml.summer.common.utils.Logs;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import javax.swing.*;

/**
 * @ClassName: SystemInitialization
 * @author: cnlyml
 * @date: 2016/9/6 13:52
 */
public class SystemInitialization implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private SpringCacheManagerWrapper cacheManager;

    private Logger logger = Logs.get();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            logger.info("----------------------------------------");
            logger.info("      Application Initialization...     ");
            logger.info("----------------------------------------");
        }
    }

    class Initialization {

        private void initCache() {

        }
    }
}
