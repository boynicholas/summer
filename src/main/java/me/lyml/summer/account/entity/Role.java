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
import org.hibernate.annotations.Where;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Role
 * @author: cnlyml
 * @date: 2016/9/2 10:08
 */
@Entity
@Table(name = "s_role")
@Where(clause = "deleted = 0")
public class Role extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -3208320020667937004L;

    @NotNull(groups = {ValidatorGroup.Add.class, ValidatorGroup.Edit.class}, message = "请填写角色名称")

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    @Column(name = "role_status")
    private Boolean roleStatus;

    @Column(name = "is_system")
    private Boolean isSystem;

    @Transient
    private List<User> userList = Lists.newArrayList();

    /**
     * 角色所属权限
     */
    @Transient
    private List<Permission> permissions = Lists.newArrayList();

    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "role_desc")
    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Column(name = "role_status")
    public Boolean getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Boolean roleStatus) {
        this.roleStatus = roleStatus;
    }

    @Column(name = "is_system")
    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    @ManyToMany
    @JoinTable(name = "s_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id ASC")
    @Where(clause = "deleted = 0")
    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @ManyToMany(mappedBy = "roleList", fetch = FetchType.LAZY)
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
