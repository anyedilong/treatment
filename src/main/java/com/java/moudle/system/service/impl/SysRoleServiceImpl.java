package com.java.moudle.system.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.dao.SysRoleDao;
import com.java.moudle.system.domain.SysRole;
import com.java.moudle.system.dto.RoleDto;
import com.java.moudle.system.service.SysRoleService;
import com.java.until.StringUtils;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysRoleServiceImpl.java</p>
 * <p>Description : 角色管理</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author : 皮雪平
 * @date : 2019/12/31 15:54
 * @version : V1.0.0
 */
@Named
@Transactional(readOnly=false)
public class SysRoleServiceImpl implements SysRoleService {
    @Inject
    private SysRoleDao sysRoleDao;


    /**
     * @Title : 保存角色
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:08 
     * @param : 
     * @return : 
     * @throws
     */
    @Override
    public String saveRole(SysRole role) {
    	String id = role.getId();
        if(StringUtils.isNull(role.getId())) {
        	id = UUIDUtil.getUUID();
            role.setId(id);
        }
        sysRoleDao.save(role);
        return id;
    }
    //角色详情
    @Override
    public SysRole getRoleDetail(String id) {
        return sysRoleDao.get(id);
    }
    //角色列表查询
    @Override
    public void getRoleList(RoleDto role, PageModel page) {
        sysRoleDao.getRoleList(role,page);
    }
    //删除角色
    @Override
    public void deleteRole(String id) {
        sysRoleDao.deleteRole(id);
    }
    //查看角色是否被用户占用
    public int queryRoleUser(String id) {
        return sysRoleDao.queryRoleUser(id);
    }
    /**
     * @Title : 查询角色编码是否存在
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/22 11:34
     * @param :
     * @return :
     * @throws
     */
    public int queryRoleCount(String roleCode){
        return sysRoleDao.queryRoleCount(roleCode);
    }
}
