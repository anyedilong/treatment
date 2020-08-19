package com.java.moudle.tripart.hcplatform.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.tripart.hcplatform.dto.TmTurnDto;
import com.java.moudle.tripart.hcplatform.service.TurnService;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysUserController.java</p>
 * <p>Description : 惠民平台调用管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : tz
 * @date : 2020/1/2 9:01
 * @version : V1.0.0
 */

@RestController
@RequestMapping("hcplatform")
public class TurnController extends BaseController {
    
	@Inject
    private TurnService turnService;

	
	 /**
	  * @Description: 查询双向转诊列表(惠民平台)
	  * @param @return
	  * @return PageModel
	  * @throws
	  */
    @RequestMapping("getTurnPage")
    public String getTurnPage(String sfzh, PageModel page) {
        try {
        	TmTurnDto turn = new TmTurnDto();
        	turn.setSfzh(sfzh);
        	turnService.getTurnPage(turn, page);
            return JSONObject.toJSONString(page);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
    /**
     * @Description: 双向转诊详情查看
     * @param @return
     * @return TmTurnDto
     * @throws
     */
    @RequestMapping("getTurnDetail")
    public String getTurnDetail(String id, String type){
    	try{
    		TmTurnDto info = turnService.getTurnDetail(id, type);
    		return JSONObject.toJSONString(info);
    	}catch (Exception e){
    		e.printStackTrace();
	    }
    	return null;
	}
    
}
