package com.student.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.student.jdbc.model.dto.Student;

import static com.student.jdbc.common.JDBCTemplate.*;

public class GradeDAO {
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**
	 * 7.1 성적 등록
	 * 
	 * @param conn
	 * @param stdNo
	 * @param subCode
	 * @param score
	 * @return
	 * @throws Exception
	 */
	public int manageStudentGrades(Connection conn, String stdNo, String subCode, int score) throws Exception {
		int result = 0;
		String grade = "";
		double gpa = 0;

		if (score >= 95) {
			grade = "A+";
			gpa = 4.5;
		} else if (score >= 90) {
			grade = "A";
			gpa = 4.0;
		} else if (score >= 85) {
			grade = "B+";
			gpa = 3.5;
		} else if (score >= 80) {
			grade = "B";
			gpa = 3.0;
		} else if (score >= 75) {
			grade = "C+";
			gpa = 2.5;
		} else if (score >= 70) {
			grade = "C";
			gpa = 2.0;
		} else if (score >= 65) {
			grade = "D+";
			gpa = 1.5;
		} else if (score >= 60) {
			grade = "D";
			gpa = 1.0;
		} else {
			grade = "F";
			gpa = 0.0;
		}

		try {
			String sql = """
					INSERT INTO KH_GRADE
					VALUES (SEQ_GRADE_NO.NEXTVAL, ?, ?, ?, ?, ?, DEFAULT)
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, stdNo);
			pstmt.setString(2, subCode);
			pstmt.setInt(3, score);
			pstmt.setString(4, grade);
			pstmt.setDouble(5, gpa);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 7.2 성적 수정
	 * @param conn
	 * @param stdNo
	 * @param changeSubCode
	 * @param score
	 * @return
	 */
	public int updateStudentGrade(Connection conn, String stdNo, String changeSubCode, int score) throws Exception {
		int result = 0;
		String grade = "";
		double gpa = 0;

		if (score >= 95) {
			grade = "A+";
			gpa = 4.5;
		} else if (score >= 90) {
			grade = "A";
			gpa = 4.0;
		} else if (score >= 85) {
			grade = "B+";
			gpa = 3.5;
		} else if (score >= 80) {
			grade = "B";
			gpa = 3.0;
		} else if (score >= 75) {
			grade = "C+";
			gpa = 2.5;
		} else if (score >= 70) {
			grade = "C";
			gpa = 2.0;
		} else if (score >= 65) {
			grade = "D+";
			gpa = 1.5;
		} else if (score >= 60) {
			grade = "D";
			gpa = 1.0;
		} else {
			grade = "F";
			gpa = 0.0;
		}
		try {
			String sql = """
					UPDATE KH_GRADE
					SET SCORE = ?, GRADE = ?, GPA = ?
					WHERE STD_NO = ? AND SUBJECT_CODE = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, score);
			pstmt.setString(2, grade);
			pstmt.setDouble(3, gpa);
			pstmt.setString(4, stdNo);
			pstmt.setString(5, changeSubCode);
			
			result = pstmt.executeUpdate();
			
		} finally {
			
		}
		return result;
	}


	/** 7.3 성적 조회
	 * @param conn
	 * @param stdNo
	 * @return
	 * @throws Exception
	 */
	public List<Student> getGradesByStudent(Connection conn, String stdNo) throws Exception {
		List<Student> studentList = new ArrayList<Student>();

		try {
			String sql = """
					SELECT SUBJECT_NAME, SCORE, GRADE, GPA, S.SUBJECT_CODE
					FROM KH_GRADE G
					JOIN KH_SUBJECT S ON G.SUBJECT_CODE = S.SUBJECT_CODE
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stdNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String subjectName = rs.getString("SUBJECT_NAME");
				int score = rs.getInt("SCORE");
				String grade = rs.getString("GRADE");
				double gpa = rs.getDouble("GPA");
				String subjectCode = rs.getString("SUBJECT_CODE");
				
				Student student = new Student();
				
				student.setSubjectName(subjectName);;
				student.setScore(score);
				student.setGrade(grade);
				student.setGpa(gpa);
				student.setSubjectCode(subjectCode);
				
				studentList.add(student);
			}

		} finally {
			close(pstmt);
		}

		return studentList;
	}




}
