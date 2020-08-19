package com.java.moudle.system.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.java.until.dba.BaseDomain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description  
 * @Author  
 * @Date 2020-01-02 
 */

@Entity
@Table ( name ="TM_FILE_UPLOAD" )
public class TmFileUpload  extends BaseDomain  {

	private static final long serialVersionUID =  7397968484984831920L;

	/**
	 * 唯一标识
	 */
   	@Column(name = "ID" )
	@Id
	private String id;

	/**
	 * 文件URL
	 */
   	@Column(name = "FILE_URL" )
	private String fileUrl;

	/**
	 * 文件路径
	 */
   	@Column(name = "FILE_NAME" )
	private String fileName;

	/**
	 * 上传时间
	 */
   	@Column(name = "UPLOAD_TIME" )
	@JSONField(format="yyyy-MM-dd")
	private Date uploadTime;

	/**
	 * 文件类型 1、知情同意书 2相关附件
	 */
   	@Column(name = "FILE_TYPE" )
	private String fileType;

	/**
	 * 申请id
	 */
   	@Column(name = "turn_id" )
	private String turnId;

	/**
	 * 上传人
	 */
   	@Column(name = "UPLOAD_USER" )
	private String uploadUser;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getTurnId() {
		return this.turnId;
	}

	public void setTurnId(String turnId) {
		this.turnId = turnId;
	}

	public String getUploadUser() {
		return this.uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

}
