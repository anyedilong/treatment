package com.java.moudle.statistical.service.impl;

import com.java.moudle.statistical.dao.StatTurnDao;
import com.java.moudle.statistical.dto.StatTurnDto;
import com.java.moudle.statistical.dto.StatTurnReturnDto;
import com.java.moudle.statistical.service.StatTurnService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@Transactional(readOnly=false)
public class StatTurnServiceImpl implements StatTurnService {
    @Inject
    private StatTurnDao statTurnDao;

    /**
     * @Title : 转出统计
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/9 17:13 
     * @param : 
     * @return : 
     * @throws
     */
    @Override
    public StatTurnReturnDto statTurnOutArea(StatTurnDto dto) {
        return statTurnDao.statTurnOutArea(dto);
    }

    /**
     * @Title : 转入统计
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/9 17:13 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public StatTurnReturnDto statTurnInArea(StatTurnDto dto) {
        return statTurnDao.statTurnInArea(dto);
    }

    @Override
    public Map<String, Object> statTurnChart(StatTurnDto dto) {
        return statTurnDao.statTurnChart(dto);
    }
}
