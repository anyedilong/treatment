package com.java.moudle.system.domain;

import com.java.until.dba.BaseDomain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//区划
@Entity
@Table(name = "sys_area")
public class SysArea extends BaseDomain {

	@Id
	private String id;
	@Column(updatable = false)
	private String areaName;// 区划名称
	@Column(name = "area_code", updatable = false)
	private String areaCode;// 区划代码
	@Column(updatable = false)
	private String parentId;// 上级区划ID
	@Column(updatable = false)
	private String sortName;// 简称
	@Column(updatable = false)
	private Integer areaLevel;// 等级
	@Column(updatable = false)
	private String createUser;// 创建人
	@Column(updatable = false)
	private Date createTime;// 创建时间
	@Column(updatable = false)
	private String deleteFlg;// 是否删除 0 否 1 是
	private String remarks;// 备注
	@Column(updatable = false)
	private String areaNum;// 区划编号
	@Column(updatable = false)
	private String areaType;// 类型
	
	@Transient
	private List<SysArea> childList =new ArrayList<SysArea>();

	public List<SysArea> getChildList() {
		return childList;
	}

	public void setChildList(List<SysArea> childList) {
		this.childList = childList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Integer getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(Integer areaLevel) {
		this.areaLevel = areaLevel;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAreaNum() {
		return areaNum;
	}

	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

}
