package com.java.moudle.turnout.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.common.message.JsonResult;
import com.java.moudle.common.utils.properties.PropertiesUtil;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.until.http.HttpRequest;
import com.java.until.resthttp.RestTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: CustomerController.java</p>
 * <p>Description : 调取居民健康浏览器档案查询，根据身份证号查询档案</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/3 10:53
 * @version : V1.0.0
 */

@RestController
@RequestMapping("${trepath}/customer")
public class CustomerController extends BaseController {
    @Inject
    private RestTemplate restTemplate;

    /**
     * @Title : 新公卫健康浏览器取档案数据
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/3/6 16:39 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("getCustomerBySfzh2")
    public String getCustomerBySfzh2(TurnOutDto turn) {
        try {
            String sfzh = turn.getSfzh();
            if (sfzh != null) {
                JsonResult result = RestTemplateUtils.sendPost(restTemplate, PropertiesUtil.getFollow("getCustomerInfoBySfzh"), turn);
                return result(result);
            } else {
                return jsonResult(null, 10001, "参数为空");
            }
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult(null, 10001, e.getMessage());
        }
    }

    /**
     * @Title : 220旧公卫居民健康浏览器提取数据
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/3/6 16:40 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("getCustomerBySfzh")
    public String getCustomerBySfzh(TurnOutDto turn) {
        try {
            String sfzh = turn.getSfzh();
            Map<String, String> paramMap = new HashMap<String, String>();
            if (sfzh != null) {
                paramMap.put("sfzh",sfzh);
                Map<String, String> paramMap2 = new HashMap<String, String>();
                paramMap2.put("doctorCode",PropertiesUtil.getFollow("doctorCode"));
                paramMap2.put("doctorName",PropertiesUtil.getFollow("doctorName"));
                String token = HttpRequest.sendPostToken(PropertiesUtil.getFollow("healthUrl"),paramMap2);
                JsonResult result = HttpRequest.sendPost(PropertiesUtil.getFollow("queryHealthArc"), paramMap,token);
                return result(result);
            } else {
                return jsonResult(null, 10001, "参数为空");
            }
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult(null, 10001, e.getMessage());
        }
    }

}
