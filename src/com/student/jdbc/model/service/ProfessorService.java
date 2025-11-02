package com.student.jdbc.model.service;

import static com.student.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.student.jdbc.model.dao.ProfessorDAO;
import com.student.jdbc.model.dto.Professor;

public class ProfessorService {
	private ProfessorDAO dao = new ProfessorDAO();

	/**
	 * 1. 교수 등록
	 * 
	 * @param pf
	 * @return
	 * @throws Exception
	 */
	public int addProfessor(Professor pf) throws Exception {
		Connection conn = getConnection();

		int result = dao.addProfessor(conn, pf);

		if (result > 0) {
			commit(conn);
		} else {
			close(conn);
		}

		return result;
	}

	/**
	 * 2. 교수 목록 조회
	 * 
	 * @param majorCode
	 * @return
	 */
	public List<Professor> showProfessorList(String majorCode) throws Exception {
		Connection conn = getConnection();

		List<Professor> profList = dao.showProfessorList(conn, majorCode);

		close(conn);

		return profList;
	}

	/**
	 * 3.1 교수 이름 수정
	 * 
	 * @param majorCode
	 * @param name
	 * @return
	 */
	public int updateName(String profId, String name) throws Exception {
		Connection conn = getConnection();

		int result = dao.updateName(conn, profId, name);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	/**
	 * 3.2 학과 코드 수정
	 * 
	 * @param majorCode
	 * @param name
	 * @return
	 */
	public int updateMajorCode(String profId, String newMajorCode) throws Exception {
		Connection conn = getConnection();

		int result = dao.updateMajorCode(conn, profId, newMajorCode);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	/**
	 * 3.3 직급 수정
	 * 
	 * @param majorCode
	 * @param name
	 * @return
	 */
	public int updatePosition(String profId, String position) throws Exception {
		Connection conn = getConnection();

		int result = dao.updatePosition(conn, profId, position);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	/**
	 * 3.4 이메일 이름 수정
	 * 
	 * @param majorCode
	 * @param name
	 * @return
	 */
	public int updateEmail(String profId, String email) throws Exception {
		Connection conn = getConnection();

		int result = dao.updateEmail(conn, profId, email);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	/**
	 * 3.5 전화번호 수정
	 * 
	 * @param majorCode
	 * @param name
	 * @return
	 */
	public int updatePhone(String profId, String phone) throws Exception {
		Connection conn = getConnection();

		int result = dao.updatePhone(conn, profId, phone);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		close(conn);

		return result;
	}

	/**
	 * 4. 교수 삭제
	 * 
	 * @param profId
	 * @return
	 */
	public int deleteProfessor(String profId) throws Exception {
		Connection conn = getConnection();

		int result = dao.deleteProfessor(conn, profId);

		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

	/**
	 * sub method 특정 교수 데이터 가져오기
	 * 
	 * @param profId
	 * @return
	 */
	public Professor getProfInfo(String profId) throws Exception {
		Connection conn = getConnection();

		Professor pf = dao.getProfInfo(conn, profId);

		close(conn);

		return pf;
	}

}
