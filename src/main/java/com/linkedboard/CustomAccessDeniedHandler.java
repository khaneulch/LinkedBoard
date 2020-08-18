package com.linkedboard;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		String redirectUrl = "/user/login";

		if( accessDeniedException instanceof AccessDeniedException) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if( auth != null && auth.isAuthenticated()) {
				

				Collection<? extends GrantedAuthority> iter = auth.getAuthorities();
				for( GrantedAuthority authority : iter) {
					if( authority.getAuthority().equals("ROLE_ADMIN")) {
						redirectUrl = "/admin/main?error=99";
					} else if( authority.getAuthority().equals("ROLE_USER")) {
						redirectUrl = "/board/main?error=99";
					}
				}
			}
		}
		response.sendRedirect(redirectUrl);
	}
}