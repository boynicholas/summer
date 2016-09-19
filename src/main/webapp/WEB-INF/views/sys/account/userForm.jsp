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

    <title>用户<c:if test="${action eq 'add'}">新增</c:if><c:if test="${action eq 'edit'}">编辑</c:if></title>
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>系统用户<c:if test="${action eq 'add'}">新增</c:if><c:if test="${action eq 'edit'}">编辑</c:if></h2>
        <ol class="breadcrumb">
            <li>
                <a href="${adminCtx}">首页</a>
            </li>
            <li>
                <a>权限管理</a>
            </li>
            <li class="active">
                <strong>系统用户<c:if test="${action eq 'add'}">新增</c:if><c:if test="${action eq 'edit'}">编辑</c:if></strong>
            </li>
        </ol>
    </div>

</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>用户信息</h5>
                    <div class="ibox-tools">

                    </div>
                </div>
                <div class="ibox-content">
                    <form method="post" id="saveForm" class="form-horizontal" data-pjax="true" action="${adminCtx}/account/user/save">
                        <c:if test="${not empty user.id}">
                            <input type="hidden" name="id" value="${user.id}">
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-10">
                                <input <c:if test="${action eq 'edit'}">disabled</c:if> type="text" name="userName" class="form-control" required value="${user.userName}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control required" id="userPass" name="userPass" <c:if test="${action eq 'add'}">required</c:if>>
                                <c:if test="${action eq 'edit'}"><span class="help-block m-b-none">如果无需修改密码，请将此输入框留空</span></c:if>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">确认密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" name="reUserPass" <c:if test="${action eq 'add'}"> equalTo="#userPass"</c:if>>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" value="${user.realName}" name="realName" required>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">手机号码</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" value="${user.mobileNo}" name="mobileNo" required>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" value="${user.email}" name="email" required>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <a href="javascript:window.location.href=${adminCtx}/account/user;" class="btn btn-white">取&nbsp;消</a>
                                <button class="btn btn-primary" type="submit">提&nbsp;交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!-- Jquery Validate -->
<script src="${ctx}/resources/js/plugins/validate/jquery.validate.min.js"></script>
<c:if test="${not empty message }">
    <script type="text/javascript">
        ZH.Pop.msg(2, "${message}");
    </script>
</c:if>
<c:if test="${action eq 'view'}">
    <script type="text/javascript">
        $("input,select,textarea,button").prop("disabled", "disabled");
    </script>
</c:if>
<script type="text/javascript">
    $(function() {
        $("#saveForm").validate();
    })
</script>
</body>