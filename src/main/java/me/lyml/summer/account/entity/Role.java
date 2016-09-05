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
import me.lyml.summer.base.validator.ValidatorGroup;
import org.apache.ibatis.annotations.Many;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName: Role
 * @author: cnlyml
 * @date: 2016/9/2 10:08
 */
@Entity
@Table(name = "s_role")
public class Role extends BaseEntity {
    @NotNull(groups = {ValidatorGroup.Add.class, ValidatorGroup.Edit.class}, message = "请填写角色名称")
    private String roleName;
    private String roleDesc;
    private Boolean roleStatus;
    private Boolean isSystem;

    /**
     * 角色所属Moudle
     */
    @Transient
    private List<Module> moduleList = Lists.newArrayList();

    /**
     * 角色所属权限
     */
    @Transient
    private List<Permission> permissions = Lists.newArrayList();

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Boolean getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Boolean roleStatus) {
        this.roleStatus = roleStatus;
    }

    public Boolean getSystem() {
        return isSystem;
    }

    public void setSystem(Boolean system) {
        isSystem = system;
    }

    @ManyToMany
    @JoinTable(name = "s_role_module", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "module_id")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id ASC")
    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    @ManyToMany
    @JoinTable(name = "s_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id ASC")
    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
