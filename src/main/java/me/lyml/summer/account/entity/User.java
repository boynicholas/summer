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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @ClassName: User
 * @author: cnlyml
 * @date: 2016/8/31 20:32
 */
@Entity
@Table(name = "s_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = -3679131346466475726L;

    @NotNull(groups = {ValidatorGroup.Add.class}, message = "用户名不能为空")
    @Length(groups = {ValidatorGroup.Add.class}, min = 4, max = 12, message = "用户名长度需在4-12位之间")
    private String userName;

    @NotNull(groups = {ValidatorGroup.Add.class}, message = "密码不能为空")
    @Length(groups = {ValidatorGroup.Add.class}, min = 6, max = 16, message = "用户密码需在6 - 16位之间")
    private String userPass;

    @NotNull(groups = {ValidatorGroup.Add.class, ValidatorGroup.Edit.class}, message = "真实姓名不能为空")
    private String realName;

    @NotNull(groups = {ValidatorGroup.Add.class, ValidatorGroup.Edit.class}, message = "邮箱不能为空")
    @Email(groups = {ValidatorGroup.Add.class, ValidatorGroup.Edit.class}, message = "请输入正确的邮箱")
    private String email;

    @NotNull(groups = {ValidatorGroup.Add.class, ValidatorGroup.Edit.class}, message = "手机号不能为空")
    @Pattern(groups = {ValidatorGroup.Add.class, ValidatorGroup.Edit.class}, regexp = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", message = "请输入正确的手机格式")
    private String mobileNo;

    private Boolean isValid;

    private String avatar;

    private String salt;

    /**
     * 用户所拥有的角色
     */
    @Transient
    private List<Role> roleList = Lists.newArrayList();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String generateCredentialsSalt() {
        return userName + salt;
    }


    @ManyToMany
    @JoinTable(name = "s_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id ASC")
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
