package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	
	// 커넥션 연결
	private Connection getConnection() throws Exception{
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/root");
		con = ds.getConnection();
		return con;
	}
	
	// 자원 해제
	public void resourceClose() {
		try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
		} catch (Exception e) {
			System.out.println("자원해제 실패 !!!!!! ");
		}
	}

	//----------------------------------------------------------------------------------------------------

	// 모든 게시글의 수
	public int allBoardCount() {
		int count = 0;
		try {
			getConnection();
			sql = "select count(*) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("allBoardCount() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return count;
	}// allBoardCount() 끝 
	
	// 내가 작성한 게시글의 수
		public int myBoardCount(String id) {
			int count = 0;
			try {
				getConnection();
				sql = "select count(*) from board where writer=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
				}
			} catch (Exception e) {
				System.out.println("myBoardCount() 내부에서 예외 발생 : "+e.toString());
			} finally {
				resourceClose();
			}
			return count;
		}// myBoardCount() 끝 
	
	// 모든 게시글을 보여주는 작업
	public ArrayList<BoardBean> allBoard(int startRow,int pageSize){
		ArrayList<BoardBean> list = new ArrayList<BoardBean>();
		try {
			getConnection();
			sql="select * from board order by bno desc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);
			System.out.println("@@@startRow  :"+startRow);
			pstmt.setInt(2, pageSize);
			System.out.println("@@@pageSize :"+pageSize);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardBean bBean = new BoardBean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(6), rs.getDate(7));
				list.add(bBean);
			}
		} catch (Exception e) {
			System.out.println("allBoard() 내부에서 예외발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		
		return list;
	}// allBoard() 끝
	
	// 내가 작성한 모든 게시글을 보여주는 작업
	public ArrayList<BoardBean> allMyBoard(String id,int startRow,int pageSize){
		ArrayList<BoardBean> list = new ArrayList<BoardBean>();
		try {
			getConnection();
			sql="select * from board where writer=? order by bno desc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, startRow-1);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardBean bBean = new BoardBean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(6), rs.getDate(7));
				list.add(bBean);
			}
		} catch (Exception e) {
			System.out.println("allMyBoard() 내부에서 예외발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		
		return list;
	}// allMyBoard() 끝
	
	
	// 게시글을 작성후 DB에 INSERT하는 작업
	public int insertBoard(BoardBean bBean) {
		int result = 0;
		try {
			getConnection();
			sql = "insert into board(writer,title,content,file) values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bBean.getWriter());
			pstmt.setString(2, bBean.getTitle());
			pstmt.setString(3, bBean.getContent());
			pstmt.setString(4, bBean.getFile());
			result = pstmt.executeUpdate();
			System.out.println("DB에 INSERT 완료!");
		} catch (Exception e) {
			System.out.println("insertBoard() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// insertBoard() 끝

	// 게시글 하나를 클릭했을때 이동되는 메소드 ( 게시글 bno를 이용하여 게시글의 정보를 보여준다)
	public BoardBean boardDatail(int bno) {
		BoardBean bBean = null;
		try {
			getConnection();
			sql = "select * from board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bBean = new BoardBean(bno, rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5), rs.getInt(6), rs.getDate(7));
			}
			
		} catch (Exception e) {
			System.out.println("boardDetail() 내부에서 예외 발생 :"+e.toString());
		} finally {
			resourceClose();
		}
		return bBean;
	}// boardDetail() 끝

	// 게시글 하나를 클릭했을때 이동되는 메소드 ( 게시글 bno를 이용하여 조회수를 1 증가시켜준다)
	public void plusRead_count(int bno) {
		try {
			getConnection();
			sql = "update board set read_count=read_count+1 where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("plusRead_count() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
	} // plusRead_count() 끝

	// 게시글 삭제하기 버튼을 클릭했을때 이동되는 메소드
	public void deleteBoard(int bno) {
		try {
			getConnection();
			sql="delete from Board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("deleteBoard() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
	}// deleteBoard() 

	// 게시글 수정하기
	public int updateBoard(String title, String content,int bno) {
		int result = 0;
		try {
			getConnection();
			sql="update board set title=?,content=? where bno=?";
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, bno);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("updateBoard() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// updateBoard() 끝

	// 나의 Board를 보여주는 메소드
	public ArrayList<BoardBean> myBoard(String id) {
		ArrayList<BoardBean> list = new ArrayList<BoardBean>();
		BoardBean bBean =null;
		try {
			getConnection();
			sql="select * from board where writer=? order by date desc limit 0,5";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bBean = new BoardBean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getDate(7));
				list.add(bBean);
			}
		} catch (Exception e) {
			System.out.println("myBoard() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		
		return list;
	}// myBoard() 끝

	
	
	
}// class 끝
