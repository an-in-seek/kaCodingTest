package com.test.book.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.test.book.service.BookService;
import com.test.book.service.KakaoBookService;
import com.test.book.service.SearchHistoryService;
import com.test.book.vo.SearchHistory;
import com.test.user.service.SecurityService;
import com.test.user.service.UserService;
import com.test.user.vo.User;

@Controller
public class BookController {

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;

	@Autowired
	private KakaoBookService kakaoBookService;

	@Autowired
	private SearchHistoryService searchHistoryService;

	@RequestMapping("/getBookList")
	public ModelAndView getBookList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam String keyWord) {
		
		String target = "title";
		int totalContent = 0;
		String username = securityService.findLoggedInUsername();
		User user = userService.findByUsername(username);
		searchHistoryService.save(new SearchHistory(keyWord, Timestamp.valueOf(LocalDateTime.now()), user));
		JSONObject meta = kakaoBookService.getKakaoBookMeta(keyWord, target, pageNo);
		JSONArray bookList = kakaoBookService.getKakaoBookList(keyWord, target, pageNo);
		totalContent = Integer.parseInt(meta.get("pageable_count").toString());
		Map<String, Object> map = bookService.getBookListPaging(totalContent, pageNo, keyWord);
		map.put("bookList", bookList);
		map.put("searchRankingList", searchHistoryService.getSearchRankingList());
		map.put("keyWord", keyWord);
		map.put("meta", meta);

		return new ModelAndView("bookList", map);
	}

	@GetMapping("/bookInfo")
	public ModelAndView getBookDetail(@RequestParam(defaultValue = "1") int pageNo, @RequestParam String keyWord, @RequestParam String isbn) {
		
		String target = "isbn";
		JSONObject meta = kakaoBookService.getKakaoBookMeta(isbn, target, pageNo);
		JSONArray bookInfo = kakaoBookService.getKakaoBookInfo(isbn, target, pageNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", pageNo);
		map.put("keyWord", keyWord);
		map.put("meta", meta);
		map.put("bookInfo", bookInfo);

		return new ModelAndView("bookInfo", map);
	}

	@RequestMapping("/searchHistoryList")
	public ModelAndView searchHistory(@RequestParam(defaultValue = "1") int pageNo) {
		
		String username = securityService.findLoggedInUsername();
		User user = userService.findByUsername(username);
		Map<String, Object> map = searchHistoryService.getSearchHistoryListByUserId(user.getId(), pageNo);
		
		return new ModelAndView("searchHistoryList", map);
	}
	

}
