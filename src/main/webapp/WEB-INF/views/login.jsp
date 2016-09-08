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

    <title>登陆 | ${fns:getConfig('productName')}</title>

    <link href="${ctx}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/resources/font-awesome/css/font-awesome.css"
          rel="stylesheet">

    <link href="${ctx}/resources/css/animate.css" rel="stylesheet">
    <link href="${ctx}/resources/css/zhihang-base.css" rel="stylesheet">

    <!-- Sweet Alert -->
    <link href="${ctx}/resources/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">

    <script src="${ctx}/resources/js/jquery-2.1.1.js" type="text/javascript"></script>
</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen animated fadeInDown">
    <div class="loginForm">
        <c:if test="${isRemember eq true}">
            <img class="profile-img" src="${ctx}${user.avatar}">
        </c:if>
        <form class="m-t" role="form" action="${ctx}/login" method="post">
            <div class="form-group">
                <c:if test="${isRemember eq true}">
                    欢迎您：${user.realName}

                    <input type="hidden" name="userName" value="${user.userName}" />
                </c:if>
                <c:if test="${empty isRemember || isRemember != true}">
                    <input type="text" class="form-control" name="userName" placeholder="请输入用户名">
                </c:if>
            </div>
            <div class="form-group">
                <input type="password" class="form-control" name="userPass" placeholder="请输入密码">
            </div>
            <input type="hidden" name="rememberMe" value="true" />
            <button type="submit" class="btn btn-primary block full-width m-b">Login</button>

            <a href="#"><small>忘记密码?</small></a>
            <c:if test="${isRemember eq true}">
                <hr>
                <div style="margin-top:10px;">您已经登陆，<a href="${ctx}/logout">使用其他帐号登陆?</a></div>
            </c:if>
        </form>
    </div>
    <p class="m-t"> <small>Copyright &copy; 2016 By Cnlyml</small> </p>
</div>

<!-- Sweet alert -->
<script src="${ctx}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="${ctx}/resources/js/zhihang-base.js" type="text/javascript"></script>
<script type="text/javascript">
    // 如果在框架或在对话框中，则弹出提示并跳转到首页
    if (self.frameElement && self.frameElement.tagName == "IFRAME"
            || $('#left').length > 0 || $('.jbox').length > 0) {
        ZH.Pop.alert(2, "登陆过期，请重新登录")
        top.location = "${ctx}";
    }
</script>

<c:if test="${not empty message}">
    <script type="text/javascript">
        $(function() {
            ZH.Pop.msg(2, "${message}", 3000);
        });
    </script>
</c:if>
</body>
</html>
