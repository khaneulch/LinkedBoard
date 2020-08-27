package com.linkedboard.community.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.linkedboard.community.service.CommunityService;
import com.linkedboard.user.service.UserService;
import com.linkedboard.utils.OtpUtils;
import com.linkedboard.utils.PaginationUtils;

@Controller
@RequestMapping("/community/{username}/{boardId}")
public class CommunityController {
	
	@Autowired
	CommunityService service;
	
	private PasswordEncoder passwordEncoder;
    
    @Autowired
    void PasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	/**
	 * 게시글 리스트
	 * @param model
	 * @param username
	 * @param boardId
	 * @param params
	 * @return
	 */
	@RequestMapping("")
	public String list( Model model
			, HttpServletRequest request
			, @PathVariable("username") String username 
			, @PathVariable("boardId") String boardId 
			, @RequestParam Map<String, Object> params) {
		
		params.put("username", username);
		params.put("boardId", boardId);
		
		Map<String, Object> board = service.getBoardInfo(params);
		params.put("board", board);
		model.addAttribute("rParam", params);

		if( checkIsBlockBoard( params)) {
			return "/community/community_block";
		}
			
		// 세션 체크
		int auth = checkSessionAuthenticated(request.getSession(), params);
		if( auth == 99) {
			PaginationUtils.setPage(service.getBoardContentCnt(params), params, model);
			model.addAttribute("list", service.selectBoardContent(params));
			
			return "/community/community_list";
		} else {
			params.put("auth", auth);
			return "/community/community_auth";
		}
	}
	
	/**
	 * 게시글 생성/수정 폼
	 * @param model
	 * @param username
	 * @param boardId
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/form", method={RequestMethod.POST, RequestMethod.GET})
	public String form( Model model
			, HttpServletRequest request
			, @PathVariable("username") String username 
			, @PathVariable("boardId") String boardId 
			, @RequestParam Map<String, Object> params) {
		params.put("username", username);
		params.put("boardId", boardId);

		if( checkIsBlockBoard( params)) {
			return "/community/community_block";
		}
		
		// 세션 체크
		int auth = checkSessionAuthenticated(request.getSession(), params);
		if( auth == 99) {
		
			if( !params.getOrDefault("contentId", "").equals("")) {
				Map<String, Object> content = new HashMap<String, Object>();
				content.putAll(service.getBoardContent(params));
				
				// 화면에서 입력한 비밀번호와 db데이터 비교
				if( content.get("contentPassword").equals(params.get("contentPassword"))) {
					
					// 타인 게시글 수정방지 위해 암호만들어서 update시 대조
					String contentKey = content.get("contentId") + "key" + params.get("boardId");
					content.put("contentKey", passwordEncoder.encode(contentKey));
					
					model.addAttribute("content", content);
				} else {
					return "redirect:/community/" + username + "/" + boardId + "/view?error=1&contentId=" + params.get("contentId");
				}
			}
			
			model.addAttribute("rParam", params);
			return "/community/community_form";
			
		} else {
			params.put("auth", auth);
			return "redirect:/community/" + username + "/" + boardId;
		}
	}
	
	/**
	 * 게시글 생성/수정
	 * @param model
	 * @param request
	 * @param username
	 * @param boardId
	 * @param params
	 * @return
	 */
	@RequestMapping("/insert")
	public String insert( Model model
			, HttpServletRequest request
			, @PathVariable("username") String username 
			, @PathVariable("boardId") String boardId 
			, @RequestParam Map<String, Object> params) {
		params.put("username", username);
		params.put("boardId", boardId);
		params.put("regIp", request.getRemoteAddr());
		
		service.insertBoardContent(params);
		return "redirect:/community/" + username + "/" + boardId + "/view?contentId=" + params.get("contentId");
	}
	
