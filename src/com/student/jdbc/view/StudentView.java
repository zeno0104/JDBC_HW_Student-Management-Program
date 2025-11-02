package com.student.jdbc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.student.jdbc.model.dto.Student;
import com.student.jdbc.model.service.MajorService;
import com.student.jdbc.model.service.StudentService;

public class StudentView {
	private Scanner sc = new Scanner(System.in);
	private StudentService studentService = new StudentService();
	private MajorService majorService = new MajorService();
	

	/**
	 * Sub method 학생 유무 확인 => 3번, 4번에서 쓰임.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Student checkStd(String stdNo) throws Exception {

		Student stdInfo = studentService.checkStd(stdNo);

		return stdInfo;
	}

	/**
	 * Sub method 과목 유무 확인
	 * 
	 * @param subName
	 * @return
	 * @throws Exception
	 */

	/**
	 * 1. 학생 등록
	 * 
	 * @throws Exception
	 */
	public void registerStudent() throws Exception {
		System.out.println("\n===1. 학생 등록===\n");

		System.out.print("학번 (예: 20250302) : ");
		
		String stdNo = sc.next();

		Student studentInfo = checkStd(stdNo);

		if (studentInfo != null) {
			System.out.println("이미 존재하는 학생입니다.");
			return;
		}

		System.out.print("학생 이름 : ");
		String name = sc.next();

		System.out.print("나이 : ");
		int age = sc.nextInt();

		System.out.print("전공 코드 : ");
		String major = sc.next().toUpperCase();
		
		int result = majorService.getMajorCode(major);
		
		if(result == 0) {
			System.out.println("존재하지 않는 학과입니다.\n");
			return;
		}

		System.out.print("입학 날짜 (EX) 2025-10-30): ");
		String entDate = sc.next();

		Student student = new Student();

		student.setStdNo(stdNo);
		student.setStdName(name);
		student.setStdAge(age);
		student.setMajor(major);
		student.setEntDate(entDate);

		// 조회

		result = studentService.registerStudent(student);

		if (result > 0) {
			System.out.println("학생을 등록하였습니다.\n");
		} else {
			System.out.println("학생 등록 실패.");
		}
	}

	/**
	 * 2. 전체 학생 조회
	 */
	public void getAllStudents() throws Exception {
		System.out.println("\n===2. 전체 학생 조회===\n");

		List<Student> studentList = studentService.getAllStudents();

		if (studentList.size() == 0) {
			System.out.println("학생이 존재하지 않습니다.");
			return;
		}
		System.out.println("\n<<< 학생 리스트 >>>\n");
		System.out.println("      학번      │      이름      │      나이      │      상태      │      입학일      │      전공");
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────");

		for (Student std : studentList) {
			System.out.println(std.toString());
		}
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────");
	}

	/**
	 * 3. 학생 정보 수정(이름, 나이, 전공 변경 가능) 3-1) 학번, 이름, 전공으로 필터 처리 3-2) 3-1번이 통과되면 3-2로 진행
	 */
	public void updateStudentInfo() throws Exception {
		System.out.println("\n===3. 학생 정보 수정(이름, 나이, 전공 변경 가능)===\n");

		System.out.print("학번 입력 : ");
		String stdNo = sc.next();
		Student stdInfo = checkStd(stdNo);

		if (stdInfo == null) {
			System.out.println("학생 정보가 일치하지 않거나 존재하지 않습니다.\n");
			return;
		}

		int input = 0;
		int result = 0;
		do {
			System.out.println("수정할 정보를 입력해주세요.");
			System.out.println("1. 이름");
			System.out.println("2. 나이");
			System.out.println("3. 전공");
			System.out.println("0. 종료\n");
			System.out.print("메뉴 선택 : ");
			input = sc.nextInt();

			switch (input) {
			case 1: // 1. 이름 수정
				System.out.print("수정할 이름 입력 : ");
				String newName = sc.next();

				result = studentService.updateStdName(stdInfo.getStdNo(), newName);

				if (result > 0) {
					System.out.println(stdInfo.getStdName() + " -> " + newName + "(으)로 변경 완료!\n");
				} else {
					System.out.println("이름 수정 실패");
					return;
				}

				break;
			case 2: // 2. 나이 수정
				System.out.print("수정할 나이 입력 : ");
				String newAge = sc.next();

				result = studentService.updateStdAge(stdInfo.getStdNo(), newAge);
				if (result > 0) {
					System.out.println(stdInfo.getStdAge() + "세 -> " + newAge + "세로 변경 완료!\n");
				} else {
					System.out.println("나이 수정 실패");
					input = 0;
				}

				break;
			case 3: // 3. 전공 수정
				System.out.print("수정할 전공 입력 : ");
				String newMajor = sc.next().toUpperCase();

				result = majorService.getMajorCode(newMajor);
				if(result == 0) {
					System.out.println("존재하지 않는 전공입니다.");
					return;
				}
				
				result = studentService.updateStdMajor(stdInfo.getStdNo(), newMajor);
				
				if (result > 0) {
					System.out.println(stdInfo.getMajor() + " -> " + newMajor + "로 변경 완료!\n");
				} else {
					System.out.println("전공 수정 실패.");
				}
				input = 0;
				break;
			case 0:
				System.out.println("수정을 종료합니다..\n");
				break;
			default:
				System.out.println("0~3번 사이의 정수를 입력해주세요.");
				input = -1;
			}
		} while (input != 0);
	}

