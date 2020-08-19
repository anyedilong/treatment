package com.java.moudle.system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.moudle.system.domain.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, String> {

}
