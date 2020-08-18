package com.linkedboard.admin.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkedboard.admin.mapper.AdminMapper;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminMapper mapper;

	/**
	 * 게시판 전체 카운트
	 */
	@Override
	public int getBoardCnt(Map<String, Object> params) {
		return mapper.getBoardCnt(params);
	}

	/**
	 * 게시판 리스트
	 */
	@Override
	public List<Map<String, Object>> selectBoard(Map<String, Object> params) {
		return mapper.selectBoard(params);
	}

	/**
	 * 게시판 차단
	 */
	@Override
	public void blockBoard(Map<String, Object> params) {
		mapper.blockBoard(params);
	}
}