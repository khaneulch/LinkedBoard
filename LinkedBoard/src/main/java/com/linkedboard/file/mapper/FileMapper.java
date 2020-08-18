package com.linkedboard.file.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
	void insertFile(Map<String, Object> fileMap);
	Map<String, Object> getFile(Map<String, Object> fileMap);
	void deleteFile(Map<String, Object> oldFile);
}