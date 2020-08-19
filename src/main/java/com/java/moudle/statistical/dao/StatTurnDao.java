package com.java.moudle.statistical.dao;

import com.java.moudle.statistical.dto.StatTurnDto;
import com.java.moudle.statistical.dto.StatTurnReturnDto;
import com.java.moudle.turnin.dao.repository.TurnInRepository;
import com.java.moudle.turnin.domain.TmTurnIn;
import com.java.until.StringUtils;
import com.java.until.dba.BaseDao;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: StatTurnDao.java</p>
 * <p>Description : 区域统计</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/8 13:58
 * @version : V1.0.0
 */
@Named
public class StatTurnDao extends BaseDao<TurnInRepository, TmTurnIn> {

    /**
     * @Title : 区域转出统计   根据年份  月份  机构  统计每天或者每月的转出回转数量
     * 和 转出回转总数计算占比   和个各机构下的转出回转数量
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/8 11:12
     * @param :
     * @return :
     * @throws
     */
    public StatTurnReturnDto statTurnOutArea(StatTurnDto turn){
        String querySql = "";
        String backSql = "";
        StatTurnReturnDto returnDto = new StatTurnReturnDto();
        List<StatTurnDto> timeList = null;
        if(!StringUtils.isNull(turn.getYear())){
            if(!StringUtils.isNull(turn.getMonth())){
                querySql = " and to_char(a.create_time,'yyyy-mm')= :month ";//转出统计  转出申请审核通过时间
                backSql = " and to_char(a.create_time,'yyyy-mm')= :month ";
                timeList = statTurnOutDays(turn);//转出  天
            }else{
                querySql = " and to_char(a.create_time,'yyyy')=:year ";
                backSql = " and to_char(a.create_time,'yyyy')=:year ";
                timeList = statTurnOutMonth(turn); //转出 月
            }
        }
        //机构分租查询
        StringBuffer sqlOrg = new StringBuffer();
        sqlOrg.append(" select a.org_name as name,nvl(b.num,0) as value,nvl(c.num,0) as value1 from tm_org a left join ");
        sqlOrg.append("(select org_id,count(id) as num from stat_turn_out a where a.status in ('2', '6') ");
        sqlOrg.append(querySql);
        if(!StringUtils.isNull(turn.getOrgID())){//转出医院
            sqlOrg.append(" and a.org_id = :orgID ");
        }
        sqlOrg.append(" group by org_id ) b on a.id = b.org_id left join ");
        sqlOrg.append(" ( select acc_org_id,count(id) as num from stat_turn_rotary a where a.in_status = '3' ");
        sqlOrg.append(backSql);
        if(!StringUtils.isNull(turn.getOrgID())){//转出回转  接收转出信息的医院
            sqlOrg.append(" and a.acc_org_id = :orgID " );
        }
        sqlOrg.append(" group by acc_org_id ) c on c.acc_org_id = a.id");
        sqlOrg.append(" where a.delete_flg = '0' and a.status = '0' ");
        if(!StringUtils.isNull(turn.getOrgID())){
            sqlOrg.append(" and a.id = :orgID ");
        }
       // sqlOrg.append(" order by a.create_time desc ");
        List<StatTurnDto> orgStatList = queryList(sqlOrg.toString(),turn,StatTurnDto.class);

        //数据转换
        //折线图数据
        List<String> timeXList = new ArrayList<>();
        List<Integer> timeY1List = new ArrayList<>();
        List<Integer> timeY2List = new ArrayList<>();
        for(StatTurnDto time : timeList){
            timeXList.add(time.getName());
            timeY1List.add(time.getValue());
            timeY2List.add(time.getValue1());
        }

        //柱状图数据
        List<String> orgXList = new ArrayList<>();
        List<Integer> orgY1List = new ArrayList<>();
        List<Integer> orgY2List = new ArrayList<>();
        int sumValue = 0;//转出总数量
        int sumValue1 = 0;//回转总数量
        for(StatTurnDto org : orgStatList){
            orgXList.add(org.getName());
            orgY1List.add(org.getValue());
            sumValue += org.getValue();
            orgY2List.add(org.getValue1());
            sumValue1 += org.getValue1();
        }
        returnDto.setTimeXList(timeXList);
        returnDto.setTimeY1List(timeY1List);
        returnDto.setTimeY2List(timeY2List);
        returnDto.setOrgXList(orgXList);
        returnDto.setOrgY1List(orgY1List);
        returnDto.setOrgY2List(orgY2List);

        //占比  饼图
        StatTurnDto forDto1 = new StatTurnDto();
        forDto1.setName("转出");
        forDto1.setValue(sumValue);
        StatTurnDto forDto2 = new StatTurnDto();
        forDto2.setName("回转");
        forDto2.setValue(sumValue1);
        List<StatTurnDto> accForList = new ArrayList<>();
        accForList.add(forDto1);
        accForList.add(forDto2);

        returnDto.setAccForList(accForList);
        return returnDto;
    }

