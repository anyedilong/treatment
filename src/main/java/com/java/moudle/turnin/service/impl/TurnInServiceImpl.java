package com.java.moudle.turnin.service.impl;

import com.java.moudle.system.dao.FileUplodDao;
import com.java.moudle.system.dao.SysDepDao;
import com.java.moudle.system.dao.SysOrgDao;
import com.java.moudle.turnin.dao.TurnInDao;
import com.java.moudle.turnin.domain.TmTurnIn;
import com.java.moudle.turnin.service.TurnInService;
import com.java.moudle.turnout.dao.TurnOutDao;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.until.StringUtils;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * <p>Title: TurnInServiceImpl.java</p>
 * <p>Description : 转入管理</p>
 * <p>Copyright: Copyright (c) 2020</p>
 * @author : 皮雪平
 * @date : 2020/1/7 9:07
 * @version : V1.0.0
 */
@Named
@Transactional(readOnly=false)
public class TurnInServiceImpl implements TurnInService {

    @Value("${ftpUrl}")
    private String ftpUrl;
    @Value("${ftpPath}")
    private String ftpPath;
    @Inject
    private FileUplodDao fileUplodDao;
    @Inject
    private TurnInDao turnInDao;
    @Inject
    private TurnOutDao turnOutDao;
    @Inject
    private SysOrgDao orgDao;
    @Inject
    private SysDepDao depDao;
    /**
     * @Title : 添加
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/7 10:35
     * @param :
     * @return : 
     * @throws
     */
    public void saveTurnIn(TmTurnIn in) {
        if(StringUtils.isNull(in.getId())) {
            in.setId(UUIDUtil.getUUID());
        }
        in.setCreateTime(new Date());
        turnInDao.save(in);
    }
    /**
     * @Title : 查询转入列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/7 9:45 
     * @param : 
     * @return : 
     * @throws
     */
    public void getTurnInList(TurnOutDto out, PageModel page) {
        turnInDao.getTurnInList(out,page);
    }
    
    /**
     * @Title : 查询详情
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/7 9:46 
     * @param :
     * @return : 
     * @throws
     */
    public TmTurnIn getTurnInDetail(String id) {
        TmTurnIn in =  turnInDao.get(id);
        in.setImageList(fileUplodDao.getListByTurnId(in.getOutId()));//附件列表
        in.setOrgName(orgDao.queryOrgNameByID(in.getOrgId()));//所在机构名称
        in.setDepName(depDao.queryDepNameByID(in.getDepId()));//所在科室名称
        in.setAccOrgName(orgDao.queryOrgNameByID(in.getAccOrgId()));//接诊机构名称
        in.setAccDepName(depDao.queryDepNameByID(in.getAccDepId()));//接诊科室名称
        
        //获取当前机构的所有上级医院
        List<TurnOutDto> outList = new ArrayList<>();
        //TurnOutDto historyTurnOut = turnOutDao.getHistoryTurnOut(in.getSfzh(), in.getOrgId(), in.getAccOrgId(), in.getOutId());
        this.getDownOutTurns(in.getSfzh(), in.getOrgId(), outList, in.getCreateTime());
        in.setHistoryTurnOut(outList);//历史转诊信息
        return in;
    }
    
    private void getDownOutTurns(String sfzh, String orgId, List<TurnOutDto> outList, Date createTime){
    	List<String> downOrgIds = orgDao.getUpOrgIdByOrgId(orgId);
    	if(downOrgIds != null && downOrgIds.size() > 0) {
    		TurnOutDto out = turnOutDao.getHistoryTurnOut(sfzh, downOrgIds, orgId, "", createTime);
        	if(out != null && out.getId() != null) {
        		outList.add(out);
        		this.getDownOutTurns(sfzh, out.getOrgId(), outList, createTime);
        	}
    	}
    }
    
    public TmTurnIn getTurnInOnly(String id) {
        return turnInDao.get(id);
    }
    
    /**
     * @Title : 审核
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/7 9:47 
     * @param : 
     * @return : 
     * @throws
     */
    public void updateTurnInStatus(String audit,String reason,String id, 
    		String userId, String name, String depId){
        //status状态 0、待审核 1、通过 2、退回 3、接收
        String status = "0";
        //status状态 0、待审核 1、审核中 2、通过 3、退回 4、撤回
        String outStatus = "0";
        TmTurnIn tmTurnIn = turnInDao.get(id);
        if(audit.equals("1")){//1 通过
            status = "1";
            outStatus = "2";//通过
            //添加接收科室
            turnInDao.updateTurnInDepId(id, depId);
            turnOutDao.updateTurnOutDepId(tmTurnIn.getOutId(), depId);
        }else if(audit.equals("2")){//退回
            status = "2";//退回
            if("1".equals(tmTurnIn.getAuditType())) {
            	outStatus = "5";//驳回
            }else {
            	outStatus = "3";//退回
            }
        }
        turnInDao.updateTurnInStatus(status, userId, new Date(), reason, id);

        //转入申请审核  同时修改转出申请的审核状态
        turnOutDao.updateTurnOutStatus(outStatus, tmTurnIn.getOutId(), userId, new Date(), "");
    }
    /**
     * @Title : 接收
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/7 9:47 
     * @param :
     * @return : 
     * @throws
     */
    public void receiveTurnIn(String id,String userId, String name) {
        TmTurnIn in = turnInDao.get(id);
        in.setAccDocId(userId);
        in.setAccDocName(name);
        in.setUpdateTime(new Date());
        in.setUpdateUser(userId);
        in.setStatus("3");
        turnInDao.save(in);
        
        String outId =  turnInDao.get(id).getOutId();
        turnOutDao.updateTurnOutStatus("6", outId, userId, new Date(), name);
    }
    
}
