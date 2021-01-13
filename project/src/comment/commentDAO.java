package comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class commentDAO {
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
	
	// 글번호에 해당하는 모든 댓글을 보여주는 메소드
	public ArrayList<commentBean> allComment(int bno) {
		ArrayList<commentBean> list = new ArrayList<commentBean>();
		commentBean cBean = null;
		try {
			getConnection();
			sql="select * from comment where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cBean = new commentBean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5));
				list.add(cBean);
			}
		} catch (Exception e) {
			System.out.println("allComment() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return list;
	}

	// 댓글 삭제하는 메소드
	public int deleteComment(int cno) {
		int result = 0;
		try {
			getConnection();
			sql="delete from comment where cno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cno);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("deleteComment() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// deleteComment() 끝

	// 댓글 등록하는 메소드
	public int uploadComment(String writer, String comment, int bno) {
		int result = 0;
		try {
			getConnection();
			sql="insert into comment(writer,comment,bno) values(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, writer);
			pstmt.setString(2, comment);
			pstmt.setInt(3, bno);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("uploadComment() 내부에서 예외 발생 :" +e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// uploadComment() 끝

	// 내가 작성한 모든 댓글을 보여주는 메소드
	public ArrayList<commentBean> myAllComment(String id,int commentStartRow,int commentPageSize) {
		ArrayList<commentBean> commentList = new ArrayList<commentBean>();
		commentBean cBean = null;
		try {
			getConnection();
			sql="select * from comment where writer=? order by date desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2,commentStartRow-1);
			pstmt.setInt(3, commentPageSize);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				cBean = new commentBean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5));
				commentList.add(cBean);
			}
		} catch (Exception e) {
			System.out.println("myAllComment()내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return commentList;
	}

	// 내가 작성한 모든 댓글의 수
	public int myCommentCount(String id) {
		int count = 0;
		try {
			getConnection();
			sql="select count(*) from comment where writer=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("myCommentCount() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return count;
	}
	
	
}// class
