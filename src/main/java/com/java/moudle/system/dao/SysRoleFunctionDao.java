package com.java.moudle.system.dao;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysRoleFunctionRepository;
import com.java.moudle.system.domain.SysRoleFunction;
import com.java.until.dba.BaseDao;


@Named
public class SysRoleFunctionDao extends BaseDao<SysRoleFunctionRepository, SysRoleFunction> {

	public void deleteByMenuId(String menuId) {
		repository.deleteByMenuId(menuId);
	}
	
	public void deleteByRoleId(String roleId) {
		repository.deleteByRoleId(roleId);
	}
}
