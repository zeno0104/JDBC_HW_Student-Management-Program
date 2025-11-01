package com.student.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import static com.student.jdbc.common.JDBCTemplate.*;
import com.student.jdbc.model.dao.GradeDAO;
import com.student.jdbc.model.dto.Student;

public class GradeService {
	private GradeDAO dao = new GradeDAO();

	/**
	 * 7.1 성적 등록
	 * 
	 * @param stdNo
	 * @param subCode
	 * @param score
	 * @return
	 * @throws Exception
	 */
	public int manageStudentGrades(String stdNo, String subCode, int score) throws Exception {
		Connection conn = getConnection();

		int result = dao.manageStudentGrades(conn, stdNo, subCode, score);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

	/**
	 * 7.2 성적 수정
	 */
	public int updateStudentGrade(String stdNo, String changeSubCode, int score) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateStudentGrade(conn, stdNo, changeSubCode, score);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}

	/**
	 * 7.3 성적 조회
	 */
	public List<Student> getGradesByStudent(String stdNo) throws Exception {
		Connection conn = getConnection();

		List<Student> studentList = dao.getGradesByStudent(conn, stdNo);

		return studentList;
	}


}
