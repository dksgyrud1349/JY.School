package com.qbbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class QbbsDAO {
	private Connection conn = DBConn.getConnection();

	// 데이터 추가
	public void insertQuestion(QbbsDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
			
		try {		// answer id, answer 뭐시기는 이미 있는 글에 글을 넣는것이므로 insert아닌 update에다 넣어줘야한다.
			sql = "INSERT INTO qbbs(writeNum, title, content, writeDate, comment, reg_Date, userId )"
					+ " VALUES (qbbs_seq.NEXTVAL, ?, ?, SYSDATE, 0, ?, SYSDATE, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getComment());
			pstmt.setString(4, dto.getUserId());

			pstmt.executeUpdate();

		} catch (Exception e) {
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
			sql = "SELECT NVL(COUNT(*), 0) FROM qbbs";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return result;
	}
	
	// 검색에서의 데이터 개수
	public int dataCount(String kwd) {	// 검색값받아서 검색할시
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) "
					+ " FROM qbbs"
					+ " WHERE INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 OR INSTR(answer, ?) >= 1 ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			pstmt.setString(3, kwd);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return result;
	}
	
	// 게시물 리스트 ( 페이지 클릭한 순간의 리스트 출력)
	public List<QbbsDTO> listQuestion(int offset, int size) {
		List<QbbsDTO> list = new ArrayList<QbbsDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
			
		try {
			sb.append(" SELECT writeNum, secret, title, q.userId, userName, answerId, ");
			sb.append("  TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date ");
			sb.append(" FROM qbbs q ");
			sb.append(" JOIN member m ON q.userId = m.userId ");
			sb.append(" ORDER BY writeNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
				
			pstmt = conn.prepareStatement(sb.toString());
				
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
				
			rs = pstmt.executeQuery();
				
			while (rs.next()) {
				QbbsDTO dto = new QbbsDTO();
				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setSecret(rs.getInt("secret"));
				dto.setTitle(rs.getString("title"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setAnswerId(rs.getString("answerId"));
				dto.setReg_date(rs.getString("reg_date"));

				list.add(dto);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
			
		return list;
	}
	// 검색에서 리스트 (검색했을때의 리스트 출력)
	public List<QbbsDTO> listQuestion(int offset, int size, String kwd) {
		List<QbbsDTO> list = new ArrayList<QbbsDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT writeNum, title, content, writeDate, comment, q.userId ");
			sb.append("  TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date ");
			sb.append(" FROM qbbs q ");
			sb.append(" JOIN member m ON q.userId = m.userId ");
			sb.append(" ORDER BY writeNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
				
			pstmt = conn.prepareStatement(sb.toString());
				
			pstmt.setString(1, kwd);
			pstmt.setString(2, kwd);
			pstmt.setString(3, kwd);
			pstmt.setInt(4, offset);
			pstmt.setInt(5, size);
				
			rs = pstmt.executeQuery();
						
			while (rs.next()) {
				QbbsDTO dto = new QbbsDTO();

				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setTitle(rs.getString("secret"));
				dto.setContent(rs.getString("content"));
				dto.setWriteDate(rs.getString("writeDate"));
				dto.setComment(rs.getString("comment"));
				dto.setUserId(rs.getString("userId"));
				dto.setReg_date(rs.getString("reg_date"));

				list.add(dto);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
			
		return list;
	}
	// 해당 게시물 보기
	public QbbsDTO findById(long writeNum) {
		QbbsDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT writeNum, secret, title, content, q.userId, writeDate, "
					+ " commnent, reg_date, answerId, answerName, answer, "
					+ " FROM qbbs q "
					+ " JOIN member m ON q.userId=m.userId "
					+ " WHERE num = ? ";
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setLong(1, writeNum);	// num=1 이라면 1번 기본키 정보를 넘겨줌
									// 예를들어 2번게시물을 들어가면 num=2게시물이든 2번에대한 필요한 모든정보를 줘야하기에 가능한 모든정보 다있음,
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QbbsDTO();
				
				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setSecret(rs.getInt("secret"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setUserId(rs.getString("userId"));
				dto.setWriteDate(rs.getString("writeDate"));
				dto.setComment(rs.getString("comment"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setAnswerId(rs.getString("answerId"));
				dto.setAnswerName(rs.getString("answerName"));
				dto.setAnswer(rs.getString("answer"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return dto;
	}
		
	// 이전글
	public QbbsDTO findByPrev(long writeNum, String kwd) {
		QbbsDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

	try {	// 글번호,제목,글내용,답변이 존재하면 -글번호 오름차순 기준으로- 글번호,비밀여부,제목,아이디 출력해서 보여줌?
		if (kwd != null && kwd.length() != 0) {
			sb.append(" SELECT writeNum, secret, title, userId ");
			sb.append(" FROM qbbs ");
			sb.append(" WHERE ( writeNum > ? ) ");
			sb.append("     AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 OR INSTR(answer, ?) >= 1) ");
			sb.append(" ORDER BY writeNum ASC ");
			sb.append(" FETCH FIRST 1 ROWS ONLY ");
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setLong(1, writeNum);
			pstmt.setString(2, kwd);
			pstmt.setString(3, kwd);
			pstmt.setString(4, kwd);	
		} else {
			sb.append(" SELECT writeNum, secret, title, userId ");
			sb.append(" FROM qbbs ");
			sb.append(" WHERE writeNum > ? ");
			sb.append(" ORDER BY writeNum ASC ");
			sb.append(" FETCH FIRST 1 ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
				
			pstmt.setLong(1, writeNum);
		}
		
		rs = pstmt.executeQuery();
			
		if (rs.next()) {
			dto = new QbbsDTO();
				
			dto.setWriteNum(rs.getLong("writeNum"));
			dto.setSecret(rs.getInt("secret"));
			dto.setTitle(rs.getString("title"));
			dto.setUserId(rs.getString("title"));
		}
			
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		DBUtil.close(rs);
		DBUtil.close(pstmt);
	}
		
	return dto;
}
		
	// 다음글
	public QbbsDTO findByNext(long writeNum, String kwd) {
		QbbsDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT writeNum, secret, title, userId ");
				sb.append(" FROM qbbs ");
				sb.append(" WHERE ( writeNum < ? ) ");
				sb.append("     AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 OR INSTR(answer, ?) >= 1) ");
				sb.append(" ORDER BY writeNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
					
				pstmt.setLong(1, writeNum);
				pstmt.setString(2, kwd);
				pstmt.setString(3, kwd);
				pstmt.setString(4, kwd);
			} else {
				sb.append(" SELECT writeNum, secret, title, userId ");
				sb.append(" FROM qbbs ");
				sb.append(" WHERE writeNum < ? ");
				sb.append(" ORDER BY writeNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
					
				pstmt.setLong(1, writeNum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QbbsDTO();
					
				dto.setWriteNum(rs.getInt("writeNum"));
				dto.setSecret(rs.getInt("secret"));
				dto.setUserId(rs.getString("userId"));
				dto.setTitle(rs.getString("title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return dto;
	}
		
	// 게시물 수정
	public void updateQuestion(QbbsDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE qbbs SET title=?, secret=?, content=? WHERE writeNum=? AND userId=?";
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setString(1, dto.getTitle());
			pstmt.setInt(2, dto.getSecret());
			pstmt.setString(3, dto.getContent());
			pstmt.setLong(4, dto.getWriteNum());
			pstmt.setString(5, dto.getUserId());
				
			pstmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	// 답글 수정
	public void updateAnswer(QbbsDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
			
		try {
			sql = "UPDATE qbbs SET answer=?, answerId=?, ";
			if(dto.getAnswer().length() == 0) {
				sql += " answer_date=NULL ";
			} else {
				sql += " answer_date=SYSDATE ";
			}
			sql += " WHERE writeNum = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getAnswer());
			pstmt.setString(2, dto.getAnswerId());
			pstmt.setLong(3, dto.getWriteNum());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	
	}
		
	// 게시물 삭제
	public void deleteQuestion(long writeNum, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (userId.equals("admin")) {	// 관리자면 그냥삭제
				sql = "DELETE FROM qbbs WHERE writeNum=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, writeNum);
				
				pstmt.executeUpdate();
			} else {						// 관리자아니면 조건삭제
				sql = "DELETE FROM qbbs WHERE writeNum=? AND userId=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, writeNum);
				pstmt.setString(2, userId);
				
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
}