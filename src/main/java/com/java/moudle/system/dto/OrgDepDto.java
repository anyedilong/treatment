package com.java.moudle.system.dto;

import com.java.until.dba.PageModel;

/**
 * <p>Title: OrgDepDto.java</p>
 * <p>Description : 科室信息列表</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 11:21
 * @version : V1.0.0
 */
public class OrgDepDto {
    private String id;//id
    private String depName;//科室名称
    private String depCode;//科室编码
    private String orgName;//机构名称
    private String depDoctor;//科室主任
    private PageModel page;

    private String orgId;//机构ID
    private String codeOrName;//查询条件
    private String isDel;//是否删除

    public String getCodeOrName() {
        return codeOrName;
    }

    public void setCodeOrName(String codeOrName) {
        this.codeOrName = codeOrName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDepDoctor() {
        return depDoctor;
    }

    public void setDepDoctor(String depDoctor) {
        this.depDoctor = depDoctor;
    }

    public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
