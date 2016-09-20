<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

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
    <title>角色管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/js/plugins/zTree/css/zTreeStyle/zTreeStyle.css">
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>系统角色管理</h2>
        <ol class="breadcrumb">
            <li>
                <a href="${ctx}">首页</a>
            </li>
            <li>
                <a>权限管理</a>
            </li>
            <li class="active">
                <strong>角色管理</strong>
            </li>
        </ol>
    </div>
    <div class="col-lg-2">

    </div>
</div>
<div class="wrapper wrapper-content animated">
    <form  method="post" role="form" id="queryForm" data-pjax="true" action="${ctx}/account/role/page">
        <div class="ibox">
            <div class="ibox-title">
                <h5>搜索</h5>
                <div class="ibox-tools">
                    <a href="${ctx}/account/role/form" data-pjax="true" class="btn btn-primary btn-xs">新增角色</a>
                </div>
            </div>
            <div class="ibox-content m-b-sm border-bottom">
                <div class="row">
                    <div class="col-sm-2">
                        <div class="form-group">
                            <label class="control-label" for="s_LIKE_userName">角色名</label>
                            <input type="text" name="s_LIKE_roleName" value="${param.s_LIKE_roleName}" placeholder="模糊搜索角色名" class="form-control">
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="form-group" style="margin-top:22px;">
                            <button class="btn btn-primary">搜&nbsp;索</button>
                            <button class="btn btn-primary">导&nbsp;出</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="ibox">
                    <div class="ibox-content">
                        <table class="table table-bordered">
                            <thead>
                            <tr>

                                <th>序号</th>
                                <th>角色名</th>
                                <th>角色描述</th>
                                <th>是否系统角色</th>
                                <th>角色状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach varStatus="s" items="${page.content}" var="role">
                                <tr>
                                    <td>${s.index + 1}</td>
                                    <td>${role.roleName}</td>
                                    <td>${role.roleDesc}</td>
                                    <td>
                                        <c:if test="${role.isSystem eq true}">是</c:if>
                                        <c:if test="${role.isSystem eq false}">否</c:if>
                                    </td>
                                    <td>
                                        <c:if test="${role.roleStatus eq true}">启用</c:if>
                                        <c:if test="${role.roleStatus eq false}">禁用</c:if>
                                    </td>
                                    <td>
                                        <a href="${adminCtx}/account/user/form?id=${user.id}" data-pjax="true" class="btn-white btn btn-xs">编辑</a>
                                        <a class="btn-white btn btn-xs" href="javascript:setUserRole('${user.id}');">设置权限</a>
                                        <c:if test="${role.roleStatus eq true}">
                                            <a class="btn-white btn btn-xs" href="buttons.html#">禁用</a>
                                        </c:if>
                                        <c:if test="${role.roleStatus eq false}">
                                            <a class="btn-white btn btn-xs" href="buttons.html#">启用</a>
                                        </c:if>
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
<c:if test="${not empty message }">
    <script type="text/javascript">
        alert("${message}");
    </script>
</c:if>
</body>
</html>