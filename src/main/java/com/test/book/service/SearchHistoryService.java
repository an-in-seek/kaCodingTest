package com.test.book.service;

import java.util.List;
import java.util.Map;

import com.test.book.vo.SearchHistory;

public interface SearchHistoryService {

	//저장하기
	public abstract void save(SearchHistory entity);
	
	//내 검색 히스토리 리스트 가져오기
	public abstract Map<String, Object> getSearchHistoryListByUserId(Long userId, int pageNo);
	
	//검색어 순위 리스트 가져오기
	public abstract  List<Map<String, Object>> getSearchRankingList();
}
