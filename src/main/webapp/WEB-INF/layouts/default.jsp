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

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><sitemesh:title /> | ${fns:getConfig('productName')}</title>

    <link href="${ctx}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/resources/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${ctx}/resources/css/animate.css" rel="stylesheet">
    <link href="${ctx}/resources/css/zhihang-base.css" rel="stylesheet">
	<sitemesh:head />
</head>

<body>
    <%@ include file="/WEB-INF/layouts/menu.jsp" %>
    <!-- Mainly scripts -->
    <script src="${ctx}/resources/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/resources/js/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/resources/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Peity -->
    <script src="${ctx}/resources/js/plugins/peity/jquery.peity.min.js"></script>
    <script src="${ctx}/resources/js/demo/peity-demo.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/resources/js/inspinia.js"></script>
    <script src="${ctx}/resources/js/plugins/pace/pace.min.js"></script>

    <!-- jQuery UI -->
    <script src="${ctx}/resources/js/plugins/jquery-ui/jquery-ui.min.js"></script>

    <!-- Sparkline -->
    <script src="${ctx}/resources/js/plugins/sparkline/jquery.sparkline.min.js"></script>
    <script src="${ctx}/resources/js/zhihang-base.js"></script>

    <script>
        $(document).ready(function() {
            
        });
    </script>
    <div class="wrapper wrapper-content wrapper-container">
        <sitemesh:body />
    </div>
</body>
</html>
