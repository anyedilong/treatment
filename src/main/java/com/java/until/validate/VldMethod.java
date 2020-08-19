package com.java.until.validate;

import java.lang.reflect.Method;

public class VldMethod {

	// 方法
	private Method method;

	private ValidateMethod vldMethod;// 检验

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public ValidateMethod getVldMethod() {
		return vldMethod;
	}

	public void setVldMethod(ValidateMethod vldMethod) {
		this.vldMethod = vldMethod;
	}

}
