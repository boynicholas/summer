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

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;

/**
 * Json数据返回
 * @ClassName: JsonRestResult
 * @author: cnlyml
 * @date: 2016年1月12日 下午1:26:58
 */
public class JsonRestResult {
	private int code;
	private String message;
	private Map<String, Object> result;
	
	public JsonRestResult(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	public JsonRestResult(int code, String message, Map<String, Object> result){
		this.code = code;
		this.message = message;
		this.result = result;
	}

	@Override
	public String toString() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("code", code);
		retMap.put("message", message);
		retMap.put("result", result);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsDateJsonValueProcessor());


		return JSONObject.fromObject(retMap, jsonConfig).toString();
	}
	
	public static String toSuccess(String message, Map<String, Object> result){
		return new JsonRestResult(Code.SUCCESS.getCode(), message, result).toString();
	}
	
	public static String toSuccess(String message){
		return toSuccess(message, null);
	}
	
	public static String toSuccess(String message, String key, Object value){
		Map<String, Object> result = new HashMap<>();
		result.put(key, value);
		return toSuccess(message, result);
	}
	
	public static String toFailure(Code code, String message){
		return new JsonRestResult(code.getCode(), message).toString();
	}
	
	public static String toFailure(String message){
		return toFailure(Code.FUNCTION_EXCEPTION, message);
	}
	
	public enum Code{
		/**
		 * 执行成功
		 */
		SUCCESS(1000),
		/**
		 * 鉴权失败
		 */
		AUTHENTICATION_FAILURE(1001),
		/**
		 * 方法异常
		 */
		FUNCTION_EXCEPTION(1002);
		
		private int code;
		
		private Code(int code){
			this.code = code;
		}
		
		public int getCode(){
			return code;
		}
	}
}