	/**
	 * 4. deleteStudentById (학번 기준 삭제)
	 */
	public void deleteStudentById() throws Exception {
		System.out.println("\n===4. 학생 삭제===\n");

		System.out.print("학번 입력 : ");
		String stdNo = sc.next();
		Student stdInfo = checkStd(stdNo);

		if (stdInfo == null) {
			System.out.println("학생 정보가 일치하지 않거나 존재하지 않습니다.\n");
			return;
		}

		System.out.print(stdInfo.getStdName() + "(" + stdInfo.getMajorName() + ")님을 정말 삭제하시겠습니까? (Y/N) ");
		String yesOrNo = sc.next();
		
		if(yesOrNo.equalsIgnoreCase("N")) {
			System.out.println("삭제를 취소합니다.");
			return;
		}
		
		int result = studentService.deleteStudentById(stdInfo.getStdNo());

		if (result > 0) {
			System.out.println(stdInfo.getStdName() + "님이 삭제되었습니다.\n");
		} else {
			System.out.println("삭제 실패.");
		}

	}

	/**
	 * 5. 전공별 학생 조회(특정 전공 학생만 필터링 조회)
	 */
	public void getStudentsByMajor() throws Exception {
		List<Student> stdList = new ArrayList<Student>();
		System.out.println("\n===5. 전공별 학생 조회===\n");

		System.out.print("조회할 학과(학부) 코드 : ");
		String major = sc.next().toUpperCase();
		
		stdList = studentService.getStudentsByMajor(major);

		if (stdList.isEmpty()) {
			System.out.println("학생이 존재하지 않습니다.\n");
			return;
		}
		String majorName = stdList.get(0).getMajor();

		System.out.println("\n<<<" + majorName + " 학생 리스트>>>\n");
		System.out.println("      학번      │      이름      │      나이      │      상태      │      입학일      │      전공");
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────");
		for (Student student : stdList) {
			System.out.println(student.toString());
		}
		System.out.println("─────────────────────────────────────────────────────────────────────────────────────────────────────────");

	}

	/**
	 * 6. 재학 상태 관리
	 */
	public void manageEnrollmentStatus() throws Exception {
		System.out.println("\n===6. 재학 상태 관리===\n");
		System.out.print("수정할 학번 입력 : ");
		String stdNo = sc.next();

		Student studentInfo = checkStd(stdNo);

		if (studentInfo == null) {
			System.out.println("\n존재하지 않는 학생입니다.\n");
			return;
		}

		System.out.println("\n" + studentInfo.getStdName() + "(" + studentInfo.getMajorName() + ") 학생의 현재 상태 : "
				+ studentInfo.getStatus() + "\n\n");

		int input = 0;

		do {
			System.out.println("1. 재학");
			System.out.println("2. 휴학");
			System.out.println("3. 졸업");
			System.out.println("4. 제적");
			System.out.println("0. 종료");
			System.out.print("메뉴를 선택하세요 : ");

			input = sc.nextInt();

			// 재학 상태 비교
			switch (input) {
			case 1, 2, 3, 4:
				int result = studentService.manageEnrollmentStatus(stdNo, input);

				if (result == -1) {
					System.out
							.println(studentInfo.getStdName() + " 학생의 재학 상태는 이미 " + studentInfo.getStatus() + " 입니다.");
				}
				if (result > 0) {
					if (input == 1) {
						System.out.println(studentInfo.getStdName() + " 학생의 상태가 '재학'으로 변경되었습니다.\n");
					} else if (input == 2) {
						System.out.println(studentInfo.getStdName() + " 학생의 상태가 '휴학'으로 변경되었습니다.\n");
					} else if (input == 3) {
						System.out.println(studentInfo.getStdName() + " 학생의 상태가 '졸업'으로 변경되었습니다.\n");
					} else {
						System.out.println(studentInfo.getStdName() + " 학생의 상태가 '제적'으로 변경되었습니다.\n");
					}
				} else {
					System.out.println("재학 상태 수정 실패\n");
				}
				break;
			case 0:
				System.out.println("재학 상태 관리 메뉴를 종료합니다.\n");
				break;
			default:
				System.out.println("0~4 사이의 정수를 입력해주세요.\n");
				input = -1;
			}
		} while (input != 0);
	}


	
}
