package com.java.moudle.system.domain;

import com.java.until.dba.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description  角色表
 * @Author  pixueping
 * @Date 2019-12-31 
 */

@Entity
@Table ( name ="TM_ROLE" )
public class SysRole extends BaseDomain {

	private static final long serialVersionUID =  8739535841949207154L;

	/**
	 * 唯一标识
	 */
   	@Column(name = "ID" )
	@Id
	private String id;

	/**
	 * 角色CODE
	 */
   	@Column(name = "ROLE_CODE" )
	private String roleCode;

	/**
	 * 角色名称
	 */
   	@Column(name = "ROLE_NAME" )
	private String roleName;
	private String roleType;//角色作用范围  1本院  2科室

	/**
	 * 状态
	 */
	@Column(updatable = false)
	private String status = "0";

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
