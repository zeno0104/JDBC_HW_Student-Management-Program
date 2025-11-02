package com.student.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import static com.student.jdbc.common.JDBCTemplate.*;
import com.student.jdbc.model.dao.SubjectDAO;
import com.student.jdbc.model.dto.Subject;

public class SubjectService {

	private SubjectDAO dao = new SubjectDAO();

	/**
	 * sub method 등록된 과목 체크
	 * 
	 * @param subName
	 * @return
	 */
	public Subject checkSubject(String subCode) throws Exception {
		Connection conn = getConnection();
		Subject subjectInfo = dao.checkSubject(conn, subCode);

		close(conn);
		return subjectInfo;
	}

	/**
	 * sub method 학생 학과에 해당하는 과목 리스트 가져오기
	 * 
	 * @param major
	 * @return
	 */
	public List<Subject> getSubjectList(String major) throws Exception {
		Connection conn = getConnection();
		List<Subject> subjectList = dao.getSubjectList(conn, major);

		close(conn);
		return subjectList;
	}

	/**
	 * sub method 특정 코드에 속하는 과목 가져오기
	 * 
	 * @param subCode
	 * @return
	 * @throws Exception
	 */
	public Subject getSubject(String subCode) throws Exception {
		Connection conn = getConnection();
		Subject subject = dao.getSubject(conn, subCode);

		close(conn);
		return subject;
	}

	/**
	 * 1. 과목 등록
	 * 
	 * @param subject
	 * @return
	 */
	public int addSubject(Subject subject) throws Exception {
		int result = 0;

		Connection conn = getConnection();

		result = dao.addSubject(conn, subject);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	/**
	 * 3.1 과목명 수정
	 * 
	 * @param newSubName
	 * @return
	 */
	public int updateSubjectName(String newSubName, String currSubName) throws Exception {
		Connection conn = getConnection();

		int result = dao.updateSubjectName(conn, newSubName, currSubName);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/**
	 * 3.2 담당 교수 수정
	 * 
	 * @param newSubName
	 * @param subjectName
	 * @return
	 */
	public int updateProfessor(String newProfessorName, String subCode) throws Exception {
		Connection conn = getConnection();

		int result = dao.updateProfessor(conn, newProfessorName, subCode);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	/**
	 * 3.3 학점 수정
	 * 
	 * @param newCredit
	 * @param subjectCode
	 * @return
	 */
	public int updateCredit(int newCredit, String subjectCode) throws Exception {
		Connection conn = getConnection();

		int result = dao.updateCredit(conn, newCredit, subjectCode);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	/** 3.4 개설년도 수정
	 * @param newYear
	 * @param subjectCode
	 * @return
	 */
	public int updateOpenYear(int newYear, String subjectCode) throws Exception {
		Connection conn = getConnection();

		int result = dao.updateOpenYear(conn, newYear, subjectCode);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	/** 3.5 학기 수정
	 * @param newSemester
	 * @param subjectCode
	 * @return
	 */
	public int updateSemester(int newSemester, String subjectCode) throws Exception{
		Connection conn = getConnection();

		int result = dao.updateSemester(conn, newSemester, subjectCode);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	/**
	 * 4. 과목 삭제
	 * 
	 * @param subCode
	 * @return
	 */
	public int deleteSubject(String subCode) throws Exception {
		Connection conn = getConnection();

		int result = dao.deleteSubject(conn, subCode);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);
		return result;
	}

	

}
