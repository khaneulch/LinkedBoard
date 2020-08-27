package com.linkedboard.mgnt.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.linkedboard.community.service.CommunityService;
import com.linkedboard.mgnt.service.MgntService;
import com.linkedboard.utils.CommonUtils;
import com.linkedboard.utils.JsonView;
import com.linkedboard.utils.PaginationUtils;

@Controller
@RequestMapping("/board/mgnt")
public class MgntController {
	
	@Autowired 
	MgntService service;
	
	@Autowired 
	CommunityService communityService;
	
	private PasswordEncoder passwordEncoder;
    
    @Autowired
    void PasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	/**
	 * 게시판내의 게시글 리스트
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list( Model model, @RequestParam Map<String, Object> params) {
		PaginationUtils.setPage(service.getContentBoardCnt(params), params, model);
		model.addAttribute("list", service.selectContentBoard(params));
		model.addAttribute("board", communityService.getBoardInfo(params));
		model.addAttribute("rParam", params);
		return "/mgnt/mgnt_list";
	}
	
	/**
	 * 게시글 등록 폼
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String form( Model model
			, @RequestParam Map<String, Object> params) {
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
				return "redirect:/board/mgnt/view?error=1&boardId=" + params.get("boardId") + "&contentId=" + params.get("contentId");
			}
		}
		model.addAttribute("board", communityService.getBoardInfo(params));
		model.addAttribute("rParam", params);
		return "/mgnt/mgnt_form";
	}
	
	/**
	 * 게시글 등록 액션
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert( Model model
			, HttpServletRequest request
			, MultipartHttpServletRequest mRequest
			, @RequestParam Map<String, Object> params ) {
		
		params.put("regIp", request.getRemoteAddr());
		params.put("adminYn", "Y");
		service.insertBoardContent(params, mRequest.getFile("mgntFile"));
		return "redirect:/board/mgnt/view?boardId=" + params.get("boardId") + "&contentId=" + params.get("contentId");
	}
	
	/**
	 * 게시글 상세
	 * @param model
	 * @return
	 */
	@RequestMapping("/view")
	public String view( Model model, @RequestParam Map<String, Object> params) {
		model.addAttribute("content", service.getBoardContent(params));
		model.addAttribute("rParam", params);
		model.addAttribute("commentList", communityService.selectBoardComment(params));
		model.addAttribute("board", communityService.getBoardInfo(params));
		return "/mgnt/mgnt_view";
	}
	
	/**
	 * 게시글 삭제
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JsonView delete( Model model, @RequestParam Map<String, Object> params) {
		service.deleteBoardContent(params);
		return new JsonView(true);
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
			, RedirectAttributes redirect
			, @RequestParam Map<String, Object> params) {
		params.put("username", CommonUtils.getUser().getUsername());
		params.put("regIp", request.getRemoteAddr());
		params.put("adminYn", "Y");

		redirect.addAttribute("boardId", params.get("boardId"));
		redirect.addAttribute("contentId", params.get("contentId"));
		communityService.insertBoardComment(params);
		return "redirect:/board/mgnt/view";
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
	@ResponseBody
	public JsonView deleteComment( Model model
			, HttpServletRequest request
			, RedirectAttributes redirect
			, @RequestParam Map<String, Object> params) {
		params.compute("username", (k, v) -> v != null ? v : CommonUtils.getUser().getUsername());
		params.put("regIp", request.getRemoteAddr());
		
		service.deleteBoardComment(params);
		return new JsonView(true);
	}
}
