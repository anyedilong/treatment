package com.java.moudle.system.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.system.domain.SysOrgRfl;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.HosOrgDto;
import com.java.moudle.system.dto.HosOrgRflDto;
import com.java.moudle.system.service.SysOrgService;
import com.java.moudle.system.service.SysUserService;
import com.java.until.StringUntil;
import com.java.until.StringUtils;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysOrgController.java</p>
 * <p>Description : 机构管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 9:01
 * @version : V1.0.0
 */

@RestController
@RequestMapping("${trepath}/sys/org")
public class SysOrgController  extends BaseController {
    @Inject
    private SysOrgService sysOrgService;
    @Inject
    private SysUserService userService;

    /**
     * @Title : 添加机构
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:01 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("addHospitalOrg")
    public String addHospitalOrg(SysOrg org) {
        try {
            int count = sysOrgService.queryOrgCount(org.getOrgCode());
            if(count > 0) {
                return jsonResult("", 10000, "该机构编码已经被占用");
            }
            SysUser temp = userService.queryInfoByCon(org.getUsername());
            if(temp != null) {
                return jsonResult(null, 10000, "用户名已经被占用");
            }
            sysOrgService.addHospitalOrg(org);
            return jsonResult("", 0, "保存成功");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 修改机构
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:01 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("updateHosOrg")
    public String updateHosOrg(SysOrg org) {
        try {
            if (StringUtils.isNull(org.getId())) {
                return jsonResult(null, 10002, "ID必填");
            }
            //是否有其他机构编码相同
            SysOrg temp = sysOrgService.queryDetailByUserName(org.getOrgCode());
            if(temp != null && !temp.getId().equals(org.getId())) {
            	return jsonResult("", 10000, "该机构编码已经被占用");
            }
            sysOrgService.addHospitalOrg(org);
            return jsonResult("", 0, "保存成功");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    /**
     * @Title : 根据id查询机构详情
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:02 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("getHosOrgDetail")
    public String getHosOrgDetail(SysOrg orgReq) {
        try {
            String id = orgReq.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            SysOrg org = sysOrgService.getHosOrgDetail(id);
            return jsonResult(org);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 查询机构列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:02 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("getHosOrgList")
    public String getHosOrgList(HosOrgDto org, PageModel page) {
        try {
            if (org == null) {
                org = new HosOrgDto();
            }
            if (page == null) {
                page = new PageModel();
            }
            sysOrgService.getHosOrgList(org,page);
            return jsonResult(page);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 删除机构
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:03 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("deleteHosOrg")
    public String deleteHosOrg(SysOrg org) {
        try {
            String id = org.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            HosOrgRflDto orgRfl = new HosOrgRflDto();
            orgRfl.setId(id);
            HosOrgRflDto rfl = sysOrgService.getRelHosOrg(orgRfl);
            List<SysOrg> upOrgList = rfl.getUpOrgList();
            List<SysOrg> downOrgList = rfl.getDownOrgList();
            if((upOrgList!=null && upOrgList.size()>0) || (downOrgList!=null&&downOrgList.size()>0)){
                return jsonResult("", 10002, "该机构存在转诊关系，无法进行删除！");
            }
            sysOrgService.deleteHosOrg(id);
            return jsonResult("", 0, "删除成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    /**
     * @Title : 修改机构状态 0启用 1 禁用
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 10:54 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("updateStatus")
    public String updateStatus(SysOrg org) {
        try {
            String id = org.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            String status = org.getStatus();
            if (StringUtils.isNull(status)) {
                return jsonResult(null, 10002, "状态必填");
            }
            sysOrgService.updateStatus(id,status);
            return jsonResult("", 0, "状态修改成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    /**
     * @Title : 添加医院转诊关系（上级机构）
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:16 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("addRelHosOrg")
    public String addRelHosOrg(SysOrgRfl orgRfl) {
        try {
            String orgId = orgRfl.getOrgId();
            if (StringUtils.isNull(orgId)) {
                return jsonResult(null, 10002, "机构ID必填");
            }
            String relOrgId = orgRfl.getRelOrgId();
            if (StringUtils.isNull(relOrgId)) {
                return jsonResult(null, 10002, "上级机构ID必填");
            }
            String msg = sysOrgService.addRelHosOrg(orgId,relOrgId);
            if(StringUntil.isNull(msg)) {
            	return jsonResult("", 0, "保存成功");
            }else {
            	return jsonResult("", 10000, msg);
            }
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Description: 查询医院转诊关系产生的条数
     * @param @param org
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("quertRelTurnCount")
    public String quertRelTurnCount(String downOrgId, String upOrgId) {
        try {
            if (StringUtils.isNull(downOrgId) || StringUtils.isNull(upOrgId)) {
                return jsonResult(null, 10000, "下级机构和上级机构的标识为空");
            }
            int count = sysOrgService.quertRelTurnCount(downOrgId, upOrgId);
            return jsonResult(count);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    
    /**
     * @Title : 删除医院转诊关系
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 9:20 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("deleteRelHosOrg")
    public String deleteRelHosOrg(SysOrg org) {
        try {
            String id = org.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            sysOrgService.deleteRelHosOrg(id);
            return jsonResult("", 0, "删除成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    
    /**
     * @Title : 查询转诊关系
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 10:29 
     * @param :
     * @return :
     * @throws
     */
    @RequestMapping("getRelHosOrg")
    public String getRelHosOrg(HosOrgRflDto orgRfl) {
        try {
            if (StringUtils.isNull(orgRfl.getId())) {
                return jsonResult(null, 10002, "ID必填");
            }
            HosOrgRflDto orgDto = sysOrgService.getRelHosOrg(orgRfl);
            return jsonResult(orgDto);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    
    /**
     * @Title : 获取机构下拉
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/10 10:36 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("queryOrgList")
    public String queryOrgList(HosOrgDto org) {
        try {
            if (org == null) {
                org = new HosOrgDto();
            }
            List<HosOrgDto> orgList =  sysOrgService.queryOrgList(org);
            return jsonResult(orgList);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

}
