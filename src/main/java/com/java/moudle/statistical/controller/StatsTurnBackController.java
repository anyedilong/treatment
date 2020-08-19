package com.java.moudle.statistical.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.statistical.dto.StatsTurnBackDto;
import com.java.moudle.statistical.service.StatsTurnBackService;
import com.java.moudle.system.domain.SysUser;
import com.java.until.SysUtil;

/**
 * @ClassName: StatsTurnBackController
 * @Description: 医院转出、转入统计
 * @author Administrator
 * @date 2020年1月9日
 */
@RestController
@RequestMapping("${trepath}/stats")
public class StatsTurnBackController  extends BaseController {
    
	@Inject
    private StatsTurnBackService turnBackService;

	/**
	 * @Description: 医院转出、转入统计
	 * @param @param info
	 * @param @return
	 * @return String
	 * @throws
	 */
    @RequestMapping("statsTurnAndRotray")
    public String statTurnInAndRotrayTime(StatsTurnBackDto info) {
        try {
        	//获取登录用户的机构
        	SysUser user = SysUtil.sysUser(request, response);
    		info.setOrgId(user.getOrgId());
    		//info.setDepId(user.getDepId());
    		return jsonResult(turnBackService.queryTurnBackOutStats(info));
        }catch(Exception e) {
            e.printStackTrace();
            return jsonResult("", -1, e.getMessage());
        }
    }

}
