package com.java.moudle.system.service.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.SysDictDao;
import com.java.moudle.system.domain.SysDict;
import com.java.moudle.system.service.SysDictService;


@Named
@Transactional(readOnly = false)
public class SysDictServiceImpl extends BaseServiceImpl<SysDictDao, SysDict> implements SysDictService{

	
	

}
