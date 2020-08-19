package com.java.moudle.turnout.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.domain.TmFileUpload;
import com.java.moudle.system.service.SysPlatformService;
import com.java.moudle.turnout.domain.TmTurnOut;
import com.java.moudle.turnout.dto.DiseaseDto;
import com.java.moudle.turnout.dto.TurnOutAuditDto;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.moudle.turnout.service.TurnOutService;
import com.java.until.StringUtils;
import com.java.until.SysUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

/**
 * <p>Title: TurnOutController.java</p>
 * <p>Description : 转出申请</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/3 10:25
 * @version : V1.0.0
 */
@RestController
@RequestMapping("${trepath}/turnout")
public class TurnOutController extends BaseController {
    @Inject
    private TurnOutService turnOutService;
    @Inject
    private SysPlatformService platformService;

    /**
     * @Title : 添加转出申请
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/3 11:14
     * @param :
     * @return :
     * @throws
     */
    @RequestMapping("addTurnOut")
    public String addTurnOut(TmTurnOut out){
        try {
            if (null == out) {
                return jsonResult(null, 10001, "参数错误");
            }
            //查询同一个人今天填写了几个转出单
            int count = turnOutService.getCountByToDay(out.getSfzh());
            if(count > 0) {
            	return jsonResult(null, 10000, "您已经提交了双向转诊（转出）申请，不能重复提交！");
            }
            
            String imageFile = out.getImageFile();
            if(!StringUtils.isNull(imageFile)){
                List<TmFileUpload> imageList = JSONArray.parseArray(imageFile,TmFileUpload.class);
                out.setImagelist(imageList);
            }
            SysUser user = SysUtil.sysUser(request,response);
            out.setId(UUIDUtil.getUUID());
            out.setCreateUser(user.getId());
            //1.为开启审核 0.关闭审核
            String flag = platformService.findList().get(0).getStatus();
            if("0".equals(flag)) {
                //turnOutService.updateTurnOutStatus("1","自动审核通过",out.getId(),user.getId());
                out.setStatus("1");
                out.setAuditType("0");
            }else {
            	out.setStatus("0");
            	out.setAuditType("1");
            }
            turnOutService.saveTurnOut(out);
            return jsonResult(out.getId(), 0, "保存成功");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 修改转入申请
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/3 15:23
     * @param :
     * @return :
     * @throws
     */
    @RequestMapping("updateTurnOut")
    public String updateTurnOut(TmTurnOut out) {
        try {
            if (null == out) {
                return jsonResult(null, 10001, "参数错误");
            }
            if (StringUtils.isNull(out.getId())) {
                return jsonResult(null, 10002, "ID必填");
            }
            String imageFile = out.getImageFile();
            if(!StringUtils.isNull(imageFile)){
                List<TmFileUpload> imageList = JSONArray.parseArray(imageFile,TmFileUpload.class);
                out.setImagelist(imageList);
            }
            
            turnOutService.updateTurnOut(out);
            return jsonResult(out.getId(), 0, "保存成功");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 获取转入详情
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/3 13:23 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("getTurnOutDetail")
    public String getTurnOutDetail(TmTurnOut outReq) {
        try {
            String id = outReq.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            TmTurnOut out = turnOutService.getTurnOutDetail(id);
            return jsonResult(out);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 删除转入申请
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/3 13:23 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("deleteTurnOut")
    public String deleteTurnOut(TmTurnOut out) {
        try {
            String id = out.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            turnOutService.deleteTurnOut(id);
            return jsonResult("", 0, "删除成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    /**
     * @Title : 查询转出列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 8:57 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("getTurnOutList")
    public String getTurnOutList(TurnOutDto out, PageModel page) {
        try {
            if (out == null) {
                out = new TurnOutDto();
            }
            if (page == null) {
                page = new PageModel();
            }
            SysUser user = SysUtil.sysUser(request,response);
            String roleType = user.getRole().getRoleType();
            out.setOrgId(user.getOrgId());//当前用户机构
            out.setDepId(user.getDepId());//当前用户科室
            out.setDocId(user.getId());//当前用户科室
            out.setRoleType(roleType);
            out.setCreateUser(user.getId());
            turnOutService.getTurnOutList(out,page);
            return jsonResult(page);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    /**
     * @Title : 审核
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 17:14 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("auditTurnOut")
    public String auditTurnOut(TurnOutAuditDto auditDto) {
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
            turnOutService.updateTurnOutStatus(audit,reason,id,user.getId());
            return jsonResult("", 0, "审核成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    /**
     * @Title : 撤回
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 17:14 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("drawTurnOut")
    public String drawTurnOut(TurnOutAuditDto out) {
        try {
            String id = out.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            SysUser user = SysUtil.sysUser(request,response);
            turnOutService.drawTurnOut(id,user.getId());
            return jsonResult("", 0, "撤回成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 诊断编码列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 14:06 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("getDiseaseList")
    public String getDiseaseList(DiseaseDto dto, PageModel page) {
        try {
            if (dto == null) {
                dto = new DiseaseDto();
            }
            if (page == null) {
                page = new PageModel();
            }
            turnOutService.getDiseaseList(dto,page);
            return jsonResult(page);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    
}
