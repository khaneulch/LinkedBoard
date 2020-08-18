package com.linkedboard.qna.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkedboard.qna.mapper.QnaMapper;
import com.linkedboard.utils.CommonUtils;

@Service
public class QnaServiceImpl implements QnaService {
	@Autowired
	QnaMapper mapper;

	/**
	 * qna 리스트 카운트
	 */
	@Override
	public int getQnaCnt(Map<String, Object> params) {
		return mapper.getQnaCnt(params);
	}

	/**
	 * qna 리스트
	 */
	@Override
	public List<Map<String, Object>> selectQna(Map<String, Object> params) {
		return mapper.selectQna(params);
	}

	/**
	 * qna 생성
	 */
	@Override
	public void insertQna(Map<String, Object> params) {
		String username = CommonUtils.getUser().getUsername();
		String qnaId = params.getOrDefault("qnaId", "") + "";
		if( qnaId.equals("")) {
			params.put("regUser", username);
			mapper.insertQna(params);
		} else {
			mapper.updateQna(params);
		}
	}

	/**
	 * qna 수정
	 */
	@Override
	public void updateQna(Map<String, Object> params) {
		mapper.updateQna(params);
	}

	/**
	 * qna 상세
	 */
	@Override
	public Map<String, Object> getQna(Map<String, Object> params) {
		return mapper.getQna(params);
	}

	/**
	 * qna 답변
	 */
	@Override
	public void replyQna(Map<String, Object> params) {
		params.put("replyUser", CommonUtils.getUser().getUsername());
		mapper.replyQna(params);
	}
}