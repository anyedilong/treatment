package com.java.moudle.system.dao.repository;

import com.java.moudle.system.domain.SysOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Title: SysOrgRepository.java</p>
 * <p>Description : 医院机构管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 8:57
 * @version : V1.0.0
 */
public interface SysOrgRepository  extends JpaRepository<SysOrg, String> {

    /**
     * @Title : 修改状态 0启动 1禁用
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 10:43 
     * @param : 
     * @return : 
     * @throws
     */
    @Transactional
    @Modifying
    @Query(value = "update tm_org a  set a.status = :status where id = :id", nativeQuery = true)
    int updateStatus(@Param("id") String id,@Param("status") String status);

    /**
     * @Title : 修改机构下所有用户状态   机构禁用  下面的用户全部禁用
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/9 16:23 
     * @param : 
     * @return : 
     * @throws
     */
    @Transactional
    @Modifying
    @Query(value = "update tm_user a  set a.status = :status where a.org_id = :id", nativeQuery = true)
    int updateUserStatus(@Param("id") String id,@Param("status") String status);

    /**
     * @Title : 修改机构下所有的科室   机构禁用  科室也禁用
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/9 16:24 
     * @param : 
     * @return : 
     * @throws
     */
    @Transactional
    @Modifying
    @Query(value = "update tm_dep a  set a.status = :status where a.org_id = :id", nativeQuery = true)
    int updateDepStatus(@Param("id") String id,@Param("status") String status);

    /**
     * @Title : 删除机构
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 10:43 
     * @param : 
     * @return : 
     * @throws
     */
    @Transactional
    @Modifying
    @Query(value = "update tm_org a  set a.delete_flg = '1' where id = :id", nativeQuery = true)
    int deleteOrg(@Param("id") String id);

    /**
     * @Title : 根据ID查询机构名称
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 10:43 
     * @param :
     * @return : 
     * @throws
     */
    @Query(value="select org_name from tm_org a where id = :id ",nativeQuery=true)
    String queryOrgNameByID(@Param("id")String id);

    /**
     * @Title : 判断机构编码是否存在
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/22 10:05 
     * @param :
     * @return :
     * @throws
     */
    @Query(value="select count(1) from tm_org where delete_flg = '0' and status = '0' and org_code = :orgCode",nativeQuery=true)
    int queryOrgCount(@Param("orgCode")String orgCode);
}
