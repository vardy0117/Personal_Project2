<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이태우의 포트폴리오</title>
</head>
<body>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style>
.note
{
    text-align: center;
    height: 80px;
	background-color: #1d809f;
    color: #fff;
    font-weight: bold;
    line-height: 80px;
}
.form-content
{
    padding: 5%;
    border: 1px solid #ced4da;
    margin-bottom: 2%;
}
.form-control{
    border-radius:1.5rem;
}
.btnSubmit
{
    border:none;
    border-radius:1.5rem;
    padding: 1%;
    width: 20%;
    cursor: pointer;
    background: #1d809f;
    color: #fff;
}
</style>
<c:if test="${sessionScope.id eq null }">
		<script type="text/javascript">
			alert("로그인 하셔야합니다.");
			location.href="index.jsp";
		</script>
	</c:if>

<div class="container register-form">
            <div class="form">
                <div class="note">
                    <p>To Be a Better Programmer MODIFY</p>
                </div>
				<form action="modifyPro.do" method="post">
	                <div class="form-content">
	                    <div class="row">
	                        <div class="col-md-6">
	                            <!--  <div class="check" id="id_check"></div>-->
	                            <div class="form-group">
	                                <input type="text" class="form-control" placeholder="Your ID *" value="${mBean.id }" name="id" id="id" readonly="readonly"/>
	                            </div>
	                          
	                        </div>
	                        <div class="col-md-6">
	                            <div class="form-group">
	                                <input type="text" class="form-control" placeholder="Your Name *" value="${mBean.name }" name="name"/>
	                            </div>
	                        </div>
	                        <div class="col-md-6">
	                            <div class="form-group">
	                                <input type="text" class="form-control" placeholder="Your Phone *" value="${mBean.phone }" name="phone"/>
	                            </div>
	                        </div>
	                    </div>
<!-- 	                    <input type="submit" class="btnSubmit" value="수정하기" onclick="return checkPwd()"> -->
	                    <input type="submit" class="btnSubmit" value="수정하기">
	                </div>
		                <a href="modifyPwd.do">비밀번호를 수정하고 싶으십니까?</a>
                </form>
            </div>
        </div>
</body>
<script type="text/javascript">
// name="newPwd" 와 name="newRePwd"의 값 비교
function checkPwd() {
	var newPwd = document.getElementById("newPwd").value;
	var newRePwd = document.getElementById("newRePwd").value;
	var oldPwd = document.getElementById("oldPwd").value;
	if(newPwd != newRePwd){
		alert("비밀번호를 확인하세요");
		document.getElementById("newRePwd").focus();
		return false;
	}
	return true;
}
</script>
</html>