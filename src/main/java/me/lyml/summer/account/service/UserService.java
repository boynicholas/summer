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

package me.lyml.summer.account.service;

import me.lyml.summer.account.entity.Role;
import me.lyml.summer.account.entity.User;
import me.lyml.summer.account.repository.UserDao;
import me.lyml.summer.base.repository.BaseDao;
import me.lyml.summer.base.service.BaseService;
import me.lyml.summer.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserService
 * @author: cnlyml
 * @date: 2016/9/5 9:47
 */
@Component
public class UserService extends BaseService<User, Long> {
    @Resource
    private UserDao dao;

    public User findUserByUserName(String userName){
        return dao.findUserByUserName(userName);
    }

    public List<Role> findRolesByUserID(Long userID) {
        return dao.findRolesByUserID(userID);
    }

    public void saveUser(User user) {
        if(user.getId() == null) {
            user.setIsValid(true);

            // 添加普通角色
            List<Role> roleList = new ArrayList<>();
            Role r = new Role();
            r.setId(10000002L);

            roleList.add(r);
            user.setRoleList(roleList);
        }

        save(user);
    }

    public void setUserRole(Long userID, String roleids){
        User user = dao.findOne(userID);
        if(user == null){
            return;
        }

        user.getRoleList().clear();

        String[] ids = StringUtils.split(roleids, ",");
        for(String roleID : ids){
            Role r = new Role();
            r.setId(Long.parseLong(roleID));
            user.getRoleList().add(r);
        }
        save(user);
    }

    @Override
    public BaseDao<User, Long> dao() {
        return dao;
    }
}
