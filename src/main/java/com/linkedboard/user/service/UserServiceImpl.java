package com.linkedboard.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.linkedboard.user.controller.CustomUserDetails;
import com.linkedboard.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	UserService userService;
	
	@Autowired
	public void setUserService( UserService userService) {
		this.userService = userService; 
	}
	
	@Autowired 
	UserMapper mapper;
	
	private PasswordEncoder passwordEncoder;
    
    @Autowired
    void PasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	/**
	 * 로그인처리
	 */
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUserDetails user = mapper.loadUserByUsername(username);
		return new User(
				user.getUsername()
				, user.getPassword()
				, user.isEnabled()
				, user.isAccountNonExpired()
				, user.isCredentialsNonExpired()
				, user.isAccountNonLocked()
				, selectAuthority(username) );
	}

	/** 계정에 해당되는 권한을 불러옴
	 * @param username
	 * @return
	 */
	public Collection<GrantedAuthority> selectAuthority(String username) {
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	List<String> string_authorities = mapper.selectAuthority(username);
    	for (String authority : string_authorities) {
    		authorities.add(new SimpleGrantedAuthority(authority));
    	}
    	return authorities;
	}

	
	/**
	 * 계정생성
	 */
	@Override
	public void createAccount( CustomUserDetails user) { 
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		mapper.createAccount(user);
		
		Map<String, Object> auth = new HashMap<String, Object>();
		auth.put("username", user.getUsername());
		auth.put("authority", "ROLE_USER");
		mapper.createAuthority(auth);
	}
	/**
	 * 아이디 중복체크
	 */
	@Override
	public int getUsernameCount(String username) {
		return mapper.getUsernameCount(username);
	}

	/**
	 * 메뉴 리스트
	 */
	@Override
	public List<Map<String, Object>> selectMenu(String authority) {
		return mapper.selectMenu(authority);
	}

	/**
	 * 사용자 리스트 카운트
	 */
	@Override
	public int getUserCnt(Map<String, Object> params) {
		return mapper.getUserCnt(params);
	}

	/**
	 * 사용자 리스트
	 */
	@Override
	public List<Map<String, Object>> selectUser(Map<String, Object> params) {
		return mapper.selectUser(params);
	}
	
	/**
	 * 로그인 실패 카운트 업데이트
	 */
	@Override
	public void updateUserLoginFailCount(Map<String, Object> params) {
		mapper.updateUserLoginFailCount(params);
	}

	/**
	 * 계정 상태 업데이트
	 */
	@Override
	public void updateUserStatus(Map<String, Object> params) {
		mapper.updateUserStatus(params);
	}

	/**
	 * 사용자 차단
	 */
	@Override
	public void blockUser(Map<String, Object> params) {
		String usernames = params.getOrDefault("usernames", "") + "";
		if( !usernames.equals("")) {
			params.put("usernames", usernames.split(","));
			params.put("status", "B");
			mapper.updateUserStatus(params);
		}
	}
}