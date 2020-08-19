package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysUserRoleDao;
import com.java.moudle.system.domain.SysUserRole;
import com.java.moudle.system.service.SysUserRoleService;


@Named
@Transactional(readOnly=false)
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRole>  implements SysUserRoleService {

	@Override
	public void deleteByUserId(String userId) {
		dao.deleteByUserId(userId);
	}

   
}
