package com.java.moudle.trunback.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.system.dao.FileUplodDao;
import com.java.moudle.system.dao.SysPlatformDao;
import com.java.moudle.system.dao.SysUserDao;
import com.java.moudle.system.domain.SysOrg;
import com.java.moudle.system.domain.SysUser;
import com.java.moudle.system.domain.TmFileUpload;
import com.java.moudle.trunback.dao.TurnRotaryDao;
import com.java.moudle.trunback.domain.TmTurnRotary;
import com.java.moudle.trunback.dto.TurnRotaryFlowDto;
import com.java.moudle.trunback.service.TurnRotaryService;
import com.java.until.StringUntil;
import com.java.until.StringUtils;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;


@Named
@Transactional(readOnly=false)
public class TurnRotaryServiceImpl extends BaseServiceImpl<TurnRotaryDao, TmTurnRotary>  implements TurnRotaryService {

	@Inject
	private SysPlatformDao platformDao;
	@Inject
	private FileUplodDao fileUplodDao;
	
	@Override
	public void getOutTurnRotaryPage(TmTurnRotary info, PageModel page) {
		dao.getOutTurnRotaryPage(info, page);
	}
	
	@Override
	public void getInTurnRotaryPage(TmTurnRotary info, PageModel page) {
		dao.getInTurnRotaryPage(info, page);
	}

	@Override
	public TmTurnRotary showTurnRotary(String id) {
		TmTurnRotary info = dao.showTurnRotary(id);
		if(info != null) {
			List<TmFileUpload> filelist = fileUplodDao.getListByTurnId(info.getId());
			if(filelist == null || filelist.size() == 0) { 
				filelist = new ArrayList<>();
			} 
			info.setFilelist(filelist);
		}
		return info;
	}

	@Override
	public void saveTurnBackInfo(TmTurnRotary info, String userId) {
		String flag = "";
		if(StringUntil.isNull(info.getId())) {
			info.setId(UUIDUtil.getUUID());
			info.setCreateTime(new Date());
			info.setCreateUser(userId);
			//1.为开启审核 0.关闭审核
			flag = platformDao.findList().get(0).getStatus();
			
		}else {
			TmTurnRotary tmTurnRotary = dao.get(info.getId());
			info.setCreateTime(tmTurnRotary.getCreateTime());
			info.setCreateUser(tmTurnRotary.getCreateUser());
			info.setUpdateTime(new Date());
			info.setUpdateUser(userId);
			//1.为开启审核 0.关闭审核
			flag = tmTurnRotary.getAuditType();
		}
		info.setDeleteFlg("0");
		if("1".equals(flag)) {
			info.setOutStatus("0");
			info.setAuditType("1");
		}else if("0".equals(flag)) {
			info.setOutStatus("1");
			info.setInStatus("0");
			info.setAuditType("0");
			info.setInCreateTime(new Date());
			info.setInCreateUser(userId);
		}
		
		fileUplodDao.deleteFileByTurnID(info.getId());
		String flieStr = info.getFilelistStr();
        if(!StringUtils.isNull(flieStr)){
        	List<TmFileUpload> filelist = JSONObject.parseArray(flieStr, TmFileUpload.class);
            for(int i = 0; i < filelist.size() ;i++){
            	filelist.get(i).setId(UUIDUtil.getUUID());
            	filelist.get(i).setTurnId(info.getId());
            	filelist.get(i).setUploadTime(new Date());
            	filelist.get(i).setUploadUser(info.getCreateUser());
            	fileUplodDao.save(filelist.get(i));
            }
        }
		dao.save(info);
	}

	@Override
	public List<SysOrg> getRotaryHospitalList(String orgId, String depId, String roleType, String type) {
		List<SysOrg> list = new ArrayList<>();
		if("in".equals(type)) {
			//转入菜单-回转的医院
			list = dao.getInRotaryHospitalList(orgId, depId, roleType);
		}else if("out".equals(type)) {
			//转出菜单-回转的医院
			list = dao.getOutRotaryHospitalList(orgId, depId, roleType);
		}
		
		return list;
	}

	@Override
	public void auditRotary(String id, String status, String reason, String type, String userId, String name, String depId) {
		TmTurnRotary info = dao.get(id);
		if("in".equals(type)) {
			info.setOutStatus(status);
			info.setOutAuditUser(userId);
			info.setOutAuditTime(new Date());
			info.setOutAuditReason(reason);
			if("1".equals(status)) {
				//转出状态为审核通过时，转入状态为待审核
				info.setInStatus("0");
				info.setInCreateTime(new Date());
				info.setInCreateUser(userId);
			} 
			info.setUpdateTime(new Date());
			info.setUpdateUser(userId);
		}else if("out".equals(type)){
			info.setInStatus(status);
			if(!"3".equals(status)) {
				info.setInAuditUser(userId);
    			info.setInAuditTime(new Date());
    			info.setInAuditReason(reason);
    			if("2".equals(status)) {
    				//转入状态为驳回时，转出状态为驳回
    				if("1".equals(info.getAuditType())) {
    					info.setOutStatus("5");
    				}else {
    					info.setOutStatus("3");
    				}
    			} else if ("1".equals(status)) {
    				//转入状态为审核通过时，转出状态为通过
    				info.setOutStatus("2");
    				info.setAccDepId(depId);
    			}
			}else {
				info.setOutStatus("6");
				info.setAccDocId(userId);
				info.setAccDocName(name);
			}
			info.setUpdateTime(new Date());
			info.setUpdateUser(userId);
		}
		dao.save(info);
	}

