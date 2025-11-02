package com.student.jdbc.view;

import java.util.List;
import java.util.Scanner;

import com.student.jdbc.model.dto.Professor;
import com.student.jdbc.model.service.ProfessorService;

public class ProfessorView {
	private Scanner sc = new Scanner(System.in);
	private ProfessorService professorService = new ProfessorService();

	public void manageProfessor() throws Exception {
		int input = 0;
		do {
			System.out.println("\n===9. 교수 관리===\n");
			System.out.println("1. 교수 등록");
			System.out.println("2. 교수 목록 조회");
			System.out.println("3. 교수 정보 수정");
			System.out.println("4. 교수 삭제");
			System.out.println("0. 종료");
			System.out.print("메뉴 선택 : ");
			input = sc.nextInt();
			switch (input) {
			case 1:
				addProfessor();
				break;
			case 2:
				showProfessorList();
				break;
			case 3:
				updateProfessor();
				break;
			case 4:
				deleteProfessor();
				break;
			case 0:
				System.out.println("교수 관리를 종료합니다.");
				break;
			default:
				System.out.println("0~4 사이의 정수를 입력해주세요.");
				input = -1;
			}
		} while (input != 0);
	}

	/**
	 * 1. 교수 등록
	 */
	public void addProfessor() throws Exception {
		System.out.println("\n===교수 등록===\n");

		System.out.print("교수 ID(예 : PR701) : ");
		String profId = sc.next().toUpperCase();

		System.out.print("교수 이름: ");
		String profName = sc.next();

		System.out.print("소속 학과 코드(ex) CS, BA..): ");
		String majorCode = sc.next().toUpperCase();

		System.out.print("직급 (예: 교수, 부교수, 조교수): ");
		String position = sc.next();

		System.out.print("이메일: ");
		String email = sc.next().toLowerCase();

		System.out.print("전화번호: ");
		String phone = sc.next();

		Professor pf = new Professor(profId, profName, majorCode, position, email, phone);
		int result = professorService.addProfessor(pf);

		if (result > 0) {
			System.out.println("교수 정보가 등록되었습니다.");
		} else {
			System.out.println("교수 등록 실패.");
		}
	}

