package com.student.jdbc.view;

import java.util.List;
import java.util.Scanner;

import com.student.jdbc.model.dto.Subject;
import com.student.jdbc.model.service.SubjectService;

public class SubjectView {
	private Scanner sc = new Scanner(System.in);
	private SubjectService subjectService = new SubjectService();

	public void manageSubject() throws Exception {
		int input = 0;
		do {
			System.out.println("\n===8. 과목관리===\n");
			System.out.println("1. 과목 등록");
			System.out.println("2. 전공별 과목 조회");
			System.out.println("3. 과목 수정");
			System.out.println("4. 과목 삭제");
			System.out.println("0. 종료");

			System.out.print("메뉴를 선택해주세요: ");
			input = sc.nextInt();

			switch (input) {
			case 1: /* addSubject() */
				addSubject();
				break;
			case 2: /* getSubjectsByMajor() */
				getSubjectsByMajor();
				break;
			case 3: /* updateSubject() */
				updateSubject();
				break;
			case 4: /* deleteSubject() */
				deleteSubject();
				break;
			case 0:
				System.out.println("과목관리를 종료합니다.\n");
				break;
			default:
				System.out.println("0~4 사이의 정수를 입력해주세요.");
				input = -1;
			}

		} while (input != 0);
	}

	/**
	 * 1. 과목 등록
	 */
	public void addSubject() throws Exception {
		int result = 0;

		System.out.print("과목 코드 : ");
		String subCode = sc.next().toUpperCase();

		sc.nextLine();
		Subject subjectInfo = subjectService.checkSubject(subCode);

		if (subjectInfo != null) {
			System.out.println("이미 등록되어있는 과목입니다.\n");
			return;
		}

		System.out.print("과목명 : ");
		String subName = sc.nextLine();

		System.out.print("개설 학과 코드 : ");
		String major = sc.next().toUpperCase();

		System.out.print("담당 교수 ID (예: PR101): ");
		String profCode = sc.next();


		System.out.print("학점(1~3) : ");
		int gpa = 0;

		while (true) {
			gpa = sc.nextInt();

			if (gpa < 1 || gpa > 3) {
				System.out.println("1~3 사이의 정수를 입력해주세요.");
				continue;
			}
			break;
		}

		System.out.print("개설 년도 (ex) 2025): ");
		int year = sc.nextInt();

		System.out.print("학기 (1 ~ 2) : ");
		int semester = 0;

		while (true) {
			semester = sc.nextInt();

			if (semester < 1 || semester > 2) {
				System.out.println("1~2 사이의 정수를 입력해주세요.");
				continue;
			}
			break;
		}

		Subject subject = new Subject(subCode, subName, major, profCode, gpa, year, semester);

		result = subjectService.addSubject(subject);

		if (result > 0) {
			System.out.println("과목이 정상적으로 등록되었습니다.\n");
		} else {
			System.out.println("과목 등록 실패.\n");
		}

	}

