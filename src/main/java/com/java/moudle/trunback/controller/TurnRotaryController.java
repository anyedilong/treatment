package com.java.moudle.trunback.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.trunback.domain.TmTurnRotary;
import com.java.moudle.trunback.service.TurnRotaryService;
import com.java.until.StringUntil;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysUserController.java</p>
 * <p>Description : 回转管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : tz
 * @date : 2020/1/2 9:01
 * @version : V1.0.0
 */

@RestController
@RequestMapping("${trepath}/rotary")
public class TurnRotaryController  extends BaseController {
    
	@Inject
	private TurnRotaryService turnRotaryService;

	
	/**
	 * @Description: 查询转出菜单-回转（分页）
	 * @param @param info
	 * @param @param page
	 * @param @return
	 * @return String
	 * @throws
	 */
    @RequestMapping("getOutTurnRotaryPage")
    public String getOutTurnRotaryPage(TmTurnRotary info, PageModel page){
    	try{
    		//获取登录用户，得到用户所属的医院
    		SysUser user = SysUtil.sysUser(request, response);
    		info.setAccOrgId(user.getOrgId());
    		info.setAccDepId(user.getDepId());
    		info.setAccDocId(user.getId());
    		info.setRoleType(user.getRole().getRoleType());
    		turnRotaryService.getOutTurnRotaryPage(info, page);
    		return jsonResult(page);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询转入菜单-回转（分页）
     * @param @param info
     * @param @param page
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("getInTurnRotaryPage")
    public String getInTurnRotaryPage(TmTurnRotary info, PageModel page){
    	try{
    		//获取登录用信息
    		SysUser user = SysUtil.sysUser(request, response);
    		info.setOrgId(user.getOrgId());
    		info.setDepId(user.getDepId());
    		info.setDocId(user.getId());
    		info.setRoleType(user.getRole().getRoleType());
    		info.setCreateUser(user.getId());
    		turnRotaryService.getInTurnRotaryPage(info, page);
    		return jsonResult(page);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 回转新建保存
     * @param @param info
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("save")
    public String saveTurnRotary(TmTurnRotary info){
    	try{
    		 //查询同一个人今天填写了几个回转单
            int count = turnRotaryService.getCountByToDay(info.getSfzh());
            if(count > 0) {
            	return jsonResult(null, 10000, "您已经提交了双向转诊（转出）申请，不能重复提交！");
            }
    		//获取登录用户信息
    		SysUser user = SysUtil.sysUser(request, response);
    		turnRotaryService.saveTurnBackInfo(info, user.getId());
    		return jsonResult(info.getId());
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 回转查看
     * @param @param id
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("show")
    public String showTurnRotary(String id){
    	try{
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择查看的回转标识");
    		}
    		TmTurnRotary info = turnRotaryService.showTurnRotary(id);
    		return jsonResult(info);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 回转删除
     * @param @param id
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("delete")
    public String deleteTurnRotary(String id){
    	try{
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择删除的回转标识");
    		}
    		turnRotaryService.deleteTurnRotary(id);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 转出菜单-回转、转入菜单-回转数据的审核
     * @param @param id 回转标识
     * @param @param status 回转状态
     * @param @param reason 原因
     * @param @param type in-转入菜单 out-转出菜单
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("audit")
    public String auditTurnRotary(String id, String status, String reason, String type, String depId){
    	try{
    		//获取登录用户信息
    		SysUser user = SysUtil.sysUser(request, response);
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择审核的回转标识");
    		}
    		turnRotaryService.auditRotary(id, status, reason, type, user.getId(), user.getName(), depId);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 获取回转单的流程图
     * @param @param 
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("getTurnRotaryFlow")
    public String getTurnRotaryFlow(String id){
    	try{
    		return jsonResult(turnRotaryService.getTurnRotaryFlow(id));
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 获取转出菜单-回转、转入菜单-回转的医院
     * @param @param type in-转入菜单 out-转出菜单
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("getTurnRotaryHospitalList")
    public String getTurnRotaryHospitalList(String type){
    	try{
    		//获取登录用户，得到用户所属的医院
    		SysUser user = SysUtil.sysUser(request, response);
    		List<SysOrg> list = turnRotaryService.getRotaryHospitalList(user.getOrgId(), user.getDepId(), user.getRole().getRoleType(), type);
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
}
