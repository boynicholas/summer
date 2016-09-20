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

package me.lyml.summer.account.entity;

import com.google.common.collect.Lists;
import me.lyml.summer.base.entity.BaseEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @ClassName: Module
 * @author: cnlyml
 * @date: 2016/9/2 19:50
 */

@Entity
@Table(name = "s_module")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Where(clause = "deleted = 0")
public class Module extends BaseEntity{

    private static final long serialVersionUID = 2206999403220813524L;

    /**
     * 上级模块
     */
    @Transient
    private Module parentModule;

    /**
     * 模块排序
     */
    private Integer moduleSort;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块地址
     */
    private String moduleUrl;

    /**
     * 模块图标
     */
    private String moduleIcon;

    /**
     * 模块标识
     */
    private String moduleCode;

    /**
     * 模块介绍
     */
    private String description;

    /**
     * 模块状态
     */
    private Integer moduleStatus;

    /**
     * Module所拥有的权限
     */
    @Transient
    private List<Permission> permissions = Lists.newArrayList();

    @Transient
    private boolean isParent;

    @OneToOne
    @JoinColumn(name = "parent_id")
    public Module getParentModule() {
        return parentModule;
    }

    public void setParentModule(Module parentModule) {
        this.parentModule = parentModule;
    }

    public Integer getModuleSort() {
        return moduleSort;
    }

    public void setModuleSort(Integer moduleSort) {
        this.moduleSort = moduleSort;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(Integer moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "module")
    @Where(clause = "deleted = 0")
    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void setIsParent(boolean parent) {
        this.isParent = parent;
}

    @Transient
    public boolean getIsParent() {
        return isParent;
    }
}
