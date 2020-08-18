package com.linkedboard.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.linkedboard.file.service.FileService;

public class CommonFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonFileUtils.class);
	
	private static String rootPath = GetPropertyUtils.getProperty("file.root.path");
	private static String uploadFolder = GetPropertyUtils.getProperty("upload.folder");
	
	@Autowired
    ServletContext context;
	
	public static void deleteFile(String fileName) {
		deleteFile(fileName, "");
	}
	
	public static void deleteFile(String fileName, String path) {
		File file = new File(rootPath + uploadFolder + "/" + path + "/" + fileName);
		if( file.exists()) file.delete();
	}
	
	public static Map<String, Object> uploadFile(MultipartFile mFile) {
		return uploadFile(mFile, "");
	}
	
	/**
	 * 파일 업로드
	 * @param mFile
	 * @param path
	 * @return
	 */
	public static Map<String, Object> uploadFile(MultipartFile mFile, String path) {
		
		Map<String, Object> file = new HashMap<String, Object>();
		
		try {
			String orgFileName = mFile.getOriginalFilename();
			
			byte[] fileData = mFile.getBytes();
		
			String ext = orgFileName.substring(orgFileName.lastIndexOf("."));
			
			String savedName = CommonUtils.getSavedFileName();
			
			File dir = new File(rootPath + uploadFolder + "/" + path);
			if( !dir.exists()) dir.mkdirs();
			
			String filePath = "/" + path + "/" + savedName + ext;
			
			File target = new File(rootPath + uploadFolder + filePath);
			
			FileCopyUtils.copy(fileData, target);
			
			file.put("filePath", filePath);
			file.put("fileName", savedName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	/**
	 * 경로에서 파일을 읽어온다.
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	private static String getFile (String filePath, String fileName) {
        FileInputStream fileInputStream = null;
        StringBuffer sb = new StringBuffer();
        
        try {
        	fileInputStream = new FileInputStream( filePath + fileName );
        	
	        byte[] readBuffer = new byte[fileInputStream.available()];
	        
			while (fileInputStream.read( readBuffer ) != -1) {
				sb.append(new String(readBuffer));
			}
			
	        fileInputStream.close();
	        
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return sb.toString();
	}
	

	/**
	 * 파일 다운로드
	 * @param zipFileName
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	public static void download(Map<String, Object> params, HttpServletResponse response) throws UnsupportedEncodingException {
		String fileName = params.get("fileName") + "";
		String filePath = params.get("filePath") + "";
		response.setContentType( "application/download; UTF-8" );
	    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ";");
	    
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	    
	    try {
	    	logger.debug("DOWNLOAD FILE : " + fileName);
	    	
	    	File file = new File(rootPath + uploadFolder + filePath);
	    
		    int nRead = 0;
		    byte btReadByte[] = new byte[(int)file.length()];
	
		    if( file.length() > 0 && file.isFile()) {
		        bis = new BufferedInputStream( new FileInputStream(file));
		        bos = new BufferedOutputStream( response.getOutputStream());

		        while( ( nRead = bis.read(btReadByte)) != -1) {
		        	bos.write(btReadByte, 0, nRead);
		        }
		    }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		if( bos != null) bos.flush();
	    		if( bos != null) bos.close();
	    		if( bis != null) bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteFileAndData(Map<String, Object> oldFile) {
		File file = new File(rootPath + uploadFolder + "/" + oldFile.get("filePath"));
		if( file.exists()) file.delete();
		
		FileService service = (FileService) CommonUtils.serviceLoader("fileService");
		service.deleteFile( oldFile);
	}
}