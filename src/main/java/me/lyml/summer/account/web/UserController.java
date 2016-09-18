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
import me.lyml.summer.account.service.UserService;
import me.lyml.summer.base.entity.pager.Pager;
import me.lyml.summer.base.web.BaseController;
import me.lyml.summer.common.utils.Logs;
import me.lyml.summer.common.utils.Servlets;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public String list(Pager pager, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "s_");

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        pager.setSort(new Sort(orders));

        Page<User> page = userService.findByFilter(searchParams, pager);

        model.addAttribute("page", page);

        return "sys/account/userList";
    }

}