	/**
	 * 게시글 삭제
	 * @param model
	 * @param username
	 * @param boardId
	 * @param params
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete( Model model
			, @PathVariable("username") String username 
			, @PathVariable("boardId") String boardId 
			, @RequestParam Map<String, Object> params) {
		
		params.put("username", username);
		params.put("boardId", boardId);
		
		service.deleteBoardContent(params);
		return "redirect:/community/" + username + "/" + boardId;
	}
	
	/**
	 * 게시글 상세
	 * @param model
	 * @param username
	 * @param boardId
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/view")
	public String view( Model model
			, HttpServletRequest request
			, @PathVariable("username") String username 
			, @PathVariable("boardId") String boardId 
			, @RequestParam Map<String, Object> params) {
		params.put("username", username);
		params.put("boardId", boardId);
		params.put("currIp", request.getRemoteAddr());
		
		if( checkIsBlockBoard( params)) {
			return "/community/community_block";
		}
		
		// 세션 체크
		int auth = checkSessionAuthenticated(request.getSession(), params);
		if( auth == 99) {
		
			// 세션에 이미 확인한 게시글 번호를 저장한다.
			HttpSession session = request.getSession();
			String contentId = boardId + "_" + params.get("contentId");
			
			if( session.getAttribute("viewBoards") != null) {
				String viewBoards = session.getAttribute("viewBoards") + "";
				if( viewBoards.indexOf(contentId) == -1) {
					session.setAttribute("viewBoards", viewBoards + "|" + contentId);
					service.updateBoardViewCnt(params);
				}
			} else {
				session.setAttribute("viewBoards", contentId);
				service.updateBoardViewCnt(params);
			}
			
			
			if( !params.containsKey("board")) {
				params.put("board", service.getBoardInfo(params));
			}
			model.addAttribute("content", service.getBoardContent(params));
			model.addAttribute("rParam", params);
			model.addAttribute("commentList", service.selectBoardComment(params));
		
			return "/community/community_view";
			
		} else {
			params.put("auth", auth);
			return "redirect:/community/" + username + "/" + boardId;
		}
	}
	
	/**
	 * 댓글 등록
	 * @param model
	 * @param request
	 * @param username
	 * @param boardId
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/insert-comment")
	public String insertComment( Model model
			, HttpServletRequest request
			, @PathVariable("username") String username 
			, @PathVariable("boardId") String boardId 
			, @RequestParam Map<String, Object> params) {
		params.put("username", username);
		params.put("boardId", boardId);
		params.put("regIp", request.getRemoteAddr());
		
		service.insertBoardComment(params);
		return "redirect:/community/" + username + "/" + boardId + "/view?contentId=" + params.get("contentId");
	}
	
	/**
	 * 댓글 삭제
	 * @param model
	 * @param request
	 * @param username
	 * @param boardId
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/delete-comment")
	public String deleteComment( Model model
			, HttpServletRequest request
			, @PathVariable("username") String username 
			, @PathVariable("boardId") String boardId 
			, @RequestParam Map<String, Object> params) {
		params.put("username", username);
		params.put("boardId", boardId);
		params.put("regIp", request.getRemoteAddr());
		
		service.deleteBoardComment(params);
		return "redirect:/community/" + username + "/" + boardId + "/view?contentId=" + params.get("contentId");
	}
	
	/**
	 * 게시판 접근 인증 페이지
	 * @param model
	 * @param request
	 * @param username
	 * @param boardId
	 * @param params
	 * @return
	 */
	@RequestMapping("/auth")
	public String auth ( Model model
			, HttpServletRequest request
			, @PathVariable("username") String username 
			, @PathVariable("boardId") String boardId 
			, @RequestParam Map<String, Object> params) {
		params.put("username", username);
		params.put("boardId", boardId);
		// auth -> 10 : 비밀번호(P) 인증 필요, 20 : OTP(O) 인증 필요
		String auth = params.getOrDefault("auth", "10") + "";
		
		Map<String, Object> boardInfo = service.getBoardInfo(params);
		
		HttpSession session = request.getSession();
		if( auth.equals("20")) {
			if( OtpUtils.checkCode(boardInfo.get("accessPass") + "", params.get("authCode") + "")) {
				session.setAttribute("auth" + username + boardId, "99");
			}
		} else {
			if( boardInfo.getOrDefault("accessPass", "").equals( params.get("authCode"))) {
				session.setAttribute("auth" + username + boardId, "99");
			}
		}
		
		return "redirect:/community/" + username + "/" + boardId;
	}
	
	/**
	 * 게시판의 접근 설정을 세팅한다.
	 * @param session
	 * @param params
	 * @return
	 */
	private int checkSessionAuthenticated( HttpSession session, Map<String, Object> params) {
		// 99 : 인증완료, 10 : 비밀번호(P) 인증 필요, 20 : OTP(O) 인증 필요, -1 : 오류
		int auth = -1;

		String username = params.get("username") + "";
		String boardId = params.get("boardId") + "";
		
		String boardAuth = session.getAttribute("auth" + username + boardId) + "";
		
		if( boardAuth == null || boardAuth.equals("") || boardAuth.equals("null")) {
			Map<String, Object> boardInfo = new HashMap<String, Object>();
			if( params.containsKey("board")) {
				boardInfo = (Map<String, Object>) params.get("board"); 
			} else {
				boardInfo = service.getBoardInfo(params);	
				params.put("board", boardInfo);
			}
					
			String accessType = boardInfo.getOrDefault("accessType", "X") + "";
			
			// 접근인증이 필요없는 경우
			if( accessType.equals("X")) {
				return 99;
			}

			if( accessType.equals("O")) {
				auth = 20;
			} else if( accessType.equals("P")) {
				auth = 10;
			}
			
		} else if( boardAuth.equals("99")) {
			auth = 99;
		}
		
		return auth;
	}
	
	/**
	 * 게시판이 차단/숨김인지 확인한다.
	 * @param params
	 * @return
	 */
	private boolean checkIsBlockBoard( Map<String, Object> params) {
		boolean result = false;
		Map<String, Object> board = null;
		if( params.containsKey("board")) {
			board = (Map<String, Object>) params.get("board"); 
		} else {
			board = service.getBoardInfo(params);
		}
		
		// 해당 게시판이 관리자에 의해 차단된 게시판인지 확인
		result = board.getOrDefault("blockYn", "N").equals("Y");
		if( !result) {
			// 게시판이 숨김상태인지 확인
			result = board.getOrDefault("dpYn", "Y").equals("N");
		}
		return result;
	}
}
