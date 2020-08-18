package com.linkedboard.main.service;

import java.util.List;
import java.util.Map;

public interface MainService {
	void insertBoard(Map<String, Object> params);
	void updateBoard(Map<String, Object> params);
	int getBoardCnt(Map<String, Object> params);
	List<Map<String, Object>> selectBoard(Map<String, Object> params);
	Map<String, Object> getBoard(Map<String, Object> params);
	void deleteBoard(Map<String, Object> params);
}
