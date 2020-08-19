package com.java.moudle.system.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SysAreaDto implements TreeEntity<SysAreaDto>{

	
	private String id;
	private String areaName;// 区划名称
	private String areaCode;// 区划代码
	private String parentId;// 上级区划ID
	private String sortName;// 简称
	private Integer areaLevel;// 等级
	private String createUser;// 创建人
	private Date createTime;// 创建时间
	private String deleteFlg;// 是否删除 0 否 1 是
	private String remarks;// 备注
	private String areaNum;// 区划编号
	private String areaType;// 类型
	private String gsCode;
	private String upFlg;

	private String name;

	private String areaId;

	
	private List<SysAreaDto> childList =new ArrayList<SysAreaDto>();

	public String getId() {
		return id;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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

	public String getGsCode() {
		return gsCode;
	}

	public void setGsCode(String gsCode) {
		this.gsCode = gsCode;
	}

	public String getUpFlg() {
		return upFlg;
	}

	public void setUpFlg(String upFlg) {
		this.upFlg = upFlg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SysAreaDto> getChildList() {
		return childList;
	}

	public void setChildList(List<SysAreaDto> childList) {
		this.childList = childList;
	}

	
}
