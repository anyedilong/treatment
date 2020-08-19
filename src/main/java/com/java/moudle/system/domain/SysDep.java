package com.java.moudle.system.domain;

import com.java.until.dba.BaseDomain;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description  科室表
 * @Author  pixueping
 * @Date 2019-12-31 
 */

@Entity
@Table ( name ="TM_DEP" )
public class SysDep extends BaseDomain  {

	private static final long serialVersionUID =  4335161531631295971L;

	/**
	 * 唯一标识
	 */
   	@Column(name = "ID" )
	@Id
	private String id;

	/**
	 * 科室名称
	 */
   	@Column(name = "DEP_NAME" )
	private String depName;

	/**
	 * 机构ID
	 */
   	@Column(updatable = false )
	private String orgId;

	/**
	 * 状态
	 */
	@Column(updatable = false)
	private String status = "0";

	/**
	 * 科室代码
	 */
   	@Column(name = "DEP_CODE" )
	private String depCode;

	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepName() {
		return this.depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepCode() {
		return this.depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

}
