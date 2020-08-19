package com.java.moudle.tripart.hcplatform.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.tripart.hcplatform.dao.TurnDao;
import com.java.moudle.tripart.hcplatform.dto.TmTurnDto;
import com.java.moudle.tripart.hcplatform.service.TurnService;
import com.java.until.dba.PageModel;


@Named
@Transactional(readOnly=false)
public class TurnServiceImpl implements TurnService {

	@Inject
	private TurnDao turnDao;
	
	@Override
	public void getTurnPage(TmTurnDto turn, PageModel page) {
		turnDao.getTurnPage(turn, page);
	}

	@Override
	public TmTurnDto getTurnDetail(String id, String type) {
		return turnDao.getTurnDetail(id, type);
	}

	
   
}
