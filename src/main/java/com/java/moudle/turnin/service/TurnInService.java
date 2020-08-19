package com.java.moudle.turnin.service;

import com.java.moudle.turnin.domain.TmTurnIn;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.until.dba.PageModel;
/**
 * <p>Title: TurnInService.java</p>
 * <p>Description : 转入管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/7 9:06
 * @version : V1.0.0
 */
public interface TurnInService {
    //查询详情
    TmTurnIn getTurnInDetail(String id);
    //审核
    void updateTurnInStatus(String audit,String reason,String id,String userId, String name, String depId);
    //接收
    void receiveTurnIn(String id,String userId, String name);
    //查询列表
    void getTurnInList(TurnOutDto out, PageModel page);

    TmTurnIn getTurnInOnly(String id);
}
