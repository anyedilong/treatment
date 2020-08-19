package com.java.moudle.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysFunctionRepository;
import com.java.moudle.system.domain.SysFunction;
import com.java.until.StringUntil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;


@Named
public class SysFunctionDao extends BaseDao<SysFunctionRepository, SysFunction> {

	public void getMenuPage(SysFunction menu, PageModel page) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.* ");
		sql.append(" from tm_function u ");
		sql.append(" where u.status = '1' ");
		if (!StringUntil.isNull(menu.getName())) {
			sql.append(" and u.name = :name ");
			map.put("name", menu.getName());
		}
		if(!StringUntil.isNull(menu.getType())) {
			sql.append(" and u.type = :type ");
			map.put("type", menu.getType());
		}
		queryPageList(sql.toString(), map, page, SysFunction.class);
	}

	public List<SysFunction> getMenuList(SysFunction menu) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.* ");
		sql.append(" from tm_function u ");
		sql.append(" where u.status = '1' ");
		if (!StringUntil.isNull(menu.getName())) {
			sql.append(" and u.name = :name ");
			map.put("name", menu.getName());
		}
		if(!StringUntil.isNull(menu.getParentId())) {
			sql.append(" and u.parent_id = :parentId ");
			map.put("parentId", menu.getParentId());
		}
		if(!StringUntil.isNull(menu.getType())) {
			sql.append(" and u.type = :type ");
			map.put("type", menu.getType());
		}
		return queryList(sql.toString(), map, SysFunction.class);
	}
	
	public List<SysFunction> getMenuListByRoleId(String roleId, String type, String parentId) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct a.* ");
		sql.append(" from tm_function a ");
		sql.append(" join tm_role_function b on a.id = b.function_id ");
		sql.append(" where a.status = '1' and b.role_id = :roleId ");
		sql.append(" and a.type = :type ");
		if(!StringUntil.isNull(parentId)) {
			sql.append(" and a.parent_id = :parentId ");
			map.put("parentId", parentId);
		}
		sql.append(" order by a.order_num  ");
		map.put("roleId", roleId);
		map.put("type", type);
		return queryList(sql.toString(), map, SysFunction.class);
	}
	
	public SysFunction getFunctionInfoByChildId(String funId) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* ");
		sql.append(" from tm_function a ");
		sql.append(" where a.status = '1' ");
		sql.append("   and a.id = (select b.parent_id from tm_function b where b.status = '1' and b.id = :funId) ");
		map.put("funId", funId);
		return queryOne(sql.toString(), map, SysFunction.class);
	}
}
