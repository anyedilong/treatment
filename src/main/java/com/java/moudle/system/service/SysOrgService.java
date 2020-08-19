package com.java.moudle.system.service;

import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.system.dto.HosOrgDto;
import com.java.moudle.system.dto.HosOrgRflDto;
import com.java.until.dba.PageModel;

import java.util.List;

/**
 * <p>Title: SysOrgService.java</p>
 * <p>Description : 机构管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 8:59
 * @version : V1.0.0
 */
public interface SysOrgService {
    //添加机构
    void addHospitalOrg(SysOrg org);
    //获取机构详情
    SysOrg getHosOrgDetail(String id);
    //根据用户名查询机构详情
    SysOrg queryDetailByUserName(String orgCode);
    //查询转诊关系产生单的数量
    int quertRelTurnCount(String downOrgId, String upOrgId);
    //删除机构
    void deleteHosOrg(String id);
    //获取机构列表
    void getHosOrgList(HosOrgDto org, PageModel page);
    //添加转诊关系
    String addRelHosOrg(String orgId,String relOrgId);
    //删除转诊关系
    void deleteRelHosOrg(String id);
    //查询转诊关系
    HosOrgRflDto getRelHosOrg(HosOrgRflDto orgRfl);
    //修改机构状态
    void updateStatus(String id, String status);
    //查询区域机构列表
    List<HosOrgDto> queryOrgList(HosOrgDto org);
    //判断机构编码是否存在
    int queryOrgCount(String orgCode);
}
