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
				location.href = contextPath + "/searchHistoryList?pageNo=" + pageNo
			}
		</script>
		<title>내 검색 히스토리 목록</title>
	</head>
	<body>
		<div class="container">
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<form id="logoutForm" method="POST" action="${contextPath}/logout">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
				<div>${pageContext.request.userPrincipal.name}님환영합니다. 
				| <a href="${contextPath}/bookList" >책 목록</a>
				| <a href="${contextPath}/searchHistoryList" >내 검색 히스토리</a>
				| <a href=# onclick="document.forms['logoutForm'].submit()">로그아웃</a>
				</div>
			</c:if>
		</div>
	
		<br>
	
		<div class="container">
			<form id='searchForm' method="POST" action="/searchHistoryList">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="hidden" name="pageNo" id="pageNo"/>
				<input type='submit' id='search' value='검색' />
			</form>
		</div>
		
		<br>
		
		<div class="container">
			<table class="border table-width">
				<thead>
					<tr>
						<th>No</th>
						<th>키워드</th>
						<th>검색일시</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(requestScope.searchHistoryList) > 0 }">
							<c:forEach items="${requestScope.searchHistoryList}" var="searchHistory">
								<tr>
									<td>${searchHistory.id}</td>
									<td>${searchHistory.keyword}</td>
									<td>${searchHistory.searchdate}</td>
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
			
			<c:if test="${fn:length(requestScope.searchHistoryList) > 0 }">
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