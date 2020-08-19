package com.java.moudle.system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.domain.SysRoleFunction;

public interface SysRoleFunctionRepository extends JpaRepository<SysRoleFunction, String> {

	
	@Transactional
	@Modifying
	@Query(value=" delete from tm_role_function where function_id = :menuId ", nativeQuery=true)
	void deleteByMenuId(@Param("menuId") String menuId);
	
	@Transactional
	@Modifying
	@Query(value=" delete from tm_role_function where role_id = :roleId ", nativeQuery=true)
	void deleteByRoleId(@Param("roleId") String roleId);
}
