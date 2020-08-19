package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysTurnHisDao;
import com.java.moudle.system.domain.SysTurnHis;
import com.java.moudle.system.service.SysTurnHisService;


@Named
@Transactional(readOnly=false)
public class SysTurnHisServiceImpl extends BaseServiceImpl<SysTurnHisDao, SysTurnHis>  implements SysTurnHisService {

	@Override
	public void deleteByTurnId(String turnId, String depId, String orgId, String sfzh, String type) {
		dao.deleteByTurnId(turnId, depId, orgId, sfzh, type);
	}

	
	
}
