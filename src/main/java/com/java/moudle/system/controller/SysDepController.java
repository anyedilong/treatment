package com.java.moudle.system.controller;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysDep;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.dto.OrgDepDto;
import com.java.moudle.system.service.SysDepService;
import com.java.until.StringUtils;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * <p>Title: SysDepController.java</p>
 * <p>Description : 科室管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/2 11:14
 * @version : V1.0.0
 */
@RestController
@RequestMapping("${trepath}/sys/dep")
public class SysDepController extends BaseController {
    @Inject
    private SysDepService sysDepService;

    /**
     * @Title : 添加科室
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 11:15 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("addOrgDept")
    public String addOrgDept(SysDep dep) {
        try {
            SysUser user = SysUtil.sysUser(request,response);
            dep.setOrgId(user.getOrgId());//根据当前用户获取机构id
            int count = sysDepService.queryDepCount(dep.getDepCode(),dep.getOrgId());
            if(count > 0){
                return jsonResult("", 10000, "该科室编码本医院已经存在！");
            }
            int countName = sysDepService.queryDepNameCount(dep.getDepName(),dep.getOrgId());
            if(countName > 0){
                return jsonResult("", 10000, "该科室名称本医院已经存在！");
            }
            sysDepService.saveDep(dep);
            return jsonResult("", 0, "保存成功");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 修改科室
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 11:15 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("updateOrgDept")
    public String updateOrgDept(SysDep dep) {
        try {
            if (StringUtils.isNull(dep.getId())) {
                return jsonResult(null, 10002, "ID必填");
            }
            SysUser user = SysUtil.sysUser(request,response);
            dep.setOrgId(user.getOrgId());//根据当前用户获取机构id
            int count = sysDepService.queryDepNum(dep.getDepCode(), dep.getOrgId(), dep.getId());
            if(count > 0){
                return jsonResult("", 10000, "该科室编码本医院已经存在！");
            }
            sysDepService.saveDep(dep);
            return jsonResult("", 0, "保存成功");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 根据id查询科室详情
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 11:15 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("getOrgDep")
    public String getOrgDep(SysDep depReq) {
        try {
            String id = depReq.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            SysDep dep = sysDepService.getDepDetail(id);
            return jsonResult(dep);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }
    
    /**
     * @Title : 查询科室列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 13:02 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("getOrgDepList")
    public String getOrgDepList(OrgDepDto dep, PageModel page) {
        try {
            if (dep == null) {
                dep = new OrgDepDto();
            }
            if (page == null) {
                page = new PageModel();
            }
            SysUser user = SysUtil.sysUser(request,response);
            String roleType = user.getRole().getRoleType();
            if(StringUtils.isNull(dep.getOrgId())){
                if(!StringUtils.isNull(roleType)){
                    //if(roleType.equals("1")){//本院
                        dep.setOrgId(user.getOrgId());//当前用户机构
                   // }else if(roleType.equals("2")){//科室
                   // /    dep.setId(user.getDepId());//当前用户科室
                   // }
                }
            }

            sysDepService.getOrgDepList(dep,page);
            return jsonResult(page);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 删除科室
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/2 11:17 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("deleteOrgDep")
    public String deleteOrgDep(SysDep dep) {
        try {
            String id = dep.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            sysDepService.deleteDep(id);
            return jsonResult("", 0, "删除成功！");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

}
