package com.java.until;

import javax.persistence.Table;


public class CommonUtils {
	
	/**
	 * 通过获取类上的@Table注解获取表名称
	 *
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class<?> clazz) {
		Table annotation = clazz.getAnnotation(Table.class);
		return annotation.name();
	}
}
