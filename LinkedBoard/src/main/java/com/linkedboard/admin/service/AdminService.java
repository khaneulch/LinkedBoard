package com.linkedboard.admin.service;

import java.util.List;
import java.util.Map;

public interface AdminService {
	int getBoardCnt(Map<String, Object> params);
	List<Map<String, Object>> selectBoard(Map<String, Object> params);
	void blockBoard(Map<String, Object> params);
}
