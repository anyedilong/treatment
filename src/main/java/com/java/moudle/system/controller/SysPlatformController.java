package com.java.moudle.system.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.TmPlatform;
import com.java.moudle.system.service.SysPlatformService;
import com.java.until.StringUntil;
import com.java.until.UUIDUtil;

/**
 * <p>Title: SysUserController.java</p>
 * <p>Description : 系统管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : tz
 * @date : 2020/1/2 9:01
 * @version : V1.0.0
 */

@RestController
@RequestMapping("/sys/platform")
public class SysPlatformController  extends BaseController {
    
	@Inject
    private SysPlatformService platformService;

    
    /**
     * @Description: 查询系统信息
     * @param @param id
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("show")
    public String show(){
    	try{
    		TmPlatform info = platformService.queryPlatDetail();
    		return jsonResult(info);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 保存系统信息
     * @param @param menu
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("save")
    public String save(TmPlatform info){
    	try{
    		if(StringUntil.isNull(info.getId())) {
    			info.setId(UUIDUtil.getUUID());
    		}
    		platformService.save(info);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}

    /**
     * @Description: 删除系统信息
     * @param @param menu
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("delete")
    public String save(String id){
    	try{
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择删除的数据");
    		}
    		TmPlatform info = platformService.get(id);
    		info.setStatus("3");
    		platformService.save(info);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询系统信息
     * @param @param id
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("reset")
    public String reset(){
    	try{
    		//重置的系统信息
    		TmPlatform info = platformService.get("685901e31e9a4116b5d7dfd3dc4eba90");
    		//最新的系统信息
    		TmPlatform info1 = platformService.queryPlatDetail();
    		info.setId(info1.getId());
    		return jsonResult(info);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}

}
