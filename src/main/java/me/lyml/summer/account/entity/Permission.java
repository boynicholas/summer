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

import me.lyml.summer.base.entity.BaseEntity;

import javax.persistence.*;

/**
 * @ClassName: Permission
 * @author: cnlyml
 * @date: 2016/9/2 19:50
 */
@Entity
@Table(name = "s_permission")
public class Permission extends BaseEntity {
    private String permissionName;
    private String permissionCode;

    @Transient
    private Module module;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
