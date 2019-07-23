package com.test.book.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface KakaoBookService {

	//카카오 책 메타 정보 가져오기
	public abstract JSONObject getKakaoBookMeta(String keyWord, String target, int pageNo);
	
	//카카오 책 세부 정보 가져오기
	public abstract JSONArray getKakaoBookInfo(String keyWord, String target, int pageNo);
	
	//카카오 책 목록 가져오기
	public abstract JSONArray getKakaoBookList(String keyWord, String target, int pageNo);
}
