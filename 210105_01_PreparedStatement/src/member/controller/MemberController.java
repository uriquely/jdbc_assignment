package member.controller;

import java.util.List;

import member.model.dao.MemberDAO;
import member.model.vo.Member;

public class MemberController {

//	컨트롤러는 DAO한테 계속 일을 시켜야 하므로 아예 필드로서 만들어둔다.
	private MemberDAO memberDAO = new MemberDAO();
	
//	mvc패턴.png 그림에서 view가 controller에 request하는 과정에 해당된다.
	public int insertMember(Member member) {
		
		return memberDAO.insertMember(member);
//		controller에서는 DAO에 메소드를 요청한다.
	}

	//MemberMenu case1 에서 요청받았음
	public List<Member> selectAll() {
		return memberDAO.selectAll();
	}

	//MemberMenu case2 에서 요청받음
	public Member retrieveMemberId(String inputId) {
		return memberDAO.retrieveMemberId(inputId);
	}
	
	//MemberMenu case3 에서 요청받음
	public List<Member> retrieveMemberName(String inputName) {
		return memberDAO.retrieveMemberName(inputName);
	}

	//MemberMenu case5 에서 요청받음
	public int modifiedMember(String inputModifiedId, Member inputModifiedMemberInfo) {
		return memberDAO.modifiedMember(inputModifiedId, inputModifiedMemberInfo);
	}

	//MemberMenu case6 에서 요청받음
	public int deleteMember(String deleteMemberId) {
		return memberDAO.deleteMember(deleteMemberId);
	}

}
