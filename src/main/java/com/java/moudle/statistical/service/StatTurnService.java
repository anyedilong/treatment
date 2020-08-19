package com.java.moudle.statistical.service;

import com.java.moudle.statistical.dto.StatTurnDto;
import com.java.moudle.statistical.dto.StatTurnReturnDto;

import java.util.Map;

public interface StatTurnService {
	
    StatTurnReturnDto statTurnOutArea(StatTurnDto dto);
    
    StatTurnReturnDto statTurnInArea(StatTurnDto dto);
    
    Map<String, Object> statTurnChart(StatTurnDto dto);
}
