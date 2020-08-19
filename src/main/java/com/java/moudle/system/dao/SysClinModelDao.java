package com.java.moudle.system.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysClinModelRepository;
import com.java.moudle.system.domain.TmClinModel;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;


@Named
public class SysClinModelDao extends BaseDao<SysClinModelRepository, TmClinModel> {

	
	public void getClinModelPage(TmClinModel info, PageModel page) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.* ");
		sql.append(" from tm_clin_model u ");
		sql.append(" where u.org_id = :orgId ");
		sql.append(" and u.dep_id = :depId ");
		sql.append(" and u.type = :type ");
		sql.append(" order by u.id ");
		map.put("orgId", info.getOrgId());
		map.put("depId", info.getDepId());
		map.put("type", info.getType());
		queryPageList(sql.toString(), map, page, TmClinModel.class);
	}
    
	public int queryInfoByTitle(String title, String orgId) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) ");
		sql.append(" from tm_clin_model u ");
		sql.append(" where u.title = :title ");
		sql.append(" and u.org_id = :orgId ");
		
		map.put("title", title);
		map.put("orgId", orgId);
		return queryOne(sql.toString(), map, Integer.class);
	}
}
