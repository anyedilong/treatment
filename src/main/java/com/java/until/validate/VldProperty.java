package com.java.until.validate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 读取Properties文件 File: bltwsProperties.java
 */
public final class VldProperty {

	protected static Properties prop;

	static {
		prop = new Properties();
		// InputStream in =
		// Object.class.getResourceAsStream("classpath:/bltsys.properties");
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("validate.properties"));
			// prop.load(in);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void put(String key, String value) {

		// 获取文件的位置
		String filePath = Thread.currentThread().getContextClassLoader().getResource("validate.properties").getFile();

		// System.out.println(filePath);
		try {
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(key, value);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + key + "' value");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String get(String key) {
		Object obj = prop.get(key);
		if (null == obj)
			return "";
		return obj.toString();
	}

	/**
	 * 私有构造方法，不需要创建对象
	 */
	private VldProperty() {
	}

}