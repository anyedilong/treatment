package com.java.moudle.system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.domain.SysOrgRfl;

/**
 * <p>Title: SysOrgRflRepository.java</p>
 * <p>Description : 医院转诊关系管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 9:18
 * @version : V1.0.0
 */
public interface SysOrgRflRepository extends JpaRepository<SysOrgRfl, String> {

    @Transactional
    @Modifying
    @Query(value = "update tm_org_rfl a  set a.status = '1' where id = :id", nativeQuery = true)
    int deleteOrgRfl(@Param("id") String id);
    
    @Transactional
    @Modifying
    @Query(value = "delete from tm_org_rfl a  where a.org_id = :orgId", nativeQuery = true)
    int deleteByOrgId(@Param("orgId") String orgId);
    

}
