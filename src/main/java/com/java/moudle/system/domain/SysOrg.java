package com.java.moudle.system.domain;

import com.java.until.dba.BaseDomain;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description  医院机构表
 * @Author  pixueping
 * @Date 2019-12-31 
 */

@Entity
@Table ( name ="TM_ORG" )
public class SysOrg extends BaseDomain  {

	private static final long serialVersionUID =  5261577930149688984L;

	/**
	 * 唯一标识
	 */
   	@Column(name = "ID" )
	@Id
	private String id;

	/**
	 * 机构名字
	 */
   	@Column(name = "ORG_NAME" )
	private String orgName;

	/**
	 * 机构编码
	 */
   	@Column(name = "ORG_CODE" )
	private String orgCode;

	/**
	 * 机构等级编码
	 */
   	@Column(name = "ORG_LEVEL" )
	private String orgLevel;

	private String province;// 省
	private String city;// 市
	private String county;// 县
	private String town;// 镇
	private Date createTime;// 添加时间

	/**
	 * 机构地址
	 */
   	@Column(name = "ORG_ADRESS" )
	private String orgAdress;

	/**
	 * 联系人姓名
	 */
   	@Column(name = "LNR_NAME" )
	private String lnrName;

	/**
	 * 联系人电话
	 */
   	@Column(name = "LNR_PHONE" )
	private String lnrPhone;

	/**
	 * 状态 0启动 1禁用
	 */
	@Column(updatable = false)
	private String status = "0";

	/**
	 * 删除状态
	 */
	@Column(updatable = false)
	private String deleteFlg = "0";

	@Transient
	private String username;
	@Transient
	private String password;
	@Transient
	private String rflId;//转诊关系id

	public String getRflId() {
		return rflId;
	}

	public void setRflId(String rflId) {
		this.rflId = rflId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getProvince() {
		return province;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgAdress() {
		return this.orgAdress;
	}

	public void setOrgAdress(String orgAdress) {
		this.orgAdress = orgAdress;
	}

	public String getLnrName() {
		return this.lnrName;
	}

	public void setLnrName(String lnrName) {
		this.lnrName = lnrName;
	}

	public String getLnrPhone() {
		return this.lnrPhone;
	}

	public void setLnrPhone(String lnrPhone) {
		this.lnrPhone = lnrPhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
}
