package com.java.moudle.turnout.service;

import com.java.moudle.turnout.domain.TmTurnOut;
import com.java.moudle.turnout.dto.DiseaseDto;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.until.dba.PageModel;

/**
 * <p>Title: TurnOutService.java</p>
 * <p>Description : 转出申请</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/3 11:05
 * @version : V1.0.0
 */
public interface TurnOutService {
    //添加转出申请
    void saveTurnOut(TmTurnOut out);
    //获取转出申请详情
    TmTurnOut getTurnOutDetail(String id);
    //修改转出申请
    void updateTurnOut(TmTurnOut out);
    //删除转出申请
    void deleteTurnOut(String id);
    //获取转出申请列表
    void getTurnOutList(TurnOutDto out, PageModel page);
    //修改审核状态
    void updateTurnOutStatus(String status,String reason,String id,String user);
    //撤回
    void drawTurnOut(String id,String user);
    //查询诊断列表
    void getDiseaseList(DiseaseDto dto, PageModel page);

    TmTurnOut getTurnOutOnly(String id);
    //查询同一个人今天填写了几个转出单
    int getCountByToDay(String sfzh);
}
