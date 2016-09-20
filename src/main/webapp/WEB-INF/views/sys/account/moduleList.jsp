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
    <title>模块管理</title>
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>系统模块管理</h2>
        <ol class="breadcrumb">
            <li>
                <a href="${ctx}">首页</a>
            </li>
            <li>
                <a>权限管理</a>
            </li>
            <li class="active">
                <strong>模块管理</strong>
            </li>
        </ol>
    </div>
    <div class="col-lg-2">

    </div>
</div>
<div class="wrapper wrapper-content animated">
    <div class="alert alert-info">
        <p>如需更改节点的上下级关系，请直接在模块列表中拖动相应的模块，模块拖动后将实时保存<br><br>模块已进行缓存，如需立即同步系统，请在<b> 缓存管理 </b>中重置模块缓存。</p>
    </div>
    <div class="row">
        <div class="col-lg-5">
            <div class="ibox-content" style="min-height: 800px;">
                <h2 class="pull-left">模块列表</h2>
                <button class="btn btn-primary pull-right" id="addModuleBtn">新增模块</button>
                <div class="clear"></div>
                <ul class="ztree" id="moduleZtree" style="clear:both;">

                </ul>
            </div>
        </div>
        <div class="col-lg-7">
            <div class="ibox-content">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="moduleName">模块名称：</label>
                            <input type="text" class="form-control" id="moduleName" name="moduleName">
                        </div>
                        <div class="form-group">
                            <label for="parentModuleName">上级模块：</label>
                            <input type="text" disabled class="form-control" id="parentModuleName" name="parentModuleName">
                            <input type="hidden" name="parentID">
                        </div>
                        <div class="form-group">
                            <label for="moduleUrl">模块地址：</label>
                            <input type="text" class="form-control" id="moduleUrl" name="moduleUrl">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="col-md-6" style="padding-left: 0;">
                            <div class="form-group">
                                <label for="moduleSort">模块顺序：</label>
                                <input type="text" class="form-control touchspin1" id="moduleSort" name="moduleSort">
                            </div>
                        </div>
                        <div class="col-md-6" style="padding-right: 0;">
                            <div class="form-group">
                                <label for="moduleIcon">模块图标：</label>
                                <input type="text" class="form-control" id="moduleIcon" name="moduleIcon">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description">模块说明：</label>
                            <input type="text" class="form-control" id="description" name="description">
                        </div>
                        <div class="form-group">
                            <label for="moduleCode">模块标识：</label>
                            <input type="text" class="form-control" id="moduleCode" name="moduleCode">
                        </div>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <button type="button" class="btn btn-primary" style="width: 100%;">禁用模块</button>
                    </div>
                    <div class="col-md-6">
                        <button type="button" class="btn btn-danger" style="width: 100%;">删除模块</button>
                    </div>
                </div>
                <div class="clear"></div>
            </div>

            <div class="row">
                <div class="col-md-7">
                    <div class="ibox-content" style="margin-top:20px;min-height:250px;">
                        <h2 id="permissionListMessage" style="line-height:250px;vertical-align: middle;text-align:center;color:#dfdfdf;">寂寞很无奈~_~</h2>
                        <table class="table permissionList" style="display:none;">
                            <thead>
                            <tr>
                                <th>权限代码</th>
                                <th>权限说明</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody class="permissionListBody">

                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="ibox-content" style="margin-top:20px;min-height:200px;">
                        <h2 id="permissionFormMessage" style="line-height:200px;vertical-align: middle;text-align:center;color:#dfdfdf;">该模块不能添加权限</h2>
                        <form id="permissionSaveForm" method="post" action="${adminPath}/account/permission/save">
                            <div class="permissionForm" style="display: none;">
                                <input type="hidden" class="form-control" name="permissionID">
                                <div class="form-group">
                                    <label for="permissionCode">权限代码：</label>
                                    <input type="text" class="form-control" id="permissionCode" name="permissionCode">
                                </div>
                                <div class="form-group">
                                    <label for="permissionDesc">权限说明：</label>
                                    <input type="text" class="form-control" id="permissionDesc" name="permissionDesc">
                                </div>
                                <div class="form-group">
                                    <button style="width:100%;" class="btn btn-primary">保存权限</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var module;
     seajs.use(["dashboard/account/module","js/jquery-2.1.1.js"], function(a){
        $(document).ready(function() {
            a.init();
            module = a;
        });
    });
</script>
<c:if test="${not empty message }">
    <script type="text/javascript">
        alert("${message}");
    </script>
</c:if>
</body>
</html>