package com.java.moudle.system.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.service.SysUserService;
import com.java.until.StringUntil;
import com.java.until.SysUtil;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysUserController.java</p>
 * <p>Description : 用户管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : tz
 * @date : 2020/1/2 9:01
 * @version : V1.0.0
 */

@RestController
@RequestMapping("${trepath}/sys/user")
public class SysUserController  extends BaseController {
    
	@Inject
    private SysUserService userService;

	/**
     * @Description: 查询用户（分页）
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getUserPage")
    public String getUserPage(SysUser user, PageModel page){
    	try{
    		String orgId = SysUtil.sysUser(request, response).getOrgId();
    		user.setOrgId(orgId);
    		userService.getUserPage(user, page);
    		return jsonResult(page);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询用户list
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getUserList")
    public String getUserList(SysUser user){
    	try{
    		List<SysUser> list = userService.getUserList(user);
    		return jsonResult(list);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 查询用户的详情
     * @param @param id
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("show")
    public String show(String id){
    	try{
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择查询的数据");
    		}
    		SysUser info = userService.get(id);
    		return jsonResult(info);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 保存用户信息
     * @param @param menu
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("save")
    public String save(SysUser user){
    	try{
    		//查询名称是否可用
    		SysUser temp = userService.queryInfoByCon(user.getUsername());
    		if(temp != null && StringUntil.isNull(user.getId())) {
    			return jsonResult(null, 10000, "用户名已经被占用");
    		}
    		if(StringUntil.isNull(user.getAuthorities())) {
    			return jsonResult(null, 10000, "请选择角色");
    		}else {
    			SysUser loginUser = SysUtil.sysUser(request, response);
        		user.setOrgId(loginUser.getOrgId());
    			//如果为科室领导，查询是否已经存在
        		int count = userService.queryNumByAuthor(user);
        		if(count > 0) {
        			return jsonResult(null, 10000, "同一个科室只能有一个科室领导");
        		}
        		userService.saveUserInfo(user);
        		return jsonResult();
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}

    /**
     * @Description: 删除用户信息
     * @param @param menu
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("delete")
    public String save(String id){
    	try{
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择删除的数据");
    		}
    		SysUser user = userService.get(id);
    		user.setStatus("3");
    		userService.save(user);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 更改用户的状态
     * @param @param id
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("updateUserStatus")
    public String updateUserStatus(String id, String status){
    	try{
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择删除的数据");
    		}
    		SysUser user = userService.get(id);
    		user.setStatus(status);
    		userService.save(user);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 用户密码修改
     * @param @param id
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping("updateUserPassword")
    public String updateUserPassword(String oldPassword, String newPassword){
    	try{
    		SysUser user = SysUtil.sysUser(request, response);
    		if(BCrypt.checkpw(oldPassword, user.getPassword())) {
    			user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
    			user.setPwd(newPassword);
    			userService.save(user);
    			boolean flag = SysUtil.updateCaUser(request, user);
    			if(flag) {
    				return jsonResult();
    			}else {
    				return jsonResult("", 10000, "用户缓存更新失败");
    			}
    		}else {
    			return jsonResult("", 10000, "旧密码不正确");
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 获取登录者的信息
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getLoginUserInfo")
    public String getLoginUserInfo(){
    	try{
    		return jsonResult(SysUtil.sysUser(request, response));
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}

}
