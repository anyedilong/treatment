package com.java.moudle.turnout.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.java.moudle.system.dao.FileUplodDao;
import com.java.moudle.system.dao.SysDepDao;
import com.java.moudle.system.dao.SysOrgDao;
import com.java.moudle.system.domain.TmFileUpload;
import com.java.moudle.turnin.dao.TurnInDao;
import com.java.moudle.turnin.domain.TmTurnIn;
import com.java.moudle.turnout.dao.TurnOutDao;
import com.java.moudle.turnout.domain.TmTurnOut;
import com.java.moudle.turnout.dto.DiseaseDto;
import com.java.moudle.turnout.dto.TurnAuditProDto;
import com.java.moudle.turnout.dto.TurnOutDto;
import com.java.moudle.turnout.service.TurnOutService;
import com.java.until.StringUtils;
import com.java.until.ToJavaUtils;
import com.java.until.UUIDUtil;
import com.java.until.dba.PageModel;

/**
 * <p>Title: TurnOutServiceImpl.java</p>  
 * <p>Description : 转出申请</p>
 * <p>Copyright: Copyright (c) 2020</p> 
 * @author : 皮雪平
 * @date : 2020/1/3 11:13 
 * @version : V1.0.0
 */
@Named
@Transactional(readOnly=false)
public class TurnOutServiceImpl implements TurnOutService {

    @Value("${ftpUrl}")
    private String ftpUrl;
    @Value("${ftpPath}")
    private String ftpPath;
    @Inject
    private TurnOutDao turnOutDao;
    @Inject
    private FileUplodDao fileUplodDao;
    @Inject
    private TurnInDao turnInDao;
    @Inject
    private SysOrgDao orgDao;
    @Inject
    private SysDepDao depDao;

    /**
     * @Title : 添加转出申请
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/3 11:13 
     * @param : 
     * @return : 
     * @throws
     */
    @Override
    public void saveTurnOut(TmTurnOut out) {
        if(StringUtils.isNull(out.getId())) {
            out.setId(UUIDUtil.getUUID());
        }
        //附件
        List<TmFileUpload> imageList = out.getImagelist();
        if(imageList != null && imageList.size() > 0) {
            for (TmFileUpload image : imageList) {
                image.setId(UUIDUtil.getUUID());
                image.setTurnId(out.getId());
                image.setUploadTime(new Date());
                image.setUploadUser(out.getCreateUser());
                fileUplodDao.save(image);
            }
        }
        out.setCreateTime(new Date());
        turnOutDao.save(out);
        if("1".equals(out.getStatus())) {
            TmTurnIn in = new TmTurnIn();
            in.setId(UUIDUtil.getUUID());
            ToJavaUtils.copyFields(out, in);
            in.setOutId(out.getId());
            in.setStatus("0");
            in.setCreateTime(new Date());
            in.setAuditType(out.getAuditType());
            turnInDao.save(in);
        }
    }
    /**
     * @Title : 修改转出申请
     * @Description :
     * @author : 皮雪平
     * @date : 2020/1/3 16:34
     * @param :
     * @return :
     * @throws
     */
    @Override
    public void updateTurnOut(TmTurnOut out) {
        fileUplodDao.deleteFileByTurnID(out.getId());//删除附件子表中的数据
        //附件
        List<TmFileUpload> imageList = out.getImagelist();
        if(imageList != null && imageList.size() > 0){
            for(TmFileUpload image : imageList){
                image.setId(UUIDUtil.getUUID());
                image.setTurnId(out.getId());
                image.setUploadTime(new Date());
                image.setUploadUser(out.getCreateUser());
                fileUplodDao.save(image);
            }
        }
        String flag =  turnOutDao.get(out.getId()).getAuditType();
        if("0".equals(flag)) {
        	out.setStatus("1");
        	out.setAuditType("0");
        }else {
        	out.setStatus("0");
        	out.setAuditType("1");
        }
        
        turnOutDao.save(out);
        if("1".equals(out.getStatus())) {
            TmTurnIn in = new TmTurnIn();
            //通过转出id查询是否存在转入信息
            TmTurnIn inTemp = turnInDao.queryInfoByOutId(out.getId());
            if(inTemp != null && !StringUtils.isNull(inTemp.getId())) {
            	in.setId(inTemp.getId());
            }else {
            	in.setId(UUIDUtil.getUUID());
            }
            ToJavaUtils.copyFields(out, in);
            in.setOutId(out.getId());
            in.setStatus("0");
            in.setCreateTime(new Date());
            in.setAuditType(out.getAuditType());
            turnInDao.save(in);
        }
    }

