package com.linkedboard.mgnt.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface MgntService {
	int getContentBoardCnt(Map<String, Object> params);
	List<Map<String, Object>> selectContentBoard(Map<String, Object> params);
	void insertBoardContent(Map<String, Object> params, MultipartFile mFile);
	Map<String, Object> getBoardContent(Map<String, Object> params);
	void deleteBoardContent(Map<String, Object> params);
	void deleteBoardComment(Map<String, Object> params);
}