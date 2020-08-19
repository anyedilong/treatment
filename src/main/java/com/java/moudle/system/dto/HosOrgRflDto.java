package com.java.moudle.system.dto;

import com.java.moudle.system.domain.SysOrg;

import java.util.List;
/**
 * <p>Title: HosOrgRflDto.java</p>
 * <p>Description : 转诊关系</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 10:57
 * @version : V1.0.0
 */
public class HosOrgRflDto {
    private String id; //机构id
    private String orgName; //机构名称
    private String orgCode;//机构编码
    private List<SysOrg> upOrgList; //上级机构列表

    private List<SysOrg> downOrgList; //下级机构列表

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<SysOrg> getUpOrgList() {
        return upOrgList;
    }

    public void setUpOrgList(List<SysOrg> upOrgList) {
        this.upOrgList = upOrgList;
    }

    public List<SysOrg> getDownOrgList() {
        return downOrgList;
    }

    public void setDownOrgList(List<SysOrg> downOrgList) {
        this.downOrgList = downOrgList;
    }
}
