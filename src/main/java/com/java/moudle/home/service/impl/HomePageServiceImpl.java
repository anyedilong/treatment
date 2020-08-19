package com.java.moudle.home.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.home.service.HomePageService;
import com.java.moudle.statistical.dao.StatsTurnBackDao;
import com.java.moudle.statistical.dto.StatsTurnBackDto;


@Named
@Transactional(readOnly=false)
public class HomePageServiceImpl implements HomePageService {

	@Inject
    private StatsTurnBackDao statsTurnBackDao;
	
	@Override
	public Map<String, Object> myToDo(String orgId, String depId, String roleType, String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		int turnOut = 0, turnBack = 0, turnInAcc = 0, turnBackAcc = 0;
		if("1".equals(roleType)) {
			//科室主任
			//转出申请审核
			turnOut = statsTurnBackDao.getTurnOutCount(orgId, depId, "", "0");
			//回转申请审核
			turnBack = statsTurnBackDao.getTurnBackCount(orgId, depId, "", "0");
			//转入接收审核
			turnInAcc = statsTurnBackDao.getTurnInAccCount(orgId, depId, "", "0");
			//回转接收审核
			turnBackAcc = statsTurnBackDao.getTurnBackAccCount(orgId, depId, "", "0");
		}else if("2".equals(roleType)) {
			//院医生
			//转出申请
			turnOut = statsTurnBackDao.getTurnOutCount(orgId, depId, userId, "10");
			//回转申请
			turnBack = statsTurnBackDao.getTurnBackCount(orgId, depId, userId, "10");
			//转入待接收
			turnInAcc = statsTurnBackDao.getTurnInAccCount(orgId, depId, userId, "1");
			//回转待接收
			turnBackAcc = statsTurnBackDao.getTurnBackAccCount(orgId, depId, userId, "1");
		}else if("3".equals(roleType) || "99".equals(roleType)) {
			//医院管理员和卫计委
			//转出申请
			turnOut = statsTurnBackDao.getTurnOutCount(orgId, "", "", "11");
			//回转申请
			turnBack = statsTurnBackDao.getTurnBackCount(orgId, "", "", "11");
			//转入接收
			turnInAcc = statsTurnBackDao.getTurnInAccCount(orgId, "", "", "3");
			//回转接收
			turnBackAcc = statsTurnBackDao.getTurnBackAccCount(orgId, "", "", "3");
		}
		resultMap.put("turnOut", turnOut);
		resultMap.put("turnBack", turnBack);
		resultMap.put("turnInAcc", turnInAcc);
		resultMap.put("turnBackAcc", turnBackAcc);
		return resultMap;
	}

	@Override
	public List<String> getLatestNews(String orgId, String depId, String roleType, String userId) {
		List<String> list = statsTurnBackDao.getLatestNews(orgId, depId, roleType, userId);
		if(list == null || list.size() == 0) {
			list = new ArrayList<>();
		}
		return list;
	}
	
	@Override
	public Map<String, Object> queryHomeTurnStats(String orgId, String depId) {
		//返回map
		Map<String, Object> resultMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		
		//转出与回转柱状图数据
		List<String> xList = new ArrayList<>();
		List<Integer> y1List = new ArrayList<>();
		List<Integer> y2List = new ArrayList<>();
		
		List<StatsTurnBackDto> inlist = statsTurnBackDao.queryHomeTurnStats(sdf1.format(new Date())+"-01", sdf.format(new Date()), orgId, depId);
		if(inlist != null && inlist.size() > 0) {
			for(int i = 0; i < inlist.size(); i++) {
				StatsTurnBackDto dto = inlist.get(i);
				//折线图
				xList.add(dto.getName());
				y1List.add(dto.getValue());
				y2List.add(dto.getValue1());
			}
		}
		resultMap.put("xList", xList);
		resultMap.put("y1List", y1List);
		resultMap.put("y2List", y2List);
		return resultMap;
	}

}
