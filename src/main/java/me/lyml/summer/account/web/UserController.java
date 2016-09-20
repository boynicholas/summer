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

import me.lyml.summer.account.entity.User;
import me.lyml.summer.account.service.RoleService;
import me.lyml.summer.account.service.UserService;
import me.lyml.summer.base.entity.pager.Pager;
import me.lyml.summer.base.validator.ValidatorGroup;
import me.lyml.summer.base.web.BaseController;
import me.lyml.summer.common.annotation.SystemLog;
import me.lyml.summer.common.config.Global;
import me.lyml.summer.common.utils.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName: UserController
 * @author: cnlyml
 * @date: 2016/9/16 17:28
 */
@Controller
@RequestMapping("${adminPath}/account/user")
public class UserController extends BaseController {
    private static final Logger logger = Logs.get();

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @RequestMapping(value = {"", "page"})
    @RequiresPermissions({"account.user.view"})
    @SystemLog(module = "用户管理", method = "列表页面", desc = "列表页面")
    public String list(Pager pager, HttpServletRequest request, Model model) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "s_");
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        pager.setSort(new Sort(orders));

        Page<User> page = userService.findByFilter(searchParams, pager);

        model.addAttribute("page", page);
        model.addAttribute("roles", roleService.getAll());

        return "sys/account/userList";
    }

    /**
     * 修改新增页面
     * @param user user
     * @param model model
     * @return url
     */
    @RequestMapping(value = {"form", "form/{id}"})
    @RequiresPermissions({"account.user.create", "account.user.modify"})
    @SystemLog(module = "用户管理", method = "新增修改页面", desc = "新增修改页面")
    public String form(User user, Model model){
        if(user.getId() != null) {
            user = userService.get(user.getId());
        }

        model.addAttribute("user", user);

        model.addAttribute("action", user.getId() == null ? "add" : "edit");

        return "sys/account/userForm";
    }

    @RequestMapping(value = {"view/{id}"})
    @RequiresPermissions({"account.user.view"})
    @SystemLog(module = "用户管理", method = "详情页面", desc = "详情页面")
    public String view(User user, Model model) {
        if(user.getId() != null) {
            user = userService.get(user.getId());
        }

        model.addAttribute("user", user);

        model.addAttribute("action", "view");

        return "sys/account/userForm";
    }

    /**
     * 保存
     * @param user user
     * @param model model
     * @param redirectAttributes redirectAttributes
     * @param request request
     * @return url
     * @throws IOException IOException
     */
    @RequestMapping(value = {"save"})
    @RequiresPermissions({"account.user.create", "account.user.modify"})
    @SystemLog(module = "用户管理", method = "用户保存", desc = "用户保存")
    public String save(User user , Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {

        if (!beanValidator(model, user, user.getId() == null ? ValidatorGroup.Add.class : ValidatorGroup.Edit.class)) {
            return form(user, model);
        }

        if (StringUtils.isNotBlank(user.getUserPass())) {
            user.setUserPass(DigestUtils.sha1Hex(user.getUserPass()));
        }

        if (user.getId() != null) {
            User oldUser = userService.get(user.getId());

            user = (User) BeanToMapUtils.beanToBean(oldUser, user, User.class);

            // beanToBean转换不了Role
            user.setRoleList(oldUser.getRoleList());
        } else {
            User queryUsers = userService.findUserByUserName(user.getUserName());
            if (queryUsers != null) {
                addMessage(model, "保存用户'" + user.getUserName() + "'失败，用户名已存在");
                return form(user, model);
            }

            /**
             * 添加头像
             */
            String savePath = request.getSession().getServletContext().getRealPath("/") + Global.getConfig("uploadPath") + "/";
            String fileName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
            try {
                FontImageUtils.createImage(user.getRealName().substring(0, 1), new File(savePath + "avatar/" + fileName));
                user.setAvatar("/" + Global.getConfig("uploadPath") + "/avatar/" + fileName);
            } catch (IOException e) {
                throw e;
            }
        }

        userService.saveUser(user);
        addMessage(redirectAttributes, "保存用户'" + user.getUserName() + "'成功");
        return "redirect:/account/user/page";
    }

    @RequestMapping(value = "setUserRole")
    @ResponseBody
    @SystemLog(module = "用户管理", method = "设置用户角色", desc = "设置用户角色")
    public String setUserRole(@RequestParam(value = "userID", required = true) Long userID, @RequestParam(value = "roleIDs", required = true) String roleIDs){
        try{
            userService.setUserRole(userID, roleIDs);

            if(logger.isInfoEnabled()){
                logger.info(MessageFormat.format("用户{0}配置用户{1}角色{2}成功", ShiroUtils.getShiroUser().getUserName(), userID, roleIDs));
            }

            return JsonRestResult.toSuccess("用户角色配置成功", null);
        }catch(Exception e){
            e.printStackTrace();
            return JsonRestResult.toFailure(JsonRestResult.Code.FUNCTION_EXCEPTION, "用户角色配置失败");
        }
    }

    @RequestMapping(value = "enable")
    @ResponseBody
    @RequiresPermissions("account:user:enable")
    @SystemLog(module = "用户管理", method = "设置用户状态", desc = "设置用户状态")
    public String enable(@RequestParam Long id, @RequestParam Integer status) {
        User user = userService.get(id);

        if(user == null) {
            return JsonRestResult.toFailure(JsonRestResult.Code.FUNCTION_EXCEPTION, "找不到该用户数据");
        }

        user.setIsValid(status == 1);
        userService.save(user);

        if(logger.isInfoEnabled()) {
            logger.info(MessageFormat.format("用户{0}配置用户{1}{2}成功", ShiroUtils.getShiroUser().getUserName(), user.getUserName(), status == 1 ? "启用" : "禁用"));
        }

        return JsonRestResult.toSuccess("变更状态成功");
    }
}
