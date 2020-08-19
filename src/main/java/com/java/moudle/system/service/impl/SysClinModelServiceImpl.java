package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysClinModelDao;
import com.java.moudle.system.domain.TmClinModel;
import com.java.moudle.system.service.SysClinModelService;
import com.java.until.dba.PageModel;


@Named
@Transactional(readOnly=false)
public class SysClinModelServiceImpl extends BaseServiceImpl<SysClinModelDao, TmClinModel>  implements SysClinModelService {

	@Override
	public void getClinModelPage(TmClinModel info, PageModel page) {
		dao.getClinModelPage(info, page);
	}

	@Override
	public int queryInfoByTitle(String title, String orgId) {
		return dao.queryInfoByTitle(title, orgId);
	}

	
}
