package com.test.book.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.book.vo.SearchHistory;
import com.test.user.vo.User;


public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>{

	@Query("SELECT a FROM SearchHistory a WHERE a.user = ?1")
	Page<SearchHistory> findByUser(User user, Pageable pageable);

	
	@Query(value = ""
			+ " SELECT a.id, a.keyword, a.searchdate, a.user_id "
			+ " FROM ("
			+ "		SELECT ceil((rownum+0.0) / :contentsPerPage ) page, a.id, a.keyword, a.searchdate, a.user_id "
			+ "		FROM ("
			+ "			SELECT a.id, a.keyword, a.searchdate, a.user_id "
			+ "			FROM search_history a "
			+ "			ORDER BY a.searchdate desc"
			+ "		) a "
			+ ") a "
			+ " WHERE a.user_id = :userId "
			+ " AND a.page = :pageNo"
			, nativeQuery = true)
	List<SearchHistory> getSearchHistoryList(@Param("userId") Long userId, @Param("pageNo")  int pageNo, @Param("contentsPerPage") int contentsPerPage);
	
	
	@Query(value = "SELECT count(a.*) cnt FROM search_history a WHERE a.user_id = :userId", nativeQuery = true)
	int selectTotalCount(@Param("userId") Long userId);
	
	
	@Query(value = ""
			+ " SELECT  x.ranking, x.keyword, x.cnt "
			+ " FROM ( "
			+ " 	SELECT  row_number() over (order by z.cnt desc) ranking, z.keyword, z.cnt"
			+ " 	FROM ( "
			+ "    		SELECT "
			+ "           	a.keyword, count(a.keyword) cnt "
			+ "    		FROM  "
			+ "          	search_history a "
			+ "    		GROUP BY "
			+ "          	a.keyword "
			+ " 	)  z "
			+ " )  x "
			+ " WHERE x.ranking < 11 "
			+ " ORDER BY x.cnt DESC "
			, nativeQuery = true)
	List<Map<String, Object>> getSearchRankingList();
	
}
