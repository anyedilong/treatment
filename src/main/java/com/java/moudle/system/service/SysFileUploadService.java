package com.java.moudle.system.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.TmFileUpload;

public interface SysFileUploadService extends BaseService<TmFileUpload> {

	void deleteByTurnId(String turnId);
	
	List<TmFileUpload> getListByTurnId(String turnId);
	
}
