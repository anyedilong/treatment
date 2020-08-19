package com.java.moudle.turnout.dto;

import com.java.until.dba.PageModel;
/**
 * <p>Title: DiseaseDto.java</p>  
 * <p>Description : 诊断编码</p>
 * <p>Copyright: Copyright (c) 2020</p> 
 * @author : 皮雪平
 * @date : 2020/1/6 14:03
 * @version : V1.0.0
 */
public class DiseaseDto {
    private String diseaseCode;//疾病code
    private String diseaseName;//疾病名称
    private String diseaseType;//疾病类型名称
    private String diseaseTypeCode;//疾病类型code
    private PageModel page;

    private String codeOrName;//查询条件

    public String getCodeOrName() {
        return codeOrName;
    }

    public void setCodeOrName(String codeOrName) {
        this.codeOrName = codeOrName;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseTypeCode() {
        return diseaseTypeCode;
    }

    public void setDiseaseTypeCode(String diseaseTypeCode) {
        this.diseaseTypeCode = diseaseTypeCode;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
