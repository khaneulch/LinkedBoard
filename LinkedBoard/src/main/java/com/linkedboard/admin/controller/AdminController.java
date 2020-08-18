package com.linkedboard.admin.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkedboard.admin.service.AdminService;
import com.linkedboard.community.service.CommunityService;
import com.linkedboard.main.service.MainService;
import com.linkedboard.mgnt.service.MgntService;
import com.linkedboard.qna.service.QnaService;
import com.linkedboard.user.service.UserService;
import com.linkedboard.utils.JsonView;
import com.linkedboard.utils.PaginationUtils;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService service;
	
	@Autowired
	MainService mainService;
	
	@Autowired
	MgntService mgntService;
	
	@Autowired
	QnaService qnaService;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	CommunityService communityService;
	
	/**
	 * 전체 게시판 목록
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/main")
	public String list( Model model, @RequestParam Map<String, Object> params) {
		PaginationUtils.setPage(service.getBoardCnt(params), params, model);
		model.addAttribute("list", service.selectBoard(params));
		return "/admin/main_list";
	}
	
	/**
	 * 게시판 상세
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/view")
	public String view( Model model, @RequestParam Map<String, Object> params) {
		model.addAttribute("board", mainService.getBoard(params));
		return "/admin/main_view";
	}
	
	/**
	 * 게시판 차단
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/block")
	@ResponseBody
	public JsonView block( Model model, @RequestParam Map<String, Object> params) {
		service.blockBoard(params);
		return new JsonView(true);
	}
	
	/**
	 * 게시판 - 게시글 목록
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/board/list")
	public String boardList( Model model, @RequestParam Map<String, Object> params) {
		PaginationUtils.setPage(mgntService.getContentBoardCnt(params), params, model);
		model.addAttribute("list", mgntService.selectContentBoard(params));
		model.addAttribute("board", communityService.getBoardInfo(params));
		model.addAttribute("rParam", params);
		return "/admin/board_list";
		
	}

	/**
	 * 게시판 - 게시글 상세
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/board/view")
	public String boardView( Model model, @RequestParam Map<String, Object> params) {
		model.addAttribute("content", mgntService.getBoardContent(params));
		model.addAttribute("rParam", params);
		model.addAttribute("commentList", communityService.selectBoardComment(params));
		model.addAttribute("board", communityService.getBoardInfo(params));
		return "/admin/board_view";
	}

	/**
	 * 사용자 목록
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/user/list")
	public String userList( Model model, @RequestParam Map<String, Object> params) {
		PaginationUtils.setPage(userService.getUserCnt(params), params, model);
		model.addAttribute("list", userService.selectUser(params));
		model.addAttribute("rParam", params);
		return "/admin/user_list";
	}
	
	/**
	 * 사용자 차단
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/user/block")
	@ResponseBody
	public JsonView userBlock( Model model, @RequestParam Map<String, Object> params) {
		userService.blockUser(params);
		return new JsonView(true);
	}
	
	/**
	 * qna 목록
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/qna/list")
	public String qnaList( Model model, @RequestParam Map<String, Object> params) {
		params.put("isAdmin", "Y");
		PaginationUtils.setPage(qnaService.getQnaCnt(params), params, model);
		model.addAttribute("list", qnaService.selectQna(params));
		model.addAttribute("rParam", params);
		return "/admin/qna_list";
	}

	/**
	 * qna 상세
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/qna/view")
	public String qnaView( Model model, @RequestParam Map<String, Object> params) {
		model.addAttribute("qna", qnaService.getQna(params));
		return "/admin/qna_view";
	}
	
	/**
	 * qna 상세
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/qna/reply")
	public String qnaReply( Model model, @RequestParam Map<String, Object> params) {
		qnaService.replyQna(params);
		return "redirect:/admin/qna/view?qnaId=" + params.get("qnaId");
	}

}