package com.java.config.filter;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.java.moudle.system.domain.SysDep;
import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.system.domain.SysTurnHis;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.service.SysDepService;
import com.java.moudle.system.service.SysOrgService;
import com.java.moudle.system.service.SysTurnHisService;
import com.java.moudle.trunback.domain.TmTurnRotary;
import com.java.moudle.trunback.service.TurnRotaryService;
import com.java.moudle.turnin.domain.TmTurnIn;
import com.java.moudle.turnin.service.TurnInService;
import com.java.moudle.turnout.domain.TmTurnOut;
import com.java.moudle.turnout.dto.TurnOutAuditDto;
import com.java.moudle.turnout.service.TurnOutService;
import com.java.until.SysUtil;

@Aspect
@Component
public class BillHisControllerAop {
	
	@Inject
	private SysDepService depService;
	@Inject
	private SysOrgService orgService;
	@Inject
	private SysTurnHisService turnHisService;
	@Inject
	private TurnRotaryService turnRotaryService;
	@Inject
	private TurnOutService turnOutService;
	@Inject
	private TurnInService turnInService;

	@Around("execution(* com.java.moudle.trunback.controller.TurnRotaryController.*(..)) "
			+ "or execution(* com.java.moudle.turnin.controller.TurnInController.*(..)) "
			+ "or execution(* com.java.moudle.turnout.controller.TurnOutController.*(..))")
	public Object process(ProceedingJoinPoint point) throws Throwable {
		
		// 访问目标方法的参数：
		Object[] args = point.getArgs();
		String modthName = point.getSignature().getName();
		
		
		// 用改变后的参数执行目标方法
		Object proceed = point.proceed(args);
		String returnStr = proceed.toString();
		returnStr = returnStr.substring(returnStr.indexOf("(")+1, returnStr.length()-1);
		JSONObject returnJson = JSONObject.parseObject(returnStr);
		String retCode = returnJson.getString("retCode");
		if("0".equals(retCode)) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
			SysUser user = SysUtil.sysUser(request, response);
			//回转controller类
			if("saveTurnRotary".equals(modthName)) {
				TmTurnRotary tmTurnRotaryInfo = (TmTurnRotary)args[0];
				String turnId = returnJson.getString("data");
				//1.为开启审核 0.关闭审核
	    		//String flag = platformService.findList().get(0).getStatus();
	    		SysDep dep = depService.getDepDetail(user.getDepId());
	    		SysOrg org = orgService.getHosOrgDetail(tmTurnRotaryInfo.getOrgId());
	    		
	    		if("1".equals(tmTurnRotaryInfo.getAuditType())) {
	    			this.saveTurnHis(user.getUsername(), "您已提交"+tmTurnRotaryInfo.getName()+"的回转转诊申请，正在等待本院领导审核", 
		    				 "2", tmTurnRotaryInfo.getDepId(), tmTurnRotaryInfo.getOrgId(), turnId, tmTurnRotaryInfo.getSfzh(), tmTurnRotaryInfo.getDocId());
	    			this.saveTurnHis(user.getUsername(), dep.getDepName()+"的"+user.getName()+"提交了"+tmTurnRotaryInfo.getName()+"回转转诊申请，正在等待您的审核", 
		    				 "1", tmTurnRotaryInfo.getDepId(), tmTurnRotaryInfo.getOrgId(), turnId, tmTurnRotaryInfo.getSfzh(), "");
	    		}else {
	    			this.saveTurnHis(user.getUsername(), "您已提交"+tmTurnRotaryInfo.getName()+"的回转转诊申请，正在等待上级领导审核", 
		    				 "2", tmTurnRotaryInfo.getDepId(), tmTurnRotaryInfo.getOrgId(), turnId, tmTurnRotaryInfo.getSfzh(), tmTurnRotaryInfo.getDocId());
	    			this.saveTurnHis(user.getUsername(), org.getOrgName()+"的"+user.getName()+"提交了"+tmTurnRotaryInfo.getName()+"的回转转诊申请，正在等待您的审核", 
							 "1", tmTurnRotaryInfo.getAccDepId(), tmTurnRotaryInfo.getAccOrgId(), tmTurnRotaryInfo.getSfzh(), tmTurnRotaryInfo.getId(), "");
	    		}
			}else if("auditTurnRotary".equals(modthName)) {
				String satatus = (String)args[1];
				String type = (String)args[3];
				TmTurnRotary info = turnRotaryService.get(args[0].toString());
				SysOrg org = orgService.getHosOrgDetail(info.getOrgId());
				SysOrg accOrg = orgService.getHosOrgDetail(info.getAccOrgId());
				SysDep dep = depService.getDepDetail(info.getDepId());
				if("in".equals(type)) {
					if("1".equals(satatus)) {
	    				//转出状态为审核通过时，转入状态为待审核
						this.saveTurnHis(user.getUsername(), info.getName()+"回转转诊申请本院领导已审核通过，正在等待"+accOrg.getOrgName()+"院领导的审核", 
								 "2", info.getDepId(), user.getOrgId(), info.getId(), info.getSfzh(), info.getDocId());
						this.saveTurnHis(user.getUsername(), "您已审核通过"+info.getName()+"的回转转诊申请，正在等待"+accOrg.getOrgName()+"院领导的审核", 
									 "1", info.getDepId(), user.getOrgId(), info.getId(), info.getSfzh(), "");
						this.saveTurnHis(user.getUsername(), org.getOrgName()+"提交了"+info.getName()+"的回转转诊申请，正在等待您的审核", 
								 "1", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), "");
	    			} else if ("3".equals(satatus)) {
	    				//院领导退回操作
	    				this.saveTurnHis(user.getUsername(), info.getName()+"的回转转诊申请本院领导已退回，退回原因请到回转转诊列表中查看", 
	    							 "2", info.getDepId(), user.getOrgId(), info.getId(), info.getSfzh(), info.getDocId());
	    				this.saveTurnHis(user.getUsername(), "您已退回"+info.getName()+"的回转转诊申请", 
   							 "1", info.getDepId(), user.getOrgId(), info.getId(), info.getSfzh(), "");
	    			}else if ("4".equals(satatus)) {
	    				//院医生撤回操作
	    				//this.saveTurnHis(user.getUsername(), info.getName()+"的回转转出申请院医生已撤回，退回原因请到回转转出界面查看", new Date(), "2");
	    				//删除给领导的审核提醒
	    				turnHisService.deleteByTurnId(info.getId(), info.getDepId(), info.getOrgId(), info.getSfzh(), "1");
	    				turnHisService.deleteByTurnId(info.getId(), info.getDepId(), info.getOrgId(), info.getSfzh(), "2");
	    			}
				}else if("out".equals(type)) {
					 if("1".equals(satatus)) {
						this.saveTurnHis(user.getUsername(), info.getName()+"的回转转诊申请接诊医院院领导已同意，正在等待接诊医生接收", 
								 "2", info.getDepId(), info.getOrgId(), info.getId(), info.getSfzh(), info.getDocId());
						//auditType 院内审核是否关闭 0 关闭 1.开启
						if("1".equals(info.getAuditType())) {
							this.saveTurnHis(user.getUsername(), dep.getDepName()+"的"+info.getName()+"的回转转诊申请接诊医院院领导已审核通过，正在等待接诊医生接收", 
									 "1", info.getDepId(), info.getOrgId(), info.getId(), info.getSfzh(), "");
						}
						
						this.saveTurnHis(user.getUsername(), "您已审核通过"+org.getOrgName()+info.getDocName()+"医生提交的双向转诊申请，正在等待接诊医生接收", 
								 "1", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), "");
						this.saveTurnHis(user.getUsername(), "您有一个双向转诊（转出）申请需要接收，请及时接收", 
								 "2", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), info.getAccDocId());
					}else if ("2".equals(satatus)) {
	    				//院领导驳回操作
    					this.saveTurnHis(user.getUsername(), info.getName()+"的回转转诊申请被接诊医院"+accOrg.getOrgName()+"驳回，退回原因请到回转转诊列表中查看", 
    							"2", info.getDepId(), info.getOrgId(), info.getId(), info.getSfzh(), info.getDocId());
    					 //auditType 院内审核是否关闭 0 关闭 1.开启
	    				if("1".equals(info.getAuditType())) {
	    					this.saveTurnHis(user.getUsername(), info.getName()+"的回转转诊申请被接诊医院"+accOrg.getOrgName()+"驳回", 
		    						 "1", info.getDepId(), info.getOrgId(), info.getId(), info.getSfzh(), "");
	    				}
	    				
	    				this.saveTurnHis(user.getUsername(), "您驳回"+org.getOrgName()+info.getName()+"的回转转诊申请", 
	    						 "1", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), "");
	    			}else if ("3".equals(satatus)) {
	    				//院医生接收操作
	    				this.saveTurnHis(user.getUsername(), info.getName()+"的回转转诊申请接诊医生已接收", 
	    						 "2", info.getDepId(), info.getOrgId(), info.getId(), info.getSfzh(), info.getDocId());
	    				//auditType 院内审核是否关闭 0 关闭 1.开启
	    				if("1".equals(info.getAuditType())) {
	    					this.saveTurnHis(user.getUsername(), dep.getDepName()+"的"+info.getName()+"的回转转诊申请接诊医生已接收", 
									 "1", info.getDepId(), info.getOrgId(), info.getId(), info.getSfzh(), "");
	    				}
	    				
	    				this.saveTurnHis(user.getUsername(), org.getOrgName()+"的"+info.getName()+"的回转转诊申请接诊医生已接收", 
								 "1", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), "");
	    			}
				}
			}else if("addTurnOut".equals(modthName)  || "updateTurnOut".equals(modthName)) {//添加转出申请
				String turnId = returnJson.getString("data");
				TmTurnOut out = (TmTurnOut) args[0];
				//1.为开启审核 0.关闭审核
				//String flag = platformService.findList().get(0).getStatus();
				SysDep dep = depService.getDepDetail(user.getDepId());
				SysOrg org = orgService.getHosOrgDetail(out.getOrgId());
				
				//auditType 院内审核是否关闭 0 关闭 1.开启
				if("1".equals(out.getAuditType())) {
					this.saveTurnHis(user.getUsername(), "您已提交"+out.getName()+"的转出申请，正在等待本院领导审核", 
		    				 "2", out.getDepId(), out.getOrgId(), turnId, out.getSfzh(), out.getDocId());
					this.saveTurnHis(user.getUsername(), dep.getDepName()+"的"+user.getName()+"提交了"+out.getName()+"转出申请，正在等待您的审核",
							 "1", out.getDepId(), out.getOrgId(), turnId, out.getSfzh(), "");
				}else {
					this.saveTurnHis(user.getUsername(), "您已提交"+out.getName()+"的转出申请，正在等待上级领导审核", 
		    				 "2", out.getDepId(), out.getOrgId(), turnId, out.getSfzh(), out.getDocId());
					this.saveTurnHis(user.getUsername(), org.getOrgName()+"的"+user.getName()+"提交了"+out.getName()+"转入申请，正在等待您的审核",
							 "1", out.getAccDepId(), out.getAccOrgId(), turnId, out.getSfzh(), "");
				}
			}else if("auditTurnOut".equals(modthName)){//转出申请审核
				TurnOutAuditDto out = (TurnOutAuditDto) args[0];
				String type = out.getAudit();
				TmTurnOut info = turnOutService.getTurnOutOnly(out.getId());
				SysOrg org = orgService.getHosOrgDetail(info.getOrgId());
				SysOrg accOrg = orgService.getHosOrgDetail(info.getAccOrgId());
				if("1".equals(type)) {
					//转出状态为审核通过时，转入状态为待审核
					this.saveTurnHis(user.getUsername(), info.getName()+"转出申请本院领导已同意，正在等待"+accOrg.getOrgName()+"院领导的审核", 
							"2", info.getDepId(), user.getOrgId(), info.getId(), info.getSfzh(), info.getDocId());
					this.saveTurnHis(user.getUsername(), "您已审核通过"+info.getName()+"的转出申请，正在等待"+accOrg.getOrgName()+"院领导的审核", 
								 "1", user.getDepId(), user.getOrgId(), info.getId(), info.getSfzh(), "");
					this.saveTurnHis(user.getUsername(), org.getOrgName()+"提交了"+info.getName()+"的转入申请，正在等待您的审核", 
							 "1", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), "");
				} else if ("2".equals(type)) {
					//院领导退回操作
					this.saveTurnHis(user.getUsername(), info.getName()+"的转出申请本院领导已退回，退回原因请到转出界面查看",
							 "2", info.getDepId(), user.getOrgId(), info.getId(), info.getSfzh(), info.getDocId());
					this.saveTurnHis(user.getUsername(), "您已退回"+info.getName()+"的转出申请",
							 "1", info.getDepId(), user.getOrgId(), info.getId(), info.getSfzh(), "");
				}
			}else if("auditTurnIn".equals(modthName)){//转入申请审核
				TurnOutAuditDto out = (TurnOutAuditDto) args[0];
				String type = out.getAudit();
				TmTurnIn info = turnInService.getTurnInOnly(out.getId());
				SysOrg accOrg = orgService.getHosOrgDetail(info.getAccOrgId());
				SysOrg org = orgService.getHosOrgDetail(info.getOrgId());
				SysDep dep = depService.getDepDetail(info.getDepId());
				if("1".equals(type)) {//院领导同意
					this.saveTurnHis(user.getUsername(), info.getName()+"的转出申请接诊医院院领导已同意，正在等待接诊医生接收",
							 "2", info.getDepId(), info.getOrgId(), info.getOutId(), info.getSfzh(), info.getDocId());
					//auditType 院内审核是否关闭 0 关闭 1.开启
					if("1".equals(info.getAuditType())) {
						this.saveTurnHis(user.getUsername(), dep.getDepName()+"的"+info.getName()+"的转出申请接诊医院院领导已审核通过，正在等待接诊医生接收",
								 "1", info.getDepId(), info.getOrgId(), info.getOutId(), info.getSfzh(), "");
					}
					
					this.saveTurnHis(user.getUsername(), "您已审核通过"+org.getOrgName()+info.getDocName()+"医生提交的转入申请，正在等待接诊医生接收", 
							 "1", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), "");
					this.saveTurnHis(user.getUsername(), "您有一个转入申请需要接收，请及时接收", 
							 "2", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), info.getAccDocId());
				}else if ("2".equals(type)) {
					//院领导驳回操作
					this.saveTurnHis(user.getUsername(), info.getName()+"的转出申请被接诊医院"+accOrg.getOrgName()+"驳回，驳回原因请到转出界面查看",
							 "2", info.getDepId(), info.getOrgId(), info.getOutId(), info.getSfzh(), info.getDocId());
					//auditType 院内审核是否关闭 0 关闭 1.开启
					if("1".equals(info.getAuditType())) {
						this.saveTurnHis(user.getUsername(), info.getName()+"的转出申请被接诊医院"+accOrg.getOrgName()+"驳回",
								 "1", info.getDepId(), info.getOrgId(), info.getOutId(), info.getSfzh(), "");
					}
					
					this.saveTurnHis(user.getUsername(), "您已驳回"+org.getOrgName()+info.getName()+"的转入申请",
							 "1", info.getAccDepId(), info.getAccOrgId(), info.getOutId(), info.getSfzh(), "");
				}
			}else if("receiveTurnIn".equals(modthName)){//接收转入申请审核
				TurnOutAuditDto out = (TurnOutAuditDto) args[0];
				TmTurnIn info = turnInService.getTurnInOnly(out.getId());
				SysOrg org = orgService.getHosOrgDetail(info.getOrgId());
				SysDep dep = depService.getDepDetail(info.getDepId());
				//院医生接收操作
				this.saveTurnHis(user.getUsername(), info.getName()+"的转出申请接诊医生已接收",
						 "2", info.getDepId(), info.getOrgId(), info.getOutId(), info.getSfzh(), info.getDocId());
				//auditType 院内审核是否关闭 0 关闭 1.开启
				if("1".equals(info.getAuditType())) {
					this.saveTurnHis(user.getUsername(), dep.getDepName()+"的"+info.getName()+"的转出申请接诊医生已接收", 
							 "1", info.getDepId(), info.getOrgId(), info.getId(), info.getSfzh(), "");
				}
				this.saveTurnHis(user.getUsername(), org.getOrgName()+"的"+info.getName()+"的转入申请接诊医生已接收", 
						 "1", info.getAccDepId(), info.getAccOrgId(), info.getId(), info.getSfzh(), "");
				//您有一个双向转诊（转出）申请需要接收，请及时接收。
			}else if("drawTurnOut".equals(modthName)) {
				TurnOutAuditDto out = (TurnOutAuditDto) args[0];
				TmTurnOut info = turnOutService.getTurnOutOnly(out.getId());
				//院医生撤回操作
				//this.saveTurnHis(user.getUsername(), info.getName()+"的回转转出申请院医生已撤回，退回原因请到回转转出界面查看", new Date(), "2");
				//删除给领导的审核提醒
				turnHisService.deleteByTurnId(info.getId(), info.getDepId(), info.getOrgId(), info.getSfzh(), "1");
				turnHisService.deleteByTurnId(info.getId(), info.getDepId(), info.getOrgId(), info.getSfzh(), "2");
			}
		}
		return proceed;
	}

	private void saveTurnHis(String username, String desc, String type, String depId, String orgId, String turnId, String sfzh, String accDocId) {
		SysTurnHis trunHis1 = new SysTurnHis(username, desc, new Date(), type, depId, orgId, turnId, sfzh, accDocId);
		turnHisService.save(trunHis1);
	}
}
