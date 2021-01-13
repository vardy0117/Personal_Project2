<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	.aTag a{
		text-decoration: none;
		color: black;
	}
	.line{
		background: #ffa500;
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
	<div class="id" style="float: right;">${sessionScope.id }님이 접속중 | <span><a href="logout.do">LOGOUT</a></span> | <span><a href="myPage.do">MYPAGE</a></span></div>
	<hr>
	<div align="center" class="line">내가 쓴 글</div>
	<table class="table table-hover" style="margin-top: 1em;">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>날짜</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${boardList }" var="boardList" varStatus="i">
			<tr onclick="location.href='boardDetail.do?bno=${boardList.bno}&pageNum=${requestScope.pageNum }'">
				<td>${boardList.bno }</td>
				<td>${boardList.title }</td>
				<td>${boardList.writer }</td>
				<td>${boardList.date }</td>
				<td>${boardList.read_count }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	
	
	<div align="center" class="aTag">
		<a href="myPage.do?pageNum=1&commentPage=${commentPage}">[처음]</a>
		<c:if test="${pageNum ne 1 }">
			<a href="myPage.do?pageNum=${pageNum-1 }&commentPage=${commentPage}">[이전]</a>
		</c:if>
		<c:forEach var="i" begin="1" end="${aTag }">
			<a href="myPage.do?pageNum=${i }&commentPage=${commentPage}" id="nowPage${i }">${i }</a>
		</c:forEach>
		<c:if test="${pageNum ne aTag }">
			<a href="myPage.do?pageNum=${pageNum+1 }&commentPage=${commentPage}">[다음]</a>
		</c:if>
		<a href="myPage.do?pageNum=${aTag}&commentPage=${commentPage}">[끝]</a>
	</div>
	<hr>
	
	<!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 댓글 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->
	
	<div align="center" class="line">내가 쓴 댓글 </div>
	<table class="table table-hover">
	<c:forEach items="${requestScope.commentList }" var="commentList">
		<tr onclick="location.href='boardDetail.do?bno=${commentList.bno}&pageNum=${requestScope.pageNum }'">
			<td style="color: #1d809f; font-size: 1em;" id="commentArea${commentList.cno }">
			${commentList.writer }
			&nbsp;<span style="color: gray; font-size: 0.5em;">${commentList.date }</span>
			&nbsp;<pre><span style="color: #000;">${commentList.comment }</span></pre>
			</td>
		</tr>
	</c:forEach>
	</table>
	
	
	
	<div align="center" class="aTag">
		<a href="myPage.do?commentPage=1&pageNum=${pageNum }">[처음]</a>
		<c:if test="${commentPage ne 1 }">
			<a href="myPage.do?commentPage=${commentPage-1 }&pageNum=${pageNum }">[이전]</a>
		</c:if>
		<c:forEach var="i" begin="1" end="${commentAtag }">
			<a href="myPage.do?commentPage=${i }&pageNum=${pageNum }" id="nowCommentPage${i }">${i }</a>
		</c:forEach>
		<c:if test="${commentPage ne commentAtag }">
			<a href="myPage.do?commentPage=${commentPage+1 }&pageNum=${pageNum }">[다음]</a>
		</c:if>
		<a href="myPage.do?commentPage=${commentAtag}&pageNum=${pageNum }">[끝]</a>
	</div>
</body>
<script type="text/javascript">
	var pageNum = ${pageNum};
	document.getElementById("nowPage"+pageNum).style.background="black";
	document.getElementById("nowPage"+pageNum).style.color="#fff";
	
	var commentPage = ${commentPage};
	document.getElementById("nowCommentPage"+commentPage).style.background="black";
	document.getElementById("nowCommentPage"+commentPage).style.color="#fff";
</script>
</html>