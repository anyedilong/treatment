package com.java.moudle.system.service;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysTurnHis;

public interface SysTurnHisService extends BaseService<SysTurnHis> {
    
	void deleteByTurnId(String turnId, String depId, String orgId, String sfzh, String type);
	
}
