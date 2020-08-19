package com.java.moudle.turnout.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.java.moudle.turnout.dao.repository.TurnOutRepository;
import com.java.moudle.turnout.domain.TmTurnOut;
import com.java.moudle.turnout.dto.DiseaseDto;
import com.java.moudle.turnout.dto.TurnAuditProDto;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.until.StringUntil;
import com.java.until.StringUtils;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

/**
 * <p>Title: TurnOutDao.java</p>
 * <p>Description : 转出申请</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/6 9:06
 * @version : V1.0.0
 */
@Named
public class TurnOutDao  extends BaseDao<TurnOutRepository, TmTurnOut> {
    //删除信息
    public void deleteTmTurnOut(String id){
        repository.deleteTmTurnOut(id);
    }
    
    /**
     * @Title : 查询转出申请列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 9:06 
     * @param :
     * @return : 
     * @throws
     */
    public void getTurnOutList(TurnOutDto out, PageModel page){
        StringBuffer sql = new StringBuffer();
        sql.append(" select a.id,a.name,a.sfzh,a.sex as sex,a.age,b.org_name as out_org_name, ");
        sql.append(" c.dep_name as out_dep_name,a.doc_name as turn_doctor,a.create_time as turn_time, ");
        sql.append(" d.org_name as in_org_name,e.dep_name as in_dep_name,a.acc_doc_name as acc_doctor,a.status  ");
        sql.append(" from tm_turn_out a join tm_org b on a.org_id = b.id ");
        sql.append(" join tm_dep c on a.dep_id = c.id ");
        sql.append(" join tm_org d on a.acc_org_id = d.id ");
        sql.append(" left join tm_dep e on a.acc_dep_id = e.id and e.status = '0' ");
        sql.append(" where a.delete_flg = '0' and b.delete_flg = '0' and c.status = '0' and d.delete_flg = '0' ");
        sql.append(" and b.status = '0' and d.status = '0' ");
        if(!StringUtils.isNull(out.getAudit())){
            sql.append(" and a.status = :audit ");//审核状态
        }
        if(!StringUtils.isNull(out.getOrgId())){//转出医院   当前用户医院
            sql.append(" and a.org_id = :orgId ");
        }
        if(!StringUtils.isNull(out.getAccOrgId())){//接收医院
            sql.append(" and a.acc_org_id = :accOrgId ");
        }
        if(!StringUtils.isNull(out.getAccDepId())){//接收医院科室
            sql.append(" and a.acc_dep_id = :accDepId ");
        }
        if(!StringUtils.isNull(out.getStartTime())){
            sql.append(" and to_char(a.create_time,'yyyy-MM-dd') >= :startTime ");
        }
        if(!StringUtils.isNull(out.getEndTime())){
            sql.append(" and to_char(a.create_time,'yyyy-MM-dd') <= :endTime ");
        }
        if(!StringUtils.isNull(out.getSfzhOrName())){
            sql.append(" and (a.sfzh like concat(concat('%',:sfzhOrName),'%') or a.name like concat(concat('%',:sfzhOrName),'%') ) ");
        }
        if(!StringUtils.isNull(out.getName())){
            sql.append(" and a.name like concat(:name,'%') ");
        }
        if(!StringUntil.isNull(out.getRoleType())) {
            if("1".equals(out.getRoleType())) {//科室主任  可以查看撤回状态外的所有信息
                if(!StringUtils.isNull(out.getDepId())){//转出科室  当前用户科室信息
                    sql.append(" and a.dep_id = :depId ");
                    sql.append(" and a.status not in ('4') ");
                }
            } else if ("2".equals(out.getRoleType())) {//医生
                if(!StringUtils.isNull(out.getDepId())){//转出科室  当前用户科室信息
                    sql.append(" and a.dep_id = :depId ");
                }
                if(!StringUtils.isNull(out.getDocId())){//转出科室  当前用户科室信息
                    sql.append(" and a.doc_id = :docId ");
                }
            }
        }
        sql.append(" order by a.create_time desc ");
        queryPageList(sql.toString(), out, page, TurnOutDto.class);
    }

    /**
     * @Title : 修改审核状态
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 9:07 
     * @param :
     * @return : 
     * @throws
     */
    public void updateTurnOutStatus(String status, String auditUser, Date auditTime, String reason, String id){
        repository.updateTurnOutStatus(status, auditUser, auditTime, reason, id);
    }

    public void updateTurnOutStatus(String status,String id, String userId, Date updateTime, String name){
        repository.updateTurnOutStatus(status, id, userId, updateTime, name);
    }

    public void updateTurnOutDepId(String id, String depId) {
        repository.updateTurnOutDepId(id, depId);
    }
    
