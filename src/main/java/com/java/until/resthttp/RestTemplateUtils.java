package com.java.until.resthttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.java.moudle.common.message.JsonResult;
import com.java.until.StringUntil;


public class RestTemplateUtils {
	
	/**
	 * 	调用公卫服务，解析返回数据
	 * @param clazz
	 * @return
	 */
	public static JsonResult sendPost(RestTemplate restTemplate, String url, Object param) {
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, param, String.class);
		String body = postForEntity.getBody();
		JSONObject result = JSONObject.parseObject(body);
		String data = result.getString("data");
		if(!StringUntil.isNull(data)) {
			if ("{".equals(data.substring(0, 1))) {
				JSONObject resultData = JSONObject.parseObject(data);
				return new JsonResult(resultData, result.getInteger("retCode"), result.getString("retMsg"));
			} else if ("[".equals(data.substring(0, 1))) {
				JSONArray resultData = JSON.parseArray(data);
				return new JsonResult(resultData, result.getInteger("retCode"), result.getString("retMsg"));
			} else {
				return new JsonResult(data, result.getInteger("retCode"), result.getString("retMsg"));
			}
		}
		return new JsonResult("", result.getInteger("retCode"), result.getString("retMsg"));
	}
}
