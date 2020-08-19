package com.java.moudle.turnout.dao.repository;

import com.java.moudle.turnout.domain.TmTurnOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>Title: TurnOutRepository.java</p>
 * <p>Description : 转出管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/3 11:22
 * @version : V1.0.0
 */
public interface TurnOutRepository extends JpaRepository<TmTurnOut, String> {
    /**
     * @Title : 修改转出申请删除标识
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/3 11:22
     * @param :
     * @return :
     * @throws
     */
    @Transactional
    @Modifying
    @Query(value = "update TM_TURN_OUT a  set a.delete_flg = '1' where id = :id", nativeQuery = true)
    int deleteTmTurnOut(@Param("id") String id);

    /**
     * @Title : 修改审核状态和审核原因
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 9:07 
     * @param :
     * @return : 
     * @throws
     */
    @Transactional
    @Modifying
    @Query(value = "update tm_turn_out a set a.status = :status,a.audit_user=:auditUser,a.audit_time=:auditTime,a.audit_reason=:reason where id = :id", nativeQuery = true)
    int updateTurnOutStatus(@Param("status") String status, @Param("auditUser") String auditUser, @Param("auditTime") Date auditTime, @Param("reason") String reason, @Param("id") String id);

    /**
     * @Title : 转入审核通过后修改转出审核状态为已通过
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/9 10:28 
     * @param : 
     * @return : 
     * @throws
     */
    @Transactional
    @Modifying
    @Query(value = "update tm_turn_out a set a.status = :status, a.update_user = :user, a.update_time = :updateTime, a.acc_doc_id = :user, a.acc_doc_name = :name where id = :id", nativeQuery = true)
    int updateTurnOutStatus(@Param("status") String status,@Param("id") String id, @Param("user") String user, @Param("updateTime") Date updateTime, @Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "update tm_turn_out a set a.acc_dep_id = :depId where id = :id", nativeQuery = true)
    int updateTurnOutDepId(@Param("id") String id, @Param("depId") String depId);
    
}
