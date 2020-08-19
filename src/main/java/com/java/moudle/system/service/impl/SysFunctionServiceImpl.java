package com.java.moudle.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysFunctionDao;
import com.java.moudle.system.domain.SysFunction;
import com.java.moudle.system.service.SysFunctionService;
import com.java.until.dba.PageModel;


@Named
@Transactional(readOnly=false)
public class SysFunctionServiceImpl extends BaseServiceImpl<SysFunctionDao, SysFunction> implements SysFunctionService {

	@Override
	public void getMenuPage(SysFunction menu, PageModel page) {
		dao.getMenuPage(menu, page);
		
	}

	@Override
	public List<SysFunction> getMenuList() {
		SysFunction menu = new SysFunction();
		//查询一级菜单
		menu.setParentId("0");
		menu.setType("1");
		List<SysFunction> list = dao.getMenuList(menu);
		if(list != null && list.size() > 0) {
			for(SysFunction info : list) {
				coverMenuInfo(info);
			}
		}
		return list;
	}

	@Override
	public List<SysFunction> getMenuListByRoleId(String roleId, String type) {
		return dao.getMenuListByRoleId(roleId, type, "");
	}
	
	@Override
	public List<SysFunction> getFunListByRoleId(String roleId) {
		List<SysFunction> list = dao.getMenuListByRoleId(roleId, "1", "0");
		if(list != null && list.size() > 0) {
			for(SysFunction info : list) {
				coverMenuInfoByRoleId(info, roleId);
			}
		}
		return list;
	}
	
	private void coverMenuInfoByRoleId(SysFunction menu, String roleId) {
		List<SysFunction> list = dao.getMenuListByRoleId(roleId, "1", menu.getId());
		if(list != null && list.size() > 0) {
			menu.setFunList(list);
			for(SysFunction info : list) {
				coverMenuInfoByRoleId(info, roleId);
			}
		}else {
			//查询菜单的按钮
			List<SysFunction> buttonList = dao.getMenuListByRoleId(roleId, "2", menu.getId());
			if(buttonList != null && buttonList.size() > 0) {
				menu.setFunList(buttonList);
			}else {
				menu.setFunList(new ArrayList<>());
			}
		}
	}
	
	
	private void coverMenuInfo(SysFunction menu) {
		SysFunction menuCon = new SysFunction();
		menuCon.setParentId(menu.getId());
		menuCon.setType("1");
		List<SysFunction> list = dao.getMenuList(menuCon);
		if(list != null && list.size() > 0) {
			menu.setFunList(list);
			for(SysFunction info : list) {
				coverMenuInfo(info);
			}
		}else {
			//查询菜单的按钮
			menuCon.setType("2");
			List<SysFunction> buttonList = dao.getMenuList(menuCon);
			if(buttonList != null && buttonList.size() > 0) {
				menu.setFunList(buttonList);
			}else {
				menu.setFunList(new ArrayList<>());
			}
		}
	}

	
}
