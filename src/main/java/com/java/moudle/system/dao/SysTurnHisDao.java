package com.java.moudle.system.dao;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysTurnHisRepository;
import com.java.moudle.system.domain.SysTurnHis;
import com.java.until.dba.BaseDao;


@Named
public class SysTurnHisDao extends BaseDao<SysTurnHisRepository, SysTurnHis> {

   
	public void deleteByTurnId(String turnId, String depId, String orgId, String sfzh, String type) {
		repository.deleteByTurnId(turnId, depId, orgId, sfzh, type);
	}
	
}
