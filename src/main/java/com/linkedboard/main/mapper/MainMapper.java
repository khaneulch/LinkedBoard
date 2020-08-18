package com.linkedboard.main.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
public interface MainMapper {
	void insertBoard(Map<String, Object> params);
	void updateBoard(Map<String, Object> params);
	int getBoardCnt(Map<String, Object> params);
	List<Map<String, Object>> selectBoard(Map<String, Object> params);
	void createBoardTable(String ddlText);
	Map<String, Object> getBoard(Map<String, Object> params);
	void updateBoardTableName(Map<String, Object> params);
	void deleteBoard(Map<String, Object> params);
} 