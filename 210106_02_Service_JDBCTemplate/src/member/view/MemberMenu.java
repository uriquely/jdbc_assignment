package member.view;

import java.util.List;
import java.util.Scanner;

/*
 ##@실습문제(2021-01-07)
회원정보변경 서브메뉴를 제공해서 처리하세요

****** 회원 정보 변경 메뉴******
1. 암호변경
2. 이메일변경
3. 전화번호변경
4. 주소 변경
9. 메인메뉴 돌아가기


요구사항
* 메인메뉴에서 회원정보변경 메뉴 선택시, 수정할 회원아이디를 입력받고, 해당 회원정보(회원아이디조회)를 우선 보여준 후 서브메뉴출력
* 회원정보 수정은 선택된 필드를 각각 처리할 것.
* 회원정보변경 수정완료 시 수정된 회원정보를 출력후, 서브메뉴를 다시 출력.
* 수정할 회원아이디를 조회해서 해당하는 회원이 없다면, 메인메뉴를 다시 출력할 것.
 */

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {
	
	private String sLine = "--------------------------";
	private String line = "------------------------"
           				+ "------------------------"
           				+ "------------------------"
           				+ "------------------------"
        				+ "------------------------";
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	
public void mainMenu() {
		
		String menu = "-------- 회원 관리 프로그램 ---------\n"
					+ "1. 회원 전체 조회\n"
					+ "2. 회원 아이디 조회\n"
					+ "3. 회원 이름 검색\n"
					+ "4. 회원 가입\n"
					+ "5. 회원 정보 수정\n"
					/*
					 	회원정보변경 서브메뉴를 제공해서 처리하세요

						****** 회원 정보 변경 메뉴******
						1. 암호변경
						2. 이메일변경
						3. 전화번호변경
						4. 주소 변경
						9. 메인메뉴 돌아가기
					 */
					+ "6. 회원 탈퇴\n"
					+ "0. 프로그램 끝내기\n"
					+ "--------------------------------\n"
					+ "메뉴 번호 선택 : ";
		
		String submenu = "\n****** 회원 정보 변경 메뉴******\n"
				+ "1. 암호 변경\n"
				+ "2. 이메일 변경\n"
				+ "3. 전화번호 변경\n"
				+ "4. 주소 변경\n"
				+ "9. 메인메뉴 돌아가기\n"
				+ "--------------------------\n"
				+ "메뉴 번호 선택 : ";
		
		while(true) {
			System.out.print(menu);
			int choice = sc.nextInt();
			int result = 0; //DML처리
			Member member = null;
			List<Member> list = null;
			String id = null;
			String password = null;
			String email = null;
			String phone = null;
			String address = null;
			
			switch(choice) {
			
			//회원 전체 조회
			case 1: 
				list = memberController.selectAll();
				displayMemberList(list);
				break;
			
			//회원 아이디 조회
			case 2: 
				//1.사용자입력값(memberId)-컨트롤러 조회요청
				//2.member객체 혹은 null화면 출력
				member = memberController.selectOneMember(inputMemberId());
				displayMember(member);
				break;
				
			//회원 이름 검색
			case 3: 
				//1.사용자입력값(memberName)-컨트롤러 조회요청
				//2.member객체 혹은 null화면 출력
				list = memberController.selectMemberName(inputMemberName());
				displayMemberName(list);
				break;
			
			//회원 가입
			case 4: 
				//1.사용자입력값 회원객체
				//2.컨트롤러에 insertMember요청
				//3.사용자피드백
				result = memberController.insertMember(inputMember());
				displayMsg(result == 1 ? line + "\n회원가입 성공!\n" : line + "\n회원가입 실패!\n");
				break;
			
			//회원 정보 수정
			case 5: 
				/*
				****** 회원 정보 변경 메뉴******
				1. 암호변경
				2. 이메일변경
				3. 전화번호변경
				4. 주소 변경
				9. 메인메뉴 돌아가기
				*/

				id = inputMemberId();
				
				//요구사항 : 메인메뉴에서 회원정보변경 메뉴 선택시, 수정할 회원아이디를 입력받고, 해당 회원정보(회원아이디조회)를 우선 보여준 후 서브메뉴출력
				member = memberController.selectOneMember(id);
				displayMember(member);
					
				Outer:
				//요구사항 : 회원정보변경 수정완료 시 수정된 회원정보를 출력후, 서브메뉴를 다시 출력.
				while(true) {

				System.out.print(submenu);
				int subchoice = sc.nextInt();
					
					switch(subchoice) {
					
					//암호변경
					case 1: 
						//사용자 입력값 전달
	
						password = inputUpdate();
	
						//memberController요청
						result = memberController.updateMemberPassword(id, password);
						
						//사용자 피드백
						displayUpdateMsg(result == 1 ? line + "\n정보수정 성공!\n" : line + "\n정보수정 실패!\n");
						
						//요구사항 : 회원정보변경 수정완료 시 수정된 회원정보를 출력후, 서브메뉴를 다시 출력.
						member = memberController.selectOneMember(id);
						displayMember(member);
						
						break;
					
					//이메일변경
					case 2: 
						email = inputUpdate();
	
						//memberController요청
						result = memberController.updateMemberEmail(id, email);
						
						//사용자 피드백
						displayUpdateMsg(result == 1 ? line + "\n정보수정 성공!\n" : line + "\n정보수정 실패!\n");
						
						//요구사항 : 회원정보변경 수정완료 시 수정된 회원정보를 출력후, 서브메뉴를 다시 출력.
						member = memberController.selectOneMember(id);
						displayMember(member);
						
						break;
					
					//전화번호변경
					case 3: 
						phone = inputUpdate();
	
						//memberController요청
						result = memberController.updateMemberPhone(id, phone);
						
						//사용자 피드백
						displayUpdateMsg(result == 1 ? line + "\n정보수정 성공!\n" : line + "\n정보수정 실패!\n");
						
						//요구사항 : 회원정보변경 수정완료 시 수정된 회원정보를 출력후, 서브메뉴를 다시 출력.
						member = memberController.selectOneMember(id);
						displayMember(member);
						
						break;
					
					//주소변경
					case 4: 
						address = inputUpdateLine(); //주소는 nextLine()사용
	
						//memberController요청
						result = memberController.updateMemberAddress(id, address);
						
						//사용자 피드백
						displayUpdateMsg(result == 1 ? line + "\n정보수정 성공!\n" : line + "\n정보수정 실패!\n");
						
						//요구사항 : 회원정보변경 수정완료 시 수정된 회원정보를 출력후, 서브메뉴를 다시 출력.
						member = memberController.selectOneMember(id);
						displayMember(member);
						break;
					
					//메인메뉴 돌아가기
					case 9: 
						System.out.println("메인으로 돌아갑니다.");
						break Outer;
					default : System.out.println("잘못 입력하셨습니다.");
						break;
					}
				}
				break;
			//회원 탈퇴
			case 6: 
				result = memberController.deleteMember(inputMemberId());
				displayDeleteMsg(result == 1 ? line + "\n회원탈퇴 성공!\n" : line + "\n회원탈퇴 실패!\n");
				break;
			
			//프로그램 종료
			case 0: 
				System.out.println("정말 끝내시겠습니까?(y/n) : ");
				if(sc.next().charAt(0) == 'y')
					return;
				break;
			default : System.out.println("잘못 입력하셨습니다.");
			}
		}
	}

private String inputUpdateLine() {
	System.out.print(sLine + "\n변경할 값 입력: ");
	sc.nextLine();
	return sc.next();
}

private String inputUpdate() {
	System.out.print(sLine + "\n변경할 값 입력: ");
	return sc.next();
}

private void displayUpdateMsg(String msg) {
	System.out.println(msg);
	
}

private void displayDeleteMsg(String msg) {
	System.out.println(msg);
}

private void displayMemberList(List<Member> list) {
	
	System.out.println(line + "\n총 회원 수 : " + list.size() + "명\n" + line);
	
	if(list != null && !list.isEmpty()) {
		
		/*
		 * list가 null이란 소리는 아직 인스턴스가 생성되지 않은 상태 즉, 
		 * list변수가 메모리에 아무런 주소 값도 참조하지 않은 상태라
		 * 
		 * list.isEmpty()는 (list.size() == 0) 와 같다. 
		 * 인스턴스는 생성되어 있지만(참조주소가 있지만) 리스트안에 아무것도 적재하지 않은 상태
		 */
		
		System.out.println( "member_id\t"
				+ "password\t"
				+ "member_name\t"
				+ "gender\t"
				+ "age\t"
				+ "email\t"
				+ "phone\t"
				+ "address\t"
				+ "hobby\t"
				+ "enroll_date\n" + line);
		for(Member member : list) {
			System.out.println(member + "\n" + line);
		}
	}
	else {
		System.out.println("조회된 정보가 없습니다.");
	}
}

private void displayMemberName(List<Member> list) {
	
	if(list != null) {
		System.out.print(line + "\n겁색된 인원 수 : ");
		System.out.println(list.size() + "명");
		System.out.println(line);
		
		for(Member member : list) {
			System.out.println(member + "\n" + line);
		}
		System.out.println("메뉴로 돌아갑니다.\n");
	}
	else {
		System.out.println("조회된 정보가 없습니다.");
	}
		System.out.println(line);	
}

private String inputMemberName() {
	System.out.print("검색할 문자(이름) 입력: ");
	return sc.next();
}

private void displayMember(Member member) {
	
	if(member != null) {
        System.out.println(line + "\n아이디 정보\n" + line);
        System.out.println(member);
        System.out.println(line);
     }
	else {
		System.out.println("조회된 정보가 없습니다. 메인으로 돌아갑니다.");
		//요구사항 : 수정할 회원아이디를 조회해서 해당하는 회원이 없다면, 메인메뉴를 다시 출력할 것.
		mainMenu();
	}
}
	


//case2
private String inputMemberId() {
	System.out.print("아이디 입력: ");
	return sc.next();
}

/*
 * DML처리 후에 사용자에게 피드백을 주는 메소드
 */
private void displayMsg(String msg) {
	System.out.println(msg);
}

/*
 * 회원가입 메소드
 * 사용자로부터 입력처리
 */
private Member inputMember() {
	System.out.println("새로운 회원정보를 입력하세요.");
	System.out.println(sLine);
	
	System.out.print("아이디 : ");
	String memberId = sc.next();
	
	System.out.print("비밀번호 : ");
	String password = sc.next();
	
	System.out.print("이름 : ");
	String memberName = sc.next();
	
	System.out.print("나이 : ");
	int age = sc.nextInt();
	
	System.out.print("성별(M/F) : ");
	String gender = sc.next().toUpperCase(); //소문자로 입력해도 대문자로 바꾸어 줌
	
	System.out.print("이메일 : ");
	String email = sc.next();
	
	System.out.print("전화번호(-빼고 입력) : ");
	String phone = sc.next();
	
	sc.nextLine();
//	next 다음에 nextLine을 쓰면 사이에 개행문자 날리기용 nextLine이 필요
	
	System.out.print("주소 : ");
	String address = sc.nextLine();

	System.out.print("취미(, 으로 나열) : ");
	String hobby = sc.nextLine();

	return new Member(memberId, 
					  password, 
					  memberName, 
					  gender, 
					  age, 
					  email, 
					  phone, 
					  address, 
					  hobby, 
					  null); //enrollDate는 null값으로 설정한다.
	}
}
