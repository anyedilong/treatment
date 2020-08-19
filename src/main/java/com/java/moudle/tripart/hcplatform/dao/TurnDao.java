package com.java.moudle.tripart.hcplatform.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.tripart.hcplatform.dto.TmTurnDto;
import com.java.until.dba.EntityManagerDao;
import com.java.until.dba.PageModel;


@Named
public class TurnDao extends EntityManagerDao {

	public void getTurnPage(TmTurnDto turn, PageModel page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.id, s.create_time, s.doc_name, s.org_name, s.dep_name, s.billName, s.type, s.sfzh, s.acc_org_name ");
		sql.append(" from(select a.id, a.create_time, a.doc_name, b.org_name, c.dep_name, '转出单' as billName, '1' as type, a.sfzh, d.org_name as acc_org_name ");
		sql.append("  from tm_turn_out a ");
		sql.append("  join tm_org b on a.org_id = b.id ");
		sql.append("  join tm_dep c on a.dep_id = c.id ");
		sql.append("  join tm_org d on a.acc_org_id = d.id ");
		sql.append("  where b.delete_flg = '0' and c.status = '0' ");
		sql.append("  and a.delete_flg = '0' and a.status = '6' ");
		sql.append("  and a.sfzh = :sfzh ");
		sql.append(" union  ");
		sql.append("   select a.id, a.create_time, a.doc_name, b.org_name, c.dep_name, '回转单' as billName, '2' as type, a.sfzh, d.org_name as acc_org_name ");
		sql.append("   from tm_turn_rotary a ");
		sql.append("   join tm_org b on a.org_id = b.id ");
		sql.append("   join tm_dep c on a.dep_id = c.id ");
		sql.append("  join tm_org d on a.acc_org_id = d.id ");
		sql.append("   where b.delete_flg = '0' and c.status = '0' ");
		sql.append("   and a.delete_flg = '0' and a.out_status = '6' ");
		sql.append("   and a.sfzh = :sfzh ");
		sql.append("  )s order by s.create_time desc ");
		queryPageList(sql.toString(), turn, page, TmTurnDto.class);
	}

	public TmTurnDto getTurnDetail(String id, String type) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.dno, a.name, a.sex, a.telephone, b.org_name, c.dep_name, a.check_time, a.update_time, a.doc_name, ");
		sql.append(" e.org_name as accOrgName, f.dep_name as accDepName, a.acc_doc_name, a.first_impression, ");
		sql.append(" a.zs, a.xbs, a.jwz, a.gms, a.jzs, a.ct, a.fzjc, a.zljg, ");
		if("1".equals(type)) {
			sql.append(" '1' as type ");
			sql.append(" from tm_turn_out a ");
		}else if("2".equals(type)) {
			sql.append(" '2' as type ");
			sql.append(" from tm_turn_rotary a ");
		}else {
			return null;
		}
		sql.append(" join tm_org b on a.org_id = b.id ");
		sql.append(" join tm_dep c on a.dep_id = c.id ");
		sql.append(" join tm_org e on a.acc_org_id = e.id ");
		sql.append(" join tm_dep f on a.acc_dep_id = f.id ");
		sql.append(" where b.delete_flg = '0' and c.status = '0' ");
		sql.append(" and e.delete_flg = '0' and f.status = '0' ");
		sql.append(" and a.delete_flg = '0' and a.id = :id ");
		if("1".equals(type)) {
			sql.append(" and a.status in ('2', '6') ");
		}else if("2".equals(type)) {
			sql.append(" and a.out_status in ('2', '6') ");
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return queryOne(sql.toString(), paramMap, TmTurnDto.class);
	}
}
