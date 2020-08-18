package com.linkedboard.main.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.linkedboard.main.service.MainService;
import com.linkedboard.utils.CommonUtils;
import com.linkedboard.utils.PaginationUtils;

@Controller
@RequestMapping("/board/main")
public class MainController {
	@Autowired MainService service;
	
	/**
	 * 일반 계정사용자 메인페이지
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String main( Model model, @RequestParam Map<String, Object> params) {
		params.put("username", CommonUtils.getUser().getUsername());
		PaginationUtils.setPage(service.getBoardCnt(params), params, model);
		model.addAttribute("list", service.selectBoard(params));
		return "/main/main_list";
	}

	/**
	 * 게시판 생성 폼
	 * @param model
	 * @return
	 */
	@RequestMapping("/form")
	public String form( Model model, @RequestParam Map<String, Object> params) {
		if( !params.getOrDefault("boardId", "").equals("")) {
			params.put("username", CommonUtils.getUser().getUsername());
			model.addAttribute("board", service.getBoard(params));
		}
		return "/main/main_form";
	}

	/**
	 * 게시판 생성 액션
	 * @param model
	 * @return
	 */
	@RequestMapping("/insert")
	public String insert( Model model, @RequestParam Map<String, Object> params) {
		if( params.getOrDefault("boardId", "").equals("")) {
			service.insertBoard(params);
		} else {
			service.updateBoard(params);
		}
		return "redirect:/board/main";
	}
	
	/**
	 * 게시판 삭제
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete( Model model
			, RedirectAttributes redirect
			, @RequestParam Map<String, Object> params) {
		params.put("username", CommonUtils.getUser().getUsername());
		service.deleteBoard(params);
		return "redirect:/board/main";
	}
}
