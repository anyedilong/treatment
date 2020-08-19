package com.java.moudle.statistical.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.statistical.dto.StatsTurnBackDto;
import com.java.moudle.trunback.dao.repository.TurnRotaryRepository;
import com.java.moudle.trunback.domain.TmTurnRotary;
import com.java.until.StringUntil;
import com.java.until.dba.BaseDao;


@Named
public class StatsTurnBackDao extends BaseDao<TurnRotaryRepository, TmTurnRotary> {

    

	public List<StatsTurnBackDto> queryHomeTurnStats(String startDate, String endDate, String orgId, String depId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from table(stats_home_turn_todo(:startDate, :endDate, :orgId, :depId)) ");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("orgId", orgId);
		paramMap.put("depId", depId);
		return queryList(sql.toString(), paramMap, StatsTurnBackDto.class);
	}
	
	public List<StatsTurnBackDto> queryTurnBackOutStats(StatsTurnBackDto info) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from table(stats_turn_back_count(:orgId, :depId, :type, :startDate, :endDate)) ");
		return queryList(sql.toString(), info, StatsTurnBackDto.class);
	}
	
	public List<String> getLatestNews(String orgId, String depId, String roleType, String userId) {
		Map<String, Object> paramMap = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select r.describe from tm_turn_his r ");
		sql.append(" where r.org_id = :orgId ");
		if(!StringUntil.isNull(depId)) {
			sql.append(" and r.dep_id = :depId ");
			paramMap.put("depId", depId);
		}
		if("1".equals(roleType)) {
			sql.append(" and (r.type = :roleType) ");
		}else {
			sql.append(" and r.type = :roleType and r.acc_doc_id = :userId ");
			paramMap.put("userId", userId);
		}
		sql.append(" and r.create_date >= (sysdate - 7) ");
		sql.append(" order by r.create_date desc ");
		paramMap.put("orgId", orgId);
		paramMap.put("roleType", roleType);
		return queryList(sql.toString(), paramMap, String.class);
	}
	
	public Integer getTurnOutCount(String orgId, String depId, String userId, String status) {
		Map<String, Object> paramMap = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from tm_turn_out a ");
		sql.append(" join tm_org b on a.org_id = b.id ");
        sql.append(" join tm_dep c on a.dep_id = c.id ");
        sql.append(" join tm_org d on a.acc_org_id = d.id ");
        sql.append(" join tm_dep e on a.acc_dep_id = e.id ");
        sql.append(" where a.delete_flg = '0' and b.delete_flg = '0' and c.status = '0' and d.delete_flg = '0' and e.status = '0' ");
        sql.append(" and b.status = '0' and d.status = '0' ");
		if(!StringUntil.isNull(orgId)) {
			sql.append(" and a.org_id = :orgId ");
			paramMap.put("orgId", orgId);
		}
		if(!StringUntil.isNull(depId)) {
			sql.append(" and a.dep_id = :depId ");
			paramMap.put("depId", depId);
		}
		if(!StringUntil.isNull(userId)) {
			sql.append(" and a.doc_id = :userId ");
			paramMap.put("userId", userId);
		}
		if("10".equals(status)) {
			sql.append(" and a.status in ('3') ");//医生的待办事项  转出申请只统计退回的
		}else if("11".equals(status)){
			sql.append(" and a.status in ('2', '6') ");
		}else if("0".equals(status)) {
			sql.append(" and a.status in ('0', '5') ");//院领导账号待办统计审核中和驳回的
//			sql.append(" and a.status = :status ");
//			paramMap.put("status", status);
		}
		return queryOne(sql.toString(), paramMap, Integer.class);
	}
	
	public Integer getTurnBackCount(String orgId, String depId, String userId, String status) {
		Map<String, Object> paramMap = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from tm_turn_rotary a ");
		sql.append(" join tm_org b on a.org_id = b.id ");
        sql.append(" join tm_dep c on a.dep_id = c.id ");
        sql.append(" join tm_org d on a.acc_org_id = d.id ");
        sql.append(" join tm_dep e on a.acc_dep_id = e.id ");
        sql.append(" where a.delete_flg = '0' and b.delete_flg = '0' and c.status = '0' and d.delete_flg = '0' and e.status = '0' ");
        sql.append(" and b.status = '0' and d.status = '0' ");
		if(!StringUntil.isNull(orgId)) {
			sql.append(" and a.org_id = :orgId ");
			paramMap.put("orgId", orgId);
		}
		if(!StringUntil.isNull(depId)) {
			sql.append(" and a.dep_id = :depId ");
			paramMap.put("depId", depId);
		}
		if(!StringUntil.isNull(userId)) {
			sql.append(" and a.doc_id = :userId ");
			paramMap.put("userId", userId);
		}
		if("10".equals(status)) {
			sql.append(" and a.out_status in ('3') ");
		}else if("11".equals(status)){
			sql.append(" and a.out_status in ('2', '6') ");
		}else  if("0".equals(status)){
			sql.append(" and a.out_status in ('0', '5') ");//院领导
//			sql.append(" and a.out_status = :status ");
//			paramMap.put("status", status);
		}
		return queryOne(sql.toString(), paramMap, Integer.class);
	}
	
	public Integer getTurnInAccCount(String orgId, String depId, String userId, String status) {
		Map<String, Object> paramMap = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from tm_turn_in a ");
		sql.append(" join tm_org b on a.org_id = b.id ");
        sql.append(" join tm_dep c on a.dep_id = c.id ");
        sql.append(" join tm_org d on a.acc_org_id = d.id ");
        sql.append(" join tm_dep e on a.acc_dep_id = e.id ");
        sql.append(" where a.delete_flg = '0' and b.delete_flg = '0' and c.status = '0' and d.delete_flg = '0' and e.status = '0' ");
        sql.append(" and b.status = '0' and d.status = '0' ");
		if(!StringUntil.isNull(orgId)) {
			sql.append(" and a.acc_org_id = :orgId ");
			paramMap.put("orgId", orgId);
		}
		if(!StringUntil.isNull(depId)) {
			sql.append(" and a.acc_dep_id = :depId ");
			paramMap.put("depId", depId);
		}if(!StringUntil.isNull(userId)) {
			sql.append(" and a.acc_doc_id = :userId ");
			paramMap.put("userId", userId);
		}
		sql.append(" and a.status = :status ");
		paramMap.put("status", status);
		return queryOne(sql.toString(), paramMap, Integer.class);
	}
	
	public Integer getTurnBackAccCount(String orgId, String depId, String userId, String status) {
		Map<String, Object> paramMap = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from tm_turn_rotary a ");
		sql.append(" join tm_org b on a.org_id = b.id ");
        sql.append(" join tm_dep c on a.dep_id = c.id ");
        sql.append(" join tm_org d on a.acc_org_id = d.id ");
        sql.append(" join tm_dep e on a.acc_dep_id = e.id ");
        sql.append(" where a.delete_flg = '0' and b.delete_flg = '0' and c.status = '0' and d.delete_flg = '0' and e.status = '0' ");
        sql.append(" and b.status = '0' and d.status = '0' ");
		if(!StringUntil.isNull(orgId)) {
			sql.append(" and a.acc_org_id = :orgId ");
			paramMap.put("orgId", orgId);
		}
		if(!StringUntil.isNull(depId)) {
			sql.append(" and a.acc_dep_id = :depId ");
			paramMap.put("depId", depId);
		}
		if(!StringUntil.isNull(userId)) {
			sql.append(" and a.acc_doc_id = :userId ");
			paramMap.put("userId", userId);
		}
		sql.append(" and a.in_status = :status ");
		paramMap.put("status", status);
		
		return queryOne(sql.toString(), paramMap, Integer.class);
	}

}
