<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
		<link href="${contextPath}/resources/css/common.css" rel="stylesheet">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script type="text/javascript">
			//페이징 처리
			function paging(contextPath, pageNo, keyWord) {
				keyWord = encodeURI(keyWord);
				location.href = contextPath + "/getBookList?pageNo=" + pageNo + "&keyWord=" + keyWord;
			}

			//상세 정보 페이지로 이동
			function goBookInfoPage(contextPath, isbn, pageNo, keyWord) {
				keyWord = encodeURI(keyWord);
				location.href=contextPath +"/bookInfo?isbn=" + isbn + "&pageNo=" + pageNo + "&keyWord=" + keyWord;
			}
		</script>
		<title>카카오 책 목록</title>
	</head>
	<body>
		<div class="container">
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<form id="logoutForm" method="POST" action="${contextPath}/logout">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div>${pageContext.request.userPrincipal.name}님환영합니다. 
					| <a href="${contextPath}/bookList" >책 목록</a>
					| <a href="${contextPath}/searchHistoryList" >내 검색 히스토리</a>
					| <a href=# onclick="document.forms['logoutForm'].submit()">로그아웃</a>
					</div>
				</form>
			</c:if>
		</div>
	
		<br>
	
		<div class="container">
			<form id='searchForm' method="GET" action="/getBookList">
				<input type="hidden" name="pageNo" id="pageNo" value="1"/>
				<input type='text' id='keyWord' name='keyWord' placeholder='검색할 키워드를 입력하세요.'  value="${requestScope.keyWord}" class="search-textbox" required /> 
				<input type='submit' id='search' value='검색' />
				<div class="div-align-right">
					<b>검색어 순위:</b> 
					<select id="searchRanking" name="searchRanking" class="search-ranking-combobox" >
						<c:choose>
						<c:when test="${fn:length(requestScope.searchRankingList) > 0 }">
							<c:forEach items="${requestScope.searchRankingList}" var="searchRanking">
								<option value="${searchRanking.ranking}">${searchRanking.ranking}위. ${searchRanking.keyword}&nbsp&nbsp&nbsp(검색횟수:${searchRanking.cnt})</option>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<option>데이터가 없습니다.</option>
						</c:otherwise>
					</c:choose>
					</select>
					</div>
			</form>
		</div>
		
		<br>
		
		<div class="container">
			<table class="border table-width">
				<thead>
					<tr>
						<th>썸네일</th>
						<th>제목</th>
						<th>저자</th>
						<th>출판사</th>
						<th hidden=true>isbn</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(requestScope.bookList) > 0 }">
							<c:forEach items="${requestScope.bookList}" var="book">
								<tr class='list-tr'>
									<td><img src="${book.thumbnail}"/></td>
									<td><a href=# onclick="goBookInfoPage('${contextPath}', '${book.isbn}', '${pagingBean.currentPage}', '${requestScope.keyWord}');">${book.title}</a></td>
									<td>${book.authors}</td>
									<td>${book.publisher}</td>
									<td hidden=true>${book.isbn}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
								<tr>
									<td colspan=4 class='text-align-center'>조회된 데이터가 없습니다.</td>
								</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			<br>
			
			<c:if test="${fn:length(requestScope.bookList) > 0 }">
				<table class="table-width">
					<tr>
						<td>
							<p align="center">
								<!-- 페이징 처리 -->
								<!-- 이전 페이지 그룹 -->
								<c:choose>
									<c:when test="${pagingBean.previousPageGroup}">
										<a href="#" onclick="paging('${contextPath}', '${pagingBean.startPageOfPageGroup-1}', '${requestScope.keyWord}')">◀&nbsp;</a>
									</c:when>
									<c:otherwise>◀&nbsp;</c:otherwise>
								</c:choose>
								<!-- 페이지 번호 -->
								<c:forEach begin="${pagingBean.startPageOfPageGroup }" end="${pagingBean.endPageOfPageGroup}" var="pageNo">
									<c:choose>
										<c:when test="${pageNo == pagingBean.currentPage }">
											&nbsp;<font color="blue" style="font-weight: bold; text-decoration: underline">${pageNo}</font>&nbsp;
										</c:when>
										<c:otherwise>
											<a href="#" onclick="paging('${contextPath}', '${pageNo}', '${requestScope.keyWord}')">&nbsp;${pageNo}&nbsp;</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<!-- 다음 페이지 그룹 -->
								<c:choose>
									<c:when test="${pagingBean.nextPageGroup }">
										<a href="#" onclick="paging('${contextPath}', '${pagingBean.endPageOfPageGroup+1}', '${requestScope.keyWord}')">&nbsp;▶</a>
									</c:when>
									<c:otherwise>&nbsp;▶</c:otherwise>
								</c:choose>
							</p>
						</td>
					</tr>
				</table>
			</c:if>
		</div>
	</body>
</html>