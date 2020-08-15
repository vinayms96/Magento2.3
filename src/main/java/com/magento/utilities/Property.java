package com.magento.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.magento.path.Constants;

public class Property implements Constants{
	public static String getProperty(String key) {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(prop_path)));
			return prop.getProperty(key);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
