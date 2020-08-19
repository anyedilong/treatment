package com.java.moudle.system.dao.repository;

import com.java.moudle.system.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SysRoleRepository extends JpaRepository<SysRole, String> {

    @Transactional
    @Modifying
    @Query(value = "update tm_role a  set a.status = '1' where id = :id", nativeQuery = true)
    int deleteRole(@Param("id") String id);

    /**
     * @Title : 查询角色是否被用户占用
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:41 
     * @param : 
     * @return :
     * @throws
     */
    @Query(value="select count(1) from tm_role a join tm_user_role b on a.id = b.role_id join tm_user c on b.user_id = c.id where a.status = '0' and c.status = '0' and a.id =:id",nativeQuery=true)
    int queryRoleUser(@Param("id")String id);

    /**
     * @Title : 查询角色编码是否存在
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/22 11:34
     * @param :
     * @return :
     * @throws
     */
    @Query(value="select count(1) from tm_role where status = '0' and role_code = :roleCode",nativeQuery=true)
    int queryRoleCount(@Param("roleCode")String roleCode);
}
