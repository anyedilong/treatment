package com.java.moudle.system.controller;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysFunction;
import com.java.moudle.system.service.SysFunctionService;
import com.java.moudle.system.service.SysRoleFunctionService;
import com.java.until.StringUntil;
import com.java.until.SysUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysFunctionController.java</p>
 * <p>Description : 菜单管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : tz
 * @date : 2020/1/2 9:01
 * @version : V1.0.0
 */

@RestController
@RequestMapping("${trepath}/sys/function")
public class SysFunctionController  extends BaseController {
    
	@Inject
    private SysFunctionService functionService;
	@Inject
	private SysRoleFunctionService roleFunctionService;

    /**
     * @Description: 查询菜单（分页）
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getMenuPage")
    public String getMenuPage(SysFunction menu, PageModel page){
    	try{
    		functionService.getMenuPage(menu, page);
    		return jsonResult(page);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询菜单list
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getMenuList")
    public String getMenuList(){
    	try{
    		List<SysFunction> list = functionService.getMenuList();
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 根据角色查询菜单list
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getMenuListByRoleId")
    public String getMenuListByRoleId(String roleId, String type){
    	try{
    		if(StringUntil.isNull(roleId)) {
    			return jsonResult(null, 10000, "角色标识为空");
    		}
    		List<SysFunction> list = functionService.getMenuListByRoleId(roleId, type);
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 根据登录者角色查询菜单list
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getFunListByRoleId")
    public String getFunListByRoleId(){
    	try{
    		String roleId = SysUtil.sysUser(request, response).getRole().getId();
    		List<SysFunction> list = functionService.getFunListByRoleId(roleId);
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询菜单的详情
     * @param @param id
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("show")
    public String show(String id){
    	try{
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择查询的数据");
    		}
    		SysFunction info = functionService.get(id);
    		return jsonResult(info);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 保存菜单信息
     * @param @param menu
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("save")
    public String save(SysFunction menu){
    	try{
    		//查询菜单名称是否可用
    		
    		if(StringUntil.isNull(menu.getId())) {
    			menu.setId(UUIDUtil.getUUID());
    			menu.setStatus("1");
    			if(StringUntil.isNull(menu.getParentId())) {
    				menu.setParentId("0");
    			}
    		}
    		functionService.save(menu);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}

    /**
     * @Description: 删除菜单信息
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
    		functionService.delete(id);
    		//删除全部该菜单与角色的关系数据
    		roleFunctionService.deleteByMenuId(id);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 角色分配权限
     * @param @param menu
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("author")
    public String author(String roleId, String funcitons){
    	try{
    		if(StringUntil.isNull(roleId) || StringUntil.isNull(funcitons)) {
    			return jsonResult(null, 10000, "请选择授权的数据");
    		}
    		List<String> funlist = Arrays.asList(funcitons.split(","));
    		roleFunctionService.authorize(roleId, funlist);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
}
