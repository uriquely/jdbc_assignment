package member.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static common.JDBCTemplate.*; //모든 부분을 전부 임포트하고싶다고 static으로 선언해준다.(클래스명 없이 메소드 사용 가능)
import member.model.dao.MemberDAO;
import member.model.vo.Member;

/*
 * Service
 * 업무로직 담당클래스 (connection생성, 트랜잭션처리, dao업무요청)
 * 
 * 1. jdbc driver 클래스 등록(dbms별로 제공) : 최초 1회
 * 2. db connection(연결)객체 : dbserver url, user, password
 * -> DAO담당
 * 6. 트랜잭션처리(commit, rollback) 
 * 7. 자원반납(connection)
 * 
 * 이렇게 한 번 DB에 갔다오는 과정 마무리
 * 
 */

public class MemberService {
	
	private MemberDAO memberDAO = new MemberDAO();
	
	public int insertMember(Member member) {
		//1. Connection 생성
		Connection conn = getConnection();
		//2. dao요청
		int result = memberDAO.insertMember(conn, member);
		//3. 트랜잭션 처리
		if(result > 0) commit(conn);
		else rollback(conn);
		//4. 자원반납
		close(conn);
		
		return result;
	}

	public int insertMember_(Member member) {
		
	    String driverClass = "oracle.jdbc.OracleDriver"; //이거 신나게 코딩하고 파일 연결 안하면 망함
	    //사용드라이버타입@ip주소:port:sid(접속 db명)
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String user = "student";
	    String password = "student";
	    Connection conn = null;
	    int result = 0;
		
		// 1. jdbc driver 클래스 등록(dbms별로 제공) : 최초 1회
	    
		try {
			Class.forName(driverClass); // 이 클래스 내가 사용하겠다고 선언하는 것
			
		// 2. db connection(연결)객체 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//자동 commit 사용 안함으로 설정(트랜잭션 처리를 java앱에서 주도적으로 처리하겠다.)
			conn.setAutoCommit(false);
		
		// -> DAO요청
			result = memberDAO.insertMember(conn, member);
			
		// 6. 트랜잭션처리(commit, rollback)
		if(result > 0) conn.commit();
		else conn.rollback();

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// 7. 자원반납(connection)
			try {
				conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//case2
	public Member selectOneMember(String memberId) {
		
		//1.Connection생성
		Connection conn = getConnection();
		
		//2.dao요청
		Member member = memberDAO.selectOneMember(conn, memberId);
		
		//3.자원반납
		close(conn);
		
		return member;
	}

	public List<Member> selectMemberName(String memberName) {
		
		//1.Connection생성
		Connection conn = getConnection();
		
		//2.dao요청
		List<Member> list = memberDAO.selectMemberName(conn, memberName);
		
		//3.자원반납
		close(conn);
		
		return list;
	}

	public List<Member> selectAll() {
		
		//1.Connection생성
		Connection conn = getConnection();
		
		//2.dao요청
		List<Member> list = memberDAO.selectAll(conn);
		
		//3.자원반납
		close(conn);
		
		return list;
	}

	public int deleteMember(String memberId) {
		
		Connection conn = getConnection();
		
		int member = memberDAO.deleteMember(conn, memberId);
		
		close(conn);
		
		return member;
	}
	
	//서브 case1 : 암호 변경
	public int updateMemberPassword(String id, String password) {
		
		Connection conn = getConnection();
		
		int member = memberDAO.updateMemberPassword(conn, id, password);
		
		close(conn);
		
		return member;
	}

	public int updateMemberEmail(String id, String email) {
		
		Connection conn = getConnection();
		
		int member = memberDAO.updateMemberEmail(conn, id, email);
		
		close(conn);
		
		return member;
	}

	public int updateMemberPhone(String id, String phone) {
		
		Connection conn = getConnection();
		
		int member = memberDAO.updateMemberPhone(conn, id, phone);
		
		close(conn);
		
		return member;
	}

	public int updateMemberAddress(String id, String address) {

		Connection conn = getConnection();
		
		int member = memberDAO.updateMemberAddress(conn, id, address);
		
		close(conn);
		
		return member;
	}

}
