package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysPlatformDao;
import com.java.moudle.system.domain.TmPlatform;
import com.java.moudle.system.service.SysPlatformService;


@Named
@Transactional(readOnly=false)
public class SysPlatformServiceImpl extends BaseServiceImpl<SysPlatformDao, TmPlatform>  implements SysPlatformService {

	@Override
	public TmPlatform queryPlatDetail() {
		return dao.queryPlatDetail();
	}

	
   
}
