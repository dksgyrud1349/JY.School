package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.util.DBConn;
import com.util.DBUtil;

public class NoticeDAO {
	private Connection conn = DBConn.getConnection();
	// 데이터 추가
	public void insertNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		long seq;
		
		try {	// 게시글번호 1씩 늘리기위해 코딩
			sql = "SELECT notice_seq.NEXTVAL FROM dual"; // 데이터베이스에서 notice_seq 시퀀스의 다음 값
			pstmt = conn.prepareStatement(sql);	// 실행
			
			rs = pstmt.executeQuery();	// 실행후 저장공간
			
			seq = 0;
			if (rs.next()) {
				seq = rs.getLong(1);	// 첫번째열 값(seq_NEXTVAL) = seq
			}
			dto.setReferNum(seq);	// dto.set기본키(seq);(여기에 때려넣음)
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;			// 종료시킨후 null로 초기화
							// answer 는 관리자가 파일 만들어진후 답글다는것이기
			sql = "INSERT INTO notice(referNum, title, content, writeDate, hitCount, userId, Notice) "
					+ " VALUES (?, ?, ?, SYSDATE, 0, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, dto.getReferNum());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getUserId());
			pstmt.setInt(5, dto.getNotice());	// '공지'이미지 띄우기위해 DB에 넣어둔다.
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if (dto.getSaveFiles() != null) {
				sql = "INSERT INTO noticeFile(noticeFileNum, refernum, saveFile, clientFile) VALUES (noticeFile_seq.NEXTVAL, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					pstmt.setLong(1, dto.getReferNum());
					pstmt.setString(2, dto.getSaveFiles()[i]);
					pstmt.setString(3, dto.getClientFiles()[i]);
					
					pstmt.executeUpdate();
				}
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	// 전체의 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice";	// notice테이블 개수
			pstmt = conn.prepareStatement(sql);	// 실행
			
			rs = pstmt.executeQuery();	// 실행값 저장
			
			if (rs.next()) {
				result = rs.getInt(1);	// notice의 count값을 result에 저장
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
			
		return result;
	}
	// 검색에서 전체의 개수
	public int dataCount(String schType, String kwd) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice n "	// 조인해야함 @@@@@@@@@@
					+ " JOIN member m ON n.userId=m.userId ";
			if (schType.equals("all")) {
				sql += " WHERE INSTR(title, ?) >=1 OR INSTR(content, ?) >= 1 ";
			} else if (schType.equals("writeDate")) {
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(writeDate, 'YYYYMMDD') = ? ";
			} else {
				sql += " WHERE INSTR(" + schType + ", ?) >= 1 ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, kwd);
			if (schType.equals("all")) {
				pstmt.setString(2, kwd);
			}
			
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
		
		return result;	// 3)결과값을 다시 넘겨준다.
	}
	// 게시물 리스트 ( 페이지 클릭한 순간의 리스트 출력)
	public List<NoticeDTO> listNotice(int offset, int size) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT referNum, Title, writeDate, userName, ");
			sb.append("  hitCount, n.userId ");
			sb.append(" FROM notice n ");
			sb.append(" JOIN member m ON n.userId = m.userId ");
			sb.append(" ORDER BY referNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				
				dto.setReferNum(rs.getLong("referNum"));
				dto.setTitle(rs.getString("title"));
				dto.setWriteDate(rs.getString("writeDate"));
				dto.setUserName(rs.getString("userName"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setUserId(rs.getString("userId"));

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
	// 검색에서 리스트 (검색했을때의 리스트 출력)
	public List<NoticeDTO> listNotice(int offset, int size, String schType, String kwd) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {			// 글올리기나 DB에서 insert하면 그 값을 select 가져오는것
			sb.append(" SELECT referNum, Title, n.userId , userName, hitCount, Content, ");
			sb.append(" writeDate ");						// writeDate에 TO_CHAR 뺐더니 검색이 된다.
			sb.append(" FROM notice n ");
			sb.append(" JOIN member m ON n.userId = m.userId ");
			if (schType.equals("all")) {
				sb.append(" WHERE INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else if (schType.equals("writeDate")) {
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(writeDate, 'YYYYMMDD') = ?");
			} else {
				sb.append(" WHERE INSTR(" + schType + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY referNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if (schType.equals("all")) {
				pstmt.setString(1, kwd);
				pstmt.setString(2, kwd);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1, kwd);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();// dto새가방 선언후 select값 저장
				
				dto.setReferNum(rs.getLong("referNum"));	// 저장된값 던져주기
				dto.setTitle(rs.getString("title"));		// SB.append:DB referNum을 가져와서 //dto.refer에 넣어라			
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setHitCount(rs.getInt("hitCount"));	
				dto.setContent(rs.getString("content"));
				dto.setWriteDate(rs.getString("writeDate"));
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
	
	// 리스트 (공지사항 눌렀을때 기본리스트)
	public List<NoticeDTO> listNotice() {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT referNum, Title, n.userId, userName, hitCount, Content, ");
			sb.append("    TO_CHAR(writeDate, 'YYYY-MM-DD') writeDate  ");
			sb.append(" FROM notice n ");
			sb.append(" JOIN member m ON n.userId=m.userId ");
			sb.append(" WHERE notice=1  ");		// 체크박스
			sb.append(" ORDER BY referNum DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				
				dto.setReferNum(rs.getLong("referNum"));
				dto.setTitle(rs.getString("title"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setContent(rs.getString("content"));
				dto.setWriteDate(rs.getString("writeDate"));
				
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
	// 해당 게시물 보기(검색할경우?)
	public NoticeDTO findById(long referNum) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT referNum, notice, n.userId, userName, title, content, hitCount, "
					+ " TO_CHAR(writeDate, 'YYYY-MM-DD') writeDate "
					+ " FROM notice n "
					+ " JOIN member m ON n.userId=m.userId "
					+ " WHERE referNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, referNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();

				dto.setReferNum(rs.getLong("referNum"));
				dto.setNotice(rs.getInt("notice"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setWriteDate(rs.getString("writeDate"));
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
	public NoticeDTO findByPrev(long referNum, String schType, String kwd) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT referNum, title ");
				sb.append(" FROM notice n ");
				sb.append(" JOIN member m ON n.userId = m.userId ");
				sb.append(" WHERE ( referNum > ? ) ");
				if (schType.equals("all")) {
					sb.append("   AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (schType.equals("writeDate")) {
					kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(writeDate, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + schType + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY referNum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, referNum);
				pstmt.setString(2, kwd);
				if (schType.equals("all")) {
					pstmt.setString(3, kwd);
				}
			} else {
				sb.append(" SELECT referNum, title FROM notice ");
				sb.append(" WHERE referNum > ? ");
				sb.append(" ORDER BY referNum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, referNum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setReferNum(rs.getLong("referNum"));
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
	public NoticeDTO findByNext(long referNum, String schType, String kwd) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT referNum, title ");
				sb.append(" FROM notice n ");
				sb.append(" JOIN member m ON n.userId = m.userId ");
				sb.append(" WHERE ( referNum < ? ) ");
				if (schType.equals("all")) {
					sb.append("   AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (schType.equals("writeDate")) {
					kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(writeDate, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + schType + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY referNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, referNum);
				pstmt.setString(2, kwd);
				if (schType.equals("all")) {
					pstmt.setString(3, kwd);
				}
			} else {
				sb.append(" SELECT referNum, title FROM notice ");
				sb.append(" WHERE referNum < ? ");
				sb.append(" ORDER BY referNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, referNum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setReferNum(rs.getLong("referNum"));
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
	// 조회수
	public void updateHitCount(long referNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE notice SET hitCount=hitCount+1 WHERE referNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, referNum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
		
	}
	// 글안에 들어가서 수정(업데이트)
	public void updateNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE notice SET notice=?, title=?, content=? "
					+ " WHERE refernum=? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setLong(4, dto.getReferNum());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;

			if (dto.getSaveFiles() != null) {
				sql = "INSERT INTO noticeFile(noticeFileNum, refernum, saveFile, clientFile) VALUES (noticeFile_seq.NEXTVAL, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					pstmt.setLong(1, dto.getReferNum());
					pstmt.setString(2, dto.getSaveFiles()[i]);
					pstmt.setString(3, dto.getClientFiles()[i]);
					
					pstmt.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
		
	}
	// 글안에 들어가서 삭제
	public void deleteNotice(long referNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM notice WHERE referNum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, referNum);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	// 리스트에서 휴지통눌러서 삭제
	public void deleteNoticeList(long[] referNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM notice WHERE referNum IN (";
			for (int i=0; i<referNum.length; i++) {
				sql += "?,";
			}
			sql = sql.substring(0, sql.length() - 1) + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			for (int i=0; i<referNum.length; i++) {
				pstmt.setLong(i+1, referNum[i]);
			}
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
	}
	// 파일리스트출력 = 참조키의 파일번호를 참조하여 가져오기위해서
	public List<NoticeDTO> listNoticeFile(long referNum) {	// void가 없으면 return을 줘야 오류가 사라진다.
		List<NoticeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT noticefileNum, referNum, saveFile, clientFile FROM noticefile WHERE referNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, referNum);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				
				dto.setNoticeFileNum(rs.getLong("noticeFileNum"));
				dto.setReferNum(rs.getLong("referNum"));
				dto.setSaveFile(rs.getString("saveFile"));
				dto.setClientFile(rs.getString("clientFile"));
				
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
	// 게시글찾기
	public NoticeDTO findByFileId(long noticeFileNum) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT noticefileNum, referNum, saveFile, clientFile FROM noticefile WHERE noticefileNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, noticeFileNum);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNoticeFileNum(rs.getLong("noticeFileNum"));
				dto.setReferNum(rs.getLong("referNum"));
				dto.setSaveFile(rs.getString("saveFile"));
				dto.setClientFile(rs.getString("clientFile"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return dto;
	}
	// 수정에서 파일만 지우는 휴지통
	public void deleteNoticeFile(String mode, long referNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if (mode.equals("all")) {
				sql = "DELETE FROM noticeFile WHERE referNum = ?";
			} else {
				sql = "DELETE FROM noticeFile WHERE noticeFileNum = ?";	//@@@@@@ noticefileNum
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, referNum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
}
