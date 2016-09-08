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

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: Department
 * @author: cnlyml
 * @date: 2016/9/2 19:51
 */
@Entity
@Table(name = "s_department")
public class Department extends BaseEntity {
    private static final long serialVersionUID = -5669888667798291049L;

}
