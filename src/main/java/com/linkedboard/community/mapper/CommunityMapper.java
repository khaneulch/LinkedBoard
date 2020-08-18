package com.linkedboard.community.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommunityMapper {
	void insertBoardContent(Map<String, Object> params);
	void updateBoardContent(Map<String, Object> params);
	void deleteBoardContent(Map<String, Object> params);
	int getBoardContentCnt(Map<String, Object> params);
	List<Map<String, Object>> selectBoardContent(Map<String, Object> params);
	Map<String, Object> getBoardContent(Map<String, Object> params);
	Map<String, Object> getBoardInfo(Map<String, Object> params);
	void insertBoardComment(Map<String, Object> params);
	void deleteBoardComment(Map<String, Object> params);
	int getBoardCommentCnt(Map<String, Object> params);
	List<Map<String, Object>> selectBoardComment(Map<String, Object> params);
	Map<String, Object> getBoardComment(Map<String, Object> params);
	void updateBoardViewCnt(Map<String, Object> params);
}