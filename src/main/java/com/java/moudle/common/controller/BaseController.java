package com.java.moudle.common.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.java.moudle.common.message.JsonResult;
import com.java.moudle.common.message.ProcessStatus;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {

	@Resource
	protected HttpServletRequest request;

	@Resource
	protected HttpServletResponse response;

	/**
	 * 客户端返回字符串
	 * 
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * <li>描述:结果集 默认状态为0</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public String jsonResult(Object data) {
		JsonResult result = new JsonResult(data);
		return result(result);
	}

	public String jsonResult() {
		JsonResult result = new JsonResult(null);
		return result(result);
	}

	/**
	 * 
	 * <li>描述:结果集加状态</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@param status
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public String jsonResult(Object data, ProcessStatus status) {
		JsonResult result = new JsonResult(data, status);
		return result(result);
	}

	/**
	 * 
	 * <li>描述:</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data 结果
	 * <li>参数:@param propertyKey 配置文件中的key
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public String jsonResult(Object data, String propertyKey) {
		ProcessStatus status = new ProcessStatus();
		JsonResult result = new JsonResult(data, status);
		return result(result);
	}

	/**
	 * 
	 * <li>描述:</li>
	 * <li>方法名称:jsonResult</li>
	 * <li>参数:@param data
	 * <li>参数:@param retCode 状态码
	 * <li>参数:@param retMsg 描述
	 * <li>参数:@return</li>
	 * <li>返回类型:JsonResult</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public String jsonResult(Object data, int retCode, String retMsg) {
		ProcessStatus status = new ProcessStatus(retCode, retMsg);
		JsonResult result = new JsonResult(data, status);
		return result(result);
	}

	public String result(JsonResult result) {
		String resultString = request.getParameter("callback") + "(" + JSON.toJSONString(result) + ")";
		return resultString;
	}
	
	public String jsonResultString(Object data) {
		JsonResult result = new JsonResult(data);
		String resultString = request.getParameter("callback") + "(" + JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat) + ")";
		return resultString;
	}

	@InitBinder
	public void init(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
