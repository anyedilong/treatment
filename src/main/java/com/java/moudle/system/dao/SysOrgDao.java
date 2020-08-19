package com.java.moudle.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.java.moudle.system.dao.repository.SysOrgRepository;
import com.java.moudle.system.dao.repository.SysOrgRflRepository;
import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.system.domain.SysOrgRfl;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.HosOrgDto;
import com.java.moudle.system.dto.HosOrgRflDto;
import com.java.until.StringUtils;
import com.java.until.dba.BaseDao;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysOrgDao.java</p>
 * <p>Description : 医院机构管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 8:57
 * @version : V1.0.0
 */
@Named
public class SysOrgDao extends BaseDao<SysOrgRepository, SysOrg> {

    @Inject
    private SysOrgRflRepository sysOrgRflRepository;
    /**
     * @Title : 删除机构
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 8:56 
     * @param :
     * @return :
     * @throws
     */
    public void deleteHosOrg(String id){
        repository.deleteOrg(id);
        repository.updateUserStatus(id, "3");//冻结机构下所有用户
        repository.updateDepStatus(id, "1");//删除
    }
    /**
     * @Title : 修改机构状态  0禁用 1 禁用
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 11:01
     * @param : 
     * @return : 
     * @throws
     */
    public void updateStatus(String id,String status){
        repository.updateStatus(id,status);
        if(status.equals("0")){
            repository.updateUserStatus(id, "1");
        }else{
            repository.updateUserStatus(id, "2");//冻结机构下所有用户
        }
        repository.updateDepStatus(id, status);//冻结

    }

    /**
     * @Title : 查询医院机构列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 8:56 
     * @param : 
     * @return : 
     * @throws
     */
    public void getHosOrgList(HosOrgDto org, PageModel page) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from tm_org where delete_flg = '0' ");
        if(!StringUtils.isNull(org.getOrgCode())){
            sql.append(" and org_code = :orgCode ");
        }
        if(!StringUtils.isNull(org.getCodeOrName())){
            sql.append(" and (org_name like concat(concat('%',:codeOrName),'%') or org_code like concat(concat('%',:codeOrName),'%')) ");
        }
        if(!StringUtils.isNull(org.getOrgId())){
            sql.append(" and id = :orgId ");
        }
        if(!StringUtils.isNull(org.getUpOrgID())){
            sql.append(" and id not in ("+org.getUpOrgID()+" ) ");
        }
        sql.append(" order by create_time desc ");
        queryPageList(sql.toString(), org, page, HosOrgDto.class);
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
    public void addOrgRfl(List<SysOrgRfl> orgRfl){
        sysOrgRflRepository.saveAll(orgRfl);
    }

    /**
     * @Title : 删除转诊关系
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:17 
     * @param :
     * @return : 
     * @throws
     */
    public void deleteOrgRfl(String id){
        sysOrgRflRepository.deleteOrgRfl(id);
    }

    public List<String> getUpOrgIdByOrgId(String orgId) {
    	Map<String, Object> map = new HashMap<>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select u.org_id ");
        sql.append(" from tm_org_rfl u ");
        sql.append(" where u.status = '0' ");
        sql.append(" and u.rel_org_id = :orgId ");
        map.put("orgId", orgId);
        return queryList(sql.toString(), map, String.class);
    }
    
