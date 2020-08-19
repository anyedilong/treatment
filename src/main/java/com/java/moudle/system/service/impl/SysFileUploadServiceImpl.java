package com.java.moudle.system.service.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.FileUplodDao;
import com.java.moudle.system.domain.TmFileUpload;
import com.java.moudle.system.service.SysFileUploadService;

@Named
@Transactional(readOnly=false)
public class SysFileUploadServiceImpl extends BaseServiceImpl<FileUplodDao, TmFileUpload> implements SysFileUploadService {

	@Override
	public void deleteByTurnId(String turnId) {
		dao.deleteFileByTurnID(turnId);
	}

	@Override
	public List<TmFileUpload> getListByTurnId(String turnId) {
		return dao.getListByTurnId(turnId);
	}

}
