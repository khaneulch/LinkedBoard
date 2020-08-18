package com.linkedboard.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkedboard.main.mapper.MainMapper;
import com.linkedboard.utils.CommonUtils;
import com.linkedboard.utils.OtpUtils;

@Service
public class MainServiceImpl implements MainService {
	@Autowired MainMapper mapper;
	
	private String createTableBoardDDL(String tableName) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("CREATE TABLE " + tableName + "(");
		sb.append("CONTENT_ID INT NOT NULL COMMENT 'ID',");
		sb.append("TITLE varchar(1000) NOT NULL COMMENT '게시판 제목',");
		sb.append("CONTENT TEXT NOT NULL COMMENT '게시판 내용',");
		sb.append("CONTENT_PASSWORD varchar(100) NOT NULL COMMENT '게시판 비밀번호',");
		sb.append("NOTICE_YN CHAR(1) DEFAULT 'N' COMMENT '공지여부(Y/N)',");
		sb.append("ADMIN_YN CHAR(1) DEFAULT 'N' COMMENT '관리자여부(Y/N)',");
		sb.append("VIEW_CNT INT DEFAULT 0 COMMENT '조회수',");
		sb.append("DEL_YN CHAR(1) DEFAULT 'N' COMMENT '삭제여부 (Y/N)',");
		sb.append("REG_DT TIMESTAMP DEFAULT current_timestamp COMMENT '생성일자',");
		sb.append("REG_IP varchar(100) NOT NULL COMMENT '작성자 IP',");
		sb.append("UDT_DT TIMESTAMP COMMENT '수정일자',");
		sb.append("UDT_IP varchar(100) COMMENT '수정자 IP',");
		sb.append("PRIMARY KEY (CONTENT_ID)");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자별 게시판_" + tableName + "';");
		
		return sb.toString();
	}
	
	private String createTableCommentDDL(String tableName) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("CREATE TABLE " + tableName + "(");
		sb.append("COMMENT_ID INT NOT NULL COMMENT 'ID',");
		sb.append("CONTENT_ID INT NOT NULL COMMENT 'BOARD_CONTENT_ID',");
		sb.append("PARENT_ID INT COMMENT '상위댓글',");
		sb.append("COMMENT TEXT NOT NULL COMMENT '댓글 내용',");
		sb.append("COMMENT_PASSWORD varchar(100) NOT NULL COMMENT '댓글 비밀번호',");
		sb.append("ADMIN_YN CHAR(1) DEFAULT 'N' COMMENT '관리자여부(Y/N)',");
		sb.append("DEL_YN CHAR(1) DEFAULT 'N' COMMENT '삭제여부 (Y/N)',");
		sb.append("REG_DT TIMESTAMP DEFAULT current_timestamp COMMENT '생성일자',");
		sb.append("REG_IP varchar(100) NOT NULL COMMENT '작성자 IP',");
		sb.append("PRIMARY KEY (COMMENT_ID)");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자별 게시판 댓글_" + tableName + "';");
		
		return sb.toString();
	}
	
	private String createTableFileDDL(String tableName) {
		StringBuffer sb = new StringBuffer();
	
		sb.append("CREATE TABLE " + tableName + "(");
		sb.append("FILE_ID INT NOT NULL COMMENT 'ID',");
		sb.append("CONTENT_ID INT DEFAULT NULL COMMENT 'BOARD_CONTENT_ID',");
		sb.append("FILE_PATH varchar(100) NOT NULL COMMENT '파일경로',");
		sb.append("FILE_ORG_NAME varchar(100) NOT NULL COMMENT '파일 원본명',");
		sb.append("DEL_YN CHAR(1) DEFAULT 'N' COMMENT '삭제여부 (Y/N)',");
		sb.append("REG_DT TIMESTAMP DEFAULT current_timestamp COMMENT '생성일자',");
		sb.append("PRIMARY KEY (FILE_ID)");
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자별 게시판 파일_" + tableName + "';");

		return sb.toString();
	}

	@Override
	public void insertBoard(Map<String, Object> params) {
		String username = CommonUtils.getUser().getUsername();
		params.put("username", username);
		
		if( params.getOrDefault("accessType", "X").equals("O")) {
			params.put("accessPass", OtpUtils.generateKey());
		}
		
		mapper.insertBoard(params);
		
		String boardId = params.get("boardId") + "";
		
		String tableBoardName = "TB_BOARD_CONTENT_" + username + "_" + boardId;
		
		params.put("tableName", tableBoardName);
		mapper.updateBoardTableName(params);
		
		// 해당 게시판에 해당하는 테이블(게시글)을 생성한다.
		String ddlText = createTableBoardDDL( tableBoardName);
		mapper.createBoardTable(ddlText);
		
		// 해당 게시판에 해당하는 테이블(댓글)을 생성한다.
		String tableCommentName = "TB_BOARD_CONTENT_COMMENT_" + username + "_" + boardId;
		
		String ddlCommentText = createTableCommentDDL( tableCommentName);
		mapper.createBoardTable(ddlCommentText);
		
		// 해당 게시판에 해당하는 테이블(파일)을 생성한다.
		String tableFileName = "TB_BOARD_CONTENT_FILE_" + username + "_" + boardId;
		String ddlFileText = createTableFileDDL( tableFileName);
		mapper.createBoardTable(ddlFileText);		
	}

	@Override
	public void updateBoard(Map<String, Object> params) {
		params.put("username", CommonUtils.getUser().getUsername());
		
		String accessType = params.getOrDefault("accessType", "X") + "";
		String accessTypeOld = params.getOrDefault("accessType_old", "X") + "";
		
		if( accessType.equals("O") && !accessType.equals(accessTypeOld)) {
			params.put("accessPass", OtpUtils.generateKey());
		}
		
		mapper.updateBoard(params);
	}

	@Override
	public int getBoardCnt(Map<String, Object> params) {
		return mapper.getBoardCnt(params);
	}

	@Override
	public List<Map<String, Object>> selectBoard(Map<String, Object> params) {
		return mapper.selectBoard(params);
	}

	@Override
	public Map<String, Object> getBoard(Map<String, Object> params) {
		return mapper.getBoard(params);
	}

	@Override
	public void deleteBoard(Map<String, Object> params) {
		mapper.deleteBoard(params);
	}

}
