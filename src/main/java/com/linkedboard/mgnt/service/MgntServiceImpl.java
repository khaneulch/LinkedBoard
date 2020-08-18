package com.linkedboard.mgnt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.linkedboard.file.mapper.FileMapper;
import com.linkedboard.mgnt.mapper.MgntMapper;
import com.linkedboard.utils.CamelListMap;
import com.linkedboard.utils.CommonFileUtils;
import com.linkedboard.utils.CommonUtils;

@Service
public class MgntServiceImpl implements MgntService {
	
	@Autowired 
	MgntMapper mapper;
	
	@Autowired 
	FileMapper fileMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private void setTableName( Map<String, Object> params) {
		String username = params.getOrDefault("username", CommonUtils.getUser().getUsername()) + "";
		String boardId = params.get("boardId") + "";
		
		params.put("username", username);
		params.put("tableName", "TB_BOARD_CONTENT_" + username + "_" + boardId);
		params.put("tableCommName", "TB_BOARD_CONTENT_COMMENT_" + username + "_" + boardId);
		params.put("tableFileName", "TB_BOARD_CONTENT_FILE_" + username + "_" + boardId);
	}

	/**
	 * 게시글 카운트를 불러온다
	 */
	@Override
	public int getContentBoardCnt(Map<String, Object> params) {
		setTableName(params);
		return mapper.getContentBoardCnt(params);
	}

	/**
	 * 게시글 목록
	 */
	@Override
	public List<Map<String, Object>> selectContentBoard(Map<String, Object> params) {
		setTableName(params);
		return mapper.selectContentBoard(params);
	}

	/**
	 * 게시글 생성
	 */
	@Override
	public void insertBoardContent(Map<String, Object> params, MultipartFile mFile) {
		setTableName(params);
		boolean isUpdate = false;
		// insert
		if( params.getOrDefault("contentId", "").equals("")) {
			mapper.insertBoardContent(params);
		} else {	//update
			isUpdate = true;
			// 타인 게시글 수정방지 위해 암호만들어서 update시 대조
			String contentKey = params.get("contentId") + "key" + params.get("boardId");
			if( passwordEncoder.matches(contentKey, (String) params.get("contentKey"))) {
				mapper.updateBoardContent(params);
			}
		}
		
		if( mFile != null && !mFile.isEmpty()) {
			String path = params.get("username") + "_" + params.get("boardId");
			Map<String, Object> file = CommonFileUtils.uploadFile(mFile, path);
			
			Map<String, Object> fileMap = new HashMap<String, Object>();
			fileMap.put("fileOrgName", mFile.getOriginalFilename());
			fileMap.put("filePath", file.get("filePath"));
			fileMap.put("fileName", file.get("fileName"));
			fileMap.put("contentId", params.get("contentId"));
			fileMap.put("tableName", params.get("tableFileName"));
			
			// 업데이트인 경우 이전 파일 삭제
			if( isUpdate) {
				Map<String, Object> oldFile = fileMapper.getFile( fileMap);
				((CamelListMap) oldFile).putNotCamel("tableName", params.get("tableFileName"));
				CommonFileUtils.deleteFileAndData( oldFile);
			}
			
			fileMapper.insertFile(fileMap);
		}
	}

	/**
	 * 게시글 정보
	 */
	@Override
	public Map<String, Object> getBoardContent(Map<String, Object> params) {
		setTableName(params);
		return mapper.getBoardContent(params);
	}

	@Override
	public void deleteBoardContent(Map<String, Object> params) {
		setTableName(params);
		String contentIds = params.getOrDefault("contentIds", "") + "";
		if( !contentIds.equals("")) {
			params.put("contentIds", contentIds.split(","));
			mapper.deleteBoardContent(params);
		}
	}

	@Override
	public void deleteBoardComment(Map<String, Object> params) {
		setTableName(params);
		String commentIds = params.getOrDefault("commentIds", "") + "";
		if( !commentIds.equals("")) {
			params.put("commentIds", commentIds.split(","));
			mapper.deleteBoardComment(params);
		}
	}

}
