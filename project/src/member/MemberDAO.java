package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
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
	
	
	//로그인 정보를 확인하는 메소드
	public int login(String id,String pwd) {
		int result = 0;
		try {
			getConnection();
			sql="select * from member where id=? and pwd=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = 1;
			}
			
		} catch (Exception e) {
			System.out.println("login 메소드 안에 에러 :"+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// login() 끝
	
	// 회원가입 INSERT 처리하는 메소드
	public int insertJoin(MemberBean mBean) {
		int result = 0;
		try {
			getConnection();
			sql="insert into member(id,pwd,name,phone) values(?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, mBean.getId());
			pstmt.setString(2, mBean.getPwd());
			pstmt.setString(3, mBean.getName());
			pstmt.setString(4, mBean.getPhone());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("insertJoin() 안에서 예외 발생 :+ "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// insertJoin() 끝

	// id 중복검사하는 메소드 result가 1이면 중복되어있다는 뜻
	public int checkId(String id) {
		int result = 0;
		try {
			getConnection();
			sql="select * from member where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = 1;
			}
		} catch (Exception e) {
			System.out.println("checkId() 내에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// checkId 끝

}
