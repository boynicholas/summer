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

package me.lyml.summer.base.service;

import me.lyml.summer.account.entity.Role;
import me.lyml.summer.account.entity.User;
import me.lyml.summer.base.entity.BaseEntity;
import me.lyml.summer.base.modules.persistence.DynamicSpecifications;
import me.lyml.summer.base.modules.persistence.SearchFilter;
import me.lyml.summer.base.repository.BaseDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BaseService
 * @author: cnlyml
 * @date: 2016/9/4 21:27
 */
public abstract class BaseService<T extends BaseEntity, ID extends Serializable>{
    public abstract BaseDao<T, ID> dao();

    /**
     * 获取对象
     * @param id ID
     * @return 实例对象
     */
    public T get(ID id){
        return dao().findOne(id);
    }

    /**
     * 是否存在此ID的对象
     * @param id ID
     * @return 实例对象
     */
    public boolean exists(ID id){
        return dao().exists(id);
    }

    /**
     * 查询多个对象
     * @param ids IDS
     * @return List实例对象
     */
    public List<T> getAll(List<ID> ids){
        return dao().findAll(ids);
    }

    /**
     * 查询所有对象
     * @return List实例对象
     */
    public List<T> getAll(){
        return dao().findAll();
    }

    /**
     * 所有对象数量
     * @return 所有数量
     */
    public long count(){
        return dao().count();
    }

    /**
     * 保存一个对象
     * @param t 实例对象
     * @return 实例对象
     */
    public T save(T t){
        if(t.getId() == null)
            t.preInsert();
        else
            t.preUpdate();

        return dao().save(t);
    }

    /**
     * 保存多个对象
     * @param entities List实例对象
     * @return List实例对象
     */
    public List<T> save(List<T> entities){
        for (T t : entities) {
            if (t.getId() == null)
                t.preInsert();
            else
                t.preUpdate();
        }
        return dao().save(entities);
    }

    /**
     * 删除对象
     * @param t 实例对象
     */
    public void delete(T t){
        dao().delete(t);
    }

    /**
     * 根据ID删除对象
     * @param id ID
     */
    public void delete(ID id){
        dao().delete(id);
    }

    /**
     * 删除多个对象
     * @param entities List实例对象
     */
    public void delete(List<T> entities){
        dao().delete(entities);
    }

    /**
     * 删除所有对象
     */
    public void deleteAll(){
        dao().deleteAll();
    }

    /**
     * 创建动态查询条件组合.
     */
    public Specification<T> buildSpecification(Map<String, Object> filterParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(filterParams);
        Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), null);
        return spec;
    }

    /**
     * 根据条件分页查询
     * @param filterParams 参数
     * @return 返回分内容与分页信息
     */
    public Page<T> findByFilter(Map<String, Object> filterParams, Pageable pager){
        return dao().findAll(buildSpecification(filterParams), pager);
    }

    /**
     * 根据条件分页查询
     * @param filterParams 参数
     * @return 查询数量
     */
    public long countByFilter(Map<String, Object> filterParams){
        return dao().count(buildSpecification(filterParams));
    }
}
