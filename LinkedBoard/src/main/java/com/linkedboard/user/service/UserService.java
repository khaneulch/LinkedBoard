package com.linkedboard.user.service;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.linkedboard.user.controller.CustomUserDetails;

public interface UserService extends UserDetailsService{
	void createAccount( CustomUserDetails user);
	int getUsernameCount( String username);
	List<Map<String, Object>> selectMenu(String authority);
	int getUserCnt(Map<String, Object> params);
	List<Map<String, Object>> selectUser(Map<String, Object> params);
	void updateUserLoginFailCount(Map<String, Object> params);
	void updateUserStatus(Map<String, Object> userMap);
	void blockUser(Map<String, Object> params);
}
