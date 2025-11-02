package com.student.jdbc.model.service;

import java.sql.Connection;

import com.student.jdbc.model.dao.MajorDAO;

import static com.student.jdbc.common.JDBCTemplate.*;

public class MajorService {

	MajorDAO dao = new MajorDAO();
	/** sub method
	 *  전공 코드 가져오기
	 * @param newMajor
	 * @return
	 */
	public int getMajorCode(String newMajor) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.getAllMajorCode(conn, newMajor);
		
		close(conn);
		
		return result;
	}

}
