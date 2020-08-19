package com.java.moudle.system.service;

import com.java.moudle.system.domain.SysArea;
import com.java.moudle.system.dto.SysAreaDto;

import java.util.List;

public interface SysAreaService {

	List<SysArea> getLowerAreaInfoById(String id);
	
	List<SysAreaDto> getAreaTree(String areaId);

	List<SysAreaDto> getOneAreaList();
}
