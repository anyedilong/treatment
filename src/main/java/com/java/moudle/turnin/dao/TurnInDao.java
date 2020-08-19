package com.java.moudle.turnin.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.turnin.dao.repository.TurnInRepository;
import com.java.moudle.turnin.domain.TmTurnIn;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.until.StringUtils;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;
/**
 * <p>Title: TurnInDao.java</p>
 * <p>Description : 转入管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/7 9:05
 * @version : V1.0.0
 */
@Named
public class TurnInDao extends BaseDao<TurnInRepository, TmTurnIn> {

    public void deleteTmTurnIn(String id) {
        repository.deleteTmTurnInByOutId(id);
    }

    public void updateTurnInStatus(String status, String auditUser, Date auditTime, String reason, String id) {
        repository.updateTurnInStatus(status, auditUser, auditTime, reason, id);
    }

    public void updateTurnInDepId(String id, String depId) {
        repository.updateTurnInDepId(id, depId);
    }
    
    /**
     * @param :
     * @return :
     * @throws
     * @Title : 分页查询转入列表
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/7 9:10
     */
    public void getTurnInList(TurnOutDto out, PageModel page) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select a.id,a.name,a.sfzh,a.sex,a.age,b.org_name as out_org_name,a.acc_doc_id as accDocId,");
        sql.append(" c.dep_name as out_dep_name,a.doc_name as turn_doctor,a.create_time as turn_time, a.doc_id as docId,");
        sql.append(" d.org_name as in_org_name,e.dep_name as in_dep_name,a.acc_doc_name as acc_doctor,a.status,  ");
        sql.append(" (case when (select s.id from tm_turn_rotary s where s.in_id = a.id and s.delete_flg = '0') is null then '0' else '1' end) as isTurnBack ");
        sql.append(" from tm_turn_in a join tm_org b on a.org_id = b.id ");
        sql.append(" join tm_dep c on a.dep_id = c.id ");
        sql.append(" join tm_org d on a.acc_org_id = d.id ");
        sql.append(" left join tm_dep e on a.acc_dep_id = e.id and e.status = '0'  ");
        sql.append(" where a.delete_flg = '0' and b.delete_flg = '0' and b.status = '0' and c.status = '0' ");
        sql.append(" and d.delete_flg = '0' and d.status = '0' ");
        sql.append(" and (a.acc_doc_id is null or a.acc_doc_id = :accDocId ) ");

        if (!StringUtils.isNull(out.getAudit())) {
            sql.append(" and a.status = :audit ");//审核状态
        }
        if (!StringUtils.isNull(out.getOrgId())) {//转出医院
            sql.append(" and a.org_id = :orgId ");
        }
        if (!StringUtils.isNull(out.getDepId())) {//转出科室
            sql.append(" and a.dep_id = :depId ");
        }
        if (!StringUtils.isNull(out.getAccOrgId())) {//接收医院  当前用户机构
            sql.append(" and a.acc_org_id = :accOrgId ");
        }
        if (!StringUtils.isNull(out.getAccDepId())) {//接收科室  当前用户科室  医生登陆  只查看审核通过的
            sql.append(" and (a.acc_dep_id is null or a.acc_dep_id = :accDepId) ");
            sql.append(" and a.status in( '1' ,'3')");
        }
       // if (!StringUtils.isNull(out.getAccDocId())) {//接收科室  当前用户科室  医生登陆  只查看审核通过的
       //     sql.append(" and a.acc_doc_id = :accDocId ");
            
       // }
        if (!StringUtils.isNull(out.getStartTime())) {
            sql.append(" and to_char(a.create_time,'yyyy-MM-dd') >= :startTime ");
        }
        if (!StringUtils.isNull(out.getEndTime())) {
            sql.append(" and to_char(a.create_time,'yyyy-MM-dd') <= :endTime ");
        }
        if (!StringUtils.isNull(out.getSfzh())) {
            sql.append(" and a.sfzh like concat(:sfzh,'%') ");
        }
        if (!StringUtils.isNull(out.getName())) {
            sql.append(" and a.name like concat(:name,'%') ");
        }
        if (!StringUtils.isNull(out.getSfzhOrName())) {
            sql.append(" and (a.sfzh like concat(concat('%',:sfzhOrName),'%') or a.name like concat(concat('%',:sfzhOrName),'%') ) ");
        }
        sql.append(" order by a.create_time desc ");

        queryPageList(sql.toString(), out, page, TurnOutDto.class);
    }

    public TmTurnIn queryInfoByOutId(String outId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from tm_turn_in where out_id = :outId ");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("outId", outId);
        return queryOne(sql.toString(), paramMap, TmTurnIn.class);
    }
   
}