    /**
     * @Title : 区域转入统计   根据年份  月份  机构  统计每天或者每月的转入 和转入回转数量
     * 和 转入 回转总数计算占比   和个各机构下的转入回转数量
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/9 16:55
     * @param :
     * @return :
     * @throws
     */
    public StatTurnReturnDto statTurnInArea(StatTurnDto turn){
        String querySql = "";
        String backSql = "";
        StatTurnReturnDto returnDto = new StatTurnReturnDto();
        //按天或者日分组查询
        List<StatTurnDto> timeList = null;
        if(!StringUtils.isNull(turn.getYear())){
            if(!StringUtils.isNull(turn.getMonth())){
                querySql = " and to_char(a.audit_time,'yyyy-mm')=:month ";//转入统计  审核通过时间
                backSql = " and to_char(a.in_audit_time,'yyyy-mm')= :month ";
                timeList = statTurnInDays(turn);//转入  天
            }else{
                querySql = " and to_char(a.audit_time,'yyyy')=:year ";
                backSql = " and to_char(a.in_audit_time,'yyyy')= :year ";
                timeList = statTurnInMonth(turn); //转入 月
            }
        }
        //按机构分组查询
        StringBuffer sqlOrg = new StringBuffer();
        sqlOrg.append(" select a.org_name as name,nvl(b.num,0) as value,nvl(c.num,0) as value1 from tm_org a left join ");
        sqlOrg.append("(select acc_org_id,count(id) as num  from stat_turn_in a where a.status = '3' ");
        sqlOrg.append(querySql);
        if(!StringUtils.isNull(turn.getOrgID())){//转入医院
            sqlOrg.append(" and a.acc_org_id = :orgID ");
        }
        sqlOrg.append("  group by acc_org_id) b on a.id = b.acc_org_id left join ");
        sqlOrg.append(" ( select org_id,count(id) as num  from stat_turn_rotary a where a.out_status in ('2', '6') ");
        sqlOrg.append(backSql);
        if(!StringUtils.isNull(turn.getOrgID())){//转入回转  发起转入信息的医院
            sqlOrg.append(" and a.org_id = :orgID " );
        }
        sqlOrg.append(" group by org_id ) c on c.org_id = a.id");
        sqlOrg.append(" where a.delete_flg = '0' and a.status = '0' ");
        if(!StringUtils.isNull(turn.getOrgID())){
            sqlOrg.append(" and a.id = :orgID ");
        }
       // sqlOrg.append(" order by a.create_time desc ");
        List<StatTurnDto> orgStatList = queryList(sqlOrg.toString(),turn,StatTurnDto.class);

        //折线图数据
        List<String> timeXList = new ArrayList<>();
        List<Integer> timeY1List = new ArrayList<>();
        List<Integer> timeY2List = new ArrayList<>();
        for(StatTurnDto time : timeList){
            timeXList.add(time.getName());
            timeY1List.add(time.getValue());
            timeY2List.add(time.getValue1());
        }

        //柱状图数据
        List<String> orgXList = new ArrayList<>();
        List<Integer> orgY1List = new ArrayList<>();
        List<Integer> orgY2List = new ArrayList<>();
        int sumValue = 0;//转人总数量
        int sumValue1 = 0;//回转总数量
        for(StatTurnDto org : orgStatList){
            orgXList.add(org.getName());
            orgY1List.add(org.getValue());
            sumValue += org.getValue();
            orgY2List.add(org.getValue1());
            sumValue1 += org.getValue1();
        }
        returnDto.setTimeXList(timeXList);
        returnDto.setTimeY1List(timeY1List);
        returnDto.setTimeY2List(timeY2List);
        returnDto.setOrgXList(orgXList);
        returnDto.setOrgY1List(orgY1List);
        returnDto.setOrgY2List(orgY2List);

        //占比  饼图
        StatTurnDto forDto1 = new StatTurnDto();
        forDto1.setName("转入");
        forDto1.setValue(sumValue);
        StatTurnDto forDto2 = new StatTurnDto();
        forDto2.setName("回转");
        forDto2.setValue(sumValue1);
        List<StatTurnDto> accForList = new ArrayList<>();
        accForList.add(forDto1);
        accForList.add(forDto2);
        returnDto.setAccForList(accForList);
        return returnDto;
    }


