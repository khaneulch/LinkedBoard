package com.linkedboard.file.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.linkedboard.utils.CommonFileUtils;
import com.linkedboard.utils.CommonUtils;
import io.micrometer.core.instrument.util.StringUtils;

@Controller
@RequestMapping("/file")
public class FileController {
	
	@Value("${file.root.path}")
	private String rootPath;
	
	@Value("${upload.folder}")
	private String uploadFolder;

	
	@RequestMapping(value="/ckImageUpload", method = RequestMethod.POST)
	@ResponseBody
	public void ckImageUpload (
			HttpServletRequest request
			, HttpServletResponse response
			, MultipartHttpServletRequest mRequest
			, @RequestParam Map<String, Object> params 
			) throws IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		String separator = File.separator;
        String filePath = rootPath + uploadFolder;
        File dir = new File(filePath);
        
        MultipartFile mFile = mRequest.getFile("upload");
        
        FileOutputStream out = null;
        
        // 관리자에서 저장하는 경우 usename을 로그인한 사용자로 받도록함.
        params.compute("username", (k, v) -> v == null ? CommonUtils.getUser().getUsername() : v);
        
        try {

            if( mFile != null) {
            	if( mFile.getSize() > 0 && StringUtils.isNotBlank(mFile.getName())) {
            		if( mFile.getContentType().toLowerCase().startsWith("image/")) {
            			
			            int index =  mFile.getOriginalFilename().lastIndexOf(separator);
			            
			            String fileOrgName = mFile.getOriginalFilename().substring(index  + 1);		// 원본 파일명
			            String ext = fileOrgName.substring(fileOrgName.indexOf("."));		// 파일 확장명
			            String newName = CommonUtils.getSavedFileName() + ext;				// 저장 파일명

			            // 컴포넌트별 서브 파일 경로
			            String editorPath = params.get("username") + "_" + params.get("boardId");
			            filePath = filePath + separator + editorPath;
			            
			            File fileDir = new File(filePath);
			            if( !fileDir.exists()) fileDir.mkdirs();
			            
			            File uploadFile = new File(filePath +  separator + newName);
			            
			            byte fileByte[] = mFile.getBytes();
			            out = new FileOutputStream(uploadFile);
			            out.write(fileByte);
			            
			            // ckeditor에 리턴할 내용
			            jsonObject.put("uploaded", 1);
			            jsonObject.put("url", uploadFolder + separator + editorPath + separator + newName);
			            jsonObject.put("fileName", newName);
			    		
			    		response.setContentType("application/json");
			    		response.setCharacterEncoding("UTF-8");
			    		response.getWriter().print(jsonObject.toString());
	    		
            		}
            	}
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
			out.close();
		}
	}
	
	@RequestMapping("/download")
	public void download (
			HttpServletResponse response
			, @RequestParam Map<String, Object> params ) throws UnsupportedEncodingException {
		CommonFileUtils.download( params, response);
	}
	

}
