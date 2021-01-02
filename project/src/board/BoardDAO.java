package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