    /**
     * @Title : 转出统计  天
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/9 15:04 
     * @param : 
     * @return :
     * @throws
     */
    public List<StatTurnDto> statTurnOutDays(StatTurnDto turn) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select  to_char(to_date(a.mdate,'yyyy-mm-dd'),'dd') as name,nvl(b.value,0) as value,nvl(c.value,0) as value1   ");
        sql.append(" from (select to_char(to_date(:month, 'yyyy-mm') + LEVEL - 1,'yyyy-mm-dd') mdate from dual ");
        sql.append(" connect by level <= to_number(to_char(last_day(to_date(:month, 'yyyy-mm')),'dd'))) a ");
        sql.append(" left join ");
        sql.append(" (select to_char(a.create_time,'yyyy-mm-dd') as days,count(a.id) as value  from stat_turn_out a where a.status in ('2', '6') ");
        if(!StringUtils.isNull(turn.getMonth())){
            sql.append(" and to_char(a.create_time,'yyyy-mm')=:month ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出医院
            sql.append(" and a.org_id = :orgID " );
        }
        sql.append(" group by to_char(a.create_time,'yyyy-mm-dd') ) b on a.mdate = b.days ");//通过审核的
        sql.append(" left join ");
        sql.append(" (select to_char(a.create_time,'yyyy-mm-dd') as days,count(a.id) as value  from stat_turn_rotary a where a.in_status in ('3') ");
        if(!StringUtils.isNull(turn.getMonth())){
            sql.append(" and to_char(a.create_time,'yyyy-mm')=:month ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出回转  接收转出信息的医院
            sql.append(" and a.acc_org_id = :orgID " );
        }
        sql.append(" group by to_char(a.create_time,'yyyy-mm-dd') ) c on a.mdate = c.days ");//转出回转是统计接收方接收后的回转信息
        sql.append(" order by a.mdate ");

        return queryList(sql.toString(), turn, StatTurnDto.class);
    }
    /**
     * @Title : 根据年度统计每月转出 回转
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/8 11:14
     * @param :
     * @return :
     * @throws
     */
    public List<StatTurnDto> statTurnOutMonth(StatTurnDto turn) {

        StringBuffer sql = new StringBuffer();
        sql.append(" select  to_char(to_date(a.mdate,'yyyy-mm'),'mm') as name,nvl(b.value,0) as value,nvl(c.value,0) as value1 ");
        sql.append(" from (select concat(:year,'-') || lpad(level, 2, 0) as mdate from dual connect by level < 13) a ");
        sql.append(" left join ");
        sql.append(" (select to_char(a.create_time,'yyyy-mm') as days,count(a.id) as value from stat_turn_out a where a.status in ('2', '6') ");
        if(!StringUtils.isNull(turn.getYear())){//转出申请审核通过时间
            sql.append(" and to_char(a.create_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出医院
            sql.append(" and a.org_id = :orgID " );
        }
        sql.append(" group by to_char(a.create_time,'yyyy-mm') ) b on a.mdate = b.days ");
        sql.append(" left join ");
        sql.append(" (select to_char(a.create_time,'yyyy-mm') as days,count(a.id) as value from stat_turn_rotary a where a.in_status in ('3') ");
        if(!StringUtils.isNull(turn.getYear())){//接收转出回转时间
            sql.append(" and to_char(a.create_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出回转  接收转出信息的医院
            sql.append(" and a.acc_org_id = :orgID " );
        }
        sql.append(" group by to_char(a.create_time,'yyyy-mm') ) c on a.mdate = c.days ");//转出回转是统计接收方接收后的回转信息
        sql.append(" order by a.mdate ");

        return queryList(sql.toString(), turn, StatTurnDto.class);
    }

    /**
     * @Title : 根据月份统计每天转入 回转
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/8 14:05
     * @param :
     * @return :
     * @throws
     */
    public List<StatTurnDto> statTurnInDays(StatTurnDto turn) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select  to_char(to_date(a.mdate,'yyyy-mm-dd'),'dd') as name,nvl(b.value,0) as value,nvl(c.value,0) as value1  ");
        sql.append(" from (select to_char(to_date(:month, 'yyyy-mm') + LEVEL - 1,'yyyy-mm-dd') mdate from dual ");
        sql.append(" connect by level <= to_number(to_char(last_day(to_date(:month, 'yyyy-mm')),'dd'))) a ");
        sql.append(" left join ");
        sql.append(" (select to_char(a.create_time,'yyyy-mm-dd') as days,count(a.id) as value from tm_turn_in a where a.status in ('3') ");
        if(!StringUtils.isNull(turn.getMonth())){
            sql.append(" and to_char(a.create_time,'yyyy-mm')=:month ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转入医院
            sql.append(" and a.acc_org_id = :orgID " );
        }
        sql.append(" group by to_char(a.create_time,'yyyy-mm-dd') ) b on a.mdate = b.days "); //接收方已接收
        sql.append(" left join ");
        sql.append(" (select to_char(a.create_time,'yyyy-mm-dd') as days,count(a.id) as value from stat_turn_rotary a where a.out_status in ('2', '6') ");
        if(!StringUtils.isNull(turn.getMonth())){
            sql.append(" and to_char(a.create_time,'yyyy-mm')=:month ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转入回转  发起回转的医院
            sql.append(" and a.org_id = :orgID " );
        }
        sql.append(" group by to_char(a.create_time,'yyyy-mm-dd') ) c on a.mdate = c.days ");//转入回转是统计转入方发起回转审核通过后的回转信息
        sql.append(" order by a.mdate ");

        return queryList(sql.toString(), turn, StatTurnDto.class);
    }

    /**
     * @Title : 根据年度统计每月转入 回转
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/8 14:35
     * @param :
     * @return :
     * @throws
     */
    public List<StatTurnDto> statTurnInMonth(StatTurnDto turn) {

        StringBuffer sql = new StringBuffer();
        sql.append(" select  to_char(to_date(a.mdate,'yyyy-mm'),'mm') as name,nvl(b.value,0) as value,nvl(c.value,0) as value1  ");
        sql.append(" from (select concat(:year,'-') || lpad(level, 2, 0) as mdate from dual connect by level < 13) a ");
        sql.append(" left join ");
        sql.append(" (select to_char(a.create_time,'yyyy-mm') as days,count(a.id) as value from stat_turn_in a where a.status in ('3') ");
        if(!StringUtils.isNull(turn.getYear())){
            sql.append(" and to_char(a.create_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转入医院
            sql.append(" and a.acc_org_id = :orgID " );
        }
        sql.append(" group by to_char(a.create_time,'yyyy-mm') ) b on a.mdate = b.days ");
        sql.append(" left join ");
        sql.append(" (select to_char(a.create_time,'yyyy-mm') as days,count(a.id) as value from stat_turn_rotary a where a.out_status in ('2', '6') ");
        if(!StringUtils.isNull(turn.getYear())){//转入回转  发起回转审核通过时间
            sql.append(" and to_char(a.create_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转入回转  发起回转的医院
            sql.append(" and a.org_id = :orgID " );
        }
        sql.append(" group by to_char(a.create_time,'yyyy-mm') ) c on a.mdate = c.days ");//转出回转是统计接收方接收后的回转信息
        sql.append(" order by a.mdate ");

        return queryList(sql.toString(), turn, StatTurnDto.class);
    }

    /**
     * @Title : 首页 年度统计 饼状图
     *  1、审核通过的转出申请数量、审核通过的回转申请数量
     *  2、审核通过的转出数量，接收转入申请后发起回转并审核通过接收的数量
     *  3、接收转入申请数量，接收后发起回转申请的数量
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/20 15:51 
     * @param :
     * @return : 
     * @throws
     */
    public Map<String, Object> statTurnChart(StatTurnDto turn) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" (select count(a.id) from stat_turn_out a where a.status not in ('3', '4') ");
        if(!StringUtils.isNull(turn.getYear())) {
            sql.append(" and to_char(a.create_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出医院
            sql.append(" and a.org_id = :orgID ");
        }
        sql.append(" ) as value, ");
        sql.append(" (select count(a.id) from stat_turn_rotary a where a.out_status not in ('3', '4') ");
        if(!StringUtils.isNull(turn.getYear())) {
            sql.append(" and to_char(a.create_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出回转  的医院
            sql.append(" and a.org_id = :orgID " );
        }
        sql.append(" ) as value1, ");
        sql.append(" (select count(a.id) from stat_turn_rotary a join stat_turn_out b on a.in_id = b.id where a.in_status = '3' and b.status in( '2','6') ");
        if(!StringUtils.isNull(turn.getYear())) {
            sql.append(" and to_char(a.create_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出回转  接收转出信息的医院
            sql.append(" and a.acc_org_id = :orgID " );
        }
        sql.append(" ) as value2 ");
        sql.append("  from dual ");
        StatTurnDto accFor = queryOne(sql.toString(), turn, StatTurnDto.class);
        List<StatTurnDto> list1 = new ArrayList<>();
        List<StatTurnDto> list2 = new ArrayList<>();
        List<StatTurnDto> list3 = new ArrayList<>();
        //年度转出&回转申请占比  转出申请和回转申请数量
        StatTurnDto forDto1 = new StatTurnDto();
        forDto1.setName("转出");
        forDto1.setValue(accFor.getValue());
        list1.add(forDto1);
        StatTurnDto forDto2 = new StatTurnDto();
        forDto2.setName("回转");
        forDto2.setValue(accFor.getValue1());
        list1.add(forDto2);
        //年度转出&回转占比  转出完成  和转出后发起回转数量（完成状态）
        StatTurnDto forDto6 = new StatTurnDto();
        forDto6.setName("转出");
        forDto6.setValue(accFor.getValue());
        StatTurnDto forDto3 = new StatTurnDto();
        forDto3.setName("回转");
        forDto3.setValue(accFor.getValue2());
        list2.add(forDto6);
        list2.add(forDto3);

        //年度接收和回转占比   接收了转入申请  和接收后发起回转的数据统计
        StringBuffer insql = new StringBuffer();
        insql.append(" select ");
        insql.append(" (select count(a.id) from stat_turn_in a where a.status = '3' ");
        if(!StringUtils.isNull(turn.getYear())) {
            insql.append(" and to_char(a.audit_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出医院
            insql.append(" and a.acc_org_id = :orgID ");
        }
        insql.append(" ) as value, ");
        insql.append(" (select count(a.id) from stat_turn_rotary a join stat_turn_in b on a.in_id = b.id where a.out_status in ('2','6') and b.status = '3' ");
        if(!StringUtils.isNull(turn.getYear())) {
            insql.append(" and to_char(a.in_audit_time,'yyyy')=:year ");
        }
        if(!StringUtils.isNull(turn.getOrgID())){//转出回转  接收转出信息的医院
            insql.append(" and a.org_id = :orgID " );
        }
        insql.append(" ) as value1 ");
        insql.append("  from dual ");
        StatTurnDto inAcc = queryOne(insql.toString(), turn, StatTurnDto.class);

        StatTurnDto forDto4 = new StatTurnDto();
        forDto4.setName("接收");
        forDto4.setValue(inAcc.getValue());
        list3.add(forDto4);
        StatTurnDto forDto5 = new StatTurnDto();
        forDto5.setName("回转");
        forDto5.setValue(inAcc.getValue1());
        list3.add(forDto5);
        Map<String, Object> map = new HashMap<>();
        map.put("list1",list1);
        map.put("list2",list2);
        map.put("list3",list3);
        return map;
    }
}
