package com.java.moudle.system.dao;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysUserRoleRepository;
import com.java.moudle.system.domain.SysUserRole;
import com.java.until.dba.BaseDao;


@Named
public class SysUserRoleDao extends BaseDao<SysUserRoleRepository, SysUserRole> {

    public void deleteByUserId(String userId) {
    	repository.deleteByUserId(userId);
    }
    
}
