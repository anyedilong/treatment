package com.java.moudle.turnout.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.moudle.system.domain.TmFileUpload;
import com.java.moudle.turnout.dto.TurnAuditProDto;
import com.java.until.dba.BaseDomain;
import com.java.until.dict.DictUtil;

/**
 * @Description  转出申请
 * @Author  
 * @Date 2020-01-03
 */

@Entity
@Table ( name ="TM_TURN_OUT" )
public class TmTurnOut  extends BaseDomain  {

	private static final long serialVersionUID =  7947901462740924215L;

	/**
	 * 唯一标识
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
	 * 科室ID
	 */
   	@Column(name = "DEP_ID" )
	private String depId;

	/**
	 * 医生姓名
	 */
   	@Column(name = "DOC_NAME" )
	private String docName;
   	
   	/**
	 * 医生id
	 */
   	@Column(name = "DOC_ID" )
	private String docId;

	/**
	 * 就诊时间
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date checkTime;

	/**
	 * 接诊医院ID
	 */
   	@Column(name = "ACC_ORG_ID" )
	private String accOrgId;

	/**
	 * 接诊科室ID
	 */
   	@Column(name = "ACC_DEP_ID" )
	private String accDepId;

	/**
	 * 接诊医生
	 */
   	@Column(name = "ACC_DOC_NAME" )
	private String accDocName;
   	
   	/**
	 * 接诊医生标识
	 */
   	@Column(name = "ACC_DOC_ID" )
	private String accDocId;

	/**
	 * 转诊目的
	 */
   	@Column(name = "REF_PURPOSE" )
	private String refPurpose;

	/**
	 * 诊断编码
	 */
   	@Column(name = "DIA_CODE" )
	private String diaCode;

	/**
	 * 初步印象
	 */
   	@Column(name = "FIRST_IMPRESSION" )
	private String firstImpression;

	/**
	 * 患者名称
	 */
   	@Column(name = "NAME" )
	private String name;

	/**
	 * 身份证号
	 */
   	@Column(name = "SFZH" )
	private String sfzh;

	/**
	 * 年龄
	 */
   	@Column(name = "AGE" )
	private String age;

	/**
	 * 性别
	 */
   	@Column(name = "SEX" )
	private String sex;

	/**
	 * 生日
	 */
   	@Column(name = "BIRTHDAY" )
	private String birthday;

	/**
	 * 体重
	 */
   	@Column(name = "WEIGHT" )
	private String weight;

	/**
	 * 身高
	 */
   	@Column(name = "HEIGHT" )
	private String height;

	/**
	 * 档案编号
	 */
   	@Column(name = "DNO" )
	private String dno;

	/**
	 * 联系方式
	 */
   	@Column(name = "TELEPHONE" )
	private String telephone;

	/**
	 * 家属联系方式
	 */
   	@Column(name = "FAMILY_PHONE" )
	private String familyPhone;

	/**
	 * 医保卡
	 */
   	@Column(name = "HEALTH_CARD" )
	private String healthCard;

	/**
	 * 医报类型
	 */
   	@Column(name = "HEALTH_TYPE" )
	private String healthType;

	/**
	 * 家庭住址
	 */
   	@Column(name = "ADRESS" )
	private String adress;

	/**
	 * 知情同意书
	 */
   	@Column(name = "ZQTYS_FILE" )
	private String zqtysFile;

	/**
	 * 相关附件
	 */
   	@Column(name = "ATTAC_FILE" )
	private String attacFile;

	/**
	 * 主诉
	 */
   	@Column(name = "ZS" )
	private String zs;

	/**
	 * 现病史
	 */
   	@Column(name = "XBS" )
	private String xbs;

	/**
	 * 既往史
	 */
   	@Column(name = "JWZ" )
	private String jwz;

	/**
	 * 过敏史
	 */
   	@Column(name = "GMS" )
	private String gms;

	/**
	 * 家族史
	 */
   	@Column(name = "JZS" )
	private String jzs;

	/**
	 * 查体
	 */
   	@Column(name = "CT" )
	private String ct;

	/**
	 * 辅助检查
	 */
   	@Column(name = "FZJC" )
	private String fzjc;

	/**
	 * 治疗经过
	 */
   	@Column(name = "ZLJG" )
	private String zljg;

	/**
	 * 备注
	 */
   	@Column(name = "REMARK" )
	private String remark;

	/**
	 * 创建人
	 */
	@Column(updatable = false)
	private String createUser;

	/**
	 * 创建时间
	 */
	@Column(updatable = false)
	@JSONField(format="yyyy-MM-dd")
	private Date createTime;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 审核人
	 */
	private String auditUser;

	/**
	 * 审核时间
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date auditTime;

	/**
	 * 审核原因
	 */
	private String auditReason;

	/**
	 * 修改人
	 */
   	@Column(name = "UPDATE_USER" )
	private String updateUser;

	/**
	 * 修改时间
	 */
   	@Column(name = "UPDATE_TIME" )
	@JSONField(format="yyyy-MM-dd")
	private Date updateTime;

