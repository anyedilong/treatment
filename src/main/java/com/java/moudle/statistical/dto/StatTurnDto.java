package com.java.moudle.statistical.dto;
/**
 * <p>Title: StatTurnDto.java</p>
 * <p>Description : 区域统计</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/8 13:58
 * @version : V1.0.0
 */
public class StatTurnDto {
    //参数
    private String year;//年度
    private String month;//月度
    private String orgID;//机构

    //返回
    private String name;//天
    private Integer value;//转出
    private Integer value1;//回转
    private Integer value2;//回转

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue1() {
        return value1;
    }

    public Integer getValue2() {
        return value2;
    }

    public void setValue2(Integer value2) {
        this.value2 = value2;
    }

    public void setValue1(Integer value1) {
        this.value1 = value1;
    }
}
