package com.java.moudle.system.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.java.until.dba.BaseDomain;

//区划
@Entity
@Table(name = "tm_turn_his")
public class SysTurnHis extends BaseDomain {

	private static final long serialVersionUID = 1686416416L;
	
	@Id
	private String id;
	//用户名称
	private String username;
	//操作描述
	private String describe;
	//操作时间
	private Date createDate;
	
	private String type;
	private String depId;
	private String orgId;
	private String turnId;
	private String accDocId;
	private String sfzh;
	
	public SysTurnHis() {}
	
	public SysTurnHis(String username, String describe, Date createDate, String type, 
			String depId, String orgId, String turnId, String sfzh, String accDocId) {
		this.username = username;
		this.describe = describe;
		this.createDate = createDate;
		this.type = type;
		this.depId = depId;
		this.orgId = orgId;
		this.turnId = turnId;
		this.sfzh = sfzh;
		this.accDocId = accDocId;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getTurnId() {
		return turnId;
	}

	public void setTurnId(String turnId) {
		this.turnId = turnId;
	}

	public String getAccDocId() {
		return accDocId;
	}

	public void setAccDocId(String accDocId) {
		this.accDocId = accDocId;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

}
