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

package me.lyml.summer.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 关于异常的工具类.
 * 
 * 参考了guava的Throwables。
 * 
 * @author calvin
 */
class Exceptions {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	static RuntimeException unchecked(Throwable ex) {
		if (ex instanceof RuntimeException) {
			return (RuntimeException) ex;
		} else {
			return new RuntimeException(ex);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable ex) {
		StringWriter stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 获取组合本异常信息与底层异常信息的异常描述, 适用于本异常为统一包装异常类，底层异常才是根本原因的情况。
	 */
	public static String getErrorMessageWithNestedException(Throwable ex) {
		Throwable nestedException = ex.getCause();
		return ex.getMessage() + " nested exception is " +
				nestedException.getClass().getName() + ":" + nestedException.getMessage();
	}

	/**
	 * 获取异常的Root Cause.
	 */
	public static Throwable getRootCause(Throwable ex) {
		Throwable cause;
		while ((cause = ex.getCause()) != null) {
			ex = cause;
		}
		return ex;
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	@SafeVarargs
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
}
