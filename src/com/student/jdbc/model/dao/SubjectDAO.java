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
	 * 1. 등록된 과목 체크
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
		
		pstmt.setString(1,subCode);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
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
	 * @param conn
	 * @param major
	 * @return
	 */

	public List<Subject> getSubjectList(Connection conn, String major) throws Exception  {
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
			
			while(rs.next()) {
				String subjectCode = rs.getString("SUBJECT_CODE");
				String subjectName = rs.getString("SUBJECT_NAME");
				String majorName = rs.getString("MAJOR");
				String professor = rs.getString("PROFESSOR");
				int credit = rs.getInt("CREDIT");
				int openYear = rs.getInt("OPEN_YEAR");
				int semester = rs.getInt("SEMESTER");
				
				Subject subject = new Subject(subjectCode, subjectName, majorName, professor, credit, openYear, semester);
				
				subjectList.add(subject);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return subjectList;
	}
}




