package com.linkedboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import com.linkedboard.user.service.UserService;

public class LoginFailureHandler implements AuthenticationFailureHandler{

	private final UserService userService;
	
    public LoginFailureHandler(UserService userService) {
    	this.userService = userService;
    }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("username", request.getParameter("username"));
		
		String errorCode = "?error=1";
		if( exception instanceof LockedException) {					// 계정 잠김
			errorCode = "?error=2";
		} else if( exception instanceof DisabledException) {		// 계정 차단
			errorCode = "?error=3";
		} else if( exception instanceof BadCredentialsException) {	// 비밀번호 틀림
			userService.updateUserLoginFailCount(userMap);
			
			if( (int)userMap.get("failCnt") >= 5) {
				userMap.put("status", "L");
				userService.updateUserStatus(userMap);
			}
		}
		
		response.sendRedirect("/user/login" + errorCode);
	}
}
