package com.student.jdbc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.student.jdbc.model.dto.Student;
import com.student.jdbc.model.service.StudentService;

public class StudentView {
	private Scanner sc = new Scanner(System.in);
	private StudentService service = new StudentService();

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
				System.out.println("0. 프로그램 종료");

				System.out.print("메뉴를 선택해주세요 : ");
				input = sc.nextInt();

				switch (input) {
				case 1: /* registerStudent() */
					registerStudent();
					break;
				case 2: /* getAllStudents() */
					getAllStudents();
					break;
				case 3: /* updateStudentInfo() */
					updateStudentInfo();
					break;
				case 4: /* deleteStudentById() */
					deleteStudentById();
					break;
				case 5: /* getStudentsByMajor() */
					getStudentsByMajor();
					break;
				case 0:
					System.out.println("프로그램을 종료합니다...");
					break;
				default:
					System.out.println("1~5 사이의 정수만 입력해주세요!!");
				}
				input = -1;
			} while (input != 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 1. 학생 등록
	 * @throws Exception
	 */
	public void registerStudent() throws Exception {
		System.out.println("\n===1. 학생 등록===\n");
		
		System.out.print("학번 : ");
		int stdNo = sc.nextInt();
		
		System.out.print("학생 이름 : ");
		String name = sc.next();
		
		System.out.print("나이 : ");
		int age = sc.nextInt();

		System.out.print("전공명 : ");
		String major = sc.next();

		System.out.print("입학 날짜 (EX) 2025-10-30): ");
		String entDate = sc.next();

		Student student = new Student();

		student.setStdNo(stdNo);
		student.setStdName(name);
		student.setStdAge(age);
		student.setMajor(major);
		student.setEntDate(entDate);

		int result = service.registerStudent(student);

		if (result > 0) {
			System.out.println("학생을 등록하였습니다.");
		} else {
			System.out.println("학생 등록 실패.");
		}
	}

	/**
	 * 2. 전체 학생 조회
	 */
	public void getAllStudents() throws Exception {
		System.out.println("\n===2. 전체 학생 조회===\n");
		
		List<Student> studentList = service.getAllStudents();
		
		if(studentList.size() == 0) {
			System.out.println("학생이 존재하지 않습니다.");
			return;
		}
		System.out.println("\n<<< 학생 리스트 >>>\n");
		System.out.println("학번      │ 이름     │ 나이 │ 전공           │ 입학일");
		System.out.println("──────────────────────────────────────────────────────────────");
		for(Student std : studentList) {
			System.out.println(std.toString());
		}
	}

	/** Sub method 
	 * 학생 유무 확인
	 * => 3번, 4번에서 쓰임.
	 * @return
	 * @throws Exception
	 */
	public Student checkStd() throws Exception{
		
		System.out.println("<<<학생 검색>>>");
		System.out.print("학번 입력 : ");
		int stdNo = sc.nextInt();
		
		System.out.print("이름 : ");
		String name = sc.next();
		
		System.out.print("학과 : ");
		String major = sc.next();
		
		Student student = new Student();
		
		student.setStdNo(stdNo);
		student.setStdName(name);
		student.setMajor(major);
		
		Student stdInfo = service.checkStd(student);
		
		return stdInfo;
	}
	/**
	 * 3. 학생 정보 수정(이름, 나이, 전공 변경 가능)
	 * 3-1) 학번, 이름, 전공으로 필터 처리
	 * 3-2) 3-1번이 통과되면 3-2로 진행
	 */
	public void updateStudentInfo() throws Exception {
		System.out.println("\n===3. 학생 정보 수정(이름, 나이, 전공 변경 가능)===\n");
		
		Student stdInfo = checkStd();
		
		if(stdInfo == null) {
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
			
			
			switch(input){
				case 1: // 1. 이름 수정
					System.out.print("수정할 이름 입력 : ");
					String newName = sc.next();
					
					result = service.updateStdName(stdInfo.getStdNo(), newName);
					
					if(result > 0) {
						System.out.println(stdInfo.getStdName() + " -> " + newName + "로 변경 완료!\n");
					} else {
						System.out.println("이름 수정 실패");
						return;
					}
					
					break;
				case 2: // 2. 나이 수정
					System.out.print("수정할 나이 입력 : ");
					String newAge = sc.next();
					
					result = service.updateStdAge(stdInfo.getStdNo() , newAge);
					if(result > 0) {
						System.out.println(stdInfo.getStdAge() + "세 -> " + newAge + "세로 변경 완료!\n");
					} else {
						System.out.println("나이 수정 실패");
						input = 0;
					}
					
					break;
				case 3: // 3. 전공 수정
					System.out.print("수정할 전공 입력 : ");
					String newMajor = sc.next();
					
					result = service.updateStdMajor(stdInfo.getStdNo(), newMajor);
					if(result > 0) {
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
		} while(input != 0);
	}

	/**
	 * 4. deleteStudentById (학번 기준 삭제)
	 */
	public void deleteStudentById() throws Exception{
		System.out.println("\n===4. 학생 삭제===\n");
		
		Student stdInfo = checkStd();
		
		if(stdInfo == null) {
			System.out.println("학생 정보가 일치하지 않거나 존재하지 않습니다.\n");
			return;
		}
		
		int result = service.deleteStudentById(stdInfo.getStdNo());
		
		if(result > 0) {
			System.out.println(stdInfo.getStdName() + "님이 삭제되었습니다.");
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
		
		System.out.print("조회할 학과(학부) 입력 : ");
		String major = sc.next();
		stdList = service.getStudentsByMajor(major);
		
		if(stdList.isEmpty()) {
			System.out.println("학생이 존재하지 않습니다.");
			return;
		} 
		String majorName = stdList.get(0).getMajor();
		
		System.out.println("\n<<<" + majorName +" 학생 리스트>>>\n");
		System.out.println("학번      │ 이름     │ 나이 │ 전공           │ 입학일");
		System.out.println("──────────────────────────────────────────────────────────────");
		for(Student student : stdList) {
			System.out.println(student.toString());
		}
		
	}
}















