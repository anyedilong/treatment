package com.java.moudle.system.domain;

import com.java.until.dba.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description  转诊医院关系表
 * @Author  pixueping
 * @Date 2019-12-31 
 */

@Entity
@Table ( name ="TM_ORG_RFL" )
public class SysOrgRfl extends BaseDomain  {

	private static final long serialVersionUID =  6384453106522081615L;

	/**
	 * 唯一标示
	 */
   	@Column(name = "ID" )
	@Id
	private String id;

	/**
	 * 机构ID
	 */
   	@Column(name = "ORG_ID" )
	private String orgId;

	/**
	 * 转诊机构ID
	 */
   	@Column(name = "REL_ORG_ID" )
	private String relOrgId;

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

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRelOrgId() {
		return this.relOrgId;
	}

	public void setRelOrgId(String relOrgId) {
		this.relOrgId = relOrgId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
