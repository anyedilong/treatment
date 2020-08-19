package com.java.until.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName ValidateCustom
 * @Description 自定义校验类
 * @author sen
 * @Date 2016年11月16日 上午9:16:06
 * @version 1.0.0
 */
public class ValidateCustom {
    /**	
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
	@ValidateMethod(type=ValidateType.MOBILE,msgProperty="MOBILE_MSG")
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
	@ValidateMethod(type=ValidateType.TELEPHONE,msgProperty="TELEPHONE_MSG")
    public static boolean isTelePhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }
}
