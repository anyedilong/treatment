package com.java.moudle.home.service;

import java.util.List;
import java.util.Map;

public interface HomePageService {
   
	//首页-我的待办
	Map<String, Object> myToDo(String orgId, String depId, String roleType, String userId);
	//首页-最新动态
	List<String> getLatestNews(String orgId, String depId, String roleType, String userId);
	//首页近一个月的转出、转入统计
	Map<String, Object> queryHomeTurnStats(String orgId, String depId);
	
}
