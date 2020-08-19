package com.java.moudle.system.controller;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.common.message.JsonResult;
import com.java.moudle.common.utils.properties.PropertiesUtil;
import com.java.moudle.system.dto.SysAreaDto;
import com.java.moudle.system.service.SysAreaService;
import com.java.until.StringUtils;
import com.java.until.resthttp.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("${trepath}/sys/area")
public class SysAreaController extends BaseController{

	@Autowired
	private SysAreaService sysAreaService;
	@Inject
	private RestTemplate restTemplate;

	@RequestMapping("areaTree")
	public String AreaTree(SysAreaDto dto) {
		String areaId = dto.getAreaId();
		List<SysAreaDto> areaTree = null;
		//因居民档案是从220的健康浏览器中抽取的，为保证数据一致性，将220的区划信息导入到了treat库中  先从本地取机构
		if(!StringUtils.isNull(areaId)){//id不为空取当前区划及下级区划信息
			areaTree = sysAreaService.getAreaTree(areaId);
		}else{//id为空取所有省级机构
			areaTree = sysAreaService.getOneAreaList();
		}
		//从新公卫中获取区划信息
//		JsonResult result = RestTemplateUtils.sendPost(restTemplate, PropertiesUtil.getFollow("areaTree"), dto);
//		return jsonResult(result.getData());
		return jsonResult(areaTree);
	}

}
