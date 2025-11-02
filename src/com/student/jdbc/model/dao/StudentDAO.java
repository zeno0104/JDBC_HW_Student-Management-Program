package com.student.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.student.jdbc.common.JDBCTemplate.*;
import com.student.jdbc.model.dto.Student;

public class StudentDAO {
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/*
	 * 데이터 형식 STD_NO NUMBER PRIMARY KEY, STD_NAME NVARCHAR2(5) NOT NULL, STD_AGE
	 * NUMBER NOT NULL, MAJOR NVARCHAR2(10) NOT NULL, ENT_DATE DATE DEFAULT SYSDATE
	 */
	/**
	 * 1. 학생 등록
	 * 
	 * @param conn
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public int registerStudent(Connection conn, Student student) throws Exception {
		int result = 0;

		try {

			String sql = """
					INSERT INTO KH_STUDENT
					VALUES(?, ?, ?, ?, ?, DEFAULT)
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getStdNo());
			pstmt.setString(2, student.getStdName());
			pstmt.setInt(3, student.getStdAge());
			pstmt.setString(4, student.getMajor());
			pstmt.setString(5, student.getEntDate());

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 2. 전체 학생 조회
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Student> getAllStudents(Connection conn) throws Exception {
		List<Student> studentList = new ArrayList<>();
		Student student = null;
		try {
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, S.MAJOR_CODE, TO_CHAR(ENT_DATE, 'YYYY-MM-DD') "ENT_DATE", STATUS, MAJOR_NAME
					FROM KH_STUDENT S
					JOIN KH_MAJOR M ON S.MAJOR_CODE = M.MAJOR_CODE
					ORDER BY MAJOR_CODE, STD_NO, STD_NAME
					""";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String stdNo = rs.getString("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String stdMajor = rs.getString("MAJOR_CODE");
				String stdEntDate = rs.getString("ENT_DATE");
				String status = rs.getString("STATUS");
				String majorName = rs.getString("MAJOR_NAME");

				student = new Student();

				student.setStdNo(stdNo);
				student.setStdName(stdName);
				student.setStdAge(stdAge);
				student.setMajor(stdMajor);
				student.setEntDate(stdEntDate);
				student.setStatus(status);
				student.setMajorName(majorName);

				studentList.add(student);
			}
		} finally {
			close(rs);
			close(pstmt);
		}

		return studentList;
	}

	/**
	 * 3-1. 학생 정보 확인(학번, 이름, 전공으로 필터 처리)
	 * 
	 * @param conn
	 * @param student
	 * @return
	 */
	public Student checkStd(Connection conn, String stdNo) throws Exception {
		Student stdInfo = null;

		try {
			String sql = """
					SELECT STD_NO, STD_AGE, STD_NAME, S.MAJOR_CODE, STATUS, MAJOR_NAME
					FROM KH_STUDENT S
					JOIN KH_MAJOR M ON S.MAJOR_CODE = M.MAJOR_CODE
					WHERE STD_NO = ?
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, stdNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String stdNoInfo = rs.getString("STD_NO");
				int stdAge = rs.getInt("STD_AGE");
				String stdName = rs.getString("STD_NAME");
				String major = rs.getString("MAJOR_CODE");
				String status = rs.getString("STATUS");
				String majorName = rs.getString("MAJOR_NAME");

				stdInfo = new Student();

				stdInfo.setStdNo(stdNoInfo);
				stdInfo.setStdAge(stdAge);
				stdInfo.setStdName(stdName);
				stdInfo.setMajor(major);
				stdInfo.setStatus(status);
				stdInfo.setMajorName(majorName);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return stdInfo;
	}

	/**
	 * 3-2. 이름 수정
	 * 
	 * @param conn
	 * @param newName
	 * @return
	 */
	public int updateStdName(Connection conn, String stdNo, String newName) throws Exception {
		int result = 0;

		try {
			String sql = """
					UPDATE KH_STUDENT
					SET STD_NAME = ?
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newName);
			pstmt.setString(2, stdNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 3-3. 나이 수정
	 * 
	 * @param conn
	 * @param newAge
	 * @return
	 */
	public int updateStdAge(Connection conn, String stdNo, String newAge) throws Exception {
		int result = 0;

		try {
			String sql = """
					UPDATE KH_STUDENT
					SET STD_AGE = ?
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newAge);
			pstmt.setString(2, stdNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 3-4 학과(학부) 수정
	 * 
	 * @param conn
	 * @param newMajor
	 * @return
	 */
	public int updateStdMajor(Connection conn, String stdNo, String newMajor) throws Exception {
		int result = 0;

		try {
			String sql = """
					UPDATE KH_STUDENT
					SET MAJOR_CODE = ?
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newMajor);
			pstmt.setString(2, stdNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 4. 학번 기준 삭제
	 * 
	 * @param conn
	 * @param stdNo
	 * @return
	 * @throws Exception
	 */
	public int deleteStudentById(Connection conn, String stdNo) throws Exception {
		int result = 0;
		try {
			String sql = """
					DELETE FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stdNo);
			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 5. 전공별 학생 조회(특정 전공 학생만 필터링 조회)
	 * 
	 * @param conn
	 * @param major
	 * @return
	 * @throws Exception
	 */
	public List<Student> getStudentsByMajor(Connection conn, String major) throws Exception {

		List<Student> stdList = new ArrayList<>();
		try {
			String sql = """
					SELECT
					    S.STD_NO,
					    S.STD_NAME,
					    S.STD_AGE,
					    S.MAJOR_CODE,
					    TO_CHAR(S.ENT_DATE, 'YYYY-MM-DD') AS ENT_DATE,
					    S.STATUS,
					    M.MAJOR_NAME
					FROM KH_STUDENT S
					JOIN KH_MAJOR M ON S.MAJOR_CODE = M.MAJOR_CODE
					WHERE S.MAJOR_CODE = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, major);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String stdNo = rs.getString("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String majorInfo = rs.getString("MAJOR_CODE");
				String entDate = rs.getString("ENT_DATE");
				String status = rs.getString("STATUS");
				String majorName = rs.getString("MAJOR_NAME");

				Student student = new Student();
				student.setStdNo(stdNo);
				student.setStdName(stdName);
				student.setStdAge(stdAge);
				student.setMajor(majorInfo);
				student.setEntDate(entDate);
				student.setStatus(status);
				student.setMajorName(majorName);

				stdList.add(student);
			}
		} finally {
			close(rs);
			close(pstmt);
		}

		return stdList;
	}

	/**
	 * 6. 재학 상태 관리
	 * 
	 * @param conn
	 * @param stdNo
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public int manageEnrollmentStatus(Connection conn, String stdNo, int input) throws Exception {
		int result = 0;

		String changeStatus = "";

		if (input == 1) {
			changeStatus = "재학";
		} else if (input == 2) {
			changeStatus = "휴학";
		} else if (input == 3) {
			changeStatus = "졸업";
		} else if (input == 4) {
			changeStatus = "제적";
		}

		try {
			String checkSql = """
					SELECT STATUS
					FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setString(1, stdNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String status = rs.getString("STATUS");
				if (status.equals(changeStatus)) {
					return -1;
				}
			}

			String sql = """
					UPDATE KH_STUDENT
					SET STATUS = ?
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, changeStatus);
			pstmt.setString(2, stdNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
			close(rs);
		}

		return result;
	}

}
