package com.java.moudle.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.dao.SysOrgDao;
import com.java.moudle.system.dao.SysUserDao;
import com.java.moudle.system.dao.SysUserRoleDao;
import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.system.domain.SysOrgRfl;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.domain.SysUserRole;
import com.java.moudle.system.dto.HosOrgDto;
import com.java.moudle.system.dto.HosOrgRflDto;
import com.java.moudle.system.service.SysOrgService;
import com.java.moudle.turnout.dao.TurnOutDao;
import com.java.until.StringUntil;
import com.java.until.StringUtils;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;
import com.java.until.ras.BCrypt;

/**
 * <p>Title: SysOrgServiceImpl.java</p>
 * <p>Description : 机构管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 8:58
 * @version : V1.0.0
 */
@Named
@Transactional(readOnly=false)
public class SysOrgServiceImpl implements SysOrgService {
    @Inject
    private SysOrgDao sysOrgDao;
    @Inject
    private SysUserDao userDao;
    @Inject
    private SysUserRoleDao userRoleDao;
    @Inject
    private TurnOutDao turnOutDao;
    @Value("${orgRoleID}")
    private String orgRoleID;
    
    /**
     * @Title : 添加修改机构
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 8:58 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void addHospitalOrg(SysOrg org) {
        if(StringUtils.isNull(org.getId())) {//新增
            org.setId(UUIDUtil.getUUID());
            org.setCreateTime(new Date());
            sysOrgDao.save(org);
            //新添加医院机构根据填写的用户名 创建医院账号
            if(!StringUtils.isNull(org.getUsername())){
                //添加医院用户
                SysUser user = new SysUser();
                user.setId(UUIDUtil.getUUID());
                user.setUsername(org.getUsername());
                user.setName(org.getOrgName());
                if(StringUntil.isNull(org.getPassword())) {
                    user.setPassword(BCrypt.hashpw("123", BCrypt.gensalt()));
                    user.setPwd("123");
                }else {
                    user.setPassword(BCrypt.hashpw(org.getPassword(), BCrypt.gensalt()));
                    user.setPwd(org.getPassword());
                }
                user.setStatus("1");
                user.setOrgId(org.getId());
                user.setAuthorities(orgRoleID);
                userDao.save(user);

                //添加角色关联
                SysUserRole role = new SysUserRole();
                role.setId(UUIDUtil.getUUID());
                role.setUserId(user.getId());
                role.setRoleId(orgRoleID);
                userRoleDao.save(role);
            }

        }else{//修改
            SysUser user = sysOrgDao.getUserByOrgID(org.getId());
            if(!StringUtils.isNull(org.getPassword())){
                boolean checkpw = BCrypt.checkpw(org.getPassword(),user.getPassword());
                if (!checkpw){//密码不相同  修改密码
                    user.setPassword(BCrypt.hashpw(org.getPassword(), BCrypt.gensalt()));
                    user.setPwd(org.getPassword());
                    userDao.save(user);
                }
            }
            sysOrgDao.save(org);
        }
    }

    /**
     * @Title :根据机构id查询机构详情
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/2 8:58
     * @param :
     * @return :
     * @throws
     */
    @Override
    public SysOrg getHosOrgDetail(String id) {
        SysUser user = sysOrgDao.getUserByOrgID(id);
        SysOrg org = sysOrgDao.get(id);
        if(user != null){
            org.setUsername(user.getUsername());
            org.setPassword(user.getPwd());
        }
        return org;
    }

    /**
     * @Title : 根据id删除机构
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 8:59 
     * @param : 
     * @return : 
     * @throws
     */
    @Override
    public void deleteHosOrg(String id) {
        sysOrgDao.deleteHosOrg(id);

    }

    /**
     * @Title : 修改机构状态
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 10:55 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void updateStatus(String id, String status) {
        sysOrgDao.updateStatus(id, status);
    }

    @Override
    public List<HosOrgDto> queryOrgList(HosOrgDto org) {
        return sysOrgDao.queryOrgList(org);
    }

    /**
     * @Title : 查询机构列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 8:59 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void getHosOrgList(HosOrgDto org, PageModel page) {
        sysOrgDao.getHosOrgList(org,page);
    }

    /**
     * @Title : 添加转诊关系
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:17 
     * @param : 
     * @return : 
     * @throws
     */
    @Override
    public String addRelHosOrg(String orgId,String relOrgId) {
        List<SysOrgRfl> orgRflList = new ArrayList<>();
        if(relOrgId.contains(",")){
            String[] rel_org_id = relOrgId.split(",");
            for(int i=0;i<rel_org_id.length;i++){
                SysOrgRfl org = new SysOrgRfl();
                org.setId(UUIDUtil.getUUID());
                org.setOrgId(orgId);
                org.setRelOrgId(rel_org_id[i]);
                org.setStatus("0");
                orgRflList.add(org);
            }
        }else{
            SysOrgRfl org = new SysOrgRfl();
            org.setId(UUIDUtil.getUUID());
            org.setOrgId(orgId);
            org.setRelOrgId(relOrgId);
            org.setStatus("0");
            orgRflList.add(org);
        }
        for(int s = 0; s < orgRflList.size(); s++) {
        	SysOrgRfl org = orgRflList.get(s);
        	//查询当先两个机构是否已有关系
        	int count = sysOrgDao.isExistTwoOrgRel(org.getRelOrgId(), org.getOrgId());
        	if(count > 0) {
        		SysOrg org1 = sysOrgDao.get(org.getOrgId());
        		SysOrg org2 = sysOrgDao.get(org.getRelOrgId());
        		return org1.getOrgName() + "和" + org2.getOrgName() + "已存在上下级关系，不能再设置";
        	}
        }
        //删除该机构原来的转诊关系
        sysOrgDao.deleteByOrgId(orgId);
        sysOrgDao.addOrgRfl(orgRflList);
        return "";
    }

    /**
     * @Title : 删除转诊关系
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:19 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void deleteRelHosOrg(String id) {
        sysOrgDao.deleteOrgRfl(id);
    }

    /**
     * @Title : 查询转诊关系
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 15:33 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public HosOrgRflDto getRelHosOrg(HosOrgRflDto orgRfl) {
        return sysOrgDao.getRelHosOrg(orgRfl);
    }
    /**
     * @Title : 判断机构编码是否存在
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/22 10:06 
     * @param : 
     * @return : 
     * @throws
     */
    public int queryOrgCount(String orgCode){
        return sysOrgDao.queryOrgCount(orgCode);
    }

	@Override
	public SysOrg queryDetailByUserName(String orgCode) {
		return sysOrgDao.queryDetailByUserName(orgCode);
	}

	@Override
	public int quertRelTurnCount(String downOrgId, String upOrgId) {
		return turnOutDao.quertRelTurnCount(downOrgId, upOrgId);
	}
   
}
