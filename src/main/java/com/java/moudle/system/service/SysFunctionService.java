package com.java.moudle.system.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysFunction;
import com.java.until.dba.PageModel;

public interface SysFunctionService extends BaseService<SysFunction> {
    
	//查询菜单（分页）
	void getMenuPage(SysFunction menu, PageModel page);
	//查询菜单list
	List<SysFunction> getMenuList();
	//根据角色查询权限list
	List<SysFunction> getMenuListByRoleId(String roleId, String type);
	//根据登录者角色查询权限list
	List<SysFunction> getFunListByRoleId(String roleId);
	
}
