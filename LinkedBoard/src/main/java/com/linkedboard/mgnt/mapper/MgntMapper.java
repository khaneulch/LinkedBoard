package com.linkedboard.mgnt.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MgntMapper {
	int getContentBoardCnt(Map<String, Object> params);
	List<Map<String, Object>> selectContentBoard(Map<String, Object> params);
	void insertBoardContent(Map<String, Object> params);
	void updateBoardContent(Map<String, Object> params);
	Map<String, Object> getBoardContent(Map<String, Object> params);
	void deleteBoardContent(Map<String, Object> params);
	void deleteBoardComment(Map<String, Object> params);
} 