package com.java.moudle.system.dao;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysRoleRepository;
import com.java.moudle.system.domain.SysRole;
import com.java.moudle.system.dto.RoleDto;
import com.java.until.StringUtils;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysRoleDao.java</p>
 * <p>Description : 角色管理</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author : 皮雪平
 * @date : 2019/12/31 15:55
 * @version : V1.0.0
 */
@Named
public class SysRoleDao extends BaseDao<SysRoleRepository, SysRole> {

    /**
     * @Title : 查询角色列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:31 
     * @param : 
     * @return : 
     * @throws
     */
    public void getRoleList(RoleDto role, PageModel page){
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from tm_role where status = '0' and role_type not in ('99', '3') ");
        if(!StringUtils.isNull(role.getRoleCode())){
            sql.append(" and role_code = :roleCode ");
        }
        if(!StringUtils.isNull(role.getCodeOrName())){
            sql.append(" and (role_name like concat(concat('%',:codeOrName),'%') or role_code like concat(concat('%',:codeOrName),'%')) ");
        }
        queryPageList(sql.toString(), role, page, RoleDto.class);
    }

    /**
     * @Title : 删除角色
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:31 
     * @param : 
     * @return : 
     * @throws
     */
    public void deleteRole(String id){
        repository.deleteRole(id);
    }

    /**
     * @Title : 查询角色是否被用户占用
     * @Description :
     * @author : 皮雪平
     * @date : 2019/12/31 16:41
     * @param :
     * @return :
     * @throws
     */
    public int queryRoleUser(String id){
        return repository.queryRoleUser(id);
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
        return repository.queryRoleCount(roleCode);
    }

}
