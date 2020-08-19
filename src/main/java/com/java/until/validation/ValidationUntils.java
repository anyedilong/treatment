package com.java.until.validation;

import com.alibaba.fastjson.JSON;
import com.java.moudle.common.message.JsonResult;
import com.java.until.StringUntil;
import com.java.until.validate.VldTestArray;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class ValidationUntils {

    private static StringBuffer vldMsg = new StringBuffer();
    private static List<String> patternList = new ArrayList<String>();

    public static void ValidationObj(Object obj,String[] patternArray,HttpServletResponse response){

            for(String pattern :patternArray){
                    patternList.add(pattern);
            }
         ValidationUntils.VldObject(obj);
        System.out.println(vldMsg.toString());
    }
   protected  static String VldMsg(Object obj){
           VldObject(obj);
       System.out.println(vldMsg.toString());
        return  vldMsg.toString();
   }

    protected  static void  VldObject(Object obj){
        if(obj==null){

          return;
        }
        if(obj instanceof Collection){
            Collection<Object> collection = (Collection<Object>) obj;
            for(Object item:collection){
                      if(StringUntil.isNull(item)){
                          vldMsg.append(String.format("%s是必填项",item));
                      }
            }
        }
        if(obj instanceof Map){
            Map<Object, Object> map = (Map<Object, Object>) obj;
            for(Map.Entry<Object,Object> entry:map.entrySet()){
                           if(StringUntil.isNull(entry.getValue())){
                               vldMsg.append(String.format("%s是必填项",entry.getKey()));
                           }
            }

        }
        if(obj instanceof String){
               String field=String.valueOf(obj);
               if(StringUntil.isNull(field)){
                      vldMsg.append(String.format("%s是必填项",field));
               }
        }
        if(obj instanceof  Class){
               vldClass(obj);
        }

    }

    protected  static  List<VldField> vldClass(Object obj){
        Class<?>   clazz=obj.getClass();
        List<VldField> vldFieldList=new ArrayList<VldField>();
        // 如果 为基本类型
        if (clazz.isPrimitive() || clazz == String.class || clazz == Integer.class || clazz == Long.class
                || clazz == Double.class || clazz == Float.class) {

        } else if (clazz == Date.class) {

        }else{
            if(clazz instanceof  Class){
                Field[] f = clazz.getDeclaredFields();
                List<Field[]> flist = new ArrayList<Field[]>();
                flist.add(f);
                Class superClazz = clazz.getSuperclass();
                while (superClazz!=null){
                   f=superClazz.getFields();
                   flist.add(f);
                   superClazz.getSuperclass();
                }
                for(Field[] fields:flist){
                    for(Field field:fields){
                        boolean vld=false;
                        VldField vldField = new VldField();
                        Validation  validation=field.getAnnotation(Validation.class);
                        String fieldName =field.getName();
                        String[] names=validation.name();
                        for(String name:names){
                            for(String pattern:patternList){
                                if(StringUntil.wildMatch(pattern,name)){
                                    vldField.setValidation(validation);
                                    break;
                                }
                            }
                        }


                        vldField.setFieldName(fieldName);
                        vldField.setFieldType(field.getType());
                        vldFieldList.add(vldField);
                    }
                }
            }
        }
        return  vldFieldList;
    }

    protected  static void  ValidationError(String msg, HttpServletResponse response)throws  Exception{

        response.setContentType("application/json;charset=UTF-8");
        JsonResult  jsonResult=new JsonResult("数据校验错误",10000, String.format("数据校验错误%s",msg));
        response.getWriter().write(JSON.toJSONString(jsonResult));
        response.getWriter().flush();
        response.getWriter().close();
    }

	public static void main(String[] args) {

		VldTestArray tArray1 = new VldTestArray();
		tArray1.setId("1");
		tArray1.setTele("123");
		VldTestArray tArray2 = new VldTestArray();
		tArray2.setId("");
		tArray2.setTele("0531-88881234");

		List<VldTestArray> tArray = new ArrayList<VldTestArray>();
		tArray.add(tArray1);
		tArray.add(tArray2);

		VldTest t = new VldTest();
		t.setId("");
		t.setPhone("00000000000");
		t.setRemarks("11");

		ValidationUntils.ValidationObj(t,new String[] { "save"},null);





	}
}
