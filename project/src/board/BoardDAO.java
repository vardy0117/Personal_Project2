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
				BoardBean bBean = new BoardBean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDate(6));
				list.add(bBean);
			}
		} catch (Exception e) {
			System.out.println("allBoard() 내부에서 예외발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		
		return list;
	}// allBoard() 끝
	
	
	// 게시글을 작성후 DB에 INSERT하는 작업
	public int insertBoard(BoardBean bBean) {
		int result = 0;
		try {
			getConnection();
			sql = "insert into board(writer,title,content) values(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bBean.getWriter());
			pstmt.setString(2, bBean.getTitle());
			pstmt.setString(3, bBean.getContent());
			result = pstmt.executeUpdate();
			System.out.println("DB에 INSERT 완료!");
		} catch (Exception e) {
			System.out.println("insertBoard() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// insertBoard() 끝
	
	
	
	
}// class 끝
