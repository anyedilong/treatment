package com.java.moudle.system.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysRoleFunction;

public interface SysRoleFunctionService extends BaseService<SysRoleFunction> {
    
	
	//根据菜单标识删除全部该菜单与角色的关系数据
	void deleteByMenuId(String menuId);
	//根据角色标识删除全部该菜单与角色的关系数据
	void deleteByRoleId(String roleId);
	
	//根据角色标识删除全部该菜单与角色的关系数据
	void authorize(String roleId, List<String> funIds);
	
}
