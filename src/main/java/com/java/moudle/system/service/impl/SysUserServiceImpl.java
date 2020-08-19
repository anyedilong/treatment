package com.java.moudle.system.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysRoleDao;
import com.java.moudle.system.dao.SysUserDao;
import com.java.moudle.system.dao.SysUserRoleDao;
import com.java.moudle.system.domain.SysRole;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.domain.SysUserRole;
import com.java.moudle.system.service.SysUserService;
import com.java.until.StringUntil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;
import com.java.until.ras.BCrypt;


@Named
@Transactional(readOnly=false)
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser>  implements SysUserService {

	@Inject
    private SysUserRoleDao userRoleDao;
	@Inject
	private SysRoleDao roleDao;
	
	@Override
	public void getUserPage(SysUser user, PageModel page) {
		dao.getUserPage(user, page);
		
	}

	@Override
	public List<SysUser> getUserList(SysUser user) {
		return dao.getUserList(user);
	}

	@Override
	public SysUser queryInfoByCon(String username) {
		return dao.queryInfoByCon("", username);
	}

	@Override
	public void saveUserInfo(SysUser user) {
		if(StringUntil.isNull(user.getId())) {
			user.setId(UUIDUtil.getUUID());
			user.setStatus("1");
		}
		if(StringUntil.isNull(user.getPwd())) {
			user.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
		}else {
			user.setPassword(BCrypt.hashpw(user.getPwd(), BCrypt.gensalt()));
		}
		//删除该用户全部与角色关系
		userRoleDao.deleteByUserId(user.getId());
		String[] roles = user.getAuthorities().split(",");
		for(int i = 0; i < roles.length; i++) {
			SysUserRole role = new SysUserRole();
			role.setId(UUIDUtil.getUUID());
			role.setUserId(user.getId());
			role.setRoleId(roles[i]);
			userRoleDao.save(role);
		}
		dao.save(user);
	}

	@Override
	public SysUser getUserInfo(String id) {
		SysUser user = dao.queryInfoByCon(id, "");
		SysRole role = roleDao.get(user.getAuthorities());
		user.setRole(role);
		return user;
	}

	@Override
	public int queryNumByAuthor(SysUser user) {
		return dao.queryNumByAuthor(user);
	}
	
}
