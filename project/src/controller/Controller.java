package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.ActionForward;
import board.BoardBean;
import board.BoardDAO;
import comment.commentBean;
import comment.commentDAO;
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
		
		// 회원정보 수정
		if(uri.equals("modify")) {
			System.out.println("회원정보 수정");
			String id = (String)session.getAttribute("id");
			request.setAttribute("id",id);
			MemberDAO mDAO = new MemberDAO();
			MemberBean mBean =  mDAO.showMember(id);
			request.setAttribute("mBean", mBean);
			forward = new ActionForward();
			forward.setNextPath("modify.jsp");
			forward.execute(request, response);
		}// modify 끝
		// modifyPwd ( 회원정보-비밀번호 수정)
		if(uri.equals("modifyPwd")) {
			System.out.println("비밀번호 수정");
			String id = (String)session.getAttribute("id");
			request.setAttribute("id",id);
			MemberDAO mDAO = new MemberDAO();
			MemberBean mBean =  mDAO.showMember(id);
			request.setAttribute("mBean", mBean);
			forward = new ActionForward();
			forward.setNextPath("modifyPwd.jsp");
			forward.execute(request, response);
		}
		// 회원정보 수정 Pro
		if(uri.equals("modifyPro")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			MemberDAO mDAO = new MemberDAO();
			int result = mDAO.updateMember(id,name,phone);
			if(result == 1) {
				System.out.println("result : "+result);
				forward = new ActionForward();
				forward.setNextPath("index.jsp");
				forward.execute(request, response);
			}
		}
		// 회원정보 - 비밀번호 수정 Pro
		if(uri.equals("modifyPwdPro")) {
			int oldPwd = Integer.parseInt(request.getParameter("oldPwd"));
			int newPwd = Integer.parseInt(request.getParameter("newPwd"));
			int reNewPwd = Integer.parseInt(request.getParameter("reNewPwd"));
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=UTF-8");
			// 기존 비밀번호 확인하기
			String id = (String)session.getAttribute("id");
			MemberDAO mDAO = new MemberDAO();
			MemberBean mBean = mDAO.showMember(id);
			int originPwd = Integer.parseInt(mBean.getPwd());
			if(oldPwd != originPwd) {
				PrintWriter out = response.getWriter();
				out.print("<script>alert('기존의 비밀번호를 확인하세요.'); history.back();</script>");
			}else {
				if(newPwd != reNewPwd) {
					PrintWriter out = response.getWriter();
					out.print("<script>alert('새로운 비밀번호를 확인하세요.'); history.back();</script>");
				}else {
					int result = mDAO.updatePwd(id,newPwd);
					if(result == 1) {
						forward = new ActionForward();
						forward.setNextPath("index.jsp");
						forward.execute(request, response);
					}
				}
			} // if/else
		}// modifyPwdPro 끝
		
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
			
			// 파일 업로드
			request.setCharacterEncoding("utf-8");
			//String uploadPath = getServletContext().getRealPath("upload");
			String uploadPath = "/Users/leetaewoo/git/repository/project/WebContent/upload";
			System.out.println(uploadPath);
			int maxSize = 1024 * 1024 * 1000; 
			try {
				MultipartRequest multi = new MultipartRequest(request, uploadPath,maxSize,"utf-8",new DefaultFileRenamePolicy());
				String id = (String)session.getAttribute("id");
				String title = multi.getParameter("title");
				String content = multi.getParameter("content");
				String file = multi.getFilesystemName("file");
				
				System.out.println(id+","+title+","+content+","+file);
				BoardBean bBean = new BoardBean();
				BoardDAO bDAO = new BoardDAO();
				System.out.println("글 작성 후 DB에 업로드한다");
				bBean.setWriter(id);
				bBean.setTitle(title);
				bBean.setContent(content);
				bBean.setFile(file);
				int result = 0;
				result = bDAO.insertBoard(bBean);
				// 게시글 등록이 정상적으로 완료된 경우!!!
				if(result == 1) {
					System.out.println("if문 들어옴");
					request.setCharacterEncoding("utf-8");
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.print("<script>alert('게시글이 등록되었습니다.'); location.href='board.do';</script>");
					out.flush();
				}
			} catch (Exception e) {
				System.out.println("writeUpload() 내에서 예외 발생 : "+e.toString());
			}
			
		}// writeUpload 끝
		
		// 게시글 상세보기
		if(uri.equals("boardDetail")) {
			// 게시글 보여주기
			int bno = Integer.parseInt(request.getParameter("bno"));
			int pageNum = Integer.parseInt(request.getParameter("pageNum"));
			BoardDAO bDAO = new BoardDAO();
			bDAO.plusRead_count(bno);
			BoardBean bBean = bDAO.boardDatail(bno);
			request.setAttribute("bno", bno);
			request.setAttribute("writer", bBean.getWriter());
			request.setAttribute("title", bBean.getTitle());
			request.setAttribute("content", bBean.getContent());
			request.setAttribute("file", bBean.getFile());
			request.setAttribute("read_count", bBean.getRead_count());
			request.setAttribute("date", bBean.getDate());
			request.setAttribute("pageNum", pageNum);
			System.out.println("@@@@@@@@@@@@  file : "+bBean.getFile());
			// 댓글 보여주기
			commentDAO cDAO = new commentDAO();
			ArrayList<commentBean> list = cDAO.allComment(bno);
			request.setAttribute("commentList", list);
			
			forward = new ActionForward();
			forward.setNextPath("boardDetail.jsp?bno="+bno+"&pageNum="+pageNum);
			forward.setRedirect(false);
			forward.execute(request, response);
		}// boardDetail 끝 
		
		// 게시글 삭제
		if(uri.equals("deleteBoard")) {
			System.out.println("글 삭제");
			int bno = Integer.parseInt(request.getParameter("bno"));
			BoardDAO bDAO = new BoardDAO();
			bDAO.deleteBoard(bno);
			forward = new ActionForward();
			forward.setNextPath("board.do");
			forward.execute(request, response);
		}// deleteBoard 끝
		
		// 게시글 수정
		if(uri.equals("updateBoard")) {
			System.out.println("글 수정");
			int bno = Integer.parseInt(request.getParameter("bno"));
			BoardDAO bDAO = new BoardDAO();
			BoardBean bBean =  bDAO.boardDatail(bno);
			request.setAttribute("bno", bno);
			request.setAttribute("writer", bBean.getWriter());
			request.setAttribute("title", bBean.getTitle());
			request.setAttribute("content", bBean.getContent());
			request.setAttribute("read_count", bBean.getRead_count());
			request.setAttribute("date", bBean.getDate());
			
			forward = new ActionForward();
			forward.setNextPath("updateBoard.jsp?bno="+bno);
			forward.execute(request, response);
		}// updateBoard 끝
		
		// 게시글 수정Pro
		if(uri.equals("updateBoardPro")) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int bno = Integer.parseInt(request.getParameter("bno"));
			BoardDAO bDAO = new BoardDAO();
			int result = bDAO.updateBoard(title,content,bno);
			if(result == 1) {
				forward = new ActionForward();
				forward.setNextPath("board.do");
				forward.execute(request, response);
			}
		}// updateBoardPro 끝
		
		// ajax방식으로 댓글 등록
		if(uri.equals("uploadComment")) {
			System.out.println("댓글등록~~");
			String writer = (String)session.getAttribute("id"); 
			String comment = request.getParameter("comment");
			int bno = Integer.parseInt(request.getParameter("bno"));
			commentDAO cDAO = new commentDAO();
			int result = cDAO.uploadComment(writer,comment,bno);
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=UTF-8");
			if(result == 1) {
				PrintWriter out = response.getWriter();
				out.print("ok");
			}else {
				PrintWriter out = response.getWriter();
				out.print("no");
			}
		}// uploadComment 끝
		
		// ajax 방식으로 댓글 삭제
		if(uri.equals("deleteComment")) {
			System.out.println("댓글삭제!!");
			int cno = Integer.parseInt(request.getParameter("cno"));
			commentDAO cDAO = new commentDAO();
			int result = cDAO.deleteComment(cno);
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=UTF-8");
			if(result==1) {
				PrintWriter out = response.getWriter();
				out.print("delete");
			}else {
				PrintWriter out = response.getWriter();
				out.print("unDelete");
			}
		}// deleteComment() 끝
	}// reqPro 끝
}// class 끝
