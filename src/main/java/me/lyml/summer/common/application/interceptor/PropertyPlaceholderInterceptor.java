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

package me.lyml.summer.common.application.interceptor;

import me.lyml.summer.common.config.Global;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring MVC 本身在针对返回redirect中没有对adminPath进行相应的注入。
 * 通过拦截器形式进行人为注入
 * @ClassName: PropertyPlaceholderInterceptor
 * @author: cnlyml
 * @date: 2016/9/18 12:54
 */
public class PropertyPlaceholderInterceptor implements WebRequestInterceptor{
    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        String name = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
        Map uriVars = (Map) webRequest.getAttribute(name, 0);
        if(uriVars == null) {uriVars = new HashMap();}
        uriVars.put("adminPath", Global.getConfig("adminPath"));

    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {

    }
}
