package com.java.moudle.system.dao;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysPlatformRepository;
import com.java.moudle.system.domain.TmPlatform;
import com.java.until.dba.BaseDao;


@Named
public class SysPlatformDao extends BaseDao<SysPlatformRepository, TmPlatform> {

	public TmPlatform queryPlatDetail() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* ");
		sql.append(" from tm_platform a ");
		return queryOne(sql.toString(), null, TmPlatform.class);
	}
	
}
