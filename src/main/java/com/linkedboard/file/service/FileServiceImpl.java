package com.linkedboard.file.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkedboard.file.mapper.FileMapper;

@Service("fileService")
public class FileServiceImpl implements FileService{
	@Autowired
	FileMapper mapper;

	@Override
	public void deleteFile(Map<String, Object> oldFile) {
		mapper.deleteFile(oldFile);
	}
}
