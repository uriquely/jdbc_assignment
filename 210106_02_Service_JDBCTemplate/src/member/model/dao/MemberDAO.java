package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static common.JDBCTemplate.*;
import member.model.vo.Member;

/*
 * DAO
 * 
 * 3. 쿼리문 생성 및 Statement객체(PreperedStatement) 생성
 * 4. 쿼리전송(실행) - 결과값 받아내기
 * 4_1. select문인 경우 결과집합을 자바객체(list)에 옮겨 담기
 * 5. 자원반납(PreparedStetement, ResultSet)
 */

public class MemberDAO {

	   public int insertMember(Connection conn, Member member) {
	      PreparedStatement pstmt = null;
	      int result = 0;
	      String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, default)";

	      try {
		    //쿼리문 생성 및 Statement객체(PreperedStatement) 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
	        pstmt.setString(3, member.getMemberName());
	        pstmt.setString(4, member.getGender());
	        pstmt.setInt(5, member.getAge());
	        pstmt.setString(6, member.getEmail());
	        pstmt.setString(7, member.getPhone());
	        pstmt.setString(8, member.getAddress());
	        pstmt.setString(9, member.getHobby());
	        
			//쿼리전송(실행) - 결과값 받아내기
	        result = pstmt.executeUpdate(); //DML인 경우 executeUpdate
	        
		} catch (Exception e) {
			e.printStackTrace();
		}	

		//5. 자원반납(PreparedStetement, ResultSet)
		close(pstmt);
		
		return result;
	   }

	public Member selectOneMember(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where member_id = ?";
		
		//리턴할 변수는 바깥에 선언
		Member member = null;
		
		try {
			//1.PreparedStatement생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			//2.실행 및 ResultSet값 -> member객체
			rset = pstmt.executeQuery();
			
			//while써도 괜찮음
			if(rset.next()) {
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
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			//3.자원반납
			close(rset);
			close(pstmt);
		}
		
//		System.out.println("member@dao = " + member); //찍어보기용
		return member;
	}

	public List<Member> selectMemberName(Connection conn, String memberName) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where member_name like '%" + memberName + "%'";
		
		//리턴할 변수는 바깥에 선언
		Member member = null;
		List<Member> list = null;
		
		
		try {
			//1.PreparedStatement생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			
			//2.실행 및 ResultSet값 -> member객체
			rset = pstmt.executeQuery();
			
			list = new ArrayList<>(); // ArrayList 객체 할당
			
			//while써도 괜찮음
			while(rset.next()) {
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
				
				list.add(member);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			//3.자원반납
			close(rset);
			close(pstmt);
		}
		
//		System.out.println("member@dao = " + member); //찍어보기용
		return list;
	}

	public List<Member> selectAll(Connection conn) {
		
		PreparedStatement pstmt = null;
		String sql = "select * from member order by enroll_date desc";
		ResultSet rset= null;
		List<Member> list = null;
		Member member = null;
		
		try {
			//1.PreparedStatement생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			
			//2.실행 및 ResultSet값 -> member객체
			//DQL select문인 경우에는 executeQuery()호출 -> ResultSet
			rset = pstmt.executeQuery();
			
			list = new ArrayList<>(); // ArrayList 객체 할당
			
			while(rset.next()) {
				member = new Member();
				
				member.setMemberId(rset.getString("member_id"));
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				
				list.add(member);
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int deleteMember(Connection conn, String memberId) {
		
		PreparedStatement pstmt = null;
		String sql = "delete from member where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			result = pstmt.executeUpdate();
			
			//트랜잭션처리
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateMemberPassword(Connection conn, String id, String password) {
		
		PreparedStatement pstmt = null;
		String sql = "update member set password = ? where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateMemberEmail(Connection conn, String id, String email) {
		
		PreparedStatement pstmt = null;
		String sql = "update member set email = ? where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateMemberPhone(Connection conn, String id, String phone) {
		
		PreparedStatement pstmt = null;
		String sql = "update member set phone = ? where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phone);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateMemberAddress(Connection conn, String id, String address) {
		
		PreparedStatement pstmt = null;
		String sql = "update member set address = ? where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, address);
			pstmt.setString(2, id);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close(pstmt);
		}

		return result;
	}
}
