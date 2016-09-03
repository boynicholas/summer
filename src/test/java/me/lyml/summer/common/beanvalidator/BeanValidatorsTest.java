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

package me.lyml.summer.common.beanvalidator;

import me.lyml.summer.account.entity.User;
import me.lyml.summer.base.validator.ValidatorGroup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.*;

public class BeanValidatorsTest {
    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateWithExceptionByHasException() throws Exception {
        User user = new User();
        try {
            BeanValidators.validateWithException(validator, user, ValidatorGroup.Add.class, ValidatorGroup.Edit.class);
            fail("ConstraintViolationException is not thrown as expected");
        }catch (Exception e) {
            assertTrue(e instanceof ConstraintViolationException);
        }

    }

    @Test
    public void validateWithExceptionByNotException() throws Exception {
        User user = new User();
        user.setUserName("admin");
        user.setUserPass("xxxxxx");
        user.setMobileNo("18080111111");
        user.setRealName("cnlyml");
        user.setEmail("cnlyml@163.com");
        try {
            BeanValidators.validateWithException(validator, user, ValidatorGroup.Add.class, ValidatorGroup.Edit.class);
            assertTrue(true);
        }catch (Exception e) {
            fail("ConstraintViolationException don't thrown as expected");
        }

    }

    @Test
    public void extractMessage() throws Exception {

    }

    @Test
    public void extractMessage1() throws Exception {

    }

    @Test
    public void extractPropertyAndMessage() throws Exception {

    }

    @Test
    public void extractPropertyAndMessage1() throws Exception {

    }

    @Test
    public void extractPropertyAndMessageAsList() throws Exception {

    }

    @Test
    public void extractPropertyAndMessageAsList1() throws Exception {

    }

    @Test
    public void extractPropertyAndMessageAsList2() throws Exception {

    }

    @Test
    public void extractPropertyAndMessageAsList3() throws Exception {

    }

}