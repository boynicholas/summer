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

package me.lyml.summer.test.spring;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.List;

/**
 * @ClassName: JettyFactory
 * @author: cnlyml
 * @date: 2016/8/30 10:24
 */
public class JettyFactory {
    private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
    private static final String WINDOWS_WEBDEFAULT_PATH = "jetty/webdefault-windows.xml";

    /**
     * 创建用于开发调试的Jetty Server,以src/main/webapp为WEB应用目录
     * @param port 端口号
     * @param contextPath Web访问路径
     * @return
     */
    public static Server createServerInSource(int port, String contextPath) {
        Server server = new Server();

        // 设置在JVM退出时关闭Jetty的钩子.
        server.setStopAtShutdown(true);

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);

        // 解决windows下重复启动Jetty不报告端口冲突的问题
        connector.setReuseAddress(false);
        server.setConnectors(new Connector[] {connector});

        WebAppContext webAppContext = new WebAppContext(DEFAULT_WEBAPP_PATH, contextPath);
        // 修改webdefault.xml，解决windows下Jetty Lock住静态文件的问题
        webAppContext.setDefaultsDescriptor(WINDOWS_WEBDEFAULT_PATH);
        server.setHandler(webAppContext);

        return server;
    }

    /**
     * 设置除jstl-*.jar外其他含tld文件的jar包的名称.
     * jar名称不需要版本号，如sitemesh, shiro-web
     */
    public static void setTldJarNames(Server server, String... jarNames) {
        WebAppContext context = (WebAppContext) server.getHandler();
        List<String> jarNameExprssions = Lists.newArrayList(".*/jstl-[^/]*\\.jar$", ".*/.*taglibs[^/]*\\.jar$");
        for (String jarName : jarNames) {
            jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
        }

        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                StringUtils.join(jarNameExprssions, '|'));

    }
}
