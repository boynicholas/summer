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

import me.lyml.summer.account.entity.Module;
import me.lyml.summer.account.service.ModuleService;
import me.lyml.summer.base.web.BaseController;
import me.lyml.summer.common.utils.JsonRestResult;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: ModuleController
 * @author: cnlyml
 * @date: 2016/9/19 14:04
 */
@Controller
@RequestMapping("${adminPath}/account/module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;

    @RequestMapping(value = {"", "page"})
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sys/account/moduleList";
    }

    @RequestMapping("getModule")
    @ResponseBody
    public String getModule(Long moduleID) {
        return JSONArray.fromObject(moduleService.findModuleByParentID(moduleID)).toString();
    }

    @RequestMapping("save")
    @ResponseBody
    public String save(Module module) {
        return null;
    }

    public String delete(Long id) {
        Module module = moduleService.get(id);
        if(module != null) {
            module.setDeleted(true);
            moduleService.save(module);
        }

        return JsonRestResult.toSuccess("删除成功");
    }
}
