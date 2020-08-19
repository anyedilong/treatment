package com.java.moudle.system.dao.repository;

import com.java.moudle.system.domain.TmFileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileUploadRepository extends JpaRepository<TmFileUpload, String> {

    @Transactional
    @Modifying
    @Query(value = "delete TM_FILE_UPLOAD where turn_id = :turnId", nativeQuery = true)
    int deleteFileByTurnID(@Param("turnId") String turnId);


    @Query(value="select * from TM_FILE_UPLOAD a where a.TURN_ID = :turnID and FILE_TYPE = :type ",nativeQuery=true)
    List<TmFileUpload> queryFileByTurnIDAndType(@Param("turnID")String turnID,@Param("type")String type);
    
    @Query(value="select * from TM_FILE_UPLOAD a where a.TURN_ID = :turnId ",nativeQuery=true)
    List<TmFileUpload> getListByTurnId(@Param("turnId")String turnId);

}
