package com.java.moudle.tripart.hcplatform.service;

import com.java.moudle.tripart.hcplatform.dto.TmTurnDto;
import com.java.until.dba.PageModel;

public interface TurnService {
    
	//查询双向转诊列表
	void getTurnPage(TmTurnDto turn, PageModel page);
	//双向转诊详情查看
	TmTurnDto getTurnDetail(String id, String type);
		
}
