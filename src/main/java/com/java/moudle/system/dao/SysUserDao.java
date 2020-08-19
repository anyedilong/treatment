package com.java.moudle.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysUserRepository;
import com.java.moudle.system.domain.SysUser;
import com.java.until.StringUntil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;


@Named
public class SysUserDao extends BaseDao<SysUserRepository, SysUser> {

	public void getUserPage(SysUser user, PageModel page) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id, b.dep_name as depName, u.profession, ");
		sql.append(" u.username, u.name, u.status, u.dep_id, ");
		//在业务流程中医生及科室主任不能删除0.不能 1.能
		sql.append(" (case (select count(t.id) from ");
		sql.append(" (select r.id, r.doc_id, r.acc_doc_id, r.audit_user as outAuditUser, null as inAuditUser from tm_turn_out r  ");
		sql.append(" union select o.id, o.doc_id, o.acc_doc_id, null as outAuditUser, o.audit_user as inAuditUser from tm_turn_in o ");
		sql.append(" union select s.id, s.doc_id, s.acc_doc_id, s.out_audit_user as outAuditUser, s.in_audit_user as inAuditUser from tm_turn_rotary s) t ");
		sql.append(" where (t.doc_id = u.id or t.acc_doc_id = u.id or t.outAuditUser = u.id or t.inAuditUser = u.id) ) when 0 then '1' else '0' end) as isDel ");
		
		sql.append(" from tm_user u ");
		sql.append(" left join tm_dep b on u.dep_id = b.id ");
		sql.append(" left join tm_role c on u.authorities = c.id ");
		sql.append(" where u.status in ('1', '2') and u.username != 'admin' ");
		sql.append(" and c.role_type in ('1', '2') ");
		if (!StringUntil.isNull(user.getName())) {
			sql.append(" and (u.username like concat(concat('%', :name), '%') or u.name like concat(concat('%', :name), '%') ) ");
			map.put("name", user.getName());
		}
		if(!StringUntil.isNull(user.getOrgId())) {
			sql.append(" and u.org_id = :orgId");
			map.put("orgId", user.getOrgId());
		}
		if(!StringUntil.isNull(user.getDepId())) {
			sql.append(" and u.dep_id = :depId ");
			map.put("depId", user.getDepId());
		}else {
			sql.append(" and u.dep_id is not null ");
		}
		queryPageList(sql.toString(), map, page, SysUser.class);
	}

	public List<SysUser> getUserList(SysUser user) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id, b.dep_name as depName, u.profession, u.username, u.name, u.status, u.dep_id ");
		sql.append(" from tm_user u ");
		sql.append(" left join tm_dep b on u.dep_id = b.id ");
		sql.append(" left join tm_role c on u.authorities = c.id ");
		sql.append(" where u.status = '1' and u.username != 'admin' ");
		sql.append(" and c.role_type = '2' ");
		if (!StringUntil.isNull(user.getName())) {
			sql.append(" and (u.username like concat(concat('%', :name), '%') or u.name like concat(concat('%', :name), '%') ) ");
			map.put("name", user.getName());
		}
		if(!StringUntil.isNull(user.getOrgId())) {
			sql.append(" and u.org_id = :orgId ");
			map.put("orgId", user.getOrgId());
		}
		if(!StringUntil.isNull(user.getDepId())) {
			sql.append(" and u.dep_id = :depId ");
			map.put("depId", user.getDepId());
		}
		return queryList(sql.toString(), map, SysUser.class);
	}
	
	public SysUser queryInfoByCon(String id, String username) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.id, u.username, u.password, u.name, u.status, ");
		sql.append(" u.dep_id, u.profession, u.authorities, u.org_id, ");
		sql.append(" b.dep_name as depName, c.org_name as orgName ");
		sql.append(" from tm_user u ");
		sql.append(" left join tm_dep b on u.dep_id = b.id ");
		sql.append(" left join tm_org c on u.org_id = c.id ");
		sql.append(" where u.status = '1' ");
		if(!StringUntil.isNull(id)) {
			sql.append(" and u.id = :id ");
			map.put("id", id);
		}
		if(!StringUntil.isNull(username)) {
			sql.append(" and u.username = :username ");
			map.put("username", username);
		}
		return queryOne(sql.toString(), map, SysUser.class);
	}
	
	public int queryNumByAuthor(SysUser user) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) ");
		sql.append(" from tm_user u ");
		sql.append(" join tm_role b on u.authorities = b.id ");
		sql.append(" where u.status = '1' ");
		sql.append(" and b.role_type = '1' and u.org_id = :orgId ");
		sql.append(" and u.dep_id = :depId and u.authorities = :authorities ");
		if(!StringUntil.isNull(user.getId())) {
			sql.append(" and u.id != :id ");
		}
		return queryOne(sql.toString(), user, Integer.class);
	}
}
