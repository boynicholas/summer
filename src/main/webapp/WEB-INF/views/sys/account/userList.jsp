<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

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

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/js/plugins/zTree/css/zTreeStyle/zTreeStyle.css">
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>系统用户管理</h2>
        <ol class="breadcrumb">
            <li>
                <a href="${adminCtx}">首页</a>
            </li>
            <li>
                <a>权限管理</a>
            </li>
            <li class="active">
                <strong>用户管理</strong>
            </li>
        </ol>
    </div>
    <div class="col-lg-2">

    </div>
</div>
<div class="wrapper wrapper-content animated">
    <form  method="post" role="form" id="queryForm" data-pjax="true" action="${adminCtx}/account/user">
        <div class="ibox">
            <div class="ibox-title">
                <h5>搜索</h5>
                <div class="ibox-tools">  
                    <shiro:hasPermission name="account:user:create">
                        <a href="${adminCtx}/account/user/form" data-pjax="true" class="btn btn-primary btn-xs btn-control">新增用户</a>
                    </shiro:hasPermission>
                </div>
            </div>
            <div class="ibox-content m-b-sm border-bottom">
                <div class="row">
                    <div class="col-sm-2">
                        <div class="form-group">
                            <label class="control-label" for="s_LIKE_userName">用户名</label>
                            <input type="text" name="s_LIKE_userName" value="${param.s_LIKE_userName}" placeholder="模糊搜索用户名" class="form-control">
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="form-group">
                            <label class="control-label" for="s_EQ_mobileNo">手机号码</label>
                            <input type="text" name="s_EQ_mobileNo" value="" placeholder="搜索手机号码" class="form-control">
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="form-group">
                            <label class="control-label" for="quantity">角色</label>
                            <select name="s_EQ_roleList.id" class="form-control">
                                <option value="">全部</option>
                                <c:forEach items="${roles}" var="role">
                                    <option value="${role.id}">${role.roleName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="form-group" style="margin-top:22px;">
                            <button type="submit" class="btn btn-primary">搜&nbsp;索</button>
                            <shiro:hasPermission name="account:user:export">
                                <button class="btn btn-primary">导&nbsp;出</button>
                            </shiro:hasPermission>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="ibox">
                    <div class="ibox-content">
                        <table class="table table-bordered client-table">
                            <thead>
                            <tr>

                                <th>序号</th>
                                <th>用户名</th>
                                <th>真实姓名</th>
                                <th>邮箱</th>
                                <th>手机号</th>
                                <th>拥有角色</th>
                                <th>是否有效</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach varStatus="s" items="${page.content}" var="user">
                                <tr>
                                    <td>${s.index + 1}</td>
                                    <td><img class="client-avatar" alt="image" src="${user.avatar}"/> ${user.userName}</td>
                                    <td>${user.realName}</td>
                                    <td>${user.email}</td>
                                    <td>${user.mobileNo}</td>
                                    <td>
                                        <c:forEach items="${user.roleList}" var="role" varStatus="s">
                                            ${role.roleName}<c:if test="${s.index != fn:length(user.roleList) - 1}">,</c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:if test="${user.isValid eq true}">
                                            <span class="label label-primary">有 效</span>
                                        </c:if>
                                        <c:if test="${user.isValid eq false}">
                                            <span class="label label-danger">无 效</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <div class="btn-group">
                                            <a href="${adminCtx}/account/user/view/${user.id}" class="btn-white btn btn-xs">查看</a>
                                            <shiro:hasPermission name="account:user:modify">
                                                <a href="${adminCtx}/account/user/form/${user.id}" data-pjax="true" class="btn-white btn btn-xs">编辑</a>
                                            </shiro:hasPermission>
                                            <div class="btn-group">
                                                <button data-toggle="dropdown" class="btn btn-white btn-xs dropdown-toggle">更多 <span class="caret"></span></button>
                                                <ul class="dropdown-menu">
                                                    <li>
                                                        <c:if test="${user.isValid eq true}">
                                                            <a href="javascript:enable('${user.id}', 0)">禁用</a>
                                                        </c:if>
                                                        <c:if test="${user.isValid eq false}">
                                                            <a href="javascript:enable('${user.id}', 1)">启用</a>
                                                        </c:if>
                                                    </li>
                                                    <li><a href="javascript:setUserRole('${user.id}');">设置角色</a></li>
                                                    <li><a href="buttons.html#">查看日志</a></li>
                                                    <li class="divider"></li>
                                                    <li><a href="buttons.html#">发送消息</a></li>
                                                    <li><a href="buttons.html#">发送邮件</a></li>
                                                </ul>

                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="pull-right">
                            <sys:pagination paginationSize="20" page="${page}" queryForm="queryForm"></sys:pagination>
                            <div class="clear"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
    </form>
</div>
<script type="text/javascript" src="${ctx}/resources/js/plugins/zTree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/modules/account/userModule.js"></script>
<c:if test="${not empty message }">
    <script type="text/javascript">
        alert("${message}");
    </script>
</c:if>
</body>
</html>