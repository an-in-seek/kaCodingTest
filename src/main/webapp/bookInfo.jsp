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
			function goBack(contextPath, pageNo, keyWord) {
				keyWord = encodeURI(keyWord);
				location.href = contextPath + "/getBookList?pageNo="+pageNo+"&keyWord="+keyWord;
			}
		</script>
		<title>카카오 책 상세정보</title>
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
			<c:choose>
				<c:when test="${fn:length(requestScope.bookInfo) > 0 }">
					<table class="table-width">
						<!--  제목, 도서 썸네일, 소개, ISBN, 저자, 출판사, 출판일, 정가, 판매가  -->
						<tr>
							<td>제목</td>
							<td><input type='text' id='title' name='title' class='textbox' value='${requestScope.bookInfo[0].title}' readonly /></td>
						</tr>
						<tr>
							<td>ISBN</td>
							<td><input type='text' id='isbn' name='isbn' class='textbox' value='${requestScope.bookInfo[0].isbn}' readonly /></td>
						</tr>
						<tr>
							<td>저자</td>
							<td><input type='text' id='authors' name='authors' class='textbox' value='${requestScope.bookInfo[0].authors}' readonly /></td>
						</tr>
						<tr>
							<td>출판사</td>
							<td><input type='text' id='publisher' name='publisher' class='textbox' value='${requestScope.bookInfo[0].publisher}' readonly /></td>
						</tr>
						<tr>
							<td>출판일</td>
							<td><input type='datetime' id='datetime' name='datetime' class='textbox' value='${requestScope.bookInfo[0].datetime}' readonly /></td>
						</tr>
						<tr>
							<td>정가</td>
							<td><input type='text' id='price' name='price' class='textbox' value='${requestScope.bookInfo[0].price}' readonly /></td>
						</tr>
						<tr>
							<td>판매가</td>
							<td><input type='text' id='sale_price' name='sale_price' class='textbox' value='${requestScope.bookInfo[0].sale_price}' readonly /></td>
						</tr>
						<tr>
							<td>소개</td>
							<td><textarea id='contents' name='contents' class='textarea' readonly>${requestScope.bookInfo[0].contents}</textarea></td>
						</tr>
						<tr>
							<td>도서 썸네일</td>
							<td><img id='thumbnail' name='thumbnail'  src='${requestScope.bookInfo[0].thumbnail}'/></td>
						</tr>
					</table>
				</c:when>
			</c:choose>
		</div>
	</body>
	
	<div class="container">
		<input id="btnBack" type="button" value="뒤로가기" onclick="goBack('${contextPath}', '${requestScope.pageNo}', '${requestScope.keyWord}')"/>
	</div>

</html>