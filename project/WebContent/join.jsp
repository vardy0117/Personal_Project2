<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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


<div class="container register-form">
            <div class="form">
                <div class="note">
                    <p>To Be a Better Programmer JOIN</p>
                </div>
				<form action="joinPro.do" method="post">
	                <div class="form-content">
	                    <div class="row">
	                        <div class="col-md-6">
	                            <!--  <div class="check" id="id_check"></div>-->
	                            <div class="form-group">
	                                <input type="text" class="form-control" placeholder="Your ID *" value="" name="id" id="id"/>
	                            </div>
	                            <div class="form-group">
	                                <input type="password" class="form-control" placeholder="Your Password *" value="" name="pwd"/>
	                            </div>
	                        </div>
	                        <div class="col-md-6">
	                            <div class="form-group">
	                                <input type="text" class="form-control" placeholder="Your Name *" value="" name="name"/>
	                            </div>
	                            <div class="form-group">
	                                <input type="password" class="form-control" placeholder="Confirm Password *" value="" name="rePwd"/>
	                            </div>
	                        </div>
	                        <div class="col-md-6">
	                            <div class="form-group">
	                                <input type="text" class="form-control" placeholder="Your Phone *" value="" name="phone"/>
	                            </div>
	                        </div>
	                    </div>
	                    <input type="submit" class="btnSubmit" value="JOIN">
	                </div>
                </form>
            </div>
        </div>
</body>

<script type="text/javascript">
	// id 입력란에 .blur()를 이용하여 포커스가 벗어났을때 function(){}이 실
	$("#id").blur(function(){
		var id="";
		id = $("#id").val();
		$.ajax({
			data:{id},
			async:true,
			url:"idCheck.do",
			success:function(data){
				// 사용할수 있는 id 이면 .css 속성을 이용하여 배경색 초록색으로 만들
				if(data == 'yes'){
					$("#id").css("background","yellowgreen");
				// 중복된 id 이면 .css 속성을 이용하여 배경색 빨간색으로 만들고 포커스 고정				
				}else{
					console.log($("#id").val());
					$("#id").css("background","red");
					$("#id").focus();
				}
			},error:function(error){
				alert(error);
			}
		}); // ajax 끝
		
	});

</script>
</html>