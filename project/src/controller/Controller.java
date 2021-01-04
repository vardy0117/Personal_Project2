package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
		
		// 	게시판으로 이동 버튼을 클릭했을때 board.do를 요청하여 게시판 화면 보여주기
		if(uri.equals("board")) {
			System.out.println("게시판으로 이동되었습니다.");
		
			ArrayList<BoardBean> list = new ArrayList<BoardBean>();
			BoardDAO bDAO = new BoardDAO();
			// ---------------------------------------------------
			// 페이징 처리
			// 게시글의 수
			int count = bDAO.allBoardCount();
			// 현재 페이지
			String pageNum = request.getParameter("pageNum");
			if(pageNum == null) {
				pageNum = "1";
			}
			int currentPage = Integer.parseInt(pageNum);
			// 한 페이지에 보여줄 게시글 수
			int pageSize = 5;
			// 해당 페이지에서 시작할 레코드번호 / 끝 번호
			int startRow = (currentPage - 1) * pageSize + 1;
			int endRow = currentPage *pageSize;
			// 페이징 a태그 개수 구하기
			int aTag = 0;
			if(count%pageSize==0) {
				aTag = count/pageSize;
			}else {
				aTag = (count/pageSize)+1;
			}
			
			// 게시판의 모든 게시글을 보여줄거임
			// 게시글
			list = bDAO.allBoard(startRow,pageSize);
			
			// 현재 페이지 번호
			
			request.setAttribute("boardList", list);
			request.setAttribute("startRow", startRow);
			request.setAttribute("endRow", endRow);
			request.setAttribute("aTag", aTag);
			request.setAttribute("pageNum", Integer.parseInt(pageNum));
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setNextPath("board.jsp");
			forward.execute(request, response);
		} // board 
		
		
		
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
			BoardBean bBean = new BoardBean();
			BoardDAO bDAO = new BoardDAO();
			System.out.println("글 작성 후 DB에 업로드한다");
			int result = 0;
			
			String id = (String)session.getAttribute("id");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			System.out.println(id+","+title+","+content);
		
			System.out.println("44");
			bBean.setWriter(id);
			bBean.setTitle(title);
			bBean.setContent(content);
			result = bDAO.insertBoard(bBean);
			System.out.println(1);
			System.out.println(result + "result");
			// 게시글 등록이 정상적으로 완료된 경우!!!
			if(result == 1) {
				System.out.println("if문 들어옴");
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("<script>alert('게시글이 등록되었습니다.'); location.href='board.do';</script>");
				out.flush();
			}
		}// writeUpload 끝
		
		
		
		
	}// reqPro 끝
}// class 끝
