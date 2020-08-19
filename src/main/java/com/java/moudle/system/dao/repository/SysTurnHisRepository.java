package com.java.moudle.system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.domain.SysTurnHis;

public interface SysTurnHisRepository extends JpaRepository<SysTurnHis, String> {

	@Transactional
    @Modifying
    @Query(value = "delete from tm_turn_his r where r.id = (select id from (select s.* from tm_turn_his s where " + 
    		"s.dep_id = :depId and s.org_id = :orgId and s.turn_id = :turnId and s.sfzh = :sfzh " + 
    		"and s.type = :type order by s.create_date desc) where rownum = 1)", nativeQuery = true)
    int deleteByTurnId(@Param("turnId") String turnId, @Param("depId") String depId, @Param("orgId") String orgId, 
    		@Param("sfzh") String sfzh, @Param("type") String type);
	
}
