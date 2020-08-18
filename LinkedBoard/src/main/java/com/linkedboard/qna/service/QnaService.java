package com.linkedboard.qna.service;

import java.util.List;
import java.util.Map;

public interface QnaService {
	int getQnaCnt(Map<String, Object> params);
	List<Map<String, Object>> selectQna(Map<String, Object> params);
	void insertQna(Map<String, Object> params);
	void updateQna(Map<String, Object> params);
	void replyQna(Map<String, Object> params);
	Map<String, Object> getQna(Map<String, Object> params);
}