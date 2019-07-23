package com.test.book.service;

import java.util.Map;

public interface BookService {

	// 책 목록 페이징 처리
	public abstract Map<String, Object> getBookListPaging(int totalContent, int pageNo, String keyWord);
}
