package com.linkedboard.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
	int getBoardCnt(Map<String, Object> params);
	List<Map<String, Object>> selectBoard(Map<String, Object> params);
	void blockBoard(Map<String, Object> params);
}