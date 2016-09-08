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

(function(w, d) {
	if (!window.ZH) {
		window.ZH = {};
	}
	
	ZH = {
		name: "Zhihang Framen Javascript Library",
		version: "1.0.0",
		debug: true,
		namespace: function(name) {
			var parts = name.split(".");
			var current = ZH;
			for (var i in parts) {
				if (!current[parts[i]]) {
					current[parts[i]] = {};
				}
				current = current[parts[i]];
			}
		},
		Module:{
			Account:{
				User:{
					setUserRole:function(userID) {
						var getRoleInfoSucc = function(obj) {
							ZH.Pop.open(
									'设置用户角色',
									'550px',
									'400px',
									'<ul id="treeDemo" class="ztree"></ul><div style="margin-top:10px;float:right;padding-right:20px"><button type="button" class="btn btn-primary btn-xs" onclick="doSetUserRole('+userID+')" id="doSetUserRole" disabled>确 定</button></div>');
							
							var zTreeOnCheck = function() {
								var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj
										.getCheckedNodes(true);
								if (nodes.length > 0) {
									$("#doSetUserRole").removeAttr("disabled");
								} else {
									$("#doSetUserRole").attr("disabled", "disabled");
								}
							}
							
							var setting = {
									data : {
										simpleData : {
											enable : true,
											idKey : "id",
											rootPId : 0
										},
										key : {
											name : 'role_name',
										}
									},
									check : {
										enable : true,
										chkStyle : "checkbox",
										chkboxType : {
											"Y" : "p",
											"N" : "s"
										}
									},
									callback : {
										onCheck : zTreeOnCheck
									}
								};

								zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, obj.result.roles);
						}
						var getRoleInfoFail = function(obj) {
							ZH.Pop.msg(2, obj["message"]);
						}
						ZH.Ajax.post('/dashboard/system/role/getRoleUser', {
							userID : userID
						}, 1, "系统加载数据中,请等候", getRoleInfoSucc, getRoleInfoFail);
					}
				}
			}
		}
	}
})(window, document);