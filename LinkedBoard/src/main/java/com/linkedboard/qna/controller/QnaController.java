package com.linkedboard.qna.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.linkedboard.qna.service.QnaService;
import com.linkedboard.utils.CommonUtils;
import com.linkedboard.utils.PaginationUtils;

@Controller
@RequestMapping("/board/qna")
public class QnaController {

	@Autowired
	QnaService service;
	
	/**
	 * qna 목록
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	public String list( Model model, @RequestParam Map<String, Object> params) {
		params.put("username", CommonUtils.getUser().getUsername());
		PaginationUtils.setPage(service.getQnaCnt(params), params, model);
		model.addAttribute("list", service.selectQna(params));
		model.addAttribute("rParam", params);
		return "/qna/qna_list";
	}
	
	/**
	 * qna 상세
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/view")
	public String view( Model model, @RequestParam Map<String, Object> params) {
		Map<String, Object> qna = service.getQna(params);
		if( qna.get("regUser").equals(CommonUtils.getUser().getUsername())) {
			model.addAttribute("qna", qna);
		}
		return "/qna/qna_view";
	}
	
	/**
	 * qna 등록폼
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/form")
	public String form( Model model, @RequestParam Map<String, Object> params) {
		if( !params.getOrDefault("qnaId", "").equals("")) {
			model.addAttribute("qna", service.getQna(params));
		}
		model.addAttribute("rParam", params);
		return "/qna/qna_form";
	}
	
	/**
	 * qna 등록/수정
	 * @param model
	 * @param params
	 * @return
	 */
	@RequestMapping("/insert")
	public String insert( Model model, @RequestParam Map<String, Object> params) {
		service.insertQna(params);
		return "redirect:/board/qna/view?qnaId=" + params.get("qnaId");
	}
}
