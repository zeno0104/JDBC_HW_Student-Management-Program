package com.student.jdbc.model.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.student.jdbc.common.JDBCTemplate.*;
import com.student.jdbc.model.dao.StudentDAO;
import com.student.jdbc.model.dto.Student;

public class StudentService {
	private StudentDAO dao = new StudentDAO();


	/** 1. 학생 등록 (Service)
	 * @param student
	 * @return
	 */
	public int registerStudent(Student student) throws Exception{
		Connection conn = getConnection();

		int result = 0;
		
		result = dao.registerStudent(conn, student);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}


	/** 2. 전체 학생 조회
	 * @return
	 */
	public List<Student> getAllStudents() throws Exception{
		Connection conn = getConnection();
		
		List<Student> studentList = dao.getAllStudents(conn);

		close(conn);
		return studentList;
	}


	/**3-1. 학생 정보 확인
	 * @param stdNo
	 * @return
	 */
	public Student checkStd(Student student) throws Exception{
		
		
		Connection conn = getConnection();
		
		Student stdInfo = dao.checkStd(conn, student);
		
		return stdInfo;
	}


	

	/** 3-2. 이름 수정
	 * @param newName
	 * @return
	 */
	public int updateStdName(int stdNo, String newName) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.updateStdName(conn, stdNo, newName);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}


	/** 3-3. 나이 수정
	 * @param newAge
	 * @return
	 */
	public int updateStdAge(int stdNo, String newAge) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.updateStdAge(conn, stdNo, newAge);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}


	/** 3-4 학과(학부) 수정
	 * @param newMajor
	 * @return
	 */
	public int updateStdMajor(int stdNo, String newMajor) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.updateStdMajor(conn, stdNo, newMajor);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}


	/** 4. 학번 기준 삭제
	 * @param stdNo
	 * @return
	 * @throws Exception
	 */
	public int deleteStudentById(int stdNo) throws Exception{
		Connection conn = getConnection();
		
		int result = dao.deleteStudentById(conn, stdNo);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		return result;
	}


	/** 5. 전공별 학생 조회(특정 전공 학생만 필터링 조회)
	 * @param major
	 * @return
	 * @throws Exception
	 */
	public List<Student> getStudentsByMajor(String major) throws Exception {
		Connection conn = getConnection();
		List<Student> stdList = dao.getStudentsByMajor(conn, major);
		
		
		return stdList;
	}
}









