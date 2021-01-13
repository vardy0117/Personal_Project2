<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<title>이태우의 포트폴리오</title>
</head>
<body>
<style>
	.logo{
		font-size: 20px;
		background: orange;
		width: 18%;
		padding: 0.5em;
		margin-bottom: 1em;
		text-decoration: none;
		color: black;
	}
	.logo:hover {
		cursor: pointer;
		text-decoration: none;
		color: black;	
	}
	.aTag a{
		text-decoration: none;
		color: black;
	}
</style>
	<c:if test="${sessionScope.id eq null }">
		<script type="text/javascript">
			alert("로그인 하셔야합니다.");
			location.href="index.jsp";
		</script>
	</c:if>
	<a class="logo" href="index.jsp">To Be a Better Programmer</a>
	<div class="id" style="float: right;">${sessionScope.id }님이 접속중 | <span><a href="logout.do">LOGOUT</a></span> | <span><a href="myPage.do">MYPAGE</a></span></div>
	<hr>
	<form action="updateBoardPro.do" method="post">
		<table class="table">
			<tr>
				<th>글 번호</th>
				<td>${requestScope.bno }</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${requestScope.date }</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${read_count }</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${requestScope.writer }</td>
			</tr>
			<tr>
				<th>글 제목</th>
				<td><input type="text" name="title" value="${requestScope.title }"></td>
			</tr>
			<tr>
				<th>글 내용</th>
				<td><textarea cols="60" rows="10" style="resize: none;" name="content" >${content }</textarea></td>
			</tr>
			<input type="hidden" value="${requestScope.bno }" name="bno">
			<c:if test="${sessionScope.id eq requestScope.writer }">
				<tr>
					<td colspan="2">
						<input type="submit" value="수정하기"  class="btn" style="border: 1px solid; background: #1d809f; border-color: #1d809f; color: #fff;">
					</td>
				</tr>
			</c:if>
		</table>
	</form>
</body>
</html>