    /**
     * @Title : 删除
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 8:56 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void deleteTurnOut(String id) {
//        fileUplodDao.deleteFileByTurnID(id);
        turnOutDao.deleteTmTurnOut(id);
    }
    /**
     * @Title : 查询审核列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 9:04 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void getTurnOutList(TurnOutDto out, PageModel page) {
        turnOutDao.getTurnOutList(out, page);
    }

    /**
     * @Title : 根据id查询转出详情
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 8:55 
     * @param : 
     * @return : 
     * @throws
     */
    @Override
    public TmTurnOut getTurnOutDetail(String id) {
        TmTurnOut out =  turnOutDao.get(id);
        List<TmFileUpload> uploads = fileUplodDao.getListByTurnId(id);
        if(uploads != null && uploads.size() > 0) {
        	out.setImagelist(uploads);//附件列表
        }else {
        	out.setImagelist(new ArrayList<>());//附件列表
        }
        out.setOrgName(orgDao.queryOrgNameByID(out.getOrgId()));//所在机构名称
        out.setDepName(depDao.queryDepNameByID(out.getDepId()));//所在科室名称
        out.setAccOrgName(orgDao.queryOrgNameByID(out.getAccOrgId()));//接诊机构名称
        out.setAccDepName(depDao.queryDepNameByID(out.getAccDepId()));//接诊科室名称
        
        List<TurnAuditProDto> list = turnOutDao.getHistoryTurnOut(id);
        List<TurnAuditProDto> tempList = null;
        //由需求确认；当流程中退回时，如果有驳回的话，则不显示
        if(list != null && list.size() == 3 && "2".equals(list.get(1).getStatus())) {
        	tempList = list.subList(0, 2);
        }
        if(list != null && list.size() == 2 && !"2".equals(list.get(1).getType()) && "1".equals(list.get(1).getAuditType())) {
        	tempList = list.subList(0, 1);
        }
        out.setAuditList((tempList == null ? list : tempList));//审批进度
        return out;
    }

    public TmTurnOut getTurnOutOnly(String id) {
        TmTurnOut out =  turnOutDao.get(id);
        return out;
    }
    /**
     * @Title : 修改申请状态  审核
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 17:14 
     * @param : 
     * @return : 
     * @throws
     */
    public void updateTurnOutStatus(String audit,String reason,String id, String user ){
        //status状态 0、待审核 1、审核中 2、通过 3、退回 4、撤回
        String status = "0";
        if(audit.equals("1")){//1 通过
            status = "1";
            //审核通过自动生成转入记录  不需要再添加附件信息  直接通过id关联查询
            TmTurnOut out =  turnOutDao.get(id);
            TmTurnIn in = new TmTurnIn();
            //通过转出id查询是否存在转入信息
            TmTurnIn inTemp = turnInDao.queryInfoByOutId(id);
            if(inTemp != null && !StringUtils.isNull(inTemp.getId())) {
            	in.setId(inTemp.getId());
            }else {
            	in.setId(UUIDUtil.getUUID());
            }
            ToJavaUtils.copyFields(out, in);
            in.setOutId(id);
            in.setStatus("0");
            // in.setCreateTime(new Date());
            turnInDao.save(in);
        }else if(audit.equals("2")){//退回
            status = "3";//退回
        }
        turnOutDao.updateTurnOutStatus(status, user, new Date(), reason, id);
    }
    /**
     * @Title : 撤回
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 17:15 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void drawTurnOut(String id,String user) {
        turnInDao.deleteTmTurnIn(id);//上级领导审核通过后撤回需删掉转入申请
        turnOutDao.updateTurnOutStatus("4", id, user, new Date(), "");
    }

    /**
     * @Title : 诊断编码列表
     * @Description : 
     * @author : 皮雪平
     * @date : 2020/1/6 14:06 
     * @param :
     * @return : 
     * @throws
     */
    @Override
    public void getDiseaseList(DiseaseDto dto, PageModel page) {
        turnOutDao.getDiseaseList(dto, page);
    }
    
    /**
     * 查询同一个人今天填写了几个转出单
     */
	@Override
	public int getCountByToDay(String sfzh) {
		return turnOutDao.getCountByToDay(sfzh);
	}

}
