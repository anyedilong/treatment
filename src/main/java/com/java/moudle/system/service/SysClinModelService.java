package com.java.moudle.system.service;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.TmClinModel;
import com.java.until.dba.PageModel;

public interface SysClinModelService extends BaseService<TmClinModel> {
    
	//查询模版
	public void getClinModelPage(TmClinModel info, PageModel page);
	
	int queryInfoByTitle(String title, String orgId);
	
}
