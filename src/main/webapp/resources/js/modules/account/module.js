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

define(function (require, exports, module) {
    require("js/jquery-2.1.1.js");
    require("plugins/zTree/css/zTreeStyle/zTreeStyle.css");
    require("plugins/zTree/js/jquery.ztree.all.min.js");
    require("plugins/touchspin/jquery.bootstrap-touchspin.min.css");
    require("plugins/touchspin/jquery.bootstrap-touchspin.min.js");

    var zTree;
    var addNum = 0;

    var settings = {
        async: {
            enable: true,
            url: Global.adminPath + "/account/module/getModule",
            autoParam:["id=moduleID"]
        },
        data: {
            key: {
                name : "moduleName"
            }
        },
        simpleData: {
            enable: true,
            idKey: "id"
        },
        callback: {
            onClick: zTreeOnClick,
            beforeRemove: beforeRemove
        },
        edit: {
            drag: {
                isCopy:false
            },
            enable:true,
            showRenameBtn:false,
            showRemoveBtn:false
        }
    };

    module.exports = {
        init: function() {
            initZtree();
            initElement();
        },

        editPermission: function(a) {
            console.log($(a).parent().parent().find("td").html());
            setInputValue("permissionCode", $(a).parent().parent().find("td").eq(0).html());
            setInputValue("permissionDesc", $(a).parent().parent().find("td").eq(1).html());

            setInputValue("permissionID", $(a).parent().parent().find("input").eq(0).html());
        }
    };

    function initZtree() {
       zTree = $.fn.zTree.init($("#moduleZtree"), settings);
    }

    function initElement() {
        $(".touchspin1").TouchSpin({
            min: 1,
            max: 100,
            step: 1,
            buttondown_class: 'btn btn-white',
            buttonup_class: 'btn btn-white'
        });

        $("#deleteModuleBtn").click(function() {
            
        });

        $("#addModuleBtn").click(function () {

            if(addNum > 0) {
                ZH.Pop.msg(5, "您有一个新建的节点未保存，请保存或删除该节点后再进行添加");
                return;
            }

            var nodes = zTree.getSelectedNodes();
            if(nodes == null || nodes.length < 1) {
                var node = zTree.addNodes(null, -1, {"moduleName":"新模块", "permissions":[]});
                zTree.selectNode(node, false);
            }else {
                var node = nodes[0];
                if(!node.isParent && node.getParentNode() == null) {
                    ZH.Pop.confirm("您是需要添加最顶级模块还是在该选中模块下建立子模块呢？<br><br>确定添加顶级模块，取消在该选中模块下建立子模块", function() {
                        var f = zTree.addNodes(null, -1, {"moduleName":"新模块", "permissions":[]});
                        zTree.selectNode(f, false);
                        layer.closeAll();
                    }, function() {
                        var f = zTree.addNodes(node, -1, {"moduleName":"新模块", "parentID": node.id, "permissions":[]});
                        zTree.selectNode(f, false);
                        layer.closeAll();
                    });
                }else {
                    var f = zTree.addNodes(node, -1, {"moduleName":"新模块", "parentID": node.id, "permissions":[]});
                    zTree.selectNode(f, false);
                }
            }

            addNum++;
        })
    }

    function beforeRemove(treeId, treeNode) {
        return true;
    }

    function zTreeOnClick(event, treeId, treeNode) {
        setInputValue("moduleName", treeNode.moduleName);
        setInputValue("moduleUrl", treeNode.moduleUrl);
        setInputValue("moduleSort", treeNode.moduleSort);
        setInputValue("description", treeNode.description);
        setInputValue("moduleCode", treeNode.moduleCode);

        setInputValue("parentModuleName", treeNode.isParent ? "" : treeNode.getParentNode() == null ? "" : treeNode.getParentNode().moduleName);
        setInputValue("parentID", treeNode.isParent ? "" : treeNode.getParentNode() == null ? "" : treeNode.getParentNode().id);

        /**
         * 判断权限列表
         */
        if(treeNode.isParent) {
            $("#permissionListMessage").css("display","block").val("寂寞很无奈~_~");
            $("#permissionFormMessage").css("display","block").val("该模块不能添加权限");
            $(".permissionList").css("display","none");
            $(".permissionForm").css("display","none");
        }else {
            $("#permissionListMessage").css("display","none");
            $("#permissionFormMessage").css("display","none");
            $(".permissionList").css("display","block");
            $(".permissionForm").css("display","block");

            var permissions = treeNode.permissions;
            if(permissions.length < 1) {
                $("#permissionListMessage").css("display","block").val("寂寞很无奈~_~");
                $(".permissionList").css("display","none");
            }else {
                var html = "";
                for(var i = 0; i < permissions.length; i++) {
                    html += "<tr>";
                    html += '<input type="hidden" name="permissions['+i+'].id" value="'+permissions[i].id+'">';
                    html += '<td>'+permissions[i].permissionCode+'</td>';
                    html += '<td>'+permissions[i].permissionDesc+'</td>';
                    html += '<td><a href="javascript:void(0);" onclick="module.editPermission(this)" class="btn btn-primary btn-xs">修改权限</a>&nbsp;&nbsp;<a href="javascript:;" class="btn btn-danger btn-xs">删除权限</a></td>';
                    html += "</tr>";
                }

                $(".permissionListBody").html(html);
            }
        }
    }

    function setInputValue(name, value) {
        $("input[name = '"+name+"']").val(value);
    }

});