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

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtils {

	private static Properties properties = null;

	public PropertiesUtils() {
		super();
		properties = new Properties();
		InputStreamReader inStream = null;
		try {
			// properties=PropertiesLoaderUtils.loadAllProperties("config/config.properties");
			ClassLoader loader = PropertiesUtils.class.getClassLoader();
			inStream = new InputStreamReader(loader.getResourceAsStream("config/config.properties"), "UTF-8");
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null)
					inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Properties getProperties() {
		if (properties == null)
			new PropertiesUtils();
		return properties;
	}

	public static Properties getProperties(String config){
		Properties properties = new Properties();
		InputStreamReader inStream = null;
		try {
			ClassLoader loader = PropertiesUtils.class.getClassLoader();
			inStream = new InputStreamReader(loader.getResourceAsStream(config), "UTF-8");
			properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null)
					inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(PropertiesUtils.getProperties().getProperty("website.default.pagesize"));
	}
}
