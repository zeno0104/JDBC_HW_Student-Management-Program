package com.student.jdbc.view;

import java.util.Scanner;

public class MainView {
	private Scanner sc = new Scanner(System.in);
	private StudentView studentView = new StudentView();
	private GradeView gradeView = new GradeView();
	private SubjectView subjectView = new SubjectView();

	public void displayMenu() {
		System.out.println("====KH대학교 학생관리 프로그램에 오신것을 환영합니다!====");
		int input = 0;

		try {
			do {
				System.out.println("1. 학생 등록");
				System.out.println("2. 전체 학생 조회");
				System.out.println("3. 학생 정보 수정");
				System.out.println("4. 학생 삭제");
				System.out.println("5. 전공별 학생 조회");
				System.out.println("6. 재학 상태 관리");
				System.out.println("7. 성적 관리");
				System.out.println("8. 과목 관리");
				System.out.println("0. 프로그램 종료");

				System.out.print("\n메뉴를 선택해주세요 : ");
				input = sc.nextInt();

				switch (input) {
				case 1: /* registerStudent() */
					studentView.registerStudent();
					break;
				case 2: /* getAllStudents() */
					studentView.getAllStudents();
					break;
				case 3: /* updateStudentInfo() */
					studentView.updateStudentInfo();
					break;
				case 4: /* deleteStudentById() */
					studentView.deleteStudentById();
					break;
				case 5: /* getStudentsByMajor() */
					studentView.getStudentsByMajor();
					break;
				case 6: /* manageEnrollmentStatus() */
					studentView.manageEnrollmentStatus();
					break;
				case 7: /* manageStudentGrades() */
					gradeView.manageStudentGrades();
					break;
				case 8: /* manageSubject() */
					subjectView.manageSubject();
					break;
				case 0:
					System.out.println("프로그램을 종료합니다...");
					break;
				default:
					System.out.println("1~5 사이의 정수만 입력해주세요!!");
					input = -1;
				}

			} while (input != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
