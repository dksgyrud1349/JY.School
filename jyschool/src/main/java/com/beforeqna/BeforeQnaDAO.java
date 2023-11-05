package com.beforeqna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class BeforeQnaDAO {
	private Connection conn = DBConn.getConnection();
	
	// 데이터 추가
	public void insertQuestion(BeforeQnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;	
	
		try {
			sql = "INSERT INTO qnabefore(qnum, qcontent, qdate, classNum2, userId, title, secret) "
					+ " VALUES (qnabefore_seq.NEXTVAL, ?, SYSDATE, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getQcontent());
			pstmt.setLong(2, dto.getClassNum2());
			pstmt.setString(3, dto.getUserId());
			pstmt.setString(4, dto.getTitle());
			pstmt.setInt(5, dto.getSecret());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}

	// 데이터 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qnabefore";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return result;
	}
	
	// 검색에서 데이터 개수
	public int dataCount(String kwd) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) "
					+ " FROM qnabefore "
					+ " WHERE INSTR(title, ?) >= 1 OR INSTR(qcontent, ?) >= 1 OR INSTR(acontent, ?) >= 1 ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			pstmt.setString(3, kwd);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return result;
	}			
	
	// 게시물 리스트
	public List<BeforeQnaDTO> listQuestion(int offset, int size) {
		List<BeforeQnaDTO> list = new ArrayList<BeforeQnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();	

		try {
			sb.append(" SELECT qnum, qdate, q.userId, userName ");
			sb.append(" , title, auserId, secret ");
			sb.append(" FROM qnabefore q ");
			sb.append(" JOIN member m ON q.userId = m.userId ");
			sb.append(" ORDER BY qnum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BeforeQnaDTO dto = new BeforeQnaDTO();
				
				dto.setQnum(rs.getLong("qnum"));
				dto.setQdate(rs.getString("qdate"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setTitle(rs.getString("title"));
				dto.setAuserId(rs.getString("auserId"));
				dto.setSecret(rs.getInt("secret"));

				list.add(dto);		
			}	
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}
	
	public List<BeforeQnaDTO> listQuestion(int offset, int size, String kwd) {
		List<BeforeQnaDTO> list = new ArrayList<BeforeQnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT qnum, qdate, q.userId, ");
			sb.append(" userName, title, auserId, secret");
			sb.append(" FROM qnabefore q ");
			sb.append(" JOIN member m ON q.userId = m.userId ");
			sb.append(" WHERE INSTR(title, ?) >= 1 OR INSTR(qcontent, ?) >= 1 OR INSTR(acontent, ?) >= 1 ");
			sb.append(" ORDER BY qnum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			pstmt.setString(3, kwd);
			pstmt.setInt(4, offset);
			pstmt.setInt(5, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BeforeQnaDTO dto = new BeforeQnaDTO();

				dto.setQnum(rs.getLong("qnum"));
				dto.setQdate(rs.getString("qdate"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setTitle(rs.getString("title"));
				dto.setAuserId(rs.getString("auserId"));
				dto.setSecret(rs.getInt("secret"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return list;
	}
	
	// 게시물 보기
	public BeforeQnaDTO findById(long qnum) {
		BeforeQnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;	

		try {
			sql = "SELECT qnum, qcontent, qdate, acontent, adate, classNum2, "
					+ " q.userId, auserId, m1.userName, m2.userName auserName, title, secret "
					+ " FROM qnabefore q "
					+ " JOIN member m1 ON q.userId=m1.userId "
					+ " LEFT OUTER JOIN member m2 ON q.auserId=m2.userId "
					+ " WHERE qnum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, qnum);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new BeforeQnaDTO();
				
				// 질문자
				dto.setQnum(rs.getLong("qnum"));
				dto.setQcontent(rs.getString("qcontent"));
				dto.setQdate(rs.getString("qdate"));
				dto.setAcontent(rs.getString("acontent"));
				dto.setAdate(rs.getString("adate"));
				
				dto.setClassNum2(rs.getLong("classNum2"));
				
				// 답변자
				dto.setUserId(rs.getString("userId"));
				dto.setAuserId(rs.getString("auserId"));
				
				dto.setUserName(rs.getString("userName"));
				dto.setAuserName(rs.getString("auserName"));
				
				dto.setTitle(rs.getString("title"));
				dto.setSecret(rs.getInt("secret"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return dto;
	}
	
	
	// 이전글
	public BeforeQnaDTO findByPrev(long num, String kwd) {
		BeforeQnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
	
		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT qnum, secret, title, userId ");
				sb.append(" FROM qnabefore ");
				sb.append(" WHERE ( qnum > ? ) ");
				sb.append("     AND ( INSTR(title, ?) >= 1 OR INSTR(qcontent, ?) >= 1 OR INSTR(acontent, ?) >= 1) ");
				sb.append(" ORDER BY num ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, kwd);
				pstmt.setString(3, kwd);
				pstmt.setString(4, kwd);
			} else {
				sb.append(" SELECT qnum, secret, title, userId ");
				sb.append(" FROM qnabefore ");
				sb.append(" WHERE qnum > ? ");
				sb.append(" ORDER BY qnum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
			}

			rs = pstmt.executeQuery();	
			
			if (rs.next()) {
				dto = new BeforeQnaDTO();
				
				dto.setQnum(rs.getLong("qnum"));
				dto.setSecret(rs.getInt("secret"));
				dto.setUserId(rs.getString("userId"));
				dto.setTitle(rs.getString("title"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return dto;
	}
	
	// 다음글
	public BeforeQnaDTO findByNext(long num, String kwd) {
		BeforeQnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT qnum, secret, title, userId ");
				sb.append(" FROM qnabefore ");
				sb.append(" WHERE ( qnum < ? ) ");
				sb.append("     AND ( INSTR(title, ?) >= 1 OR INSTR(qcontent, ?) >= 1 OR INSTR(acontent, ?) >= 1) ");
				sb.append(" ORDER BY qnum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, kwd);
				pstmt.setString(3, kwd);
				pstmt.setString(4, kwd);
			} else {
				sb.append(" SELECT qnum, secret, title, userId ");
				sb.append(" FROM qnabefore ");
				sb.append(" WHERE qnum < ? ");
				sb.append(" ORDER BY qnum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BeforeQnaDTO();
				
				dto.setQnum(rs.getLong("qnum"));
				dto.setSecret(rs.getInt("secret"));
				dto.setUserId(rs.getString("userId"));
				dto.setTitle(rs.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return dto;
	}

	// 게시물 수정
	public void updateQuestion(BeforeQnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;	
	
		try {
			sql = "UPDATE qnabefore SET title=?, secret=?, qcontent=? WHERE qnum=? AND userId=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setInt(2, dto.getSecret());
			pstmt.setString(3, dto.getQcontent());
			pstmt.setLong(4, dto.getQnum());
			pstmt.setString(5, dto.getUserId());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}	
	
	
	public void updateAnswer(BeforeQnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE qnabefore SET acontent=?, auserId=?, ";
			if(dto.getAcontent().length() == 0) {
				sql += " adate=NULL ";
			} else {
				sql += " adate=SYSDATE ";
			}
			sql += " WHERE qnum = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getAcontent());
			pstmt.setString(2, dto.getAuserId());
			pstmt.setLong(3, dto.getQnum());
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}	
	
	
	
	// 게시물 삭제
	public void deleteQuestion(long qnum, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
	
		try {
			if (userId.equals("admin")) {
				sql = "DELETE FROM qnabefore WHERE qnum=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, qnum);
				
				pstmt.executeUpdate();
			} else {
				sql = "DELETE FROM qnabefore WHERE qnum=? AND userId=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, qnum);
				pstmt.setString(2, userId);
				
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	
			
}
