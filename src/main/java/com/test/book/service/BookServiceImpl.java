package com.test.book.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.test.common.util.PagingBean;

@Service
public class BookServiceImpl implements BookService {

	
	/**
	 *책 목록 페이징 처리
	 */
	@Override
	public Map<String, Object> getBookListPaging(int totalContent, int pageNo, String keyWord) {
		PagingBean pagingBean = new PagingBean(totalContent, pageNo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pagingBean", pagingBean);
		return map;
	}
}