    /**
     * @Title : 查询转诊机构关系  上级接诊结构和下级机构
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:48 
     * @param :
     * @return : 
     * @throws
     */
    public HosOrgRflDto getRelHosOrg(HosOrgRflDto org) {
        //当前机构名称
        SysOrg sysOrg = repository.getOne(org.getId());
        org.setOrgName(sysOrg.getOrgName());
        org.setOrgCode(sysOrg.getOrgCode());
        //根据机构id查询转诊机构信息  上级机构
        StringBuffer sql = new StringBuffer();
        sql.append("select a.id as rfl_id,b.* from tm_org_rfl a join tm_org b on a.rel_org_id = b.id where a.status = '0' and b.status = '0' and b.delete_flg = '0' ");
        if(!StringUtils.isNull(org.getId())){
            sql.append(" and a.org_id = :id ");
        }
        List<SysOrg> orgList = queryList(sql.toString(), org, SysOrg.class);
        org.setUpOrgList(orgList);

        //查询当前机构的下级机构
        StringBuffer downSql = new StringBuffer();
        downSql.append("select a.id as rfl_id,b.* from tm_org_rfl a join tm_org b on a.org_id = b.id where a.status = '0' and b.status = '0' and b.delete_flg = '0' ");
        if(!StringUtils.isNull(org.getId())){
            downSql.append(" and a.rel_org_id = :id ");
        }
        List<SysOrg> downList = queryList(downSql.toString(), org, SysOrg.class);
        org.setDownOrgList(downList);
        return org;
    }


    public SysUser getUserByOrgID(String orgID) {
        Map<String, Object> map = new HashMap<>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select u.* ");
        sql.append(" from tm_user u ");
        sql.append(" join tm_role a on u.authorities = a.id ");
        sql.append(" where u.status = '1' ");
        sql.append(" and u.org_id = :orgID ");
        sql.append(" and a.role_type = '3' ");
        map.put("orgID", orgID);
        List<SysUser> userList = queryList(sql.toString(), map, SysUser.class);
        if(userList != null && userList.size() > 0){
            return userList.get(0);
        }
        return null;
    }
    /**
     * @Title : 获取机构下拉
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/10 10:35 
     * @param : 
     * @return : 
     * @throws
     */
    public List<HosOrgDto> queryOrgList(HosOrgDto org) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select id,org_name from tm_org where delete_flg = '0' and status = '0' ");
        if(!StringUtils.isNull(org.getOrgId())){
            sql.append(" and id = :orgId ");
        }
        if(!StringUtils.isNull(org.getProvince())){
            sql.append(" and province = :province ");
        }
        if(!StringUtils.isNull(org.getCity())){
            sql.append(" and city = :city ");
        }
        if(!StringUtils.isNull(org.getCounty())){
            sql.append(" and county = :county ");
        }
        if(!StringUtils.isNull(org.getTown())){
            sql.append(" and town = :town ");
        }
        sql.append(" order by create_time desc ");
        return queryList(sql.toString(), org, HosOrgDto.class);
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
    public String queryOrgNameByID(String id){
        return repository.queryOrgNameByID(id);
    }

    /**
     * @Title : 判断机构编码是否存在
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/22 10:05
     * @param :
     * @return :
     * @throws
     */
    public int queryOrgCount(String orgCode) {
        return repository.queryOrgCount(orgCode);
    }
    /**
     * @Description: 删除原有的机构转诊关系
     * @param @param orgId
     * @param @return
     * @return int
     * @throws
     */
    public int deleteByOrgId(String orgId) {
    	return sysOrgRflRepository.deleteByOrgId(orgId);
    }
    /**
     * @Description: 判断两个机构是否已有转诊关系
     * @param @param refOrgId
     * @param @param orgId
     * @param @return
     * @return int
     * @throws
     */
    public int isExistTwoOrgRel(String refOrgId, String orgId) {
    	StringBuffer sql = new StringBuffer();
        sql.append(" select count(1) from tm_org_rfl a  where a.org_id = :orgId and a.rel_org_id = :refOrgId and a.status = '0' ");
        Map<String, Object> map = new HashMap<>();
        map.put("refOrgId", orgId);
        map.put("orgId", refOrgId);
        return queryOne(sql.toString(), map, Integer.class);
    }
    
    public SysOrg queryDetailByUserName(String orgCode) {
    	 Map<String, Object> map = new HashMap<>();
         StringBuffer sql = new StringBuffer();
         sql.append(" select a.* ");
         sql.append(" from tm_org a ");
         sql.append(" where a.status = '0' and a.org_code = :orgCode ");
         map.put("orgCode", orgCode);
         return queryOne(sql.toString(), map, SysOrg.class);
    }
}
