package com.java.moudle.system.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.dba.PageModel;

public interface SysUserService extends BaseService<SysUser> {
    
	//查询用户（分页）
	void getUserPage(SysUser user, PageModel page);
	//查询用户list
	List<SysUser> getUserList(SysUser user);
	//查询用户名是否被占用
	SysUser queryInfoByCon(String username);
	//保存用户信息
	void saveUserInfo(SysUser user);
	//获取用户信息
	SysUser getUserInfo(String id);
	//如果为科室领导，查询是否已经存在
	int queryNumByAuthor(SysUser user);
}