	/**
	 * 2. 전공별 과목 조회
	 */
	public void getSubjectsByMajor() throws Exception {
		System.out.println("\n===전공별 과목 조회===\n");

		System.out.print("조회할 학과 코드 입력 : ");
		String major = sc.next().toUpperCase();

		List<Subject> subject = subjectService.getSubjectList(major);

		if(subject.size() == 0) {
			System.out.println("존재하지 않습니다.");
			return;
		}
		System.out.println("───────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("코드     |     학기     |     교수코드     |     학점     |     년도     |     과목명");
		System.out.println("───────────────────────────────────────────────────────────────────────────────────────");

		for (Subject sub : subject) {
			System.out.printf("%-12s   %-12d   %-16s   %-10d    %-10d   %s \n", sub.getSubjectCode(), sub.getSemester(),
					sub.getProfessor(), sub.getCredit(), sub.getOpenYear(), sub.getSubjectName());
		}

	}

	/**
	 * 3. 과목 수정
	 */
	public void updateSubject() throws Exception {
		System.out.println("\n===과목 수정===\n");
		int input = 0;

		do {
			System.out.println("1. 과목명 수정");
			System.out.println("2. 담당 교수 수정");
			System.out.println("3. 학점 수정");
			System.out.println("4. 개설년도 수정");
			System.out.println("5. 학기 수정");
			System.out.println("0. 종료");
			System.out.println("─────────────────────────────────────────────────────────────────────────────────");
			System.out.print("메뉴 선택 : ");
			input = sc.nextInt();

			switch (input) {
			case 1: /* updateSubjectName() */
				updateSubjectName();
				break;
			case 2: /* updateProfessor() */
				updateProfessor();
				break;
			case 3: /* updateCredit() */
				updateCredit();
				break;
			case 4: /* updateOpenYear() */
				updateOpenYear();
				break;
			case 5: /* updateSemester() */
				updateSemester();
				break;
			case 0:
				System.out.println("과목 수정을 종료합니다.\n");
				break;
			default:
				System.out.println("0~5 사이의 정수를 입력하세요.");
			}
		} while (input != 0);

	}

	/**
	 * 1. 과목명 수정
	 */
	public void updateSubjectName() throws Exception {
		System.out.println("\n===1. 과목명 수정===\n");
		System.out.print("수정할 과목 코드 입력 : ");
		String subCode = sc.next().toUpperCase();

		Subject subject = subjectService.getSubject(subCode);
		if (subject == null) {
			System.out.println("등록된 과목이 없습니다.\n");
			return;
		}

		System.out.println("현재 과목명 : " + subject.getSubjectName());
		System.out.print("변경할 과목명 입력 : ");
		String newSubName = sc.next();

		int result = subjectService.updateSubjectName(newSubName, subject.getSubjectName());

		if (result > 0) {
			System.out.println("과목명이 변경되었습니다.\n");
		} else {
			System.out.println("과목명 변경 실패.\n");
		}
	}

	/**
	 * 2. 담당 교수 수정
	 */
	public void updateProfessor() throws Exception {
		System.out.println("\n===2. 담당 교수 수정===\n");
		System.out.print("수정할 과목 코드 입력 : ");
		String subCode = sc.next().toUpperCase();

		Subject subject = subjectService.getSubject(subCode);
		if (subject == null) {
			System.out.println("등록된 과목이 없습니다.\n");
			return;
		}

		System.out.println("현재 담당 교수 : " + subject.getProfName() + "(" + subject.getProfessor() + ")");
		System.out.print("새로운 담당 교수 코드 입력 : ");
		String professorCode = sc.next().toUpperCase();

		int result = subjectService.updateProfessor(professorCode, subject.getSubjectCode());

		if (result > 0) {
			System.out.println("담당 교수명이 변경되었습니다.\n");
		} else {
			System.out.println("담당 교수명 변경 실패.\n");
		}
	}

	/**
	 * 3. 학점 수정
	 */
	public void updateCredit() throws Exception {
		System.out.println("\n===3. 학점 수정===\n");
		System.out.print("수정할 과목 코드 입력 : ");
		String subCode = sc.next().toUpperCase();

		Subject subject = subjectService.getSubject(subCode);
		if (subject == null) {
			System.out.println("등록된 과목이 없습니다.\n");
			return;
		}

		System.out.println("현재 학점 : " + subject.getCredit());
		System.out.print("변경할 학점 입력 (1~3 사이 정수) : ");
		int newCredit = sc.nextInt();

		int result = subjectService.updateCredit(newCredit, subject.getSubjectCode());

		if (result > 0) {
			System.out.println("학점이 변경되었습니다.\n");
		} else {
			System.out.println("학점 변경 실패.\n");
		}
	}

	/**
	 * 4. 개설년도 수정
	 */
	public void updateOpenYear() throws Exception {
		System.out.println("\n===4. 개설년도 수정===\n");
		System.out.print("수정할 과목 코드 입력 : ");
		String subCode = sc.next().toUpperCase();

		Subject subject = subjectService.getSubject(subCode);
		if (subject == null) {
			System.out.println("등록된 과목이 없습니다.\n");
			return;
		}

		System.out.println("현재 개설년도 : " + subject.getOpenYear());
		System.out.print("변경할 개설년도 입력 (예: 2025) : ");
		int newYear = sc.nextInt();

		int result = subjectService.updateOpenYear(newYear, subject.getSubjectCode());

		if (result > 0) {
			System.out.println("개설년도가 변경되었습니다.\n");
		} else {
			System.out.println("개설년도 변경 실패.\n");
		}
	}

	/**
	 * 5. 학기 수정
	 */
	public void updateSemester() throws Exception {
		System.out.println("\n===5. 학기 수정===\n");

		System.out.print("수정할 과목 코드 입력 : ");
		String subCode = sc.next().toUpperCase();

		Subject subject = subjectService.getSubject(subCode);
		if (subject == null) {
			System.out.println("등록된 과목이 없습니다.\n");
			return;
		}

		System.out.println("현재 학기 : " + subject.getSemester());
		System.out.print("변경할 학기 입력 (1 또는 2) : ");
		int newSemester = sc.nextInt();

		int result = subjectService.updateSemester(newSemester, subject.getSubjectCode());

		if (result > 0) {
			System.out.println("학기가 변경되었습니다.\n");
		} else {
			System.out.println("학기 변경 실패.\n");
		}
	}

	/**
	 * 4. 과목 삭제
	 */
	public void deleteSubject() throws Exception {
		System.out.println("\n===과목 삭제===\n");
		int result = 0;

		System.out.print("\n삭제할 과목 코드 입력 : ");
		String subCode = sc.next().toUpperCase();

		Subject subject = subjectService.getSubject(subCode);

		if (subject == null) {
			System.out.println(subCode + " 과목이 존재하지 않습니다.\n");
			return;
		}

		System.out.printf("정말로 %s (%s) 과목을 삭제하시겠습니까? (Y/N)", subject.getSubjectCode(), subject.getSubjectName());
		String yesOrNo = sc.next();

		if (yesOrNo.equalsIgnoreCase("N")) {
			System.out.println("삭제를 취소했습니다.\n");
			return;
		}
		result = subjectService.deleteSubject(subCode);

		if (result > 0) {
			System.out.println("과목이 성공적으로 삭제되었습니다.\n");
		} else {
			System.out.println("과목 삭제 실패\n");
		}
	}
}
