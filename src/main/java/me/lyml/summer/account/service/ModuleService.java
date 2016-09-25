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

import me.lyml.summer.account.entity.Module;
import me.lyml.summer.account.repository.ModuleDao;
import me.lyml.summer.account.repository.mybatis.ModuleMapperDao;
import me.lyml.summer.base.repository.BaseDao;
import me.lyml.summer.base.service.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ModuleService
 * @author: cnlyml
 * @date: 2016/9/5 9:47
 */
@Component
@Transactional(readOnly = true)
public class ModuleService extends BaseService<Module, Long> {
    @Resource
    private ModuleDao dao;
    @Resource
    private ModuleMapperDao mapperDao;

    public List<Module> findModuleByParentID(Long parentID) {
        return mapperDao.findModuleByParentID(parentID);
    }

    @Transactional(readOnly = false)
    public void saveModule(Module module) {
        if(module.getId() != null) {
            Module oldModule = get(module.getId());

            oldModule.setModuleName(module.getModuleName());
            oldModule.setModuleCode(module.getModuleCode());

        }
    }

    @Override
    public BaseDao<Module, Long> dao() {
        return dao;
    }
}
