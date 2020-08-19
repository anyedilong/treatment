package com.java.moudle.trunback.service;

import java.util.List;

import com.java.moudle.common.service.BaseService;
import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.trunback.domain.TmTurnRotary;
import com.java.moudle.trunback.dto.TurnRotaryFlowDto;
import com.java.until.dba.PageModel;

public interface TurnRotaryService extends BaseService<TmTurnRotary> {
    
	//查询转出菜单-回转（分页）
	void getOutTurnRotaryPage(TmTurnRotary info, PageModel page);
	//查询转入菜单-回转（分页）
	void getInTurnRotaryPage(TmTurnRotary info, PageModel page);
	//查询回转详情
	TmTurnRotary showTurnRotary(String id);
	//保存回转申请单
	void saveTurnBackInfo(TmTurnRotary info, String userId);
	//获取回转中医院
	List<SysOrg> getRotaryHospitalList(String orgId, String depId, String roleType, String type);
	//回转审核
	void auditRotary(String id, String status, String reason, String type, String userId, String name, String depId);
	//获取回转单的流程图
	List<TurnRotaryFlowDto> getTurnRotaryFlow(String id);
	//查询同一个人今天填写了几个转出单
    int getCountByToDay(String sfzh);
    //删除回转单
    void deleteTurnRotary(String id);
		
}
