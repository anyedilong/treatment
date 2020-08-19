package com.java.moudle.statistical.service;

import java.util.Map;

import com.java.moudle.statistical.dto.StatsTurnBackDto;

public interface StatsTurnBackService {
   
	//医院转出、转入统计
	Map<String, Object> queryTurnBackOutStats(StatsTurnBackDto info);
	
}
