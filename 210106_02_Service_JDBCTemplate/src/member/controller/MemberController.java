package member.controller;

import java.util.List;

import member.model.service.MemberService;
import member.model.vo.Member;

public class MemberController {
	
	private MemberService memberService = new MemberService();
	
	public int insertMember(Member member) {
		return memberService.insertMember(member);
	}

	//case2
	public Member selectOneMember(String memberId) {
		return memberService.selectOneMember(memberId);
	}

	public List<Member> selectMemberName(String memberName) {
		return memberService.selectMemberName(memberName);
	}

	public List<Member> selectAll() {
		return memberService.selectAll();
	}

	public int deleteMember(String memberId) {
		return memberService.deleteMember(memberId);
	}
	
	//서브 case1 : 암호 변경
	public int updateMemberPassword(String id, String password) {
		return memberService.updateMemberPassword(id, password);
	}

	public int updateMemberEmail(String id, String email) {
		return memberService.updateMemberEmail(id, email);
	}

	public int updateMemberPhone(String id, String phone) {
		return memberService.updateMemberPhone(id, phone);
	}

	public int updateMemberAddress(String id, String address) {
		return memberService.updateMemberAddress(id, address);
	}

}
