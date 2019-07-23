package com.test.book.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.test.user.vo.User;

@Entity
@Table(name = "search_history")
public class SearchHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String keyword;

	@Column(nullable = false)
	private Timestamp searchdate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	public SearchHistory() {
	}

	public SearchHistory(String keyword, Timestamp searchdate, User user) {
		super();
		this.keyword = keyword;
		this.searchdate = searchdate;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Timestamp getSearchdate() {
		return searchdate;
	}

	public void setSearchdate(Timestamp searchdate) {
		this.searchdate = searchdate;
	}

	public User getUser() {
		return user;
	}

	public void setUserid(User user) {
		this.user = user;
	}

}
