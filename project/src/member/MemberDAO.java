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
	//Id를 이용하여 회원정보를 보여주는 메소드
	public MemberBean showMember(String id) {
		MemberBean mBean = null;
		try {
			getConnection();
			sql="select * from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mBean = new MemberBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (Exception e) {
			System.out.println("showMember() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return mBean;
	}// showMember() 끝
	
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

	// 회원정보 수정하는 메소드
	public int updateMember(String id, String name, String phone) {
		int result = 0;
		try {
			getConnection();
			sql="update member set name=?,phone=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			pstmt.setString(3, id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("updateMember() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}// updateMember() 끝

	// 회원정보 비밀번호 수정 메소드
	public int updatePwd(String id, int newPwd) {
		int result = 0;
		try {
			getConnection();
			sql="update member set pwd=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newPwd);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("updatePwd() 내부에서 예외 발생 : "+e.toString());
		} finally {
			resourceClose();
		}
		return result;
	}

	

}
