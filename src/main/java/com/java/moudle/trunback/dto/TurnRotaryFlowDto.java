package com.java.moudle.trunback.dto;


import java.io.Serializable;

import com.java.until.StringUtils;


/**
 * @ClassName: TurnRotaryFlowDto
 * @Description: 回转单流程图实体类
 * @author Administrator
 * @date 2020年1月20日
 */

public class TurnRotaryFlowDto implements Serializable {
    
	
    private static final long serialVersionUID = -7654555577404281757L;

    private String createDate;//时间
    private String descrip;   //描述
    private String reason;    //原因
    
    public TurnRotaryFlowDto() {}
    
    public TurnRotaryFlowDto(String createDate, String descrip, String reason) {
    	this.createDate = StringUtils.isNull(createDate) ? "" : createDate;
    	this.descrip = StringUtils.isNull(descrip) ? "" : descrip;
    	this.reason = StringUtils.isNull(reason) ? "" : reason;
    }
    
    
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = StringUtils.isNull(createDate) ? "" : createDate;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = StringUtils.isNull(descrip) ? "" : descrip;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = StringUtils.isNull(reason) ? "" : reason;
	}

}