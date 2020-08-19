package com.java.moudle.system.service.impl;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.dao.SysDepDao;
import com.java.moudle.system.domain.SysDep;
import com.java.moudle.system.dto.OrgDepDto;
import com.java.moudle.system.service.SysDepService;
import com.java.until.StringUtils;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysDepServiceImpl.java</p>
 * <p>Description : 科室管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 11:09
 * @version : V1.0.0
 */
@Named
@Transactional(readOnly=false)
public class SysDepServiceImpl implements SysDepService {
    @Inject
    private SysDepDao sysDepDao;

    /**
     * @Title : 保存科室
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 11:05 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void saveDep(SysDep dep) {
        if(StringUtils.isNull(dep.getId())) {
            dep.setId(UUIDUtil.getUUID());
            dep.setCreateTime(new Date());
        }
        sysDepDao.save(dep);
    }
    //科室详情
    @Override
    public SysDep getDepDetail(String id) {
        return sysDepDao.get(id);
    }
    //删除科室
    @Override
    public void deleteDep(String id) {
        sysDepDao.deleteDep(id);
    }
    //查询科室列表
    @Override
    public void getOrgDepList(OrgDepDto dep, PageModel page) {
        sysDepDao.getOrgDepList(dep, page);
    }

    /**
     * @Title : 查询科室编码是否存在
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/22 11:34
     * @param :
     * @return :
     * @throws
     */
    public int queryDepCount(String depCode,String orgId){
        return sysDepDao.queryDepCount(depCode,orgId);
    }
    /**
     * @Title : 查询科室名称是否重复
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/3/6 13:12 
     * @param : 
     * @return :
     * @throws
     */
    public int queryDepNameCount(String depName,String orgId){
        return sysDepDao.queryDepNameCount(depName,orgId);
    }

    public int queryDepNum(String depCode,String orgId, String id){
        return sysDepDao.queryDepNum(depCode,orgId, id);
    }
}
