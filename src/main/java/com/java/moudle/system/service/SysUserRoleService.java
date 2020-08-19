package com.java.moudle.system.service;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysUserRole;

public interface SysUserRoleService extends BaseService<SysUserRole> {
    
	//删除该用户全部与角色关系
	void deleteByUserId(String userId);
		
}
