package com.java.moudle.system.dao;

import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysDepRepository;
import com.java.moudle.system.domain.SysDep;
import com.java.moudle.system.dto.OrgDepDto;
import com.java.until.StringUtils;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysDepDao.java</p>
 * <p>Description : 科室管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 11:05
 * @version : V1.0.0
 */
@Named
public class SysDepDao extends BaseDao<SysDepRepository, SysDep> {

    /**
     * @Title : 删除科室
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 11:04 
     * @param :
     * @return : 
     * @throws
     */
    public void deleteDep(String id){
        repository.deleteDep(id);
    }

    /**
     * @Title : 查询科室列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 11:53 
     * @param : 
     * @return : 
     * @throws
     */
    public void getOrgDepList(OrgDepDto depDto, PageModel page){
        StringBuffer sql = new StringBuffer();
        sql.append(" select a.id,a.dep_code,a.dep_name,b.org_name,c.name as dep_doctor,  ");
        //有医生的科室不能删除0.不能 1.能
        sql.append(" case (select count(t.id) from tm_user t where t.dep_id = a.id and t.status in ('1', '2')) when 0 then '1' else '0' end as isDel ");
        sql.append(" from tm_dep a join tm_org b on a.org_id = b.id  ");
        sql.append(" left join (select c.dep_id,c.name from tm_user c join tm_role d on c.authorities = d.id  ");
        sql.append(" where  c.status = '1' and d.role_type = '1') c on a.id = c.dep_id ");
        sql.append(" where a.status = '0' and b.delete_flg = '0' and b.status = '0' ");
        if(!StringUtils.isNull(depDto.getDepCode())){
            sql.append(" and a.dep_code = :depCode ");
        }
        if(!StringUtils.isNull(depDto.getOrgId())){
            sql.append(" and a.org_id = :orgId ");
        }
        if(!StringUtils.isNull(depDto.getId())){
            sql.append(" and a.id = :id ");
        }
        if(!StringUtils.isNull(depDto.getCodeOrName())){
            sql.append(" and (a.dep_name like  concat(concat('%',:codeOrName),'%') or a.dep_code  like concat(concat('%',:codeOrName),'%')) ");
        }
        sql.append(" order by a.create_time desc ");
        queryPageList(sql.toString(), depDto, page, OrgDepDto.class);
    }

    /**
     * @Title : 根据id查询名字
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/17 15:34
     * @param :
     * @return :
     * @throws
     */
    public String queryDepNameByID(String id){
        return repository.queryDepNameByID(id);
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
        return repository.queryDepCount(depCode,orgId);
    }
    public int queryDepNum(String depCode,String orgId, String id){
        return repository.queryDepNum(depCode,orgId, id);
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
    public int queryDepNameCount(String depName, String orgId) {
        return repository.queryDepNameCount(depName, orgId);
    }
}
