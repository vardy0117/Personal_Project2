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
</style>
<body>
	<c:if test="${sessionScope.id eq null }">
		<script type="text/javascript">
			alert("로그인 하셔야합니다.");
			location.href="index.jsp";
		</script>
	</c:if>
	
	<a class="logo" href="index.jsp">To Be a Better Programmer</a>
	<div class="id" style="float: right;">${sessionScope.id }님이 접속중 | <span><a href="logout.do">LOGOUT</a></span></div>
	<hr>
	<form action="writeUpload.do" method="post">
		<table align="center" class="table">
			<tbody>
				<tr>
					<th>작성자</th>
					<td>${sessionScope.id }</td>
				</tr>
				<tr>
					<th>글 제목</th>
					<td><input type="text" style="width: 500px;" name="title"></td>
				</tr>
				<tr>
					<th>글 내용</th>
					<td><textarea cols="60" rows="10" style="resize: none;" name="content"></textarea></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="글쓰기" class="btn" style="border: 1px solid; background: #1d809f; border-color: #1d809f; color: #fff;"></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>