package com.java.moudle.system.controller;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysRole;
import com.java.moudle.system.dto.RoleDto;
import com.java.moudle.system.service.SysRoleService;
import com.java.until.StringUtils;
import com.java.until.dba.PageModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
/**
 * <p>Title: SysRoleController.java</p>
 * <p>Description : 角色管理</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author : 皮雪平
 * @date : 2019/12/31 16:59
 * @version : V1.0.0
 */
@RestController
@RequestMapping("${trepath}/sys/role")
public class SysRoleController  extends BaseController {
    @Inject
    private SysRoleService roleService;

    /**
     * @Title : 添加角色
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:11 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("addRole")
    public String addRole(SysRole role) {
        try {
            int count = roleService.queryRoleCount(role.getRoleCode());
            if(count > 0){
                return jsonResult("", 10000, "该角色编码已经存在！");
            }
            String id = roleService.saveRole(role);
            return jsonResult(id, 0, "保存成功");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 修改角色
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:11 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("updateRole")
    public String updateRole(SysRole role) {
        try {
            if (StringUtils.isNull(role.getId())) {
                return jsonResult(null, 10002, "ID必填");
            }
            roleService.saveRole(role);
            return jsonResult("", 0, "保存成功");
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 获取角色详情
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:11 
     * @param :
     * @return :
     * @throws
     */
    @RequestMapping("getRoleDetail")
    public String getRoleDetail(SysRole roleReq) {
        try {
            String id = roleReq.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            SysRole role = roleService.getRoleDetail(id);
            return jsonResult(role);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 查询角色列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:59 
     * @param :
     * @return : 
     * @throws
     */
    @RequestMapping("getRoleList")
    public String getRoleList(RoleDto role, PageModel page) {
        try {
            if (role == null) {
                role = new RoleDto();
            }
            if (page == null) {
                page = new PageModel();
            }
            roleService.getRoleList(role,page);
            return jsonResult(page);
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

    /**
     * @Title : 删除角色
     * @Description : 
     * @author : 皮雪平
     * @date : 2019/12/31 16:59 
     * @param : 
     * @return : 
     * @throws
     */
    @RequestMapping("deleteRole")
    public String deleteRole(SysRole role) {
        try {
            String id = role.getId();
            if (StringUtils.isNull(id)) {
                return jsonResult(null, 10002, "ID必填");
            }
            int count = roleService.queryRoleUser(id);
            if(count > 0){
                return jsonResult("", 0, "该角色正在使用，不能删除！");
            }else {
                roleService.deleteRole(id);
                return jsonResult("", 0, "删除成功！");
            }
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, "系统错误");
        }
    }

}
