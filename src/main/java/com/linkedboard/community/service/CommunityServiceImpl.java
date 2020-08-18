package com.linkedboard.community.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.linkedboard.community.mapper.CommunityMapper;
import com.linkedboard.utils.CommonUtils;

@Service
public class CommunityServiceImpl implements CommunityService {
	@Autowired
	CommunityMapper mapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	/**
	 * 테이블명 생성
	 * @param params
	 */
	private void setTableBoardName( Map<String, Object> params) {
		Map<String, Object> board = new HashMap<String, Object>();
		if( params.containsKey("board")) {
			board = (Map<String, Object>) params.get("board");
		} else {
			board = mapper.getBoardInfo(params);
			params.put("board", board);
		}
				
		String username = params.get("username") + "";
		String boardId = params.get("boardId") + "";
		
		params.put("tableName", board.get("tableName"));
		params.put("tableFileName", "TB_BOARD_CONTENT_FILE_" + username + "_" + boardId);
		params.put("tableCommName", "TB_BOARD_CONTENT_COMMENT_" + username + "_" + boardId);
	}

	/**
	 * 게시글 생성
	 */
	@Override
	public void insertBoardContent(Map<String, Object> params) {
		setTableBoardName(params);
		// insert
		if( params.getOrDefault("contentId", "").equals("")) {
			mapper.insertBoardContent(params);
		} else {	//update
			// 타인 게시글 수정방지 위해 암호만들어서 update시 대조
			String contentKey = params.get("contentId") + "key" + params.get("boardId");
			if( passwordEncoder.matches(contentKey, (String) params.get("contentKey"))) {
				mapper.updateBoardContent(params);
			}
		}
	}
	
	/**
	 * 게시글 수정
	 */
	@Override
	public void updateBoardContent(Map<String, Object> params) {
		setTableBoardName(params);
		mapper.updateBoardContent(params);
	}

	/**
	 * 게시글 삭제
	 */
	@Override
	public void deleteBoardContent(Map<String, Object> params) {
		setTableBoardName(params);

		Map<String, Object> content = mapper.getBoardContent(params);
		// 화면에서 입력한 비밀번호와 db데이터 비교
		if( content.get("contentPassword").equals(params.get("contentPassword"))) {
			mapper.deleteBoardContent(params);
		}
	}

	/**
	 * 게시글 리스트 개수
	 */
	@Override
	public int getBoardContentCnt(Map<String, Object> params) {
		setTableBoardName(params);
		return mapper.getBoardContentCnt(params);
	}

	/**
	 * 게시글 리스트
	 */
	@Override
	public List<Map<String, Object>> selectBoardContent(Map<String, Object> params) {
		setTableBoardName(params);
		return mapper.selectBoardContent(params);
	}

	/**
	 * 게시글 상세
	 */
	@Override
	public Map<String, Object> getBoardContent(Map<String, Object> params) {
		setTableBoardName(params);
		return mapper.getBoardContent(params);
	}

	/**
	 * 댓글 작성
	 */
	@Override
	public void insertBoardComment(Map<String, Object> params) {
		setTableBoardName(params);
		mapper.insertBoardComment(params);
	}

	/**
	 * 댓글 삭제
	 */
	@Override
	public void deleteBoardComment(Map<String, Object> params) {
		setTableBoardName(params);
		
		Map<String, Object> comment = mapper.getBoardComment(params);
		// 화면에서 입력한 비밀번호와 db데이터 비교
		if( comment.get("commentPassword").equals(params.get("commentPassword"))) {
			mapper.deleteBoardComment(params);
		}
	}

	/**
	 * 댓글 리스트
	 */
	@Override
	public int getBoardCommentCnt(Map<String, Object> params) {
		setTableBoardName(params);
		return mapper.getBoardCommentCnt(params);
	}

	/**
	 * 댓글 리스트
	 */
	@Override
	public List<Map<String, Object>> selectBoardComment(Map<String, Object> params) {
		setTableBoardName(params);
		return mapper.selectBoardComment(params); 
	}

	/**
	 * 게시글 조회수 증가
	 */
	@Override
	public void updateBoardViewCnt(Map<String, Object> params) {
		setTableBoardName(params);
		Map<String, Object> content = mapper.getBoardContent(params);
		
		// 등록, 수정자와 IP가 같은경우 조회수 증가되지 않도록
		if( !params.get("currIp").equals(content.get("regIp"))
				&& !params.get("currIp").equals(content.get("udtIp"))) {
			mapper.updateBoardViewCnt(params);
		}
	}

	/**
	 * 게시판 정보를 불러옴
	 */
	@Override
	public Map<String, Object> getBoardInfo(Map<String, Object> params) {
		params.compute("username", (k, v) -> v == null ? CommonUtils.getUser().getUsername() : v);
		return mapper.getBoardInfo(params);
	}
}