package com.java.moudle.system.dao.repository;

import com.java.moudle.system.domain.SysDep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysDepRepository extends JpaRepository<SysDep, String> {
    //删除科室
    @Transactional
    @Modifying
    @Query(value = "update tm_dep a  set a.status = '1' where id = :id", nativeQuery = true)
    int deleteDep(@Param("id") String id);

    /**
     * @Title : 根据机构ID查询科室信息
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/3 13:31 
     * @param :
     * @return : 
     * @throws
     */
    @Query(value="select * from tm_dep a where a.org_id = :orgID ",nativeQuery=true)
    List<SysDep> queryDepListByOrgID(@Param("orgID")String orgID);

    /**
     * @Title : 根据id查询名称
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/17 15:33
     * @param : 
     * @return : 
     * @throws
     */
    @Query(value="select dep_name from tm_dep a where id = :id ",nativeQuery=true)
    String queryDepNameByID(@Param("id")String id);

    /**
     * @Title : 查询科室编码是否存在
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/22 11:34 
     * @param :
     * @return : 
     * @throws
     */
    @Query(value="select count(1) from tm_dep where status = '0' and dep_code = :depCode and org_id = :orgId ",nativeQuery=true)
    int queryDepCount(@Param("depCode")String depCode, @Param("orgId")String orgId);

    /**
     * @Title : 查询科室名称是否存在
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/3/6 13:10 
     * @param :
     * @return : 
     * @throws
     */
    @Query(value="select count(1) from tm_dep where status = '0' and dep_name = :depName and org_id = :orgId ",nativeQuery=true)
    int queryDepNameCount(@Param("depName")String depName, @Param("orgId")String orgId);

    @Query(value="select count(1) from tm_dep where status = '0' and dep_code = :depCode and org_id = :orgId and id != :id ",nativeQuery=true)
    int queryDepNum(@Param("depCode")String depCode, @Param("orgId")String orgId, @Param("id")String id);
}
