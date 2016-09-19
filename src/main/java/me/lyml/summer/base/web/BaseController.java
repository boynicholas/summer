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

package me.lyml.summer.base.web;

import me.lyml.summer.common.beanvalidator.BeanValidators;
import me.lyml.summer.common.config.Global;
import me.lyml.summer.common.utils.Logs;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 控制器支持类
 * @ClassName: BaseController
 * @author: cnlyml
 * @date: 2016/9/3 14:20
 */
public abstract class BaseController {
    protected Logger logger = Logs.get();

    @Resource
    protected Validator validator;

    /**
     * 服务端参数有效性验证
     * @Title: beanValidator
     * @param: @param model
     * @param: @param object 验证的实体对象
     * @param: @param groups 验证组
     * @return: 验证成功：返回true；验证失败，将错误信息添加到message中
     * @throws
     */
    protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        }catch(ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据校验失败: ");
            addMessage(model, list.toArray(new String[]{}));
            return false;
        }

        return true;
    }

    /**
     * 服务端参数有效性验证
     * @Title: beanValidator
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param message
     * @param: @param object 验证的实体对象
     * @param: @param groups 验证组
     * @param: 验证成功：返回true；验证失败，将错误信息添加到message中
     * @return: boolean
     * @throws
     */
    protected boolean beanValidator(String message, Object object, Class<?>...groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        }catch(ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据校验失败: ");
            addMessage(message, list.toArray(new String[]{}));
            return false;
        }

        return true;
    }

    /**
     * 服务端参数有效性验证
     * @Title: beanValidator
     * @param: @param redirectAttributes
     * @param: @param object 验证的实体对象
     * @param: @param groups 验证组
     * @return: 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
     * @throws
     */
    protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        }catch(ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据校验失败: ");

            return false;
        }

        return true;
    }

    /**
     * 服务端参数有效性验证
     * @Title: beanValidator
     * @param: @param object 验证的实体对象
     * @param: @param groups 验证组，不传入此参数时，同@Valid注解验证
     * @return: void 验证成功：继续执行；验证失败：抛出异常跳转400页面
     * @throws
     */
    protected void beanValidator(Object object, Class<?>... groups) {
        BeanValidators.validateWithException(validator, object, groups);
    }

    /**
     * 添加Model消息
     * @Title: addMessage
     * @param: @param model
     * @param: @param messages
     * @return: void
     * @throws
     */
    protected void addMessage(Model model, String... messages) {

        StringBuilder sb = new StringBuilder();
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * 添加Flash消息
     * @Title: addMessage
     * @param: @param redirectAttributes
     * @param: @param message
     * @return: void
     * @throws
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuffer sb = new StringBuffer();
        for(String message : messages) {
            sb.append(message).append(message.length() > 1 ? "<br />" : "");
        }

        redirectAttributes.addFlashAttribute("message", sb.toString());
    }

    /**
     * 添加String消息
     * @Title: addMessge
     * @param: @param message
     * @param: @param messages
     * @param: @return
     * @return: String
     * @throws
     */
    protected void addMessage(String message, String... messages) {
        StringBuffer sb = new StringBuffer();
        for(String m : messages) {
            sb.append(m).append(m.length() > 1 ? "<br />" : "");
        }

        message = sb.toString();
    }

    /**
     * 参数绑定异常
     * @Title: bindException
     * @param: @return
     * @return: String
     * @throws
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {
        return "error/400";
    }

    @ExceptionHandler({AccessDeniedException.class})
    public String authenticationException() {
        return "error/403";
    }

    /**
     * 初始化数据绑定
     * 1.将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2.将字段中的Date类型转换为Stirng类型
     * @Title: initBinder
     * @param: @param binder
     * @return: void
     * @throws
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport(){
            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }
        });

        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    setValue(DateUtils.parseDate(text));
                } catch (ParseException e) {
                    throw new ValidationException("时间参数转换失败");
                }
            }
        });
    }


    protected String getAdminPath() {
        return Global.getConfig("adminPath");
    }
}
