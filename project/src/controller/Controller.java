package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionForward;
import board.BoardBean;
import board.BoardDAO;
import member.MemberBean;
import member.MemberDAO;

@WebServlet("*.do")
public class Controller extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	public void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 한글깨짐방지
		request.setCharacterEncoding("utf-8");
		
		// uri 의 요청주소값 얻어내기 
		String pureUri = request.getRequestURI().substring(9);
		StringTokenizer uri2 = new StringTokenizer(pureUri, ".");
		String uri = uri2.nextToken();
		System.out.println(uri);
		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		//uri 의 요청주소값이 login인 경우
		if(uri.equals("login")) {
			System.out.println("로그인 하러 가자 ");
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			
			MemberDAO mDAO = new MemberDAO();
			int result = mDAO.login(id, pwd);
			
			// 로그인 성공 
			if(result == 1) {
				System.out.println("로그인 성공 ");
				
				session.setAttribute("id", id);	//session에 id를 저장
				
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath("index.jsp");
				forward.execute(request, response);
			// 로그인 실패 
			}else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("<script>alert('로그인에 실패하셨습니다.'); history.back();</script>");
				out.flush();
			}
		} // login
		
		// uri의 요청값이 logout일 경우
		if(uri.equals("logout")) {
			System.out.println("로그아웃요청");
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
		
		// uri의 요청주소값이 join일 경우	(index화면에서 회원가입화면으로 이동)
		if(uri.equals("join")) {
			System.out.println("회원가입하러가자");
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setNextPath("join.jsp");
			forward.execute(request, response);
		}// join
		
		// uri의 요청주소값이 joinPro일 경우 (회원가입화면에서 정보를 입력 후 DB에 INSERT를 수행하기위해 이동)
		if(uri.equals("joinPro")) {
			System.out.println("회원가입 처리하러 가야한다");
			
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String rePwd = request.getParameter("rePwd");
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			
			// 비밀번호 맞게 입력했는지 확인
			if(pwd.equals(rePwd)) {
				MemberBean mBean = new MemberBean(id, pwd, name, phone);
				MemberDAO mDAO = new MemberDAO();
				int result = mDAO.insertJoin(mBean);
				if(result == 1) {
					System.out.println("회원가입 성공~~~~");
					forward = new ActionForward();
					forward.setRedirect(false);
					forward.setNextPath("index.jsp");
					forward.execute(request, response);
				}else {
					System.out.println("회원가입 실패!!!!!!!");
				}
			}else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("<script>alert('비밀번호를 다시 확인하세요'); history.back();</script>");
				out.flush();
			}
		}// joinPro 끝
		
		// id중복검사를 ajax비동기 방식으로 처리한다
		if(uri.equals("idCheck")) {
			System.out.println("ajax로 컨트롤러 접근해서 id중복검사!!!!");
			String id = request.getParameter("id");
			System.out.println(id);
			MemberDAO mDAO = new MemberDAO();
			int result = mDAO.checkId(id);
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=UTF-8");
			// id가 중복일때
			if(result==1) {
				System.out.println("id 중복");
				PrintWriter out = response.getWriter();
				out.print("no");
			// id가 중복이 아닐때
			}else {	
				System.out.println("id 중복아님");
				PrintWriter out = response.getWriter();
				out.print("yes");
			}
		} // idCheck 끝
		
		// 글쓰기 버튼을 클릭했을때 이동되는 페이지 (글을 작성할 수 있는 페이지)
		if(uri.equals("write")) {
			System.out.println("글쓰러왔음");
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setNextPath("write.jsp");
			forward.execute(request, response);
		}// write 끝
		
		// 글을 작성한 후 DB에 INSERT 작업
		if(uri.equals("writeUpload")) {
			System.out.println("글 작성 후 DB에 업로드한다");
			
			
			String id = (String)session.getAttribute("id");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			System.out.println(id+","+title+","+content);
		
			BoardBean bBean = new BoardBean();
			bBean.setWriter(id);
			bBean.setTitle(title);
			bBean.setContent(content);
			BoardDAO bDAO = new BoardDAO();
			int result = bDAO.insertBoard(bBean);

			// 게시글 등록이 정상적으로 완료된 경우!!!
			if(result == 1) {
				
			}
		}// writeUpload 끝
		
		
		
		
	}// reqPro 끝
}// class 끝
