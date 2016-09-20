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

package me.lyml.summer.account.web;

import me.lyml.summer.account.entity.Permission;
import me.lyml.summer.base.web.BaseController;
import me.lyml.summer.common.utils.JsonRestResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName: PermissionController
 * @author: cnlyml
 * @date: 2016/9/19 14:07
 */
@Controller
@RequestMapping("${adminPath}/account/permission")
public class PermissionController extends BaseController {

    @RequiresPermissions("account:permission:save")
    @RequestMapping("save")
    @ResponseBody
    public String save(Permission permission) {
        return null;
    }

    @RequiresPermissions("account:permission:remove")
    @RequestMapping("remove")
    @ResponseBody
    public String delete(Long permissionID) {

        return JsonRestResult.toSuccess("删除成功");
    }
}
