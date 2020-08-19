package com.java.moudle.common.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.java.until.StringUntil;
import com.java.until.UUIDUtil;
import com.java.until.ftpup.UpUtils;

/**
 * <br>
 * <b>功能：</b>CustomerController<br>
 * <b>作者：</b>blt<br>
 * <b>版权所有：<b>版权所有(C) 2016，blt<br>
 */
@RestController
@RequestMapping("commontools")
public class CommonToolsController {

	@Value("${ftpUrl}")
    private String ftpUrl;
	
	/**
	 * @Description: 上传文件，保存到ftp上
	 * @param @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "/saveFile", method = RequestMethod.POST)
	public String saveImage(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("fileData");
			String oriName = file.getOriginalFilename();
			if(!StringUntil.isNull(oriName)) {
				String fileName = UUIDUtil.getUUID() + oriName.substring(oriName.lastIndexOf("."));
				//ftp上传
				boolean upload = UpUtils.upload(file.getInputStream(), "/treat/", fileName);
				if(upload) {
					return ftpUrl+"/treat/"+fileName + "," + oriName;
				}else {
					return "1000";
				}
			}else {
				return "1001";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}
	
}
