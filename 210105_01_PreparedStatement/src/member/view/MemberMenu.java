package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {
	
	private String line = "------------------------"
			            + "------------------------"
			            + "------------------------"
			            + "------------------------"
			            + "------------------------";
	private Scanner sc = new Scanner(System.in);
	
//	위에서 MemberController를 생성해서 아래에서 controller로 가져다 쓸 수 있도록 해준다.
	private MemberController controller = new MemberController();

	/*
	 * 2. 회원아이디 조회 : 사용자로부터 아이디를 입력받고 일치하는 회원 1명 조회
	 * 3. 회원이름 검색 : 사용자로부터 이름을 입력받고, 일부라도 일치하는 회원 n명 조회
	 * 5. 회원정보 수정 : 변경할 정보(아이디를 제외한 정보)를 입력받고, DB에 반영하기(아이디, 등록일은 변경불가)
	 * 6. 회원 탈퇴 : 사용자로부터 아이디를 입력받고, 일치하는 회원 1명 삭제
	 */
	public void mainMenu() {
		
		String menu = "-------- 회원 관리 프로그램 ---------\n"
					+ "1. 회원 전체 조회\n"
					+ "2. 회원 아이디 조회\n"
					+ "3. 회원 이름 검색\n"
					+ "4. 회원 가입\n"
					+ "5. 회원 정보 수정\n"
					+ "6. 회원 탈퇴\n"
					+ "0. 프로그램 끝내기\n"
					+ "--------------------------------\n"
					+ "선택 : ";
		
		while(true) {
			System.out.println(menu);
			int choice = sc.nextInt();
			
			Member member = null; //우리가 만든 Member 객체 임포트
			int result = 0; //정수형 변수 처리결과를 result에 담을 것
			List<Member> list = null;
			
			switch(choice) {
			case 1: 
				//1.controller요청
				list = controller.selectAll();
				//2.회원목록 출력
				displayMemberList(list);
				break;
				
//			2. 회원 아이디 조회 : 사용자로부터 아이디를 입력받고 일치하는 회원 1명 조회
			case 2: 
				//1.검색할 id 입력받기
				System.out.print("검색할 아이디를 입력하세요. : ");
				String inputId = sc.next();
				
				//2.controller에 요청
				member = controller.retrieveMemberId(inputId);
				
				//3.검색된 아이디 출력
				displayMemberId(member);
				break;
			
//			3. 회원이름 검색 : 사용자로부터 이름을 입력받고, 일부라도 일치하는 회원 n명 조회
			case 3: 
				
				//1.검색할 name 입력받기
				System.out.print("검색할 이름을 입력하세요. : ");
				String inputName = sc.next();
				
				//2.controller에 요청
				list = controller.retrieveMemberName(inputName);
				
				//3.검색된 아이디 출력
				displayMemberName(list);
				break;
			
			case 4: 
				
				//1.사용자 입력값 -> member객체 생성
				member = inputMember();
				
				//2.controller에 insert요청
				result = controller.insertMember(member);
//				System.out.println(result); 확인용
				
				//3.사용자 피드백
				displayMsg(result == 1 ? line + "\n회원가입 성공!\n" : line + "\n회원가입 실패!\n");
				break;
				
//			5. 회원정보 수정 : 변경할 정보(아이디를 제외한 정보)를 입력받고, DB에 반영하기(아이디, 등록일은 변경불가)
			case 5: 
				
				//1.사용자 입력값 -> member객체 생성
				System.out.print("정보를 수정할 아이디를 입력하세요. : ");
				String inputModifiedId = sc.next();
				Member inputModifiedMemberInfo = InputModifiedMember();
				
				//2.controller요청
				result = controller.modifiedMember(inputModifiedId, inputModifiedMemberInfo);
				
				//3.사용자 피드백
				displayModifiedMsg(result == 1 ? line + "\n정보수정 성공!\n" : line + "\n정보수정 실패!\n");
				break;
			
//			6. 회원 탈퇴 : 사용자로부터 아이디를 입력받고, 일치하는 회원 1명 삭제
			case 6: 
				//1.사용자 입력값
				System.out.print("탈퇴하고자 하는 아이디를 입력하세요. : ");
				String deleteMemberId = sc.next();
				
				//2.controller요청
				result = controller.deleteMember(deleteMemberId);
				
				//3.사용자 피드백
				displayDeleteMsg(result == 1 ? line + "\n회원탈퇴 성공!\n" : line + "\n회원탈퇴 실패!\n");
				break;
			
			case 0: 
				System.out.print("정말로 끝내시겠습니까?(y/n) : ");
				if(sc.next().charAt(0) == 'y') 
					return; //run으로 돌아간다.
				break;
			default : System.out.println("잘못 입력하셨습니다.");
			}
		}
	}
	
	private void displayDeleteMsg(String msg) {
		System.out.println(msg);
		
	}

	private void displayModifiedMsg(String msg) {
		System.out.println(msg);
	}

	/*
	 * 5. 회원정보 수정 : 변경할 정보(아이디를 제외한 정보)를 입력받고, DB에 반영하기(아이디, 등록일은 변경불가)
	 */
	private Member InputModifiedMember() {
		
		System.out.println(line + "\n변경 정보 입력하기\n" + line);
		
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
//		next 다음에 nextLine을 쓰면 사이에 개행문자 날리기용 nextLine이 필요
		
		System.out.print("주소 : ");
		String address = sc.nextLine();

		System.out.print("취미(, 으로 나열) : ");
		String hobby = sc.nextLine();

		return new Member(null,
						  password, 
						  memberName, 
						  gender, 
						  age, 
						  email, 
						  phone, 
						  address, 
						  hobby, 
						  null); //memberId, enrollDate는 null값으로 설정한다.(변경 불가)
	}

	/*
	 * 회원이름 검색 : 사용자로부터 이름을 입력받고, 일부라도 일치하는 회원 n명 조회
	 */
	private void displayMemberName(List<Member> list) {
		if(list != null) {
			System.out.println("\n검색 결과는 " 
							   + list.size() 
							   + "명 입니다.\n" 
							   + line);
			for(Member member : list) {
				System.out.println(member + "\n" + line);
			}
			System.out.println("메뉴로 돌아갑니다.\n");
		}
		else {
			System.out.println("일치하는 이름의 정보가 없습니다. 다시 시도해 주세요.");
		}
	}

	/*
	 * 회원 아이디 조회 : 사용자로부터 아이디를 입력받고 일치하는 회원 1명 조회
	 */
	private void displayMemberId(Member member) {
		if(member != null) {
			System.out.println("\n조회된 내용은 아래와 같습니다." + "\n"
							   + line + "\n" 
							   + member + "\n" 
							   + line + "\n"
							   + "메뉴로 돌아갑니다.\n");
		}
		else {
			System.out.println("일치하는 ID의 정보가 없습니다. 다시 시도해 주세요.");
		}
	}

	/*
	 * 회원 목록 조회메소드
	 * (여러명 즉 여러행이겠지)
	 */
	private void displayMemberList(List<Member> list) {
		
		System.out.println(line);
		//조회된 회원정보가 있을 때
		if(list != null && !list.isEmpty()) {
			
			System.out.println("MemberId\t"
							   + "MemberName\t"
							   + "Gender\t"
							   + "Age\t"
							   + "Email\t"
							   + "Phone\t"
							   + "Address\t\t"
							   + "Hobby\t"
							   + "Enroll Date\n"
							   + line);
			for(Member member : list) {
				System.out.println(member);
			}
		}
		//조회된 회원정보가 없을 때
		else {
			System.out.println("조회된 회원이 없습니다.");
		}
		System.out.println(line);
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
		System.out.println("-------------------------------");
		
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
//		next 다음에 nextLine을 쓰면 사이에 개행문자 날리기용 nextLine이 필요
		
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
