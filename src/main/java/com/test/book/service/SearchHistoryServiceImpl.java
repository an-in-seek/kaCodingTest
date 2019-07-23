package com.test.book.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.book.dao.SearchHistoryRepository;
import com.test.book.vo.SearchHistory;
import com.test.common.util.PagingBean;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {
	@Autowired
	SearchHistoryRepository seaerchHistoryRepository;

	/**
	 * 저장하기
	 */
	@Override
	@Transactional
	public void save(SearchHistory entity) {
		seaerchHistoryRepository.save(entity);
	}

	/**
	 * UserId로 검색 히스토리 리스트 가져오기
	 */
	@Override
	public Map<String, Object> getSearchHistoryListByUserId(Long userId, int pageNo) {
		List<SearchHistory> searchHistoryList = seaerchHistoryRepository.getSearchHistoryList(userId, pageNo, PagingBean.CONTENTS_PER_PAGE);
		int totalContent = seaerchHistoryRepository.selectTotalCount(userId);
		PagingBean pagingBean = new PagingBean(totalContent, pageNo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchHistoryList", searchHistoryList);
		map.put("pagingBean", pagingBean);
		return map;
	}

	/**
	 * 검색어 순위 리스트 가져오기
	 */
	@Override
	public List<Map<String, Object>> getSearchRankingList() {
		List<Map<String, Object>> searchRankingList = seaerchHistoryRepository.getSearchRankingList();
		return searchRankingList;
	}
}
