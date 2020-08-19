package com.java.moudle.statistical.controller;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.statistical.dto.StatTurnDto;
import com.java.moudle.statistical.dto.StatTurnReturnDto;
import com.java.moudle.statistical.service.StatTurnService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.StringUtils;
import com.java.until.SysUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>Title: StatTurnController.java</p>
 * <p>Description : 区域统计</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/8 13:58
 * @version : V1.0.0
 */
@RestController
@RequestMapping("${trepath}/stat")
public class StatTurnController  extends BaseController {
    @Inject
    private StatTurnService turnService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy");
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");

    /**
     * @Title : 区域转出统计   根据年份  月份  机构  统计每天或者每月的转出回转数量
     * 和 转出回转总数计算占比   和个各机构下的转出回转数量
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/8 17:12
     * @param :
     * @return :
     * @throws
     */
    @RequestMapping("statTurnOutAndRotrayTime")
    public String statTurnOutAndRotrayTime(StatTurnDto dto) {
        try {
            String year = dto.getYear();
            if (StringUtils.isNull(year)) {
                //默认当前年度当前月
                dto.setYear(format.format(new Date()));
//                dto.setMonth(format1.format(new Date()));
            }
            if (!StringUtils.isNull(dto.getMonth()) && dto.getMonth().length() < 3) {
                dto.setMonth(dto.getYear() + "-" + dto.getMonth());
            }
            if(StringUtils.isNull(dto.getOrgID())){
                SysUser user = SysUtil.sysUser(request, response);
                String roleType = user.getRole().getRoleType();
                if(!StringUtils.isNull(roleType)) {
                    //if (roleType.equals("1")) {//本院
                        dto.setOrgID(user.getOrgId());//当前用户机构
                    //}
                }
            }
            StatTurnReturnDto returnDto = turnService.statTurnOutArea(dto);
            return jsonResult(returnDto);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 区域转入统计   根据年份  月份  机构  统计每天或者每月的转入 和转入回转数量
     * 和 转入 回转总数计算占比   和个各机构下的转入回转数量
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/9 17:25
     * @param :
     * @return :
     * @throws
     */
    @RequestMapping("statTurnInAndRotrayTime")
    public String statTurnInAndRotrayTime(StatTurnDto dto) {
        try {
            String year = dto.getYear();
            if (StringUtils.isNull(year)) {
                //默认当前年度当前月
                dto.setYear(format.format(new Date()));
//                dto.setMonth(format1.format(new Date()));
            }
            if (!StringUtils.isNull(dto.getMonth()) && dto.getMonth().length() < 3) {
                dto.setMonth(dto.getYear() + "-" + dto.getMonth());
            }
            if(StringUtils.isNull(dto.getOrgID())){
                SysUser user = SysUtil.sysUser(request, response);
                String roleType = user.getRole().getRoleType();
                if(!StringUtils.isNull(roleType)) {
                    //if (roleType.equals("1")) {//本院
                        dto.setOrgID(user.getOrgId());//当前用户机构
                    //}
                }
            }
            StatTurnReturnDto returnDto = turnService.statTurnInArea(dto);
            return jsonResult(returnDto);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }


    /**
     * @Title : 首页 年度统计 饼状图
     * 1、审核通过的转出申请数量、审核通过的回转申请数量（后改为申请数量，除撤回退回外所有状态）
     *
     * （2、3暂未使用）
     * 2、审核通过的转出数量，接收转入申请后发起回转并审核通过的数量
     * 3、接收转入申请数量，接收后发起回转申请的数量
     *
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/20 15:52 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("statTurnChart")
    public String statTurnChart(StatTurnDto dto) {
        try {
            String year = dto.getYear();
            if (StringUtils.isNull(year)) {
                //默认当前年度当前月
                dto.setYear(format.format(new Date()));
            }
            SysUser user = SysUtil.sysUser(request, response);
            dto.setOrgID(user.getOrgId());
            Map<String, Object> returnDto = turnService.statTurnChart(dto);
            return jsonResult(returnDto);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
}
