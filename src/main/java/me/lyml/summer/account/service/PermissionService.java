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

import me.lyml.summer.account.entity.Permission;
import me.lyml.summer.account.repository.PermissionDao;
import me.lyml.summer.base.repository.BaseDao;
import me.lyml.summer.base.service.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName: PermissionService
 * @author: cnlyml
 * @date: 2016/9/20 17:26
 */
@Component
@Transactional(readOnly = true)
public class PermissionService extends BaseService<Permission, Long>{
    @Resource
    private PermissionDao dao;

    @Override
    public BaseDao<Permission, Long> dao() {
        return dao;
    }
}
