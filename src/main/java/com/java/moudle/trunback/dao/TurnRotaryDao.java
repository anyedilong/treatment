package com.java.moudle.trunback.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.trunback.dao.repository.TurnRotaryRepository;
import com.java.moudle.trunback.domain.TmTurnRotary;
import com.java.until.StringUntil;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;


@Named
public class TurnRotaryDao extends BaseDao<TurnRotaryRepository, TmTurnRotary> {

	public void getOutTurnRotaryPage(TmTurnRotary info, PageModel page) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name, a.sex, a.age, a.sfzh, a.doc_name as accDocName, a.create_time, ");
		sql.append("  a.acc_doc_name as docName, a.in_status, ");
		sql.append(" (select b.org_name from tm_org b where b.id = a.org_id) as accOrgName, ");
		sql.append(" (select c.dep_name from tm_dep c where c.id = a.dep_id) as accDepName, ");
		sql.append(" (select e.org_name from tm_org e where e.id = a.acc_org_id) as orgName, ");
		sql.append(" (select f.dep_name from tm_dep f where f.id = a.acc_dep_id) as depName, ");
		sql.append(" a.acc_doc_id, a.doc_id ");
		sql.append(" from tm_turn_rotary a ");
		sql.append(" join tm_org b on a.org_id = b.id ");
        sql.append(" join tm_dep c on a.dep_id = c.id ");
        sql.append(" join tm_org d on a.acc_org_id = d.id ");
        sql.append(" left join tm_dep e on a.acc_dep_id = e.id and e.status = '0' ");
        sql.append(" where a.delete_flg = '0' and b.delete_flg = '0' and c.status = '0' and d.delete_flg = '0' ");
        sql.append(" and b.status = '0' and d.status = '0' ");
		sql.append(" and (a.out_status in ('1', '2', '5', '6') or a.in_status = '2') and a.delete_flg != '1' ");
		if(!StringUntil.isNull(info.getStartDate())) {
			sql.append(" and to_date(to_char(a.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date(:startDate, 'yyyy-mm-dd') ");
			map.put("startDate", info.getStartDate());
		}
		if(!StringUntil.isNull(info.getEndDate())) {
			sql.append(" and to_date(to_char(a.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date(:endDate, 'yyyy-mm-dd') ");
			map.put("endDate", info.getEndDate());
		}
		if(!StringUntil.isNull(info.getNameOrSfzh())) {
			sql.append(" and (a.name like concat(concat('%', :nameOrSfzh), '%') or a.sfzh like concat(concat('%', :nameOrSfzh), '%')) ");
			map.put("nameOrSfzh", info.getNameOrSfzh());
		}
		if(!StringUntil.isNull(info.getInStatus())) {
			sql.append(" and a.in_status = :inStatus ");
			map.put("inStatus", info.getInStatus());
		}
		if(!StringUntil.isNull(info.getOrgId())) {
			sql.append(" and a.org_id = :orgId ");
			map.put("orgId", info.getOrgId());
		}
		if(!StringUntil.isNull(info.getAccOrgId())) {
			sql.append(" and a.acc_org_id = :accOrgId ");
			map.put("accOrgId", info.getAccOrgId());
		}
		//根据角色展示数据
		if(!StringUntil.isNull(info.getRoleType())) {
			if("1".equals(info.getRoleType())) {
				//sql.append(" and a.acc_dep_id = :accDepId ");
				//sql.append(" and a.in_status in ('0', '2') ");
				//map.put("accDepId", info.getAccDepId());
			}else if("2".equals(info.getRoleType())) {
				sql.append(" and a.acc_dep_id = :accDepId ");
				sql.append(" and (a.acc_doc_id is null or a.acc_doc_id = :accDocId) ");
				sql.append(" and a.in_status in ('1', '3') ");
				map.put("accDepId", info.getAccDepId());
				map.put("accDocId", info.getAccDocId());
			}
		}
		sql.append(" order by a.create_time desc ");
		queryPageList(sql.toString(), map, page, TmTurnRotary.class);
	}
    
	public void getInTurnRotaryPage(TmTurnRotary info, PageModel page) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.name, a.sex, a.age, a.sfzh, a.doc_name, a.create_time, a.acc_doc_name, a.out_status, ");
		sql.append(" (select b.org_name from tm_org b where b.id = a.org_id) as orgName, ");
		sql.append(" (select c.dep_name from tm_dep c where c.id = a.dep_id) as depName, ");
		sql.append(" (select e.org_name from tm_org e where e.id = a.acc_org_id) as accOrgName, ");
		sql.append(" (select f.dep_name from tm_dep f where f.id = a.acc_dep_id) as accDepName, ");
		sql.append(" a.acc_doc_id ");
		sql.append(" from tm_turn_rotary a ");
		sql.append(" join tm_org b on a.org_id = b.id ");
        sql.append(" join tm_dep c on a.dep_id = c.id ");
        sql.append(" join tm_org d on a.acc_org_id = d.id ");
        sql.append(" left join tm_dep e on a.acc_dep_id = e.id and e.status = '0' ");
        sql.append(" where a.delete_flg = '0' and b.delete_flg = '0' and c.status = '0' and d.delete_flg = '0' ");
        sql.append(" and b.status = '0' and d.status = '0' ");
		sql.append(" and a.delete_flg != '1' ");
		if(!StringUntil.isNull(info.getStartDate())) {
			sql.append(" and to_date(to_char(a.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') >= to_date(:startDate, 'yyyy-mm-dd') ");
			map.put("startDate", info.getStartDate());
		}
		if(!StringUntil.isNull(info.getEndDate())) {
			sql.append(" and to_date(to_char(a.create_time, 'yyyy-MM-dd'), 'yyyy-mm-dd') <= to_date(:endDate, 'yyyy-mm-dd') ");
			map.put("endDate", info.getEndDate());
		}
		if(!StringUntil.isNull(info.getNameOrSfzh())) {
			sql.append(" and (a.name like concat(concat('%', :nameOrSfzh), '%') or a.sfzh like concat(concat('%', :nameOrSfzh), '%')) ");
			map.put("nameOrSfzh", info.getNameOrSfzh());
		}
		if(!StringUntil.isNull(info.getOutStatus())) {
			sql.append(" and a.out_status = :outStatus ");
			map.put("outStatus", info.getOutStatus());
		}
		if(!StringUntil.isNull(info.getAccOrgId())) {
			sql.append(" and a.acc_org_id = :accOrgId ");
			map.put("accOrgId", info.getAccOrgId());
		}
		if(!StringUntil.isNull(info.getOrgId())) {
			sql.append(" and a.org_id = :orgId ");
			map.put("orgId", info.getOrgId());
		}
		//根据角色展示数据
		if(!StringUntil.isNull(info.getRoleType())) {
			if("1".equals(info.getRoleType())) {
				sql.append(" and a.dep_id = :depId ");
				//sql.append(" and a.out_status not in ('3', '4') ");
				sql.append(" and a.out_status not in ('4') ");
				map.put("depId", info.getDepId());
			} else if ("2".equals(info.getRoleType())) {
				sql.append(" and a.dep_id = :depId ");
				sql.append(" and a.doc_id = :docId ");
				map.put("depId", info.getDepId());
				map.put("docId", info.getDocId());
			}
		}
		sql.append(" order by a.create_time desc ");
		queryPageList(sql.toString(), map, page, TmTurnRotary.class);
	}
	
	public List<SysOrg> getInRotaryHospitalList(String hospitalId, String depId, String roleType) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.id, s.org_name ");
		sql.append(" from tm_org s ");
		sql.append(" where s.id in (select a.acc_org_id ");
		sql.append(" 	from tm_turn_rotary a ");
		sql.append(" 	where 1 = 1 ");
		if(!StringUntil.isNull(roleType)) {
			if("1".equals(roleType)) {
				sql.append(" and a.org_id = :hospitalId ");
				map.put("hospitalId", hospitalId);
			} else if ("2".equals(roleType)) {
				sql.append(" and a.org_id = :hospitalId and a.dep_id = :depId ");
				map.put("hospitalId", hospitalId);
				map.put("depId", depId);
			}
		}
		sql.append(" 	group by a.acc_org_id) ");
		return queryList(sql.toString(), map, SysOrg.class);
	}
	
	public List<SysOrg> getOutRotaryHospitalList(String hospitalId, String depId, String roleType) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.id, s.org_name ");
		sql.append(" from tm_org s ");
		sql.append(" where s.id in (select a.org_id ");
		sql.append(" 	from tm_turn_rotary a ");
		sql.append(" 	where 1 = 1 ");
		if(!StringUntil.isNull(roleType)) {
			if("1".equals(roleType)) {
				sql.append(" and a.acc_org_id = :hospitalId ");
				map.put("hospitalId", hospitalId);
			} else if ("2".equals(roleType)) {
				sql.append(" and a.acc_org_id = :hospitalId and a.acc_dep_id = :depId ");
				map.put("hospitalId", hospitalId);
				map.put("depId", depId);
			}
		}
		sql.append(" 	group by a.org_id) ");
		return queryList(sql.toString(), map, SysOrg.class);
	}
	
	public TmTurnRotary showTurnRotary(String turnId) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.in_id, a.out_id, a.name, a.sex, a.age, a.sfzh, a.doc_name, a.create_time, a.acc_doc_name, a.out_status, ");
		sql.append(" (select b.org_name from tm_org b where b.id = a.org_id) as orgName, ");
		sql.append(" (select c.dep_name from tm_dep c where c.id = a.dep_id) as depName, ");
		sql.append(" (select e.org_name from tm_org e where e.id = a.acc_org_id) as accOrgName, ");
		sql.append(" (select f.dep_name from tm_dep f where f.id = a.acc_dep_id) as accDepName, ");
		sql.append(" a.org_id, a.dep_id, a.check_time, a.acc_org_id, a.acc_dep_id, a.ref_purpose, a.dia_code, a.first_impression, ");
		sql.append(" a.birthday, a.weight, a.height, a.dno, a.telephone, a.family_phone, a.health_card, a.health_type, ");
		sql.append(" a.adress, a.province, a.city, a.county, a.town, a.zs, a.xbs, a.jwz, a.gms, a.jzs, a.ct, a.fzjc, a.zljg, a.remark, ");
		sql.append(" a.in_status, a.in_audit_user, a.in_audit_time, a.in_audit_reason, ");
		sql.append(" a.out_audit_time, a.out_audit_reason, a.jcjg, a.kfjy, a.acc_doc_id, a.audit_type ");
		sql.append(" from tm_turn_rotary a ");
		sql.append(" where a.delete_flg != '1' ");
		sql.append(" and a.id = :turnId ");
		map.put("turnId", turnId);
		return queryOne(sql.toString(), map, TmTurnRotary.class);
	}
	
	public int getCountByToDay(String sfzh) {
    	StringBuffer sql = new StringBuffer();
        sql.append(" select count(1) ");
        sql.append(" from tm_turn_rotary r ");
        sql.append(" where to_char(r.create_time, 'yyyy-mm-dd') = to_char(sysdate, 'yyyy-mm-dd') ");
        sql.append(" and r.out_status not in ('3', '4') ");
        sql.append(" and r.sfzh = :sfzh ");
        Map<String,Object> map = new HashMap<>();
        map.put("sfzh",sfzh);
        return queryOne(sql.toString(), map, Integer.class);
    }
	
	public void deleteTurnRotary(String id) {
		repository.deleteTurnRotary(id);
	}
}
