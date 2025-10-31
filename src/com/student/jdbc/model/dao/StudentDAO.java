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
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	/*
	 * 데이터 형식 
	 * STD_NO NUMBER PRIMARY KEY, 
	 * STD_NAME NVARCHAR2(5) NOT NULL, 
	 * STD_AGE NUMBER NOT NULL, 
	 * MAJOR NVARCHAR2(10) NOT NULL, 
	 * ENT_DATE DATE DEFAULT SYSDATE
	 */
	/**1. 학생 등록
	 * @param conn
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public int registerStudent(Connection conn, Student student) throws Exception{
		int result = 0;

		try {
			
			String sql = """
				INSERT INTO KH_STUDENT
				VALUES(?, ?, ?, ?, ?)
				""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, student.getStdNo());
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
	/**2. 전체 학생 조회
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Student> getAllStudents(Connection conn) throws Exception{
		List<Student> studentList = new ArrayList<>();
		Student student = null;
		try {
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR, TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') "ENT_DATE"
					FROM KH_STUDENT
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int stdId = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String stdMajor = rs.getString("MAJOR");
				String stdEntDate = rs.getString("ENT_DATE");
				
				student = new Student(stdId, stdName, stdAge, stdMajor, stdEntDate);
				
				studentList.add(student);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return studentList;
	}
	
	/**3-1. 학생 정보 확인(학번, 이름, 전공으로 필터 처리)
	 * @param conn
	 * @param student
	 * @return
	 */
	public Student checkStd(Connection conn, Student student) throws Exception{
		Student stdInfo = null;
		
		try {
			String sql = """
					SELECT STD_NO, STD_AGE, STD_NAME, MAJOR
					FROM KH_STUDENT
					WHERE STD_NO = ? AND STD_NAME = ? AND MAJOR = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, student.getStdNo());
			pstmt.setString(2, student.getStdName());
			pstmt.setString(3, student.getMajor());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int stdNo = rs.getInt("STD_NO");
				int stdAge = rs.getInt("STD_AGE");
				String stdName = rs.getString("STD_NAME");
				String major = rs.getString("MAJOR");
				stdInfo = new Student();
				
				stdInfo.setStdNo(stdNo);
				stdInfo.setStdAge(stdAge);
				stdInfo.setStdName(stdName);
				stdInfo.setMajor(major);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return stdInfo;
	}
	/** 3-2. 이름 수정
	 * @param conn
	 * @param newName
	 * @return
	 */
	public int updateStdName(Connection conn, int stdNo, String newName) throws Exception{
		int result = 0;
		
		try {
			String sql = """
					UPDATE KH_STUDENT
					SET STD_NAME = ?
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newName);
			pstmt.setInt(2, stdNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	/** 3-3. 나이 수정
	 * @param conn
	 * @param newAge
	 * @return
	 */
	public int updateStdAge(Connection conn, int stdNo, String newAge) throws Exception{
		int result = 0;
		
		try {
			String sql = """
					UPDATE KH_STUDENT
					SET STD_AGE = ?
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newAge);
			pstmt.setInt(2, stdNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	/** 3-4 학과(학부) 수정
	 * @param conn
	 * @param newMajor
	 * @return
	 */
	public int updateStdMajor(Connection conn, int stdNo, String newMajor) throws Exception{
		int result = 0;
		
		try {
			String sql = """
					UPDATE KH_STUDENT
					SET MAJOR = ?
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newMajor);
			pstmt.setInt(2, stdNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	/** 4. 학번 기준 삭제
	 * @param conn
	 * @param stdNo
	 * @return
	 * @throws Exception
	 */
	public int deleteStudentById(Connection conn, int stdNo) throws Exception{
		int result = 0;
		try {
			String sql = """
					DELETE FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stdNo);
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	/** 5. 전공별 학생 조회(특정 전공 학생만 필터링 조회)
	 * @param conn
	 * @param major
	 * @return
	 * @throws Exception
	 */
	public List<Student> getStudentsByMajor(Connection conn, String major) throws Exception {
		
		List<Student> stdList = new ArrayList<>();
		try {
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR, TO_CHAR(ENT_DATE, 'YYYY"년" MM"월" DD"일"') "ENT_DATE"
					FROM KH_STUDENT
					WHERE MAJOR = ?
					""";
		    pstmt = conn.prepareStatement(sql);
		    
		    pstmt.setString(1, major);
		    
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()) {
		    	int stdNo = rs.getInt("STD_NO");
		    	String stdName = rs.getString("STD_NAME");
		    	int stdAge = rs.getInt("STD_AGE");
		    	String majorInfo = rs.getString("MAJOR");
		    	String entDate = rs.getString("ENT_DATE");
		    	
		    	Student student = new Student(stdNo, stdName, stdAge, majorInfo, entDate);
		    	
		    	stdList.add(student);
		    }
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return stdList;
	}

}








