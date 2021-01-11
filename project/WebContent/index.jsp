<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>   
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>이태우의 포트폴리오</title>

  <!-- Bootstrap Core CSS -->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom Fonts -->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
  <link href="vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet">

  <!-- Custom CSS -->
  <link href="css/stylish-portfolio.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Navigation -->
  <a class="menu-toggle rounded" href="#">
    <i class="fas fa-bars"></i>
  </a>
  <nav id="sidebar-wrapper">
    <ul class="sidebar-nav">
      <li class="sidebar-brand">
        <a class="js-scroll-trigger" href="#page-top">To Be a Better Programmer</a>
      </li>
      <li class="sidebar-nav-item">
        <a class="js-scroll-trigger" href="#page-top">Home</a>
      </li>
      <li class="sidebar-nav-item">
        <a class="js-scroll-trigger" href="#portfolio">Portfolio</a>
      </li>
      <c:if test="${sessionScope.id ne null }">
	      <li class="sidebar-nav-item">
	        <a class="js-scroll-trigger" href="modify.do">modify</a>
	      </li>
      </c:if>
<!--       <li class="sidebar-nav-item"> -->
<!--         <a class="js-scroll-trigger" href="#contact">Contact</a> -->
<!--       </li> -->
    </ul>
  </nav>

  <!-- Header -->
  <header class="masthead d-flex">
    <div class="container text-center my-auto">
      <h1 class="mb-1">To Be a Better Programmer</h1>
      <h3 class="mb-5">
      </h3>
      
		<!-- session에 아이디가 저장되어있는지 == 로그인이 되어있는지 확인 -->
		<c:choose>
			<c:when test="${sessionScope.id eq null }">
				<a class="btn btn-primary btn-xl js-scroll-trigger" onclick="login()">게시판으로 이동</a>
			      <div id="loginForm">
			      <form action="login.do" method="post">
						<table style="margin: 0 auto">
							<tr>
								<td><input type="text" name="id" placeholder="ID"></td>
							</tr>
							<tr>
								<td><input type="password" name="pwd" placeholder="PW"></td>
							</tr>
							<tr>
								<td><input type="submit" value="login" class="btn btn-primary"></td>
							</tr>
							<tr>
								<td><a href="join.do">Don't have an account?</a></td>
							</tr>
						</table>
				 	</form>
					</div>
			</c:when>
			<c:otherwise>
				<a class="btn btn-primary btn-xl js-scroll-trigger" onclick="location.href='board.do'">게시판으로 이동</a>
			</c:otherwise>
		</c:choose>
     
    </div>
    <div class="overlay"></div>
  </header>
  <script type="text/javascript">
  	
  	document.getElementById("loginForm").style.display="none";
  	function login() {
	  	document.getElementById("loginForm").style.display="block";
  	}
  
  </script>
  <!-- About -->
<!--   <section class="content-section bg-light" id="about"> -->
<!--     <div class="container text-center"> -->
<!--       <div class="row"> -->
<!--         <div class="col-lg-10 mx-auto"> -->
<!--           <h2>Stylish Portfolio is the perfect theme for your next project!</h2> -->
<!--           <p class="lead mb-5">This theme features a flexible, UX friendly sidebar menu and stock photos from our friends at -->
<!--             <a href="https://unsplash.com/">Unsplash</a>!</p> -->
<!--           <a class="btn btn-dark btn-xl js-scroll-trigger" href="#services">What We Offer</a> -->
<!--         </div> -->
<!--       </div> -->
<!--     </div> -->
<!--   </section> -->

  <!-- Services -->
<!--   <section class="content-section bg-primary text-white text-center" id="services"> -->
<!--     <div class="container"> -->
<!--       <div class="content-section-heading"> -->
<!--         <h3 class="text-secondary mb-0">Services</h3> -->
<!--         <h2 class="mb-5">What We Offer</h2> -->
<!--       </div> -->
<!--       <div class="row"> -->
<!--         <div class="col-lg-3 col-md-6 mb-5 mb-lg-0"> -->
<!--           <span class="service-icon rounded-circle mx-auto mb-3"> -->
<!--             <i class="icon-screen-smartphone"></i> -->
<!--           </span> -->
<!--           <h4> -->
<!--             <strong>Responsive</strong> -->
<!--           </h4> -->
<!--           <p class="text-faded mb-0">Looks great on any screen size!</p> -->
<!--         </div> -->
<!--         <div class="col-lg-3 col-md-6 mb-5 mb-lg-0"> -->
<!--           <span class="service-icon rounded-circle mx-auto mb-3"> -->
<!--             <i class="icon-pencil"></i> -->
<!--           </span> -->
<!--           <h4> -->
<!--             <strong>Redesigned</strong> -->
<!--           </h4> -->
<!--           <p class="text-faded mb-0">Freshly redesigned for Bootstrap 4.</p> -->
<!--         </div> -->
<!--         <div class="col-lg-3 col-md-6 mb-5 mb-md-0"> -->
<!--           <span class="service-icon rounded-circle mx-auto mb-3"> -->
<!--             <i class="icon-like"></i> -->
<!--           </span> -->
<!--           <h4> -->
<!--             <strong>Favorited</strong> -->
<!--           </h4> -->
<!--           <p class="text-faded mb-0">Millions of users -->
<!--             <i class="fas fa-heart"></i> -->
<!--             Start Bootstrap!</p> -->
<!--         </div> -->
<!--         <div class="col-lg-3 col-md-6"> -->
<!--           <span class="service-icon rounded-circle mx-auto mb-3"> -->
<!--             <i class="icon-mustache"></i> -->
<!--           </span> -->
<!--           <h4> -->
<!--             <strong>Question</strong> -->
<!--           </h4> -->
<!--           <p class="text-faded mb-0">I mustache you a question...</p> -->
<!--         </div> -->
<!--       </div> -->
<!--     </div> -->
<!--   </section> -->

  <!-- Callout -->
