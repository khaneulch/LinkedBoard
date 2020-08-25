package com.linkedboard;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.linkedboard.user.service.UserService;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private final UserService userService;
    
    private String loginidname;
    private String defaultUrl;
    
    public LoginSuccessHandler(UserService userService) { 
    	this.userService = userService;
    }

    /**
     * 로그인 성공시 액션
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    	
    	String username = request.getParameter("username");
    	
    	// 로그인 실패카운트 초기화
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("username", username);
		userMap.put("reset", "Y");
    	userService.updateUserLoginFailCount(userMap);
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("username", username);
		
		String redirectURL = "/board/main";
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); 
		Iterator<? extends GrantedAuthority> iter = authorities.iterator(); 
		while (iter.hasNext()) { 
			GrantedAuthority auth = iter.next();
			
			// 권한에 따른 메뉴 세팅
			session.setAttribute("menu", userService.selectMenu(auth.getAuthority()));
			
			// 관리자 권한을 가진경우 관리자 메인 페이지로 이동하도록
			if( auth.getAuthority().equals("ROLE_ADMIN")) {
				redirectURL = "/admin/main";
				break;
			}
		}

		response.sendRedirect( redirectURL);
    }
 
    public String getLoginidname() {
        return loginidname;
    }
 
    public void setLoginidname(String loginidname) {
        this.loginidname = loginidname;
    }
 
    public String getDefaultUrl() {
        return defaultUrl;
    }
 
    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
}