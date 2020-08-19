package com.java.moudle.system.service;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.TmPlatform;

public interface SysPlatformService extends BaseService<TmPlatform> {
    
	TmPlatform queryPlatDetail();
	
}
