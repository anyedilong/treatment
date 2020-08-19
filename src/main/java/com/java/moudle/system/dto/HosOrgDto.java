package com.java.moudle.system.dto;

import com.java.until.StringUtils;
import com.java.until.dba.PageModel;
/**
 * <p>Title: HosOrgDto.java</p>
 * <p>Description : 机构列表</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 10:57
 * @version : V1.0.0
 */
public class HosOrgDto {
    private String id;
    private String orgName;
    private String orgCode;
    private String orgLevel;
    private String orgAdress;
    private String lnrName;
    private String lnrPhone;
    private String status;
    private PageModel page;

    private String orgId;
    private String codeOrName;//查询条件

    private String province;// 省
    private String city;// 市
    private String county;// 县
    private String town;// 镇
    private String upOrgID;
    
    private String isDel;//是否删除

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUpOrgID() {
        String ids = "";
        if(!StringUtils.isNull(upOrgID)){
            if(upOrgID.contains(",")){
                String[] idArray = upOrgID.split(",");
                for(int i = 0;i<idArray.length;i++){
                    ids += "'"+idArray[i]+"',";
                }
                ids = ids.substring(0,ids.length()-1);
            }else{
                ids =  "'"+upOrgID+"'";
            }
            return ids;
        }else{
            return null;
        }
    }

    public void setUpOrgID(String upOrgID) {
        this.upOrgID = upOrgID;
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

    public String getCodeOrName() {
        return codeOrName;
    }

    public void setCodeOrName(String codeOrName) {
        this.codeOrName = codeOrName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgAdress() {
        return orgAdress;
    }

    public void setOrgAdress(String orgAdress) {
        this.orgAdress = orgAdress;
    }

    public String getLnrName() {
        return lnrName;
    }

    public void setLnrName(String lnrName) {
        this.lnrName = lnrName;
    }

    public String getLnrPhone() {
        return lnrPhone;
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
}
