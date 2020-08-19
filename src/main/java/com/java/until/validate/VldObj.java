package com.java.until.validate;

import com.java.until.StringUntil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class VldObj {

	private static final Map<ValidateType, VldMethod> VALIDATE_METHOD_MAP = getVldMethod();

	private Logger logger = LoggerFactory.getLogger("【object校验工具】");

	private StringBuffer vldMsg = new StringBuffer();

	// 1 正常 2 小写 3大写
	private int FIELD_TYPE = 1;

	// 1 正常 2 驼峰 3 下划线
	private int FIELD_TYPE_CASE = 1;

	private String[] patternArray;

	private List<String> patternList;

	private int patternState = 3;// 验证通配符状态 1* 2*+other 3other

	public VldObj(int fieldTypeCase, int fieldType, String[] patternArray) {
		this.patternArray = patternArray;
		this.FIELD_TYPE_CASE = fieldTypeCase;
		this.FIELD_TYPE = fieldType;

		patternList = new ArrayList<String>();
		// 验证通配符状态 1* 2*+other 3other
		boolean isAllVld = false;
		if (null != patternArray && patternArray.length > 0) {
			for (String pattern : patternArray) {
				if (!"*".equals(pattern)) {
					patternList.add(pattern);
				} else {
					isAllVld = true;
				}
			}
		}

		if (patternList.size() > 0) {
			if (isAllVld) {
				patternState = 2;
			}
		} else {
			patternState = 1;
		}

	}

	public VldObj() {
	}

	/**
	 * 获取校验消息
	 */
	public String getVldMsg(Object vldObj) {

		vldObject(vldObj, null, null);
		return vldMsg.toString();
	}

	public void vldObject(Object vldObj, VldField vldField, String parentName) {
		if (vldObj == null) {
			if (null != vldField && vldField.getValidate() != null) {
				// 如果为必填项
				if (vldField.getValidate().required()) {
					vldMsg.append(String.format(VldProperty.get("REQUIRED_MSG"), vldField.getFieldName()));
				}
			}
			return;
		}

		if (vldObj instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) vldObj;

			if (null != vldField && vldField.getValidate() != null) {
				// 如果为必填项
				if (vldField.getValidate().required()) {
					vldMsg.append(String.format(VldProperty.get("REQUIRED_MSG"), vldField.getFieldName()));
				}
			}
			return;
		}

		if (vldObj instanceof Collection) {
			// 集合
			Collection<Object> collection = (Collection<Object>) vldObj;
			int i = 0;
			for (Object item : collection) {
				if (vldField != null) {
					parentName = String.format("%s[%s]", vldField.getFieldName(), i);
				}
				vldObject(item, vldField, parentName);
				i++;
			}
		} else {
			if (StringUtils.isBlank(parentName)) {
				if (vldField != null) {
					parentName = vldField.getFieldName();
				}
			}
			vldClass(vldObj, vldField, parentName);
		}
	}

	/**
	 * 校验CLASS
	 */
	private void vldClass(Object vldObj, VldField vldField, String parentName) {
		Class<?> clazz = vldObj.getClass();

		// 如果 为基本类型
		if (clazz.isPrimitive() || clazz == String.class || clazz == Integer.class || clazz == Long.class
				|| clazz == Double.class || clazz == Float.class) {
			vldField(vldObj, vldField, clazz);
		} else if (clazz == Date.class) {
			// vldField(vldObj, vldField,clazz);
		} else {
			if (clazz instanceof Class) {
				// 获取class 属性
				List<VldField> vldFieldArray = getField(clazz, parentName);

				for (VldField _vldField : vldFieldArray) {
					try {
						Method method = clazz.getMethod(_vldField.showMethodName());
						Object obj = method.invoke(vldObj);

						vldObject(obj, _vldField, _vldField.getFieldName());

					} catch (NoSuchMethodException | SecurityException e) {
						// TODO Auto-generated catch block
						logger.info(e.getMessage());
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 校验字段
	 * 
	 * @param clazz
	 */
	private void vldField(Object vldObj, VldField vldField, Class<?> clazz) {
		if (null != vldField && null != vldField.getValidate()) {
			Validate vld = vldField.getValidate();
			// 是否必填
			String vldStr = StringUntil.toString(vldObj);
			if (StringUntil.isNull(vldStr) && vld.required()) {
				vldMsg.append(String.format(VldProperty.get("REQUIRED_MSG"), vldField.getFieldName()));
			}

			// 长度
			int length = vldStr.length();
			int maxLength = vld.maxLength();
			int minLength = vld.minLength();
			int vldLength = vld.length();
			if (vldLength > 0) {
				if (vldLength != length) {
					vldMsg.append(String.format(VldProperty.get("LENGTH_MSG"), vldField.getFieldName(), vldLength));
				}
			} else if (maxLength > 0 && minLength > 0) {
				if (maxLength < length || length < minLength) {
					vldMsg.append(String.format(VldProperty.get("BETWEEN_LENGTH_MSG"), vldField.getFieldName(),
							minLength, maxLength));
				}
			} else if (maxLength > 0) {
				if (maxLength < length) {
					vldMsg.append(String.format(VldProperty.get("MAX_LENGTH_MSG"), vldField.getFieldName(), maxLength));
				}
			} else if (minLength > 0) {
				if (maxLength > length) {
					vldMsg.append(String.format(VldProperty.get("MIN_LENGTH_MSG"), vldField.getFieldName(), minLength));
				}

			}

			// 校验类型
			ValidateType[] validateTypes = vld.type();
			if (null != validateTypes && validateTypes.length > 0) {
				for (ValidateType validateType : validateTypes) {
					vldFieldType(vldStr, vldField.getFieldName(), validateType);
				}
			}
		}

	}

	/**
	 * 校验数据类型
	 * 
	 * @param fieldMsg
	 */
	private void vldFieldType(String vldStr, String fieldMsg, ValidateType validateType) {
		// TODO 其他
		VldMethod valMethod = VALIDATE_METHOD_MAP.get(validateType);
		if (null != valMethod) {
			try {
				boolean bl = StringUntil.toBoolean(valMethod.getMethod().invoke(null, vldStr));
				// 校验失败
				if (!bl) {
					// 获取配置文件内容
					String msgProperty = valMethod.getVldMethod().msgProperty();
					if (!StringUntil.isNull(msgProperty)) {
						vldMsg.append(String.format(VldProperty.get(msgProperty), fieldMsg));
					}
				}

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取校验类中的所有校验方法
	 */
	private static Map<ValidateType, VldMethod> getVldMethod() {
		Map<ValidateType, VldMethod> methodMap = new HashMap<ValidateType, VldMethod>();
		Method[] methods = ValidateCustom.class.getDeclaredMethods();

		for (Method method : methods) {

			ValidateMethod validateMethod = method.getAnnotation(ValidateMethod.class);
			if (null != validateMethod) {
				ValidateType validateType = validateMethod.type();
				VldMethod vldMethod = new VldMethod();

				vldMethod.setMethod(method);
				vldMethod.setVldMethod(validateMethod);

				methodMap.put(validateType, vldMethod);
			}
		}
		return methodMap;
	}

	/**
	 * 获取类中需要校验的属性
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private List<VldField> getField(Class clazz, String parentName) {
		List<VldField> vldFieldArray = new ArrayList<VldField>();

		Field[] f = clazz.getDeclaredFields();
		List<Field[]> flist = new ArrayList<Field[]>();
		flist.add(f);

		Class superClazz = clazz.getSuperclass();
		while (superClazz != null) {
			f = superClazz.getFields();
			flist.add(f);
			superClazz = superClazz.getSuperclass();
		}

		// 遍历所有属性
		for (Field[] fields : flist) {
			for (Field field : fields) {

				Validate vld = field.getAnnotation(Validate.class);
				VldField vldField = new VldField();
				String fieldName = "";
				if (null != vld) {
					fieldName = vld.fieldName();
					String[] vldNames = vld.name();
					boolean isVld = false;

					if (patternState == 1) {
						isVld = true;
					} else {
						// 如果 vldname 为空
						if (null == vldNames || vldNames.length == 0) {
							if (patternState == 2) {
								isVld = true;
							}
						} else {
							// 需要验证数据 是否存在通配符下
							for (String vldName : vldNames) {
								if (StringUntil.isNull(vldName)) {
									if (patternState == 2) {
										isVld = true;
										break;
									}
								} else {
									for (String pattern : patternList) {
										if (StringUntil.wildMatch(pattern, vldName)) {
											isVld = true;
											break;
										}
									}
								}
							}
						}

					}

					if (isVld) {
						vldField.setValidate(vld);
					}
				}
				if (StringUntil.isNull(fieldName)) {
					fieldName = field.getName();
					if (FIELD_TYPE_CASE == 2) {
						fieldName = StringUntil.toCapitalizeCamelCase(fieldName);
					} else if (FIELD_TYPE_CASE == 3) {
						fieldName = StringUntil.toUnderScoreCase(fieldName);
					}
					if (FIELD_TYPE == 2) {
						fieldName = fieldName.toLowerCase();
					} else if (FIELD_TYPE == 3) {
						fieldName = fieldName.toUpperCase();
					}

				}
				if (!StringUntil.isNull(parentName)) {
					fieldName = String.format("%s.%s", parentName, fieldName);
				}
				vldField.setFieldName(fieldName);// 字段名称
				vldField.setFieldType(field.getType());// 字段类型
				vldField.setField(field);// 字段
				vldFieldArray.add(vldField);
			}
		}

		return vldFieldArray;
	}

}
