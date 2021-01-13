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
			<td>${title }</td>
		</tr>
		<tr>
			<th>글 내용</th>
			<td><img src="upload/${file }"> <pre>${content }</pre></td>
		</tr>
	</table>
	<table class="table">
		<tr>
			<th colspan="1">댓글${fn:length(commentList) }</th>
		</tr>
	<c:forEach items="${requestScope.commentList }" var="commentList">
		<tr>
			<td style="color: #1d809f; font-size: 1em;" id="commentArea${commentList.cno }">
			${commentList.writer }
			&nbsp;<span style="color: gray; font-size: 0.5em;">${commentList.date }</span>
			<c:if test="${commentList.writer eq sessionScope.id }">
				<a onclick="deleteComment(${commentList.cno})" style="color: #000; text-decoration: none; font-size: 0.5em; cursor: pointer;">[삭제]</a>
			</c:if>
			&nbsp;<pre><span style="color: #000;">${commentList.comment }</span></pre>
			</td>
		</tr>
	</c:forEach>
		<tr>
			<td colspan="3">
				<textarea rows="5" cols="50" style="resize: none;" name="comment" id="comment"></textarea>
				<input type="button" value="댓글달기" class="btn" onclick="uploadComment(${requestScope.bno})" style="border: 1px solid; background: #1d809f; border-color: #1d809f; color: #fff;">	
			</td>
		</tr>
		<c:choose>
			<c:when test="${sessionScope.id eq requestScope.writer }">
				<tr>
					<td colspan="3">
						<input type="button" onclick="location.href='deleteBoard.do?bno=${requestScope.bno}'" value="글삭제"  class="btn" style="border: 1px solid; background: #1d809f; border-color: #1d809f; color: #fff;">
						<input type="button" onclick="location.href='updateBoard.do?bno=${requestScope.bno}'" value="글수정"  class="btn" style="border: 1px solid; background: #1d809f; border-color: #1d809f; color: #fff;">
						<input type="button" onclick="location.href='board.do?pageNum=${requestScope.pageNum}'" value="글목록"  class="btn" style="border: 1px solid; background: #1d809f; border-color: #1d809f; color: #fff;">
					</td>
				</tr>
			</c:when>
			<c:when test="${sessionScope.id ne requestScope.writer }">
				<tr>
					<td colspan="3">
						<input type="button" onclick="location.href='board.do?pageNum=${requestScope.pageNum}'" value="글목록"  class="btn" style="border: 1px solid; background: #1d809f; border-color: #1d809f; color: #fff;">
					</td>
				</tr>
			</c:when>
		</c:choose>
	</table>
</body>
<script type="text/javascript">
	function uploadComment(bno) {
		var comment = document.getElementById("comment").value;
		var bno = bno;
		$.ajax({
			data:{comment,bno},
			async:true,
			url:"uploadComment.do",
			success:function(data){
				if(data=="ok"){
					alert("댓글등록이 완료되었습니다.");
					location.reload();
				}else{
					alert("댓글등록이 실패하였습니다.")
				}
			},error:function(error){
				alert(error);
			}
		}); // ajax 끝
	}// uploadComment() 끝
	
	
	function deleteComment(cno){
		var cno = cno;
		$.ajax({
			data:{cno},
			url:"deleteComment.do",
			success:function(data){
				if(data=="delete"){
					alert("삭제되었습니다.");
					document.getElementById("commentArea"+cno).remove();
				}
			},error:function(error){
				alert(error);
			}
		});//ajax끝
	}//deleteComment() 끝
</script>
</html>