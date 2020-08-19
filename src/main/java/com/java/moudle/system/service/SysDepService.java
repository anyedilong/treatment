package com.java.moudle.system.service;

import com.java.moudle.system.domain.SysDep;
import com.java.moudle.system.dto.OrgDepDto;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysDepService.java</p>
 * <p>Description : 科室管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 11:09
 * @version : V1.0.0
 */
public interface SysDepService {
    //添加科室
    void saveDep(SysDep dep);
    //根据id获取详情
    SysDep getDepDetail(String id);
    //删除科室
    void deleteDep(String id);
    //查询科室列表
    void getOrgDepList(OrgDepDto dep, PageModel page);
    //查询科室编码是否存在
    int queryDepCount(String depCode,String orgId);
    //查询科室名称是否存在
    int queryDepNameCount(String depName,String orgId);

    int queryDepNum(String depCode,String orgId, String id);

}
