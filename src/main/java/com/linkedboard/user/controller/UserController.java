package com.linkedboard.user.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.linkedboard.utils.CommonUtils;
import com.linkedboard.utils.JsonView;

@Controller
@RequestMapping({"/user", "/"})
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired 
	private JavaMailSender mailSender;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
	
	/**
	 * 비밀번호 변경 링크 전송
	 * @param model
	 * @param request
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changePwd")
	public JsonView changePwd(Model model, HttpServletRequest request
			, @RequestParam Map<String, Object> params) {
		String username = params.get("username") + "";
		int cnt = service.getUsernameCount( username);
		if( cnt == 0) {
			return new JsonView(false);
		} else {
			try {
				List<Map<String, Object>> list = service.selectUser(params);
				if( list != null) {
					
					String url = request.getRequestURL().toString();
					String sendUrl = url.substring(0, url.lastIndexOf("/") + 1) + "changePassword?data=";
					
					Calendar caln = Calendar.getInstance();
					caln.add(Calendar.MINUTE, 10);
					
					String encodeText = CommonUtils.stringEncoder( username + "_" + dateFormat.format(caln.getTime()));
					
					StringBuffer sb = new StringBuffer();
					sb.append("[");
					sb.append(username);
					sb.append("]");
					sb.append("비밀번호 변경 링크입니다.<br>");
					sb.append("링크는 10분동안 유효하며 이후 만료되니 만료 이전 비밀번호를 변경하세요.<br>");
					sb.append(sendUrl + encodeText);
					
					Map<String, String> mail = new HashMap<String, String>(); 
					
					mail.put("address", list.get(0).get("email") + "");
					mail.put("subject", "[게시판] 비밀번호 변경 링크입니다.");
					mail.put("text", sb.toString());
					
					CommonUtils.sendMail(mailSender, mail);
					
					// 계정을 잠금 상태로 업데이트한다.
					params.put("status", "L");
					service.updateUserStatus(params);
				} else {
					return new JsonView(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new JsonView(false);
			} 
			return new JsonView(true);
		}
	}
	
	/**
	 * 비밀번호 변경 페이지
	 * @param model
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/changePassword")
	public String changePassword(Model model, HttpServletRequest request
			, @RequestParam Map<String, Object> params) throws ParseException {
		
		String data = CommonUtils.stringDecoder(params.get("data") + "");
		
		String arr[] = data.split("_");
		
		int compare = new Date().compareTo( dateFormat.parse(arr[1]));
		
		if( compare > 0) {
			model.addAttribute("expired", "비밀번호 찾기 링크가 만료되었습니다.");
		} else {
			
			params.put("username", arr[0]);
			List<Map<String, Object>> list = service.selectUser(params);
			
			if( list != null) {
				if( list.get(0).get("status").equals("A")) {
					model.addAttribute("expired", "비밀번호 찾기 링크가 만료되었습니다.");
				} else {
					model.addAttribute("rParam", params);
				}
			}
		}
		return "/user/change_password";
	}
	
	/**
	 * 개인정보 변경
	 * @param model
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/updateUser")
	public String updateUser(Model model, HttpServletRequest request
			, @RequestParam Map<String, Object> params) throws ParseException {
		
		if( !params.getOrDefault("data", "").equals("")) {
			String data = CommonUtils.stringDecoder(params.get("data") + "");
			String arr[] = data.split("_");
			
			params.put("username", arr[0]);
			params.put("status", "A");
		}	
		
		service.updateUser(params);
		
		return "/user/login";
	}
}
