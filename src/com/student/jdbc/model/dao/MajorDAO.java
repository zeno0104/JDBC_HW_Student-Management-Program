package com.student.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.student.jdbc.common.JDBCTemplate.*;

public class MajorDAO {

	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	/** sub method 
	 * 전공 코드 모두 가져오기
	 * @param conn
	 * @param newMajor
	 * @return
	 */
	public int getAllMajorCode(Connection conn, String newMajor) throws Exception{
		int result = 0;
		
		try {
			String sql = """
					SELECT COUNT(*)
					FROM KH_MAJOR
					WHERE MAJOR_CODE = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, newMajor);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

}
