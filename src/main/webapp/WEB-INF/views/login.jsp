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

<div class="loginColumns animated fadeInDown">
    <div class="row">

        <div class="col-md-6">
            <h2 class="font-bold">欢迎访问Framen</h2>

            <p>它是一个孤单的产物，它是一个拖欠的产物，它和哪吒一样怀胎三年，幸运的是，它来了！</p>
            <p>Welcome to Zhihang Framen</p>
            <p>
                <small>Framen，一个高集成的项目框架，为了高开发，生成一个基础项目只需1分钟。如果你喜欢，来用吧！</small>
            </p>

        </div>
        <div class="col-md-6">
            <div class="ibox-content">
                <form id="loginForm" class="m-t" role="form"
                      action="${ctx}/login"
                      method="post">
                    <div class="form-group">
                        <input type="text" name="userName" class="form-control required"
                               placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <input type="password" name="userPass"
                               class="form-control required" placeholder="密码">
                    </div>
                    <c:if test="${isValidateCodeLogin}">
                        <div class="form-group">
                            <label class="input-label mid" for="validateCode">验证码</label>
                            <account:validateCode name="validateCode"
                                              inputCssStyle="margin-bottom:0;" />
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-primary block full-width m-b">登&nbsp;陆</button>

                </form>
                <p class="m-t">
                    <small>本系统生成自Zhihang|Lyml Generate &copy; 2016</small>
                </p>
            </div>
        </div>
    </div>
    <hr />
    <div class="row">
        <div class="col-md-6">Copyright Zhihang|Lyml</div>
        <div class="col-md-6 text-right">
            <small>© 2016</small>
        </div>
    </div>
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
</body>
</html>
