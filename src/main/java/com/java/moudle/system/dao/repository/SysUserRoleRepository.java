package com.java.moudle.system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.domain.SysUserRole;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, String> {

	@Transactional
	@Modifying
	@Query(value=" delete from tm_user_role where user_id = :userId ", nativeQuery=true)
	void deleteByUserId(@Param("userId") String userId);
	
}
