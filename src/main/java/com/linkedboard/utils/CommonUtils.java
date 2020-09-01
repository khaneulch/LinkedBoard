package com.linkedboard.utils;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.Map;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.mail.javamail.JavaMailSender;
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
	
	/**
	 * 메일 전송
	 * @return
	 */
	public static void sendMail( JavaMailSender mailSender, Map<String, String> mail) throws Exception{
		MimeMessage message = mailSender.createMimeMessage();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress( mail.get("address")));
        message.setSubject( mail.get("subject"));
        message.setContent(mail.get("text"), "text/html; charset=UTF-8");
		mailSender.send(message); 
	}
	
	/**
	 * 인코딩
	 * @return
	 */
	public static String stringEncoder( String text) {
		byte[] targetBytes = text.getBytes();  
		
		Encoder encoder = Base64.getEncoder(); 
		byte[] encodedBytes = encoder.encode(targetBytes);
		
		return new String(encodedBytes);
	}
	
	/**
	 * 디코딩
	 * @return
	 */
	public static String stringDecoder( String text) {
		byte[] targetBytes = text.getBytes();  
		
		Decoder decoder = Base64.getDecoder(); 
		byte[] decodedBytes = decoder.decode(targetBytes);
		
		return new String(decodedBytes);
	}
}
