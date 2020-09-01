package com.linkedboard.user.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import com.linkedboard.user.controller.CustomUserDetails;

@Mapper
public interface UserMapper {
	public CustomUserDetails loadUserByUsername(String username);
	public void createAccount(CustomUserDetails user);
	public void createAuthority(Map<String, Object> auth);
	public List<String> selectAuthority(String username);
	public int getUsernameCount(String username);
	public List<Map<String, Object>> selectMenu(String authority);
	public int getUserCnt(Map<String, Object> params);
	public List<Map<String, Object>> selectUser(Map<String, Object> params);
	public void updateUserLoginFailCount(Map<String, Object> params);
	public void updateUserStatus(Map<String, Object> params);
	public void updateUser(Map<String, Object> params);
}