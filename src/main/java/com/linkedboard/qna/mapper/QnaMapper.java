package com.linkedboard.qna.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaMapper {
	int getQnaCnt(Map<String, Object> params);
	List<Map<String, Object>> selectQna(Map<String, Object> params);
	void insertQna(Map<String, Object> params);
	void updateQna(Map<String, Object> params);
	Map<String, Object> getQna(Map<String, Object> params);
	void replyQna(Map<String, Object> params);
}