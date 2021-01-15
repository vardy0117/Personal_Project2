<%@page import="board.BoardBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
	.aTag a{
		text-decoration: none;
		color: black;
	}
	.boardList{
		width: 1450px;
		height: 50px;
		background: #1d809f;
		list-style: none;
		margin: 0;
		padding: 0;
	    text-align: center;
	}
	.boardList li{
		float: left;
		width: 25%;
		height: 100%;
		line-height: 50px;
		text-align: center;
	}
	.boardList a{
		display:  block;
		text-decoration: none;
		color: #fff;
	}
	.clicked{
		background: orange;
	}

</style>
<body>

<!-- 상단 배너 작업 -->
	<c:if test="${sessionScope.id eq null }">
		<script type="text/javascript">
			alert("로그인 하셔야합니다.");
			location.href="index.jsp";
		</script>
	</c:if>
	<a class="logo" href="index.jsp">To Be a Better Programmer</a>
	<div class="id" style="float: right;">${sessionScope.id }님이 접속중 | <span><a href="logout.do">LOGOUT</a></span> | <span><a href="myPage.do">MYPAGE</a></span></div>
	<hr>
	<ul class="boardList">
		<li><a href="board.do" id="board">자유게시판</a></li>
		<li><a href="notice.do" id="notice">공지사항</a></li>
		<li><a href="covid.do" id="covid">코로나 현황</a></li>
		<li><a href="youtube.do" id="youtube" class="clicked">유튜브</a></li>
	</ul>
	
	
	
	
	
	
</body>
</html>