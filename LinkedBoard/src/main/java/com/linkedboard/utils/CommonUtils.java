package com.linkedboard.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class CommonUtils {
	
	/**
	 * 파일명 생성
	 * @return
	 */
	public static String getSavedFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String savedName = sdf.format(new Date());
		return savedName;
	}
	
	/**
	 * service를 리턴함
	 * @param serviceName
	 * @return
	 */
	public static Object serviceLoader( String serviceName) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		
		return context.getBean(serviceName);
	}
	
	/**
	 * 현재 로그인한 사용자 정보 리턴
	 * @return
	 */
	public static User getUser( ) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}
}
