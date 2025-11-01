package com.student.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.student.jdbc.common.JDBCTemplate.*;
import com.student.jdbc.model.dto.Subject;

public class SubjectDAO {
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**
	 * sub method 등록된 과목 체크
	 * 
	 * @param conn
	 * @param subCode
	 * @return
	 */
	public Subject checkSubject(Connection conn, String subCode) throws Exception {
		Subject subjectInfo = null;

		try {
			String sql = """
					SELECT SUBJECT_CODE, SUBJECT_NAME, MAJOR,
					PROFESSOR, CREDIT, OPEN_YEAR, SEMESTER
					FROM KH_SUBJECT
					WHERE SUBJECT_CODE = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, subCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String subjectCode = rs.getString("SUBJECT_CODE");
				String subjectName = rs.getString("SUBJECT_NAME");
				String major = rs.getString("MAJOR");
				String professor = rs.getString("PROFESSOR");
				int credit = rs.getInt("CREDIT");
				int openYear = rs.getInt("OPEN_YEAR");
				int semester = rs.getInt("SEMESTER");

				subjectInfo = new Subject(subjectCode, subjectName, major, professor, credit, openYear, semester);
			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return subjectInfo;
	}

	/**
	 * sub method 학생 학과에 해당하는 과목 리스트 가져오기
	 * 
	 * @param conn
	 * @param major
	 * @return
	 */

	public List<Subject> getSubjectList(Connection conn, String major) throws Exception {
		List<Subject> subjectList = new ArrayList<>();

		try {
			String sql = """
					SELECT SUBJECT_CODE, SUBJECT_NAME, MAJOR, PROFESSOR, CREDIT, OPEN_YEAR, SEMESTER
					FROM KH_SUBJECT
					WHERE MAJOR = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, major);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String subjectCode = rs.getString("SUBJECT_CODE");
				String subjectName = rs.getString("SUBJECT_NAME");
				String majorName = rs.getString("MAJOR");
				String professor = rs.getString("PROFESSOR");
				int credit = rs.getInt("CREDIT");
				int openYear = rs.getInt("OPEN_YEAR");
				int semester = rs.getInt("SEMESTER");

				Subject subject = new Subject(subjectCode, subjectName, majorName, professor, credit, openYear,
						semester);

				subjectList.add(subject);
			}

		} finally {
			close(rs);
			close(pstmt);
		}
		return subjectList;
	}

	/**
	 * 1. 과목 등록
	 * 
	 * @param conn
	 * @param subject
	 * @return
	 */
	public int addSubject(Connection conn, Subject subject) throws Exception {
		int result = 0;

		try {
			String sql = """
					INSERT INTO KH_SUBJECT
					VALUES(?, ?, ?, ?, ?, ?, ?)
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, subject.getSubjectCode());
			pstmt.setString(2, subject.getSubjectName());
			pstmt.setString(3, subject.getMajor());
			pstmt.setString(4, subject.getProfessor());
			pstmt.setInt(5, subject.getCredit());
			pstmt.setInt(6, subject.getOpenYear());
			pstmt.setInt(7, subject.getSemester());

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * sub method 특정 코드에 속하는 과목 가져오기
	 * 
	 * @param conn
	 * @param subCode
	 * @return
	 */
	public Subject getSubject(Connection conn, String subCode) throws Exception {
		Subject subject = null;

		try {
			String sql = """
					SELECT SUBJECT_CODE, SUBJECT_NAME
					FROM KH_SUBJECT
					WHERE SUBJECT_CODE = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, subCode);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String subjectCode = rs.getString("SUBJECT_CODE");
				String subjectName = rs.getString("SUBJECT_NAME");
				subject = new Subject();

				subject.setSubjectCode(subjectCode);
				subject.setSubjectName(subjectName);
			}

		} finally {
			close(rs);
			close(pstmt);
		}
		return subject;
	}

	/**
	 * 3.1 과목명 수정
	 * 
	 * @param conn
	 * @param newSubName
	 * @return
	 */
	public int updateSubjectName(Connection conn, String newSubName, String currSubName) throws Exception {
		int result = 0;

		try {
			String sql = """
					UPDATE KH_SUBJECT
					SET SUBJECT_NAME = ?
					WHERE SUBJECT_NAME = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newSubName);
			pstmt.setString(2, currSubName);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 3.2 담당 교수 수정
	 * 
	 * @param conn
	 * @param newProfessorName
	 * @param currProcessorName
	 * @return
	 */
	public int updateProfessor(Connection conn, String newProfessorName, String subCode) throws Exception {
		int result = 0;

		try {
			String sql = """
					UPDATE KH_SUBJECT
					SET PROFESSOR = ?
					WHERE SUBJECT_CODE = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newProfessorName);
			pstmt.setString(2, subCode);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}
	/** 3.3 학점 수정
	 * @param conn
	 * @param newCredit
	 * @param subjectCode
	 * @return
	 */
	public int updateCredit(Connection conn, int newCredit, String subjectCode) throws Exception{
		int result = 0;

		try {
			String sql = """
					UPDATE KH_SUBJECT
					SET CREDIT = ?
					WHERE SUBJECT_CODE = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, newCredit);
			pstmt.setString(2, subjectCode);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}
	/** 3.4 개설년도 수정
	 * @param conn
	 * @param newYear
	 * @param subjectCode
	 * @return
	 */
	public int updateOpenYear(Connection conn, int newYear, String subjectCode) throws Exception {
		int result = 0;

		try {
			String sql = """
					UPDATE KH_SUBJECT
					SET OPEN_YEAR = ?
					WHERE SUBJECT_CODE = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, newYear);
			pstmt.setString(2, subjectCode);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}
	/** 3.5 학기 수정
	 * @param conn
	 * @param newSemester
	 * @param subjectCode
	 * @return
	 */
	public int updateSemester(Connection conn, int newSemester, String subjectCode) throws Exception{
		int result = 0;

		try {
			String sql = """
					UPDATE KH_SUBJECT
					SET SEMESTER = ?
					WHERE SUBJECT_CODE = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, newSemester);
			pstmt.setString(2, subjectCode);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}
	/**
	 * 4. 과목 삭제
	 * 
	 * @param conn
	 * @param subCode
	 * @return
	 */
	public int deleteSubject(Connection conn, String subCode) throws Exception {
		int result = 0;

		try {
			String sql = """
					DELETE FROM KH_SUBJECT
					WHERE SUBJECT_CODE = ?
					""";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subCode);
			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	



	

}
