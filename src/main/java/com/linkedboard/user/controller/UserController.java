package com.linkedboard.user.controller;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkedboard.user.service.UserService;
import com.linkedboard.utils.JsonView;

@Controller
@RequestMapping({"/user", "/"})
public class UserController {
	
	@Autowired
	private UserService service;

	/**
	 * 회원가입 폼
	 * @param model
	 * @return
	 */
	@RequestMapping("/register")
	public String register(Model model) {
		
		// 이미 로그인된 경우 메인으로 리다이렉트
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if ( auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
		    return "redirect:/main";
		}
		
		return "/user/register";
	}
	
	/**
	 * 회원가입 액션
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping("/registerProc")
	public String registerProc(Model model, CustomUserDetails user) {
		service.createAccount(user);
		return "redirect:/user/login";
	}
	
	/**
	 * 로그인폼
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping({"/login", ""})
	public String login(Model model, HttpServletRequest request) {
		
		// 이미 로그인된 경우 메인으로 리다이렉트
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if ( auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			String redirectUrl = "redirect:/board/main";
			if( auth != null && auth.isAuthenticated()) {
				
				Collection<? extends GrantedAuthority> iter = auth.getAuthorities();
				for( GrantedAuthority authority : iter) {
					if( authority.getAuthority().equals("ROLE_ADMIN")) {
						redirectUrl = "redirect:/admin/main";
					}
				}
			}
			
		    return redirectUrl;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("Referer", request.getHeader("Referer"));
		
		return "/user/login";
	}
	
	/**
	 * 아이디 중복체크 액션
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/duplicate")
	public JsonView duplicate(Model model, HttpServletRequest request
			, @RequestParam Map<String, Object> params) {
		int cnt = service.getUsernameCount(params.get("username") + "");
		if( cnt == 0) {
			return new JsonView(true);
		} else {
			return new JsonView(false);
		}
	}
}
