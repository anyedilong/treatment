package com.java.moudle.system.service;

import com.java.moudle.system.domain.SysRole;
import com.java.moudle.system.dto.RoleDto;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysRoleService.java</p>
 * <p>Description : 角色管理</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author : 皮雪平
 * @date : 2019/12/31 15:54
 * @version : V1.0.0
 */
public interface SysRoleService {
    //添加角色
    String saveRole(SysRole role);
    //获取角色详情
    SysRole getRoleDetail(String id);
    //查询角色列表
    void getRoleList(RoleDto role, PageModel page);
    //删除角色
    void deleteRole(String id);
    //查询角色是否被占用
    int queryRoleUser(String id);
    //查询角色编码是否存在
    int queryRoleCount(String roleCode);
}
