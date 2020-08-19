package com.java.moudle.system.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysDict;
import com.java.moudle.system.service.SysDictService;
import com.java.until.UUIDUtil;
import com.java.until.dict.DictUtil;


@RestController
@RequestMapping("${trepath}/sys/dict")
public class SysDictController extends BaseController {

    @Inject
    private SysDictService dictService;
   
	@RequestMapping("saveDictInfo")
    public String saveDictInfo(SysDict info) {
    	try {
    		info.setId(UUIDUtil.getUUID());
    		info.setDeleteFlg(0);
    		info.setUpdateTime(new Date());
    		dictService.save(info);
    		return jsonResult();
    	}catch(Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.getMessage(), -1, "系统错误");
    	}
    }
	
	@RequestMapping("getYblxDict")
    public String getYblxDict() {
    	try {
    		return jsonResult(DictUtil.getDict("yblx"));
    	}catch(Exception e) {
    		e.printStackTrace();
    		return jsonResult(e.getMessage(), -1, "系统错误");
    	}
    }
}
