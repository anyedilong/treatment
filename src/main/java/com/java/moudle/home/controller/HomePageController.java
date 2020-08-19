package com.java.moudle.home.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.home.service.HomePageService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.SysUtil;

/**
 * @ClassName: StatsTurnBackController
 * @Description: 医院转出、转入统计
 * @author Administrator
 * @date 2020年1月9日
 */
@RestController
@RequestMapping("${trepath}/home")
public class HomePageController  extends BaseController {
    
	@Inject
    private HomePageService homePageService;

	/**
	 * @Description: 首页-我的待办
	 * @param @return
	 * @return String
	 * @throws
	 */
    @RequestMapping("myToDo")
    public String myToDo() {
        try {
        	//获取登录用户
        	SysUser user = SysUtil.sysUser(request, response);
    		String orgId = user.getOrgId();
    		String depId = user.getDepId();
    		String roleType = user.getRole().getRoleType();
    		return jsonResult(homePageService.myToDo(orgId, depId, roleType, user.getId()));
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, e.getMessage());
        }
    }
    
    /**
	 * @Description: 首页-最新动态
	 * @param @return
	 * @return String
	 * @throws
	 */
    @RequestMapping("getLatestNews")
    public String getLatestNews() {
        try {
        	//获取登录用户
        	SysUser user = SysUtil.sysUser(request, response);
    		return jsonResult(homePageService.getLatestNews(user.getOrgId(), user.getDepId(), user.getRole().getRoleType(), user.getId()));
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, e.getMessage());
        }
    }
	
	/**
	 * @Description: 首页近一个月的转出、转入统计
	 * @param @return
	 * @return String
	 * @throws
	 */
    @RequestMapping("statsHomeTurnAndRotray")
    public String statsHomeTurnAndRotray() {
        try {
        	//获取登录用户的机构
        	SysUser user = SysUtil.sysUser(request, response);
    		String orgId = user.getOrgId();
    		String depId = user.getDepId();
    		return jsonResult(homePageService.queryHomeTurnStats(orgId, depId));
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, e.getMessage());
        }
    }
    

}
