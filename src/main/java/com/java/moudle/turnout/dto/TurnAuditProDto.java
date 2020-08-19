package com.java.moudle.turnout.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class TurnAuditProDto {

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;//本院审核时间
    private String auditUser;//本院审核人
    private String orgName;//本院审核机构
    private String status;//本院审核状态 1 通过 2 退回 3 接收
    private String reason;//退回原因
    private String type;//1、医生  2、本院领导  3、上级领导  4、接诊医生
    private String auditType;

    public Date getAuditTime() {
        return auditTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
}
