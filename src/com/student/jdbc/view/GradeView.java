package com.student.jdbc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.student.jdbc.model.dto.Student;
import com.student.jdbc.model.dto.Subject;
import com.student.jdbc.model.service.GradeService;
import com.student.jdbc.model.service.StudentService;
import com.student.jdbc.model.service.SubjectService;

public class GradeView {

	private Scanner sc = new Scanner(System.in);
	private StudentService studentService = new StudentService();
	private SubjectService subjectService = new SubjectService();
	private GradeService gradeService = new GradeService();

	/**
	 * sub method 학생이 있는지 조회
	 * 
	 * @param stdNo
	 * @return
	 * @throws Exception
	 */
	public Student checkStd(String stdNo) throws Exception {

		Student stdInfo = studentService.checkStd(stdNo);

		return stdInfo;
	}

	/** 성적 등록
	 * @return
	 */
	public void addGrade(Student studentInfo) throws Exception {
		String stdNo = "";
		int result = 0;
		while (true) {
			System.out.print("\n학번 입력 : ");
			stdNo = sc.next();

			studentInfo = checkStd(stdNo);

			if (studentInfo == null) {
				System.out.println("\n존재하지 않는 학생입니다.\n");
				continue;
			}
			break;
		}

		List<Subject> subjectList = subjectService.getSubjectList(studentInfo.getMajor());
		if (subjectList.size() == 0) {
			System.out.println("해당 학과에 등록된 과목이 없습니다.");
			return;
		}

		System.out.println("\n=== " + studentInfo.getMajor() + " 개설 과목 목록 ===");
		System.out.printf("%-10s │ %-15s │ %-8s │ %-4s │ %-8s │ %-3s%n", "코드", "과목명", "교수명", "학점", "개설년도", "학기");
		System.out.println("──────────────────────────────────────────────────────────────");

		for (Subject subject : subjectList) {
			System.out.println(subject.toString());
		}
		String subCode = "";

		while (true) {
			System.out.print("과목 코드 입력 : ");
			subCode = sc.next().toUpperCase();

			Subject subjectInfo = subjectService.checkSubject(subCode);

			if (subjectInfo == null) {
				System.out.print("등록되지 않은 과목입니다.\n");
				continue;
			}
			break;
		}

		int score = 0;

		while (true) {
			System.out.print("점수 입력 (0~100): ");
			score = sc.nextInt();
			if (score < 0 || score > 100) {
				System.out.println("점수는 0~100 사이의 수를 입력해주세요.");
				continue;
			}
			break;
		}

		result = gradeService.manageStudentGrades(stdNo, subCode, score);
		if (result > 0) {
			System.out.println("성적이 등록되었습니다.");
		} else {
			System.out.println("성적 등록 실패.");
		}
	}

	/**
	 * 7.1 성적 등록 -> 중복으로 넣었을 때는 안되!
	 */
	public void manageStudentGrades() throws Exception {
		System.out.println("\n====7. 성적 관리 ====");
		int input = 0;
		int result = 0;
		Student studentInfo = null;

		do {
			System.out.println("1. 성적 등록");
			System.out.println("2. 성적 수정");
			System.out.println("3. 성적 조회");
			System.out.println("0. 종료");
			System.out.print("메뉴 선택 : ");
			input = sc.nextInt();

			switch (input) {
			case 1: // 1. 성적 등록
				addGrade(studentInfo);
				break;
			case 2: // 2. 성적 수정
				updateStudentGrade();
				break;
			case 3: // 3. 성적 조회
				getGradesByStudent();
				break;
			case 0: // 0. 종료
				System.out.println("성적 관리를 종료합니다.");
				break;
			default:
				System.out.println("0~3 사이의 정수를 입력하세요.");
				input = -1;
			}

		} while (input != 0);

	}

	/**
	 * 7.2 성적 수정
	 * 
	 * @throws Exception
	 */
	public void updateStudentGrade() throws Exception {
		List<Student> studentGradeList = new ArrayList<Student>();

		System.out.println("\n===성적 수정===");
		System.out.print("학번 입력 : ");
		String stdNo = sc.next();

		Student studentInfo = checkStd(stdNo);

		if (studentInfo == null) {
			System.out.println("해당 학생이 존재하지 않습니다.");
			return;
		}
		studentGradeList = gradeService.getGradesByStudent(stdNo);

		if (studentGradeList.size() == 0) {
			System.out.println("해당 학생의 성적이 존재하지 않습니다.");
			return;
		}
		System.out.println("───────────────────────────────────────────────────────────");
		System.out.printf("학번 : %s | 이름 : %s | 전공 : %s\n", studentInfo.getStdNo(), studentInfo.getStdName(),
				studentInfo.getMajor());
		System.out.println("───────────────────────────────────────────────────────────");

		System.out.println("      과목 코드      |      과목명      │   점수    |   학점");
		System.out.println("───────────────────────────────────────────────────────────");

		for (Student student : studentGradeList) {
			System.out.printf("         %-15s %-15s %-10d %s\n", student.getSubjectCode(), student.getSubjectName(),
					student.getScore(), student.getGrade());
		}
		System.out.println("───────────────────────────────────────────────────────────");

		boolean flag = false;
		String changeSubCode = "";

		while (true) {
			System.out.print("수정할 과목 코드 입력 : ");
			changeSubCode = sc.next().toUpperCase();
			for (Student grade : studentGradeList) {
				if (grade.getSubjectCode().equals(changeSubCode)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				System.out.print("이수한 과목 코드와 일치하지 않습니다.\n");
				continue;
			}
			break;
		}
		int score = 0;
		while (true) {
			System.out.print("새 점수 입력 (0~100): ");
			score = sc.nextInt();

			if (score < 0 || score > 100) {
				System.out.println("점수는 0~100 사이 점수만 입력해주세요. ");
				continue;
			}
			break;
		}

		int result = gradeService.updateStudentGrade(stdNo, changeSubCode, score);

		if (result > 0) {
			System.out.println("성적이 수정되었습니다.");

		} else {
			System.out.println("성적 수정 실패.");
		}
	}

	/**
	 * 7.3 성적 조회
	 * 
	 * @throws Exception
	 */
	public void getGradesByStudent() throws Exception {

		List<Student> studentGradeList = new ArrayList<Student>();

		System.out.println("\n===성적 조회===\n");
		System.out.print("학번 입력 : ");
		String stdNo = sc.next();

		Student studentInfo = checkStd(stdNo);

		if (studentInfo == null) {
			System.out.println("학생이 존재하지 않습니다.");
			return;
		}

		studentGradeList = gradeService.getGradesByStudent(stdNo);

		if (studentGradeList.size() == 0) {
			System.out.println("해당 학생의 성적이 존재하지 않습니다.");
			return;
		}
		System.out.println("──────────────────────────────────────────────");
		System.out.printf("학번 : %s | 이름 : %s | 전공 : %s\n", studentInfo.getStdNo(), studentInfo.getStdName(),
				studentInfo.getMajor());
		System.out.println("──────────────────────────────────────────────");

		System.out.println("과목명      │   점수    |   학점");
		System.out.println("──────────────────────────────────────────────");
		double totalGpa = 0;
		double avgGpa = 0;
		for (Student student : studentGradeList) {
			System.out.printf("%-10s %-10d %s\n", student.getSubjectName(), student.getScore(), student.getGrade());
			totalGpa += student.getGpa();
		}
		avgGpa = totalGpa / studentGradeList.size();

		System.out.println("──────────────────────────────────────────────");
		System.out.printf("평균 평점 : %.2f\n", avgGpa);
	}
}
