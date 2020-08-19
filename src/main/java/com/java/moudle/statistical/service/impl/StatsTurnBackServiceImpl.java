package com.java.moudle.statistical.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.statistical.dao.StatsTurnBackDao;
import com.java.moudle.statistical.dto.StatsTurnBackDto;
import com.java.moudle.statistical.service.StatsTurnBackService;
import com.java.until.StringUtils;


@Named
@Transactional(readOnly=false)
public class StatsTurnBackServiceImpl implements StatsTurnBackService {
   
	@Inject
    private StatsTurnBackDao statsTurnBackDao;

	@Override
	public Map<String, Object> queryTurnBackOutStats(StatsTurnBackDto info) {
		//返回map
		Map<String, Object> resultMap = new HashMap<>();
		//初始化柱状图数据
		List<String> xList = new ArrayList<>();
		List<Integer> y1List = new ArrayList<>();
		List<Integer> y2List = new ArrayList<>();
		//初始化饼状图数据
		List<StatsTurnBackDto> sumList = new ArrayList<>();
		List<StatsTurnBackDto> turnInList = new ArrayList<>();
		List<StatsTurnBackDto> turnBackList = new ArrayList<>();
		
		converDateInfo(info);
		List<StatsTurnBackDto> list = statsTurnBackDao.queryTurnBackOutStats(info);
		if(list != null && list.size() > 0) {
			int sum = 0;
			int backSum = 0;
			for(int i = 0; i < list.size(); i++) {
				StatsTurnBackDto dto = list.get(i);
				//柱状图
				xList.add(dto.getName());
				y1List.add(dto.getValue());
				y2List.add(dto.getValue1());
				//饼图，数据为零的剔除
				if(dto.getValue() > 0) {
					sum += dto.getValue();
					StatsTurnBackDto temp = new StatsTurnBackDto();
					temp.setValue(dto.getValue());
					temp.setName(dto.getName());
					turnInList.add(temp);
				}
				if(dto.getValue1() > 0) {
					backSum += dto.getValue1();
					StatsTurnBackDto temp = new StatsTurnBackDto();
					temp.setValue(dto.getValue1());
					temp.setName(dto.getName());
					turnBackList.add(temp);
				}
			}
			StatsTurnBackDto sumDto1 = new StatsTurnBackDto();
			sumDto1.setName(("1".equals(info.getType()) ? "转出" : "转入"));
			sumDto1.setValue(sum);
			StatsTurnBackDto sumDto2 = new StatsTurnBackDto();
			sumDto2.setName("回转");
			sumDto2.setValue(backSum);
			sumList.add(sumDto1);
			sumList.add(sumDto2);
		}
		resultMap.put("xList", xList);
		resultMap.put("y1List", y1List);
		resultMap.put("y2List", y2List);
		resultMap.put("sumList", sumList);
		if("1".equals(info.getType())) {
			resultMap.put("turnOutList", turnInList);
		}else if("2".equals(info.getType())) {
			resultMap.put("turnInList", turnInList);
		}
		resultMap.put("turnBackList", turnBackList);
		return resultMap;
	}

	/**
	 * 起始时间填写
	 */
	private void converDateInfo(StatsTurnBackDto info) {
		if(!StringUtils.isNull(info.getYear()) && StringUtils.isNull(info.getMonth())) {
			int endyear = Integer.parseInt(info.getYear());
			info.setStartDate(info.getYear()+"-01-01");
			info.setEndDate((endyear+1)+"-01-01");
		}else if(!StringUtils.isNull(info.getYear()) && !StringUtils.isNull(info.getMonth())) {
			int endmonth = Integer.parseInt(info.getMonth());
			info.setStartDate(info.getYear()+ "-" + info.getMonth()+"-01");
			if(endmonth < 9) {
				info.setEndDate(info.getYear()+ "-0" + (endmonth+1)+"-01");
			}else {
				info.setEndDate(info.getYear()+ "-" + (endmonth+1)+"-01");
			}
		}else {
			//防止sql报错；年是必填项，传参正确，不会走这个分支
			info.setStartDate("1997-01-01");
			info.setEndDate("1997-01-01");
		}
	}
}