	/**
	 * 删除标识
	 */
	@Column(updatable = false)
	private String deleteFlg = "0";
	
	/**
	 * 是否开启院内审核（0.关闭 1.开启）
	 */
	@Column(updatable = false)
	private String auditType;

	private String province;// 省
	private String city;// 市
	private String county;// 县
	private String town;// 镇

	@Transient
	private List<TmFileUpload> imagelist;//附件表
	@Transient
	private List<TurnAuditProDto> auditList;//审批进度
	@Transient
	private String orgName;//申请机构名称
	@Transient
	private String depName;//申请科室名称
	@Transient
	private String accOrgName;//接收机构名称
	@Transient
	private String accDepName;//接收科室名称
	@Transient
	private String imageFile;
	@Transient
	private String provinceName;
	@Transient
	private String cityName;
	@Transient
	private String countyName;
	@Transient
	private String townName;

	public List<TmFileUpload> getImagelist() {
		return imagelist;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public String getProvinceName() {
		this.provinceName = DictUtil.getDictValue("areadict", this.province);
		return provinceName;
	}

	public String getCityName() {
		this.cityName = DictUtil.getDictValue("areadict", this.city);
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		this.countyName = DictUtil.getDictValue("areadict", this.county);
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getTownName() {
		this.townName = DictUtil.getDictValue("areadict", this.town);
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public void setImagelist(List<TmFileUpload> imagelist) {
		this.imagelist = imagelist;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAccOrgName() {
		return accOrgName;
	}

	public void setAccOrgName(String accOrgName) {
		this.accOrgName = accOrgName;
	}

	public String getAccDepName() {
		return accDepName;
	}

	public void setAccDepName(String accDepName) {
		this.accDepName = accDepName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getProvince() {
		return province;
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

	public List<TurnAuditProDto> getAuditList() {
		return auditList;
	}

	public void setAuditList(List<TurnAuditProDto> auditList) {
		this.auditList = auditList;
	}

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

	public String getDepId() {
		return this.depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getAccOrgId() {
		return this.accOrgId;
	}

	public void setAccOrgId(String accOrgId) {
		this.accOrgId = accOrgId;
	}

	public String getAccDepId() {
		return this.accDepId;
	}

	public void setAccDepId(String accDepId) {
		this.accDepId = accDepId;
	}

	public String getAccDocName() {
		return this.accDocName;
	}

	public void setAccDocName(String accDocName) {
		this.accDocName = accDocName;
	}

	public String getAccDocId() {
		return accDocId;
	}

	public void setAccDocId(String accDocId) {
		this.accDocId = accDocId;
	}

	public String getRefPurpose() {
		return this.refPurpose;
	}

	public void setRefPurpose(String refPurpose) {
		this.refPurpose = refPurpose;
	}

	public String getDiaCode() {
		return this.diaCode;
	}

	public void setDiaCode(String diaCode) {
		this.diaCode = diaCode;
	}

	public String getFirstImpression() {
		return this.firstImpression;
	}

	public void setFirstImpression(String firstImpression) {
		this.firstImpression = firstImpression;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSfzh() {
		return this.sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getDno() {
		return this.dno;
	}

	public void setDno(String dno) {
		this.dno = dno;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFamilyPhone() {
		return this.familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	public String getHealthCard() {
		return this.healthCard;
	}

	public void setHealthCard(String healthCard) {
		this.healthCard = healthCard;
	}

	public String getHealthType() {
		return this.healthType;
	}

	public void setHealthType(String healthType) {
		this.healthType = healthType;
	}

	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getZqtysFile() {
		return this.zqtysFile;
	}

	public void setZqtysFile(String zqtysFile) {
		this.zqtysFile = zqtysFile;
	}

	public String getAttacFile() {
		return this.attacFile;
	}

	public void setAttacFile(String attacFile) {
		this.attacFile = attacFile;
	}

	public String getZs() {
		return this.zs;
	}

	public void setZs(String zs) {
		this.zs = zs;
	}

	public String getXbs() {
		return this.xbs;
	}

	public void setXbs(String xbs) {
		this.xbs = xbs;
	}

	public String getJwz() {
		return this.jwz;
	}

	public void setJwz(String jwz) {
		this.jwz = jwz;
	}

	public String getGms() {
		return this.gms;
	}

	public void setGms(String gms) {
		this.gms = gms;
	}

	public String getJzs() {
		return this.jzs;
	}

	public void setJzs(String jzs) {
		this.jzs = jzs;
	}

	public String getCt() {
		return this.ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getFzjc() {
		return this.fzjc;
	}

	public void setFzjc(String fzjc) {
		this.fzjc = fzjc;
	}

	public String getZljg() {
		return this.zljg;
	}

	public void setZljg(String zljg) {
		this.zljg = zljg;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuditUser() {
		return this.auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditReason() {
		return this.auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDeleteFlg() {
		return this.deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

}
