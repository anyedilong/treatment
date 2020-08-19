package com.java.moudle.turnin.controller;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.turnin.domain.TmTurnIn;
import com.java.moudle.turnin.service.TurnInService;
import com.java.moudle.turnout.dto.TurnOutAuditDto;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.until.StringUtils;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
/**
 * <p>Title: TurnInController.java</p>
 * <p>Description : 转入管理</p>
 * <p>Copyright: Copyright (c) 2020</p> 
 * @author : 皮雪平
 * @date : 2020/1/7 9:04 
 * @version : V1.0.0
 */
@RestController
@RequestMapping("${trepath}/turnin")
public class TurnInController extends BaseController {
    @Inject
    private TurnInService turnInService;
    
    /**
     * @Title : 查询转入详情
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/7 9:43 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("getTurnInDetail")
    public String getTurnInDetail(TmTurnIn inReq) {
        try {
            String id = inReq.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            TmTurnIn in = turnInService.getTurnInDetail(id);
            return jsonResult(in);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    /**
     * @Title : 查询列表  分页查询
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/7 9:25 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("getTurnInList")
    public String getTurnInList(TurnOutDto out, PageModel page) {
        try {
            if (out == null) {
                out = new TurnOutDto();
            }
            if (page == null) {
                page = new PageModel();
            }
            SysUser user = SysUtil.sysUser(request,response);
            String roleType = user.getRole().getRoleType();
            if(!StringUtils.isNull(roleType)) {
            	out.setAccOrgId(user.getOrgId());//当前用户机构
                if (roleType.equals("1")) {//本科室
                	//out.setAccDepId(user.getDepId());//当前用户科室
                } else if (roleType.equals("2")) {//本人
                	out.setAccDepId(user.getDepId());//当前用户科室
                    out.setAccDocId(user.getId());
                }
            }
            out.setCreateUser(user.getId());
            turnInService.getTurnInList(out,page);
            return jsonResult(page);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 转入申请审核
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/7 14:16 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("auditTurnIn")
    public String auditTurnIn(TurnOutAuditDto auditDto) {
        try {
            String id = auditDto.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            //1 通过  2退回
            String audit = auditDto.getAudit();
            if (StringUtils.isNull(audit)) {
                return jsonResult(null, 10002, "审核状态必填");
            }
            String reason = auditDto.getAuditReason();
            if (audit.equals("2") && StringUtils.isNull(reason)) {
                return jsonResult(null, 10002, "退回原因必填");
            }
            SysUser user = SysUtil.sysUser(request,response);
            turnInService.updateTurnInStatus(audit, reason, id, user.getId(), user.getName(), auditDto.getDepId());
            return jsonResult("", 0, "审核成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    /**
     * @Title : 接收
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/8 9:00 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("receiveTurnIn")
    public String receiveTurnIn(TurnOutAuditDto out) {
        try {
            String id = out.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            SysUser user = SysUtil.sysUser(request,response);
            turnInService.receiveTurnIn(id, user.getId(), user.getName());
            return jsonResult("", 0, "接收成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }


}
