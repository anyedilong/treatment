package com.java.moudle.system.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.moudle.common.controller.BaseController;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.domain.TmClinModel;
import com.java.moudle.system.service.SysClinModelService;
import com.java.until.StringUntil;
import com.java.until.SysUtil;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

/**
 * <p>Title: SysUserController.java</p>
 * <p>Description : 临床信息模版管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : tz
 * @date : 2020/1/2 9:01
 * @version : V1.0.0
 */

@RestController
@RequestMapping("${trepath}/sys/clin")
public class SysClinModelController  extends BaseController {
    
	@Inject
    private SysClinModelService clinModelService;

	/**
     * @Description: 查询模版
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("getClinModelPage")
    public String getClinModelPage(TmClinModel info, PageModel page){
    	try{
    		SysUser user = SysUtil.sysUser(request, response);
    		info.setOrgId(user.getOrgId());
    		info.setDepId(user.getDepId());
    		clinModelService.getClinModelPage(info, page);
    		return jsonResult(page);
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 保存模版
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("save")
    public String save(TmClinModel info){
    	try{
    		if(StringUntil.isNull(info.getId())) {
    			info.setId(UUIDUtil.getUUID());
    			SysUser user = SysUtil.sysUser(request, response);
        		info.setOrgId(user.getOrgId());
        		info.setDepId(user.getDepId());
    		}
    		int count = clinModelService.queryInfoByTitle(info.getTitle(), info.getOrgId());
    		if(count == 0) {
    			clinModelService.save(info);
    		}else {
    			return jsonResult("", 10000, "模板的标题重复，请修改");
    		}
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
    
    /**
     * @Description: 删除模版
     * @param @return
     * @return JsonResult
     * @throws
     */
    @RequestMapping("delete")
    public String delete(String id){
    	try{
    		if(StringUntil.isNull(id)) {
    			return jsonResult(null, 10000, "请选择删除的模版");
    		}
    		clinModelService.delete(id);
    		return jsonResult();
    	}catch (Exception e){
    		e.printStackTrace();
    		return jsonResult(null, -1, "系统错误");
	    }
	}
}
