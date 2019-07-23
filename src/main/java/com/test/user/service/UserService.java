package com.test.user.service;

import com.test.user.vo.User;

public interface UserService {
	
	//저장하기
	public abstract void save(User user);

	//username으로 찾기
	public abstract User findByUsername(String username);
}
