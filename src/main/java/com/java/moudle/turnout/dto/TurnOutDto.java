package com.java.moudle.turnout.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.PageModel;

import java.util.Date;

/**
 * <p>Title: TurnOutDto.java</p>
 * <p>Description : 转出申请列表</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/6 9:07
 * @version : V1.0.0
 */
public class TurnOutDto {
    //返回参数
    private String id;
    private String name;//患者名称
    private String sex;//性别
    private String age;//年龄
    private String sfzh;//身份证号
    private String outOrgName;//转出医院名称
    private String outDepName;//转出科室
    private String turnDoctor;//申请医生
    @JSONField(format = "yyyy-MM-dd")
    private Date turnTime;//申请时间
    private String inOrgName;//转入医院
    private String inDepName;//转入科室
    private String accDoctor;//接诊医生
    private String status;//状态
    private PageModel page;

    private String refPurpose;//诊断目的
    private String firstImpression;//初步诊断
    private String docId;
    private String accDocId;
    private String isTurnBack;//是否回转0.没有 1.有
    private String roleType;

    //查询条件
    private String orgId;//查询机构
    private String accOrgId;//接收机构
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String audit;//审核状态
    private String sfzhOrName;//身份证号或者姓名
    private String depId;//科室id
    private String accDepId;//接收科室id
    private String createUser;

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getAccDepId() {
        return accDepId;
    }

    public void setAccDepId(String accDepId) {
        this.accDepId = accDepId;
    }

    public String getRefPurpose() {
        return refPurpose;
    }

    public void setRefPurpose(String refPurpose) {
        this.refPurpose = refPurpose;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getSfzhOrName() {
        return sfzhOrName;
    }

    public void setSfzhOrName(String sfzhOrName) {
        this.sfzhOrName = sfzhOrName;
    }

    public String getFirstImpression() {
        return firstImpression;
    }

    public void setFirstImpression(String firstImpression) {
        this.firstImpression = firstImpression;
    }

    public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getAccDocId() {
		return accDocId;
	}

	public void setAccDocId(String accDocId) {
		this.accDocId = accDocId;
	}

	public String getIsTurnBack() {
		return isTurnBack;
	}

	public void setIsTurnBack(String isTurnBack) {
		this.isTurnBack = isTurnBack;
	}

	public String getAccOrgId() {
        return accOrgId;
    }

    public void setAccOrgId(String accOrgId) {
        this.accOrgId = accOrgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getOutOrgName() {
        return outOrgName;
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName;
    }

    public String getTurnDoctor() {
        return turnDoctor;
    }

    public void setTurnDoctor(String turnDoctor) {
        this.turnDoctor = turnDoctor;
    }

    public Date getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(Date turnTime) {
        this.turnTime = turnTime;
    }

    public String getInOrgName() {
        return inOrgName;
    }

    public void setInOrgName(String inOrgName) {
        this.inOrgName = inOrgName;
    }

    public String getOutDepName() {
        return outDepName;
    }

    public void setOutDepName(String outDepName) {
        this.outDepName = outDepName;
    }

    public String getInDepName() {
        return inDepName;
    }

    public void setInDepName(String inDepName) {
        this.inDepName = inDepName;
    }

    public String getAccDoctor() {
        return accDoctor;
    }

    public void setAccDoctor(String accDoctor) {
        this.accDoctor = accDoctor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}
