package com.java.moudle.trunback.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.trunback.domain.TmTurnRotary;

public interface TurnRotaryRepository extends JpaRepository<TmTurnRotary, String> {

	
	@Transactional
    @Modifying
    @Query(value = "update tm_turn_rotary a set a.delete_flg = '1' where id = :id", nativeQuery = true)
    int deleteTurnRotary(@Param("id") String id);
	
}
