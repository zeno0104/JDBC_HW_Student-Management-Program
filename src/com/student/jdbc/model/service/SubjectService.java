package com.student.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import static com.student.jdbc.common.JDBCTemplate.*;
import com.student.jdbc.model.dao.SubjectDAO;
import com.student.jdbc.model.dto.Subject;

public class SubjectService {
	private SubjectDAO dao = new SubjectDAO();

	/**
	 * 1. 등록된 과목 체크
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

	/** sub method 
	 * 학생 학과에 해당하는 과목 리스트 가져오기
	 * @param major
	 * @return
	 */
	public List<Subject> getSubjectList(String major) throws Exception  {
		Connection conn = getConnection();
		List<Subject> subjectList = dao.getSubjectList(conn, major);
		
		close(conn);
		return subjectList;
	}

}
