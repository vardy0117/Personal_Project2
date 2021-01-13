package notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class NoticeDAO {
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
	
	// 공지글 DB에 INSERT 작업
	public int insertNotice(NoticeBean nBean) {
		int result = 0;
		try {
			getConnection();
			sql = "insert into notice(writer,title,content,file) values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nBean.getWriter());
			pstmt.setString(2, nBean.getTitle());
			pstmt.setString(3, nBean.getContent());
			pstmt.setString(4, nBean.getFile());
			result = pstmt.executeUpdate();
			System.out.println("DB에 INSERT 완료!");
		} catch (Exception e) {
			System.out.println("insertNotice() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// insertNotice() 끝

	// 공지글의 수
	public int noticeCount() {
		int count = 0;
		try {
			getConnection();
			sql="select count(*) from notice";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("noticeCount() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return count;
	}// noticeCount() 끝

	// 공지글을 보여주는 메소드
	public ArrayList<NoticeBean> allNotice(int startRow) {
		ArrayList<NoticeBean> noticeList = new ArrayList<NoticeBean>();
		System.out.println("allNotice 내부에서 startRow 테스트 : "+startRow);
		try {
			getConnection();
			sql="select * from notice order by date desc limit ?,5";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				NoticeBean nBean = new NoticeBean(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getDate(7));
				noticeList.add(nBean);
			}
		} catch (Exception e) {
			System.out.println("allNotice() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return noticeList;
	}// allNotice() 끝
	
	
}// class 끝
