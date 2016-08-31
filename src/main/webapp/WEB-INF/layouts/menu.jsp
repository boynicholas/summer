<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>

<%--
  ~ Copyright 2016 Cnlyml
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<div id="wrapper" class="parentWrapper">
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element"> <span>
                            <img alt="image" class="img-circle" src="${fns:getSessionUser().avatar}" style="width:50px;" />
                             </span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="dashboard_2.html#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">${fns:getSessionUser().realName}</strong>
                             </span> <span class="text-muted text-xs block">${fns:getSessionUser().userName} <b class="caret"></b></span> </span> </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="profile.html">个人设置</a></li>
                            <li><a href="contacts.html">修改密码</a></li>
                            <li class="divider"></li>
                            <li><a href="${ctx}/j_spring_security_logout">注销</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">
                        ML+
                    </div>
                </li>
                <li class="active">
                    <a href="javascript:;">
                    	<i class="fa fa-th-large"></i>
						<span class="nav-label">权限管理</span>
						<span class="fa arrow"></span>
					</a>
                    <ul class="nav nav-second-level">
                        <li class="active"><a href="/account/user/page" data-pjax="true">用户管理</a></li>
                        <li><a href="/account/role/page", data-pjax="true">角色管理</a></li>
                        <li><a href="dashboard_3.html">菜单管理</a></li>
                        <li><a href="dashboard_4_1.html">日志管理</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                    	<i class="fa fa-th-large"></i>
						<span class="nav-label">数据监控</span>
						<span class="fa arrow"></span>
					</a>
                    <ul class="nav nav-second-level">
                        <li><a href="/account/user/page?pjax=true">数据连接池监控</a></li>
                        <li class="active"><a href="dashboard_2.html">在线用户监控</a></li>
                        <li><a href="dashboard_3.html">系统日志监控</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                    	<i class="fa fa-th-large"></i>
						<span class="nav-label">开发控制</span>
						<span class="fa arrow"></span>
					</a>
                    <ul class="nav nav-second-level">
                        <li><a href="/account/user/page?pjax=true">代码生成</a></li>
                    </ul>
                </li>
            </ul>

        </div>
    </nav>
	</div>
        <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
        <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="javascript:;"><i class="fa fa-bars"></i> </a>
            <form role="search" class="navbar-form-custom" action="search_results.html">
                <div class="form-group">
                    <input type="text" placeholder="这个搜索没用..." class="form-control" name="top-search" id="top-search">
                </div>
            </form>
        </div>
            <ul class="nav navbar-top-links navbar-right">
                <li>
                    <span class="m-r-sm text-muted welcome-message">欢迎访问Framen</span>
                </li>
                
                <li class="dropdown">
                    <a class="dropdown-toggle count-info" data-toggle="dropdown" href="dashboard_2.html#">
                        <i class="fa fa-bell"></i>  <span class="label label-primary">8</span>
                    </a>
                    <ul class="dropdown-menu dropdown-alerts">
                        <li>
                            <a href="mailbox.html">
                                <div>
                                    <i class="fa fa-envelope fa-fw"></i> 你有 16 条消息
                                    <span class="pull-right text-muted small">4 分钟前</span>
                                </div>
                            </a>
                        </li>
                        <!--<li class="divider"></li>
                        <li>
                            <a href="profile.html">
                                <div>
                                    <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                    <span class="pull-right text-muted small">12 minutes ago</span>
                                </div>
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="grid_options.html">
                                <div>
                                    <i class="fa fa-upload fa-fw"></i> Server Rebooted
                                    <span class="pull-right text-muted small">4 minutes ago</span>
                                </div>
                            </a>
                        </li>-->
                        <li class="divider"></li>
                        <li>
                            <div class="text-center link-block">
                                <a href="notifications.html">
                                    <strong>查看全部信息</strong>
                                    <i class="fa fa-angle-right"></i>
                                </a>
                            </div>
                        </li>
                    </ul>
                </li>


                <li>
                    <a href="${ctx}/j_spring_security_logout">
                        <i class="fa fa-sign-out"></i> 注&nbsp;销
                    </a>
                </li>

            </ul>

        </nav>
        </div>