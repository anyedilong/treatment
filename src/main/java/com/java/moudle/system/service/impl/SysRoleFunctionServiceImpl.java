package com.java.moudle.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysFunctionDao;
import com.java.moudle.system.dao.SysRoleFunctionDao;
import com.java.moudle.system.domain.SysFunction;
import com.java.moudle.system.domain.SysRoleFunction;
import com.java.moudle.system.service.SysRoleFunctionService;
import com.java.until.UUIDUtil;


@Named
@Transactional(readOnly=false)
public class SysRoleFunctionServiceImpl extends BaseServiceImpl<SysRoleFunctionDao, SysRoleFunction>  implements SysRoleFunctionService {

	@Inject
	private SysFunctionDao functionDao;
	
	@Override
	public void deleteByMenuId(String menuId) {
		dao.deleteByMenuId(menuId);
	}

	@Override
	public void deleteByRoleId(String roleId) {
		dao.deleteByRoleId(roleId);
	}

	@Override
	public void authorize(String roleId, List<String> funIds) {
		//删除以前角色和权限的关系
		dao.deleteByRoleId(roleId);
		//获取权限的父节点
		Set<String> funSet = new HashSet<>(funIds);
		for(String funId : funIds) {
			coverFunParentId(funSet, funId);
		}
		
		List<String> funlist = new ArrayList<>(funSet);
		for(int i = 0; i < funlist.size(); i++) {
			SysRoleFunction info = new SysRoleFunction();
			info.setId(UUIDUtil.getUUID());
			info.setRoleId(roleId);
			info.setFunctionId(funlist.get(i));
			dao.save(info);
		}
		
	}

	private void coverFunParentId(Set<String> funSet, String funId) {
		SysFunction function = functionDao.getFunctionInfoByChildId(funId);
		if(function != null) {
			funSet.add(function.getId());
			if(!"0".equals(function.getParentId())) {
				coverFunParentId(funSet, function.getId());
			}
		}
	}
   
}
