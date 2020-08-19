package com.java.moudle.system.dao;

import com.java.moudle.system.dao.repository.FileUploadRepository;
import com.java.moudle.system.domain.TmFileUpload;
import com.java.until.dba.BaseDao;

import javax.inject.Named;
import java.util.List;

@Named
public class FileUplodDao extends BaseDao<FileUploadRepository, TmFileUpload> {

    public void deleteFileByTurnID(String turnId){
        repository.deleteFileByTurnID(turnId);
    }

    public List<TmFileUpload> queryFileByTurnIDAndType(String turnId, String type){
        return repository.queryFileByTurnIDAndType(turnId,type);
    }
    
    public List<TmFileUpload> getListByTurnId(String turnId) {
    	return repository.getListByTurnId(turnId);
    }
}
