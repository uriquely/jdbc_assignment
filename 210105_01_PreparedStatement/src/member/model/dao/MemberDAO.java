package member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import member.model.vo.Member;

public class MemberDAO {

	/*
	 * DAO
	 * Data Access Object
	 * 
	 * 1. jdbc driver 클래스 등록(dbms별로 제공) : 최초 1회
	 * 2. db connection(연결)객체 : dbserver url, user, password
	 * 3. 쿼리문 생성 및 Statement객체(PreperedStatement) 생성
	 * 4. 쿼리전송(실행) - 결과값 받아내기
	 * 4_1. select문인 경우 결과집합을 자바객체(list)에 옮겨 담기
	 * 5. 트랜잭션처리(commit, rollback) 
	 * 6. 자원반납
	 * 
	 * 이렇게 한 번 DB에 갔다오는 과정 마무리
	 * 
	 * 
	 */
	
	private String driverClass = "oracle.jdbc.OracleDriver"; //필드로 빼둔것
	
	//사용드라이버타입@ip주소:port:sid(접속DB명)
	private String url = "jdbc:oracle:thin:@localhost:1521:xe"; //localhost : 내 컴퓨터 의미
	
	private String user  = "student";
	private String password  = "student";
	
	public MemberDAO() {
		
		/*
		 * C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib
		 * 들어가서 이클립스의 루트디렉토리 lib 폴더에다가 ojbc6.jar 파일을 복사해서 붙여넣는다.
		 * 
		 * 그냥 웹상에서 바로 다운로드 받아서 사용해도 된다.
		 * 
		 * 루트 디렉토리의 Properties - Java Build Path - Libraries - Add JARs...
		 * 위에서 넣어두었던 lib 폴더의 ojbc6.jar파일 추가하고 Apply
		 * 
		 */
		
		try {
			Class.forName(driverClass);
			
		} catch (ClassNotFoundException e) {
			//클래스를 찾지 못했을 때 발생하는 오류
			System.out.println("ojdbc6.jar를 확인하세요.");
			e.printStackTrace();
		}
	}
	
	
	/* 
	 * 1번은 위에서 진행했고, 2번부터 아래 메소드에서 진행한다.
	 * 
	 * 2. db connection(연결)객체 : dbserver url, user, password
	 * 3. 쿼리문 생성 및 Statement객체(PreperedStatement) 생성
	 * 4. 쿼리전송(실행) - 결과값 받아내기
	 * 4_1. select문인 경우 결과집합을 자바객체(list)에 옮겨 담기
	 * 5. 트랜잭션처리(commit, rollback) 
	 * 6. 자원반납
	 */
	public int insertMember(Member member) {
		
		Connection conn = null; //jdbc api중 하나
		PreparedStatement pstmt = null; //Statement의 하위 인터페이스
		
		/*
		Statement 클래스
		- SQL 구문을 실행하는 역할
		- 스스로는 SQL 구문 이해 못함(구문해석 X) -> 전달역할
		- SQL 관리 O + 연결 정보 X
		
		PreparedStatement 클래스
		- Statement 클래스의 기능 향상
		- 인자와 관련된 작업이 특화(매개변수)
		- 코드 안정성 높음. 가독성 높음.
		- 코드량이 증가 -> 매개변수를 set해줘야하기 때문에..
		- 텍스트 SQL 호출
		 */

//		sql작성 시 주의점 : 세미콜론(;)을 찍지 않는다.
//					      공백에 주의할 것
		String sql = "insert into member "
				   + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
		
		int result = 0;
		
		try {
			
//			2. db connection(연결)객체 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//자동 commit 사용 안함으로 설정(트랜잭션 처리를 java앱에서 주도적으로 처리하겠다.)
			conn.setAutoCommit(false);
			
//			3. 쿼리문 생성 및 Statement객체(PreperedStatement) 생성
			pstmt  = conn.prepareStatement(sql); //미완성쿼리전달
			//Statement객체 생성 후 ?에 값 대입 쿼리 완성
			
			//매개변수에 넘어온 member객체에서 각 값을 꺼내어서 전달해주는 과정
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			//enrolldate는 sysdate처리할 것이라 대입은 안 해도 된다.			
			
//			4. 쿼리전송(실행) - 결과값 받아내기
			//DML인 경우는 int값(처리된 행의 수)이 리턴된다.
			result = pstmt.executeUpdate(); //dml인 경우 executeUpdate
			
//			DML : Data Manipulation Language 데이터 조작어
//			select(데이터 조회 및 검색) / insert / update / delete(데이터 변형)
			
			/*
			 ExecuteUpdate
			1. 수행결과로 Int 타입의 값을 반환합니다.
			2. SELECT 구문을 제외한 다른 구문을 수행할 때 사용되는 함수입니다.
			executeUpdate 함수를 사용하는 방법입니다.

 			-> INSERT / DELETE / UPDATE 관련 구문에서는 반영된 레코드의 건수를 반환합니다.
 			-> CREATE / DROP 관련 구문에서는 -1 을 반환합니다.
			 */
			
//			4_1. select문인 경우 결과집합을 자바객체(list)에 옮겨 담기
			
//			5. 트랜잭션처리(commit, rollback) 
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
//			6. 자원반납 : 생성 역순 pstmt conn
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
//		아까 돌려받은 result값을 리턴 !!!!!!반드시 하십쇼!!!!!!
	}
	
//	MemberController의 selectAll() 메소드에서 요청받음
	public List<Member> selectAll() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "select * from member order by enroll_date desc";
		ResultSet rset = null;
		List<Member> list = null;
		
		try {
//			2. db connection(연결)객체 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//자동 commit 사용 안함으로 설정(트랜잭션 처리를 java앱에서 주도적으로 처리하겠다.)
			conn.setAutoCommit(false);
			
//			3. 쿼리문 생성 및 Statement객체(PreperedStatement) 생성
			pstmt = conn.prepareStatement(sql);
			//채워야할 ?가 있다면, 값 대입 그러나 여기는 없다.
			
//			4. 쿼리전송(실행) - 결과값 받아내기
			//DQL select문인 경우에는 executeQuery()호출 -> ResultSet
			rset = pstmt.executeQuery();
			//이부분 rset 반납할때 같이 반납되어버려서 객체에 따로 담아주어야 함
			
//			4_1. select문인 경우 결과집합을 자바객체(list)에 옮겨 담기
			
			//while문으로 한 행씩 옮겨담는 과정
			
			list = new ArrayList<>(); // ArrayList 객체 할당
			
			while(rset.next()) { //다음 행 있니? 물어보는 것, 한 행의 컬럼 정보에 접근이 가능해진다.
				
				//한행 -> member vo객체로 옮겨 담는 작업
				Member member = new Member();
				
				member.setMemberId(rset.getString("member_id")); //db컬럼명을 대소문자 구분 없이 적는다.
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				
				//list에 추가
				System.out.println("list@dao = " + list); //어디서 찍힌지 모르니까 확인용
				list.add(member);
			}
			
//			5. 트랜잭션처리(commit, rollback)
			//select문은 트랜잭션처리 불필요(수정한 부분이 없기 때문이다.)
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		
//		6. 자원반납 (생성의 역순으로 진행)
		
			try {
				rset.close();
				pstmt.close();
				conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	
//	MemberController의 retrieveMemberId() 메소드에서 요청받음
	public Member retrieveMemberId(String inputId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		//검색을 할 때 쿼리문 사이에 전달받은 memberID를 끼워넣을 수 있다.
		String sql = "select * from member where member_id = '" + inputId + "'";
		ResultSet rset = null;
		Member member = null;
		
		try {
//			2. db connection(연결)객체 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//자동 commit 사용 안함으로 설정(트랜잭션 처리를 java앱에서 주도적으로 처리하겠다.)
			conn.setAutoCommit(false);
			
//			3. 쿼리문 생성 및 Statement객체(PreperedStatement) 생성
			pstmt = conn.prepareStatement(sql);
			//채워야할 ?가 있다면, 값 대입 그러나 여기는 없다.
			
//			4. 쿼리전송(실행) - 결과값 받아내기
			//DQL select문인 경우에는 executeQuery()호출 -> ResultSet
			rset = pstmt.executeQuery();
			//이부분 rset 반납할때 같이 반납되어버려서 객체에 따로 담아주어야 함
			
//			4_1. select문인 경우 결과집합을 자바객체(list)에 옮겨 담기
			
			//if로 이 아래 정보가 모두 동일하다면 member객체를 리턴한다(일치하는 한 행)
			
			if(rset.next()) { //다음 행 있니? 물어보는 것, 한 행의 컬럼 정보에 접근이 가능해진다.
				
				//한행 -> member vo객체로 옮겨 담는 작업
				member = new Member();
				
				member.setMemberId(rset.getString("member_id")); //db컬럼명을 대소문자 구분 없이 적는다.
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
			}
			
//			5. 트랜잭션처리(commit, rollback)
			//select문은 트랜잭션처리 불필요(수정한 부분이 없기 때문이다.)
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		
//		6. 자원반납 (생성의 역순으로 진행)
		
			try {
				rset.close();
				pstmt.close();
				conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return member;
	}

	
//	MemberController의 retrieveMemberName() 메소드에서 요청받음
	public List<Member> retrieveMemberName(String inputName) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		//검색을 할 때 쿼리문 사이에 전달받은 memberID를 끼워넣을 수 있다.
		//여기 많이 헷갈리는데 와일드카드(%)사용해야 포함된 내역 검색할 수 있음 
		String sql = "select * from member where member_name like '%" + inputName + "%'";
		ResultSet rset = null;
		List<Member> list = null;
		
		try {
//			2. db connection(연결)객체 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//자동 commit 사용 안함으로 설정(트랜잭션 처리를 java앱에서 주도적으로 처리하겠다.)
			conn.setAutoCommit(false);
			
//			3. 쿼리문 생성 및 Statement객체(PreperedStatement) 생성
			pstmt = conn.prepareStatement(sql);
			//채워야할 ?가 있다면, 값 대입 그러나 여기는 없다.
			
//			4. 쿼리전송(실행) - 결과값 받아내기
			//DQL select문인 경우에는 executeQuery()호출 -> ResultSet
			rset = pstmt.executeQuery();
			//이부분 rset 반납할때 같이 반납되어버려서 객체에 따로 담아주어야 함
			
//			4_1. select문인 경우 결과집합을 자바객체(list)에 옮겨 담기
			
			list = new ArrayList<>(); // ArrayList 객체 할당
			
			while(rset.next()) { //다음 행 있니? 물어보는 것, 한 행의 컬럼 정보에 접근이 가능해진다.
				
				//한행 -> member vo객체로 옮겨 담는 작업
				Member member = new Member();
				
				member.setMemberId(rset.getString("member_id")); //db컬럼명을 대소문자 구분 없이 적는다.
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				
				//list에 추가
				list.add(member);
			}
			
//			5. 트랜잭션처리(commit, rollback)
			//select문은 트랜잭션처리 불필요(수정한 부분이 없기 때문이다.)
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		
//		6. 자원반납 (생성의 역순으로 진행)
		
			try {
				rset.close();
				pstmt.close();
				conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

//	MemberController의 modifiedMember() 메소드에서 요청받음
	public int modifiedMember(String inputModifiedId, Member inputModifiedMemberInfo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		//수정은 update문을 사용한다.
		String sql = "update member "
				   + "set password = ?,"
				   + "member_name = ?,"
				   + "gender = ?,"
				   + "age = ?,"
				   + "email = ?,"
				   + "phone = ?,"
				   + "address = ?,"
				   + "hobby = ?"
				   + "where member_id = ?";
		int result = 0;
		
		try {
//			2. db connection(연결)객체 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//자동 commit 사용 안함으로 설정(트랜잭션 처리를 java앱에서 주도적으로 처리하겠다.)
			conn.setAutoCommit(false);
			
//			3. 쿼리문 생성 및 Statement객체(PreperedStatement) 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputModifiedMemberInfo.getPassword());
			pstmt.setString(2, inputModifiedMemberInfo.getMemberName());
			pstmt.setString(3, inputModifiedMemberInfo.getGender());
			pstmt.setInt(4, inputModifiedMemberInfo.getAge());
			pstmt.setString(5, inputModifiedMemberInfo.getEmail());
			pstmt.setString(6, inputModifiedMemberInfo.getPhone());
			pstmt.setString(7, inputModifiedMemberInfo.getAddress());
			pstmt.setString(8, inputModifiedMemberInfo.getHobby());
			pstmt.setString(9, inputModifiedId);
			
//			4. 쿼리전송(실행) - 결과값 받아내기
			result = pstmt.executeUpdate();
			
//			5. 트랜잭션처리(commit, rollback)
			//select문은 트랜잭션처리 불필요(수정한 부분이 없기 때문이다.)
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		
//		6. 자원반납 (생성의 역순으로 진행)
		
			try {
				pstmt.close();
				conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

//	MemberController의 deleteMember() 메소드에서 요청받음
	public int deleteMember(String deleteMemberId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from member "
				   + "where member_id = ?";
		int result = 0;
		
		try {
			//2. db connection객체 생성 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			
			//자동 commit 사용 안함으로 설정(트랜잭션 처리를 java앱에서 주도적으로 처리하겠다.)
			conn.setAutoCommit(false);
			
			//3. 쿼리문 생성 및 Statement객체(PreparedStatement) 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deleteMemberId);
			//4. 쿼리전송(실행) - 결과값 받아내기
			result = pstmt.executeUpdate();
			
			//5. 트랜잭션처리(commit, rollback)
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			//6. 자원반납
			try {
				pstmt.close();
				conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
