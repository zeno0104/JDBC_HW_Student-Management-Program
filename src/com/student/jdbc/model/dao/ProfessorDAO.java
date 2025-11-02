package com.student.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.student.jdbc.common.JDBCTemplate.*;
import com.student.jdbc.model.dto.Professor;

public class ProfessorDAO {
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**
	 * 1. 교수 등록
	 * 
	 * @param conn
	 * @param pf
	 * @return
	 * @throws Exception
	 */
	public int addProfessor(Connection conn, Professor pf) throws Exception {
		int result = 0;

		try {
			String sql = """
					INSERT INTO KH_PROFESSOR
					VALUES(?, ?, ?, ?, ?, ?, DEFAULT)
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, pf.getProfId());
			pstmt.setString(2, pf.getProfName());
			pstmt.setString(3, pf.getMajorCode());
			pstmt.setString(4, pf.getPosition());
			pstmt.setString(5, pf.getEmail());
			pstmt.setString(6, pf.getPhone());

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 2. 교수 목록 조회
	 * 
	 * @return
	 */
	public List<Professor> showProfessorList(Connection conn, String majorCode) throws Exception {
		List<Professor> profList = new ArrayList<Professor>();

		try {
			String sql = "";
			if (majorCode.equals("ALL")) {
				sql = """
						SELECT PROF_ID, PROF_NAME, MAJOR_CODE, POSITION, EMAIL, PHONE
						FROM KH_PROFESSOR
						""";
				pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					String profId = rs.getString("PROF_ID");
					String profName = rs.getString("PROF_NAME");
					String majorCodeInfo = rs.getString("MAJOR_CODE");
					String position = rs.getString("POSITION");
					String email = rs.getString("EMAIL");
					String phone = rs.getString("PHONE");

					Professor pf = new Professor(profId, profName, majorCodeInfo, position, email, phone);
					profList.add(pf);
				}
			} else {
				sql = """
						SELECT PROF_ID, PROF_NAME, MAJOR_CODE, POSITION, EMAIL, PHONE
						FROM KH_PROFESSOR
						WHERE MAJOR_CODE = ?
						""";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, majorCode);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					String profId = rs.getString("PROF_ID");
					String profName = rs.getString("PROF_NAME");
					String majorCodeInfo = rs.getString("MAJOR_CODE");
					String position = rs.getString("POSITION");
					String email = rs.getString("EMAIL");
					String phone = rs.getString("PHONE");

					Professor pf = new Professor(profId, profName, majorCodeInfo, position, email, phone);
					profList.add(pf);
				}
			}
		} finally {
			close(rs);
			close(pstmt);
		}

		return profList;
	}

	/**
	 * 4. 교수 삭제
	 * 
	 * @param conn
	 * @param profId
	 * @return
	 */
	public int deleteProfessor(Connection conn, String profId) throws Exception {
		int result = 0;

		try {
			String sql = """
					DELETE FROM KH_PROFESSOR
					WHERE PROF_ID = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, profId);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * sub method 특정 교수 데이터 가져오기
	 * 
	 * @param conn
	 * @param profId
	 * @return
	 */
	public Professor getProfInfo(Connection conn, String profId) throws Exception {
		Professor pf = null;

		try {
			String sql = """
					SELECT PROF_ID, PROF_NAME, MAJOR_CODE, POSITION, EMAIL, PHONE
					FROM KH_PROFESSOR
					WHERE PROF_ID = ?
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, profId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String profIdInfo = rs.getString("PROF_ID");
				String profName = rs.getString("PROF_NAME");
				String majorCode = rs.getString("MAJOR_CODE");
				String position = rs.getString("POSITION");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");

				pf = new Professor();
				pf.setProfId(profIdInfo);
				pf.setProfName(profName);
				pf.setMajorCode(majorCode);
				pf.setPosition(position);
				pf.setEmail(email);
				pf.setPhone(phone);
			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return pf;
	}

	public int updateName(Connection conn, String profId, String name) throws Exception {
		int result = 0;
		try {
			String sql = """
					UPDATE KH_PROFESSOR
					SET PROF_NAME = ?
					WHERE PROF_ID = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name);
			pstmt.setString(2, profId);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateMajorCode(Connection conn, String profId, String newMajorCode) throws Exception {
		int result = 0;
		try {
			String sql = """
					UPDATE KH_PROFESSOR
					SET MAJOR_CODE = ?
					WHERE PROF_ID = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newMajorCode);
			pstmt.setString(2, profId);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updatePosition(Connection conn, String profId, String position) throws Exception {
		int result = 0;
		try {
			String sql = """
					UPDATE KH_PROFESSOR
					SET POSITION = ?
					WHERE PROF_ID = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, position);
			pstmt.setString(2, profId);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateEmail(Connection conn, String profId, String email) throws Exception {
		int result = 0;
		try {
			String sql = """
					UPDATE KH_PROFESSOR
					SET EMAIL = ?
					WHERE PROF_ID = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			pstmt.setString(2, profId);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updatePhone(Connection conn, String profId, String phone) throws Exception {
		int result = 0;
		try {
			String sql = """
					UPDATE KH_PROFESSOR
					SET PHONE = ?
					WHERE PROF_ID = ?
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, phone);
			pstmt.setString(2, profId);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}

}
