package com.java.moudle.turnout.dto;

/**
 * <p>Title: TurnOutAuditDto.java</p>
 * <p>Description : 转出申请审核</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/6 9:08
 * @version : V1.0.0
 */
public class TurnOutAuditDto {
    private String id;
    private String audit;//审核状态
    private String auditReason;//审核原因
    private String depId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}
}
