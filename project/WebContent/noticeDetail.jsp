<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
	<table class="table">
		<tr>
			<th>글 번호</th>
			<td>${requestScope.nBean.nno }</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${requestScope.nBean.date }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>관리자</td>
		</tr>
		<tr>
			<th>글 제목</th>
			<td>${nBean.title }</td>
		</tr>
		<tr>
			<th>글 내용</th>
			<td><img src="noticeUpload/${nBean.file }" style="max-width: 700px; max-height: 500px;"> <pre>${nBean.content }</pre></td>
		</tr>
		<tr>
			<td colspan="3">
				<input type="button" onclick="location.href='notice.do?noticePageNum=${requestScope.noticePageNum}'" value="글목록"  class="btn" style="border: 1px solid; background: #1d809f; border-color: #1d809f; color: #fff;">
			</td>
		</tr>
	</table>
</body>
</html>