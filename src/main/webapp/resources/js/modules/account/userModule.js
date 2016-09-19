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

function setUserRole(userID){
	var getRoleInfoSucc = function(obj) {
		ZH.Pop.open(
				'为用户设置角色',
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
	};
	
	var getRoleInfoFail = function(obj) {
		ZH.Pop.msg(2, obj["message"]);
	}
	ZH.Ajax.post(Global.adminPath + '/account/role/getRoleUser', {
		userID : userID
	}, 1, getRoleInfoSucc, getRoleInfoFail);
}

function doSetUserRole(userID) {
	
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj
			.getCheckedNodes(true);
	var roles = '';
	for (var i = 0; i < nodes.length; i++) {
		roles += nodes[i].id + ",";
	}

	var doSetUserRoleSucc = function(obj) {
		ZH.Pop.msg(1, obj.message, 3000);
		layer.closeAll('page');
		setTimeout(function(){
			ZH.Pjax.reload();
		}, 2000);
	}
	
	var doSetUserRoleFail = function(obj) {
		var i = ZH.Pop.msg(5, obj.message, 3000);
		layer.closeAll('page');
	}
	if (!ZH.Utils.isBlank(roles)) {
		roles = roles.substr(0, roles.length - 1);
		ZH.Ajax.post(Global.adminPath + '/account/user/setUserRole', {
			userID : userID,
			roleIDs : roles
		}, 1, doSetUserRoleSucc, doSetUserRoleFail);
	}
}