<!--   <section class="callout"> -->
<!--     <div class="container text-center"> -->
<!--       <h2 class="mx-auto mb-5">Welcome to -->
<!--         <em>your</em> -->
<!--         next website!</h2> -->
<!--       <a class="btn btn-primary btn-xl" href="https://startbootstrap.com/theme/stylish-portfolio/">Download Now!</a> -->
<!--     </div> -->
<!--   </section> -->

  <!-- Portfolio -->
  <section class="content-section" id="portfolio">
    <div class="container">
      <div class="content-section-heading text-center">
        <h3 class="text-secondary mb-0">Portfolio</h3>
        <h2 class="mb-5">Other Projects</h2>
      </div>
      <div class="row no-gutters">
        <div class="col-lg-6">
          <a class="portfolio-item" href="pdf/딜리벤져스-ppt.pdf" target="_blank">
            <div class="caption">
              <div class="caption-content">
                <div class="h2">Dog salon de beaute</div>
                <p class="mb-0">[Personal Project]</p>
                <p class="mb-0">애견 미용 예약 사이트를 만들어보았습니다!</p>
              </div>
            </div>
            <img class="img-fluid" src="img/portfolio-1.jpg" alt="">
          </a>
        </div>
        <div class="col-lg-6">
          <a class="portfolio-item" href="pdf/동물미용-예약-사이트.pdf" target="_blank">
            <div class="caption">
              <div class="caption-content">
                <div class="h2">Delivengers</div>
                <p class="mb-0">[Team Project]</p>
                <p class="mb-0">음식 배달 주문 사이트를 만들어보았습니다!</p>
              </div>
            </div>
            <img class="img-fluid" src="img/portfolio-2.jpg" alt="">
          </a>
        </div>
<!--         <div class="col-lg-6"> -->
<!--           <a class="portfolio-item" href="#!"> -->
<!--             <div class="caption"> -->
<!--               <div class="caption-content"> -->
<!--                 <div class="h2">Strawberries</div> -->
<!--                 <p class="mb-0">Strawberries are such a tasty snack, especially with a little sugar on top!</p> -->
<!--               </div> -->
<!--             </div> -->
<!--             <img class="img-fluid" src="img/portfolio-3.jpg" alt=""> -->
<!--           </a> -->
<!--         </div> -->
<!--         <div class="col-lg-6"> -->
<!--           <a class="portfolio-item" href="#!"> -->
<!--             <div class="caption"> -->
<!--               <div class="caption-content"> -->
<!--                 <div class="h2">Workspace</div> -->
<!--                 <p class="mb-0">A yellow workspace with some scissors, pencils, and other objects.</p> -->
<!--               </div> -->
<!--             </div> -->
<!--             <img class="img-fluid" src="img/portfolio-4.jpg" alt=""> -->
<!--           </a> -->
<!--         </div> -->
<!--       </div> -->
<!--     </div> -->
  </section>

  <!-- Call to Action -->
<!--   <section class="content-section bg-primary text-white"> -->
<!--     <div class="container text-center"> -->
<!--       <h2 class="mb-4">The buttons below are impossible to resist...</h2> -->
<!--       <a href="#!" class="btn btn-xl btn-light mr-4">Click Me!</a> -->
<!--       <a href="#!" class="btn btn-xl btn-dark">Look at Me!</a> -->
<!--     </div> -->
<!--   </section> -->

  <!-- Map -->
<!--   <div id="contact" class="map"> -->
<!--     <iframe src="pdf/성실함과 꾸준함을 가진 인재입니다._vardy0117.pdf" width="10px"></iframe> -->
<!--     <br /> -->
<!--   </div> -->

  <!-- Footer -->
  <footer class="footer text-center">
    <div class="container">
      <ul class="list-inline mb-5">
        <li class="list-inline-item">
          <a class="social-link rounded-circle text-white mr-3" href="https://www.instagram.com/0oweat/" target="_blank">
            <i class="icon-social-instagram"></i>
          </a>
        </li>
        <li class="list-inline-item">
          <a class="social-link rounded-circle text-white" href="https://github.com/vardy0117" target="_blank">
            <i class="icon-social-github"></i>
          </a>
        </li>
      </ul>
      <p class="text-muted small mb-0">Copyright &copy; Your Website 2020</p>
    </div>
  </footer>

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded js-scroll-trigger" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Plugin JavaScript -->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for this template -->
  <script src="js/stylish-portfolio.min.js"></script>

</body>

</html>