	@Inject
	private SysUserDao userDao;
	
	@Override
	public List<TurnRotaryFlowDto> getTurnRotaryFlow(String id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TurnRotaryFlowDto> list = new ArrayList<>();
		TmTurnRotary info = dao.get(id);
		//回转转出状态：4.撤回
		if(!"4".equals(info.getOutStatus())) {
			SysUser createUser = userDao.queryInfoByCon(info.getCreateUser(), "");
			coverFlowModel(sdf.format(info.getCreateTime()), createUser.getOrgName()+createUser.getDepName()+createUser.getName()+"提交了转诊申请", "", list);
		}
		//回转转出状态：3退回
		if("3".equals(info.getOutStatus())) {
			//回转转入状态：2 驳回
			//if("2".equals(info.getInStatus())) {
			//	SysUser byUser = userDao.queryInfoByCon(info.getOutAuditUser(), "");
			//	coverFlowModel(sdf.format(info.getCreateTime()), byUser.getOrgName()+"院领导"+byUser.getName()+"审核通过了您的转诊申请", info.getOutAuditReason(), list);
				//SysUser xyUser = userDao.queryInfoByCon(info.getInAuditUser(), "");
				//coverFlowModel(sdf.format(info.getCreateTime()), xyUser.getOrgName()+"院领导"+xyUser.getName()+"驳回了您的转诊申请", info.getInAuditReason(), list);
			//}
			if("1".equals(info.getAuditType())) {
				SysUser byUser = userDao.queryInfoByCon(info.getOutAuditUser(), "");
				coverFlowModel(sdf.format(info.getOutAuditTime()), byUser.getOrgName()+"院领导"+byUser.getName()+"退回了您的转诊申请", info.getOutAuditReason(), list);
			}else {
				SysUser xyUser = userDao.queryInfoByCon(info.getInAuditUser(), "");
				coverFlowModel(sdf.format(info.getInAuditTime()), xyUser.getOrgName()+"院领导"+xyUser.getName()+"驳回了您的转诊申请", info.getInAuditReason(), list);
			}
		}
		//回转转入状态：0.待审核
		if("1".equals(info.getOutStatus()) && "1".equals(info.getAuditType())) {
			SysUser byUser = userDao.queryInfoByCon(info.getOutAuditUser(), "");
			coverFlowModel(sdf.format(info.getOutAuditTime()), byUser.getOrgName()+"院领导"+byUser.getName()+"审核通过了您的转诊申请", info.getOutAuditReason(), list);
		}
		//回转转出状态：5.驳回
		if("5".equals(info.getOutStatus())) {
			if("1".equals(info.getAuditType())) {
				SysUser byUser = userDao.queryInfoByCon(info.getOutAuditUser(), "");
				coverFlowModel(sdf.format(info.getOutAuditTime()), byUser.getOrgName()+"院领导"+byUser.getName()+"审核通过了您的转诊申请", info.getOutAuditReason(), list);
			}
			SysUser xyUser = userDao.queryInfoByCon(info.getInAuditUser(), "");
			coverFlowModel(sdf.format(info.getInAuditTime()), xyUser.getOrgName()+"院领导"+xyUser.getName()+"驳回了您的转诊申请", info.getInAuditReason(), list);
		}
		//回转转入状态：1.审核通过
		if("1".equals(info.getInStatus())) {
			if("1".equals(info.getAuditType())) {
				SysUser byUser = userDao.queryInfoByCon(info.getOutAuditUser(), "");
				coverFlowModel(sdf.format(info.getOutAuditTime()), byUser.getOrgName()+"院领导"+byUser.getName()+"审核通过了您的转诊申请", info.getOutAuditReason(), list);
			}
			SysUser xyUser = userDao.queryInfoByCon(info.getInAuditUser(), "");
			coverFlowModel(sdf.format(info.getInAuditTime()), xyUser.getOrgName()+"院领导"+xyUser.getName()+"审核通过了您的转诊申请", info.getInAuditReason(), list);
		}
		//回转转入状态：3.接收
		if("3".equals(info.getInStatus())) {
			if("1".equals(info.getAuditType())) {
				SysUser byUser = userDao.queryInfoByCon(info.getOutAuditUser(), "");
				coverFlowModel(sdf.format(info.getOutAuditTime()), byUser.getOrgName()+"院领导"+byUser.getName()+"审核通过了您的转诊申请", info.getOutAuditReason(), list);
			}
			SysUser xyUser = userDao.queryInfoByCon(info.getInAuditUser(), "");
			coverFlowModel(sdf.format(info.getInAuditTime()), xyUser.getOrgName()+"院领导"+xyUser.getName()+"审核通过了您的转诊申请", info.getInAuditReason(), list);
			SysUser accUser = userDao.queryInfoByCon(info.getUpdateUser(), "");
			coverFlowModel(sdf.format(info.getUpdateTime()), accUser.getOrgName()+"接诊医生"+accUser.getName()+"接收了您的转诊申请", "", list);
		}
		return list;
	}
	
	private void coverFlowModel(String createDate, String descrip, String reason, List<TurnRotaryFlowDto> list) {
		TurnRotaryFlowDto flow = new TurnRotaryFlowDto(createDate, descrip, reason);
		list.add(flow);
	}

	@Override
	public int getCountByToDay(String sfzh) {
		return dao.getCountByToDay(sfzh);
	}

	@Override
	public void deleteTurnRotary(String id) {
		dao.deleteTurnRotary(id);
	}
   
}
