package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * DB Connection 객체생성
 * 트랜잭션처리
 * 자원반납
 * 관련한 공통 코드를 작성(예외처리 포함)
 * 
 * static 메소드로 작성해서 객체생성없이 바로 호출
 */
public class JDBCTemplate {
	
	public static Connection getConnection() {
	   
		String driverClass = "oracle.jdbc.OracleDriver"; //이거 신나게 코딩하고 파일 연결 안하면 망함
	    //사용드라이버타입@ip주소:port:sid(접속 db명)
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String user = "student";
	    String password = "student";
	    Connection conn = null;
	    int result = 0;

		try {
			// 1. jdbc driver 클래스 등록(dbms별로 제공) : 최초 1회
			Class.forName(driverClass); // 이 클래스 내가 사용하겠다고 선언하는 것
			
			// 2. db connection(연결)객체 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//자동 commit 사용 안함으로 설정(트랜잭션 처리를 java앱에서 주도적으로 처리하겠다.)
			conn.setAutoCommit(false);

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return conn;
	}

	public static void commit(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed())
				conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void rollback(Connection conn) {	
		
		try {
			if(conn != null && !conn.isClosed())
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static void close(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pstmt) {
		
		try {
			if(pstmt != null && !pstmt.isClosed())
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
	      try {
	         if(rset != null&& !rset.isClosed())
	            rset.close();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	   }
}
