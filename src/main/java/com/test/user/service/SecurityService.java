package com.test.user.service;

public interface SecurityService {
	
	//Username 으로 가져오기
	public abstract String findLoggedInUsername();

	//자동 로그인
	public abstract void autoLogin(String username, String password);
}
