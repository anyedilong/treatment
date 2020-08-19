package com.java.moudle.turnin.dao.repository;

import com.java.moudle.turnin.domain.TmTurnIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
/**
 * <p>Title: TurnInRepository.java</p>
 * <p>Description : 转入管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/7 9:06
 * @version : V1.0.0
 */
public interface TurnInRepository extends JpaRepository<TmTurnIn, String> {
    @Transactional
    @Modifying
    @Query(value = "update tm_turn_in a  set a.delete_flg = '1' where id = :id", nativeQuery = true)
    int deleteTmTurnIn(@Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "update tm_turn_in a set a.status = :status,a.audit_user=:auditUser,a.audit_time=:auditTime,a.audit_reason=:reason where id = :id", nativeQuery = true)
    int updateTurnInStatus(@Param("status") String status, @Param("auditUser") String auditUser, @Param("auditTime") Date auditTime, @Param("reason") String reason, @Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "update tm_turn_in a set a.acc_dep_id = :depId where id = :id", nativeQuery = true)
    int updateTurnInDepId(@Param("id") String id, @Param("depId") String depId);


    @Transactional
    @Modifying
    @Query(value = "delete from tm_turn_in where out_id = :id", nativeQuery = true)
    int deleteTmTurnInByOutId(@Param("id") String id);

}