	/**
	 * 2. 교수 목록 조회
	 */
	public void showProfessorList() throws Exception {
		System.out.println("===2. 교수 목록 조회===");
		System.out.print("학과 코드 입력 (ex) CS, 전체 조회는 ALL) : ");
		String majorCode = sc.next().toUpperCase();
		List<Professor> profList = professorService.showProfessorList(majorCode);

		if (profList.size() == 0) {
			System.out.println("교수가 존재하지 않습니다.");
			return;
		}

		System.out.println(
				"─────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("교수ID   │ 이름     │ 학과코드 │ 직급     │ 이메일                   │ 전화번호");
		System.out.println(
				"─────────────────────────────────────────────────────────────────────────────────────────────");

		for (Professor pf : profList) {
			System.out.printf("%-10s  %-10s %-5s %-10s %-25s %s\n", pf.getProfId(), pf.getProfName(), pf.getMajorCode(),
					pf.getPosition(), pf.getEmail(), pf.getPhone());
		}

	}

	/**
	 * 3. 교수 정보 수정
	 */
	public void updateProfessor() throws Exception {
		System.out.println("\n===3. 교수 정보 수정===");
		System.out.print("수정할 교수 ID 입력: ");
		String profId = sc.next().toUpperCase();

		Professor pfInfo = professorService.getProfInfo(profId);

		if (pfInfo == null) {
			System.out.println("교수가 존재하지 않습니다.");
			return;
		}
		int input = 0;
		System.out.println("<<< 현재 정보 >>>");
		System.out.println("이름: " + pfInfo.getProfName());
		System.out.println("학과: " + pfInfo.getMajorCode());
		System.out.println("직급: " + pfInfo.getPosition());
		System.out.println("이메일: " + pfInfo.getEmail());
		System.out.println("전화번호: " + pfInfo.getPhone());

		System.out.println("\n\n< 수정할 항목 선택 >");
		System.out.println("1. 이름");
		System.out.println("2. 학과 코드");
		System.out.println("3. 직급");
		System.out.println("4. 이메일");
		System.out.println("5. 전화번호");
		System.out.print("선택: ");
		input = sc.nextInt();

		switch (input) {
		case 1:
			updateName(pfInfo);
			break;
		case 2:
			updateMajorCode(pfInfo);
			break;
		case 3:
			updatePosition(pfInfo);
			break;
		case 4:
			updateEmail(pfInfo);
			break;
		case 5:
			updatePhone(pfInfo);
			break;
		case 0:
			System.out.println("프로그램을 종료합니다.");
			break;
		default:
			System.out.println("0~5 사이의 정수를 입력해주세요.");
		}
	}

	/**
	 * 3.1 교수 이름 수정
	 * 
	 * @param pfInfo
	 */
	public void updateName(Professor pfInfo) throws Exception {
		System.out.println("\n===이름 수정===\n");

		System.out.print("새로운 이름 입력: ");
		String name = sc.next();

		int result = professorService.updateName(pfInfo.getProfId(), name);

		if (result > 0) {
			System.out.println("교수 정보(이름)가 수정되었습니다.");
		} else {
			System.out.println("교수 정보(이름) 수정 실패.");
		}
	}

	/**
	 * 3.2 학과 코드 수정
	 * 
	 * @param pfInfo
	 */
	public void updateMajorCode(Professor pfInfo) throws Exception {
		System.out.println("\n===학과 코드 수정===\n");

		System.out.print("새로운 학과 코드 입력: ");
		String newMajorCode = sc.next().toUpperCase();

		int result = professorService.updateMajorCode(pfInfo.getProfId(), newMajorCode);

		if (result > 0) {
			System.out.println("학과 코드가 수정되었습니다.");
		} else {
			System.out.println("학과 코드 수정 실패.");
		}
	}

	/**
	 * 3.3 직급 수정
	 * 
	 * @param pfInfo
	 */
	public void updatePosition(Professor pfInfo) throws Exception {
		System.out.println("\n===직급 수정===\n");

		System.out.print("새로운 직급 입력: ");
		String position = sc.next();

		int result = professorService.updatePosition(pfInfo.getProfId(), position);

		if (result > 0) {
			System.out.println("직급이 수정되었습니다.");
		} else {
			System.out.println("직급 수정 실패.");
		}
	}

	/**
	 * 3.4 이메일 이름 수정
	 * 
	 * @param pfInfo
	 */
	public void updateEmail(Professor pfInfo) throws Exception {
		System.out.println("\n===이메일 수정===\n");

		System.out.print("새로운 이메일 입력: ");
		String email = sc.next();

		int result = professorService.updateEmail(pfInfo.getProfId(), email);

		if (result > 0) {
			System.out.println("이메일이 수정되었습니다.");
		} else {
			System.out.println("이메일 수정 실패.");
		}
	}

	/**
	 * 3.5 전화번호 수정
	 * 
	 * @param pfInfo
	 */
	public void updatePhone(Professor pfInfo) throws Exception {
		System.out.println("\n===전화번호 수정===\n");

		System.out.print("새로운 전화번호 입력: ");
		String phone = sc.next();

		int result = professorService.updatePhone(pfInfo.getProfId(), phone);

		if (result > 0) {
			System.out.println("전화번호가 수정되었습니다.");
		} else {
			System.out.println("전화번호 수정 실패.");
		}
	}

	/**
	 * 4. 교수 삭제
	 */
	public void deleteProfessor() throws Exception {
		System.out.print("삭제할 교수 ID 입력: ");
		String profId = sc.next().toUpperCase();

		Professor pfInfo = professorService.getProfInfo(profId);

		if (pfInfo == null) {
			System.out.println("교수가 존재하지 않습니다.");
			return;
		}

		int result = 0;
		System.out.print("정말 삭제하시겠습니까? (Y/N): ");
		String yesOrNo = sc.next().toUpperCase();

		if (yesOrNo.equalsIgnoreCase("Y")) {
			result = professorService.deleteProfessor(profId);
		} else {
			System.out.println("삭제를 취소합니다.");
		}

		if (result > 0) {
			System.out.println("교수 정보가 삭제되었습니다.");
		} else {
			System.out.println("교수 정보 삭제 실패.");
		}
	}

}