    /**
     * @Title : 查询诊断列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 13:52 
     * @param :
     * @return :
     * @throws
     */
    public void getDiseaseList(DiseaseDto dto, PageModel page) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select a.disease_code,a.disease_name,b.disease_type,b.disease_type_code ");
        sql.append(" from sys_disease a join sys_disease_type b on a.disease_type = b.disease_type_code ");
        sql.append(" where a.disease_type = 'D' ");
        if(!StringUtils.isNull(dto.getCodeOrName())){
            sql.append(" and (a.disease_name like concat(concat('%',:codeOrName),'%') or a.disease_code like concat(concat('%',:codeOrName),'%') ) ");
        }
        queryPageList(sql.toString(), dto, page, DiseaseDto.class);
    }

    /**
     * @Title : 查询历史转诊信息
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/8 15:08
     * @param :
     * @return : 
     * @throws
     */
    public TurnOutDto getHistoryTurnOut(String sfzh, List<String> orgIds, String accOrgId, String outId, Date createTime) {
    	Map<String, Object> map = new HashMap<>();
    	StringBuffer sql = new StringBuffer();
        sql.append(" select a.check_time as turn_time,b.org_name as out_org_name,c.org_name as in_org_name,a.first_impression,a.ref_purpose  ");
        sql.append(" from tm_turn_out a join tm_org b on a.org_id = b.id join tm_org c on a.acc_org_id = c.id ");
        sql.append(" where a.delete_flg = '0' and a.status in ('2', '6') ");//未删除 以通过审核的历史转诊信息
        sql.append(" and b.status = '0' and b.delete_flg = '0' and c.status = '0' and c.delete_flg = '0' ");
        sql.append(" and a.sfzh = :sfzh and a.acc_org_id = :accOrgId ");
        if(!StringUntil.isNull(outId)) {
        	sql.append(" and a.id <> :outId ");//根据身份证号查询历史转诊信息并且不等于本次
        	map.put("outId", outId);
        }
        sql.append(" and a.org_id in ( ");
        for(int i = 0; i< orgIds.size(); i++) {
        	if(i > 0) {
        		sql.append(" , ");
        	}
        	sql.append("'"+orgIds.get(i)+"'");
        }
        sql.append(" ) and a.create_time <= :createTime ");
        sql.append(" order by a.create_time desc ");
        map.put("sfzh", sfzh);
        map.put("accOrgId", accOrgId);
        map.put("createTime", createTime);
        return queryOne(sql.toString(), map, TurnOutDto.class);

    }

    /**
     * @Title : 查询审批进度
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/8 9:52 
     * @param :
     * @return : 
     * @throws
     */
    public List<TurnAuditProDto> getHistoryTurnOut(String id) {
        //List<TurnAuditProDto> list = new ArrayList<>();
        String querySql = "";
        if(!StringUtils.isNull(id)){
            querySql = " and a.id = :id";
        }
        StringBuffer sql = new StringBuffer();
        //提交
        sql.append(" select '1' as type,a.doc_name as audit_user,b.org_name as org_name,a.check_time as audit_time,null as status,null as reason, a.audit_type ");
        sql.append(" from tm_turn_out a join tm_org b on a.org_id = b.id where a.status != '4' ");
        sql.append(querySql);
        sql.append(" union ");
        //院内审批
        sql.append(" select '2' as type,c.name as audit_user,b.org_name as org_name,a.audit_time, ");
        sql.append(" (case when a.status in('1','2','5', '6') then '1' when a.status = '3' then '2' else null end) as status,  ");
        sql.append(" a.audit_reason  as reason, a.audit_type from tm_turn_out a join tm_org b on a.org_id = b.id  join tm_user c on a.audit_user = c.id  ");
        sql.append(" where a.status in ('1','2','3','5', '6')  ");
        sql.append(querySql);
        sql.append(" union ");
        //上级审批
        sql.append(" select '3' as type,c.name as audit_user,b.org_name as org_name,i.audit_time as audit_time, ");
        sql.append(" (case when i.status in ('1','3') then '1' when i.status = '2' then '2' else null end) as status, ");
        sql.append(" i.audit_reason as reason, a.audit_type  ");
        sql.append(" from tm_turn_out a join tm_turn_in i on a.id = i.out_id join tm_org b on i.acc_org_id = b.id join tm_user c on i.audit_user = c.id ");
        sql.append(" where i.status != '0'  ");
        sql.append(querySql);
        sql.append(" union ");
        //接收
        sql.append(" select '4' as type,c.name as audit_user,b.org_name as org_name,i.update_time as audit_time, ");
        sql.append(" (case when i.status = '3' then '3' else null end )as status, null as reason, a.audit_type  ");
        sql.append(" from tm_turn_out a join tm_turn_in i on a.id = i.out_id join tm_org b on i.acc_org_id = b.id join tm_user c on i.update_user = c.id  ");
        sql.append(" where i.status = '3' ");
        sql.append(querySql);
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return queryList(sql.toString(),map,TurnAuditProDto.class);
    }
    /**
     * @Description: 查询同一个人今天填写了几个转出单
     * @param @param sfzh
     * @param @return
     * @return int
     * @throws
     */
    public int getCountByToDay(String sfzh) {
    	StringBuffer sql = new StringBuffer();
        sql.append(" select count(1) ");
        sql.append(" from tm_turn_out r ");
        sql.append(" where to_char(r.create_time, 'yyyy-mm-dd') = to_char(sysdate, 'yyyy-mm-dd') ");
        sql.append(" and r.sfzh = :sfzh ");
        Map<String,Object> map = new HashMap<>();
        map.put("sfzh",sfzh);
        return queryOne(sql.toString(), map, Integer.class);
    }
    /**
     * @Description: 查询转诊关系产生单的数量
     * @param @param downOrgId
     * @param @param upOrgId
     * @param @return
     * @return int
     * @throws
     */
    public int quertRelTurnCount(String downOrgId, String upOrgId) {
    	StringBuffer sql = new StringBuffer();
        sql.append(" select count(1) ");
        sql.append(" from tm_turn_out r ");
        sql.append(" where r.org_id = :downOrgId and r.acc_org_id = :upOrgId ");
        Map<String,Object> map = new HashMap<>();
        map.put("downOrgId",downOrgId);
        map.put("upOrgId",upOrgId);
        return queryOne(sql.toString(), map, Integer.class);
    }
}
