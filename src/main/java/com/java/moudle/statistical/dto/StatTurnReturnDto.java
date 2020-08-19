package com.java.moudle.statistical.dto;

import java.util.List;

/**
 * <p>Title: StatTurnReturnDto.java</p>
 * <p>Description : 区域统计数据返回</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/10 16:16
 * @version : V1.0.0
 */
public class StatTurnReturnDto {

    private List<String> timeXList;//月/日
    private List<Integer> timeY1List;//转出数量
    private List<Integer> timeY2List;//回转数量

    private List<String> orgXList;
    private List<Integer> orgY1List;
    private List<Integer> orgY2List;

    private List<StatTurnDto> accForList;//饼状图

    public List<String> getTimeXList() {
        return timeXList;
    }

    public void setTimeXList(List<String> timeXList) {
        this.timeXList = timeXList;
    }

    public List<Integer> getTimeY1List() {
        return timeY1List;
    }

    public void setTimeY1List(List<Integer> timeY1List) {
        this.timeY1List = timeY1List;
    }

    public List<Integer> getTimeY2List() {
        return timeY2List;
    }

    public void setTimeY2List(List<Integer> timeY2List) {
        this.timeY2List = timeY2List;
    }

    public List<String> getOrgXList() {
        return orgXList;
    }

    public void setOrgXList(List<String> orgXList) {
        this.orgXList = orgXList;
    }

    public List<Integer> getOrgY1List() {
        return orgY1List;
    }

    public void setOrgY1List(List<Integer> orgY1List) {
        this.orgY1List = orgY1List;
    }

    public List<Integer> getOrgY2List() {
        return orgY2List;
    }

    public void setOrgY2List(List<Integer> orgY2List) {
        this.orgY2List = orgY2List;
    }

    public List<StatTurnDto> getAccForList() {
        return accForList;
    }

    public void setAccForList(List<StatTurnDto> accForList) {
        this.accForList = accForList;
    }
}
