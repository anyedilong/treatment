package com.java.until.validate;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class VldField {

	// 字段名称
	private String fieldName;
	// 字段类型
	private Class<?> fieldType;

	private Field field;

	private Validate validate;// 检验

	public Validate getValidate() {
		return validate;
	}

	public void setValidate(Validate validate) {
		this.validate = validate;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getMethodName() {
		StringBuffer methodName = new StringBuffer("set");
		String _filedName = field.getName();
		if(StringUtils.isBlank(_filedName)){
			return "";
		}
		
		if(fieldName.length() > 1){
			//判断第二个字母
			String fieldTwo =  _filedName.substring(1, 2);
			if(!fieldTwo.equals(fieldTwo.toUpperCase())){
				methodName.append(_filedName.substring(0, 1).toUpperCase());
			}else{
				methodName.append(_filedName.substring(0, 1));
			}
			methodName.append(_filedName.substring(1, _filedName.length()));
			return methodName.toString();
		}else{
			return _filedName.toUpperCase();
		}
	}

	public String showMethodName() {
		StringBuffer methodName = new StringBuffer("get");
		String _filedName = field.getName();
		if(StringUtils.isBlank(_filedName)){
			return "";
		}
		
		if(_filedName.length() > 1){
			//判断第二个字母
			String fieldTwo =  _filedName.substring(1, 2);
			if(!fieldTwo.equals(fieldTwo.toUpperCase())){
				methodName.append(_filedName.substring(0, 1).toUpperCase());
			}else{
				methodName.append(_filedName.substring(0, 1));
			}
			methodName.append(_filedName.substring(1, _filedName.length()));
			return methodName.toString();
		}else{
			return _filedName.toUpperCase();
		}
	}

}
