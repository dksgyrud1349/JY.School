package com.reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class ReferenceDAO {
	private Connection conn = DBConn.getConnection();
	
	// 등록
	public void insertReference(ReferenceDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		long seq;	
		
		try {
			sql = "SELECT File_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if (rs.next()) {
				seq = rs.getLong(1);
			}
			dto.setWriteNum(seq);

			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;

			sql = "INSERT INTO referenceroom(writeNum, title, content, writeDate, userId) "
					+ "  VALUES (?, ?, ?, SYSDATE, ?)";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getWriteNum());
			pstmt.setString(2, dto.getTitle());			
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getUserId());

			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if (dto.getSaveFiles() != null) {
				sql = "INSERT INTO referfile(referfileNum, writeNum, saveFile, clientFile) VALUES (Field_seq.NEXTVAL, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					pstmt.setLong(1, dto.getWriteNum());
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
	
	// 데이터 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM referenceroom";
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
	
	// 검색에서 전체의 개수
	public int dataCount(String schType, String kwd) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM referenceroom r "
					+ " JOIN member m ON r.userId=m.userId ";
			if (schType.equals("all")) {
				sql += "  WHERE INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else if (schType.equals("writeNum")) {
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(writeDate, 'YYYYMMDD') = ? ";
			} else {
				sql += "  WHERE INSTR(" + schType + ", ?) >= 1 ";
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

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return result;
	}
	
	// 게시물 리스트
	public List<ReferenceDTO> listNotice(int offset, int size) {
		List<ReferenceDTO> list = new ArrayList<ReferenceDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();	

		try {
			sb.append(" SELECT writeNum, r.userId, userName, title, writeDate ");
			sb.append(" FROM referenceroom r ");
			sb.append(" JOIN member m ON r.userId = m.userId ");
			sb.append(" ORDER BY writeNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();	
	
			while (rs.next()) {
				ReferenceDTO dto = new ReferenceDTO();

				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setTitle(rs.getString("title"));
				dto.setWriteDate(rs.getString("writeDate"));

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

	// 검색에서 리스트
	public List<ReferenceDTO> listNotice(int offset, int size, String schType, String kwd) {
		List<ReferenceDTO> list = new ArrayList<ReferenceDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT writeNum, r.userId, userName, title, writeDate ");
			sb.append(" FROM referenceroom r ");
			sb.append(" JOIN member m ON r.userId = m.userId ");
			if (schType.equals("all")) {
				sb.append(" WHERE INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else if (schType.equals("writeDate")) {
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(writeDate, 'YYYYMMDD') = ?");
			} else {
				sb.append(" WHERE INSTR(" + schType + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY writeNum DESC ");
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
				ReferenceDTO dto = new ReferenceDTO();

				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setTitle(rs.getString("title"));
				dto.setWriteDate(rs.getString("writeDate"));

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
	
	
	// 자료실 리스트
	public List<ReferenceDTO> listNotice() {
		List<ReferenceDTO> list = new ArrayList<ReferenceDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
	
		try {
			sb.append(" SELECT writeNum, r.userId, userName, title, ");
			sb.append(" TO_CHAR(writeDate, 'YYYY-MM-DD') writeDate  ");
			sb.append(" FROM referenceroom r ");
			sb.append(" JOIN member m ON r.userId=m.userId ");
			sb.append(" ORDER BY writeNum DESC ");

			pstmt = conn.prepareStatement(sb.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReferenceDTO dto = new ReferenceDTO();

				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setTitle(rs.getString("title"));
				dto.setWriteDate(rs.getString("writeDate"));

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
	
	public ReferenceDTO findById(long num) {
		ReferenceDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT writeNum,  r.userId, userName, title, content, writeDate "
					+ " FROM referenceroom r "
					+ " JOIN member m ON r.userId=m.userId "
					+ " WHERE writeNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ReferenceDTO();

				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
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
	public ReferenceDTO findByPrev(long num, String schType, String kwd) {
		ReferenceDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();	
		
		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT writeNum, title ");
				sb.append(" FROM referenceroom r ");
				sb.append(" JOIN member m ON r.userId = m.userId ");
				sb.append(" WHERE ( writeNum > ? ) ");
				if (schType.equals("all")) {
					sb.append("   AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (schType.equals("writeDate")) {
					kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(writeDate, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + schType + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY writeNum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, kwd);
				if (schType.equals("all")) {
					pstmt.setString(3, kwd);
				}
			} else {
				sb.append(" SELECT writeNum, title FROM referenceroom ");
				sb.append(" WHERE writeNum > ? ");
				sb.append(" ORDER BY writeNum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ReferenceDTO();
				
				dto.setWriteNum(rs.getLong("writeNum"));
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
	public ReferenceDTO findByNext(long num, String schType, String kwd) {
		ReferenceDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (kwd != null && kwd.length() != 0) {
				sb.append(" SELECT writeNum, title ");
				sb.append(" FROM referenceroom r ");
				sb.append(" JOIN member m ON r.userId = m.userId ");
				sb.append(" WHERE ( writeNum < ? ) ");
				if (schType.equals("all")) {
					sb.append("   AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (schType.equals("writeDate")) {
					kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(writeDate, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + schType + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY writeNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, kwd);
				if (schType.equals("all")) {
					pstmt.setString(3, kwd);
				}
			} else {
				sb.append(" SELECT writeNum, title FROM referenceroom ");
				sb.append(" WHERE writeNum < ? ");
				sb.append(" ORDER BY writeNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ReferenceDTO();
				
				dto.setWriteNum(rs.getLong("writeNum"));
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
	
	// 자료글 수정
	public void updateReference(ReferenceDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;	
		
		try {
			sql = "UPDATE referenceroom SET title=?, content=? "
					+ " WHERE writeNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setLong(3, dto.getWriteNum());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;

			if (dto.getSaveFiles() != null) {
				sql = "INSERT INTO referfile(referfileNum, writeNum, saveFile, clientFile) VALUES (referfile_seq.NEXTVAL, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					pstmt.setLong(1, dto.getWriteNum());
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
	
	// 자료글 글보기 삭제
	public void deleteReference(long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;	
	
		try {
			sql = "DELETE FROM referenceroom WHERE writeNum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}	
	
	// 자료글 리스트에서 바로 삭제
	public void deleteReferenceList(long[] nums) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM referenceroom WHERE writeNum IN (";
			for (int i = 0; i < nums.length; i++) {
				sql += "?,";
			}
			sql = sql.substring(0, sql.length() - 1) + ")";

			pstmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < nums.length; i++) {
				pstmt.setLong(i + 1, nums[i]);
			}

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}
	
	public List<ReferenceDTO> listReferFile(long num) {
		List<ReferenceDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;	
		
		try {
			sql = "SELECT referfileNum, writeNum, saveFile, clientFile FROM referfile WHERE writeNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReferenceDTO dto = new ReferenceDTO();

				dto.setReferfileNum(rs.getLong("referfileNum"));
				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setSaveFile(rs.getString("saveFile"));
				dto.setClientFile(rs.getString("clientFile"));
				
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
	
	public ReferenceDTO findByFileId(long num) {
		ReferenceDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;	
		
		try {
			sql = "SELECT referfileNum, writeNum, saveFile, clientFile FROM referfile WHERE referfileNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ReferenceDTO();

				dto.setReferfileNum(rs.getLong("referfileNum"));
				dto.setWriteNum(rs.getLong("writeNum"));
				dto.setSaveFile(rs.getString("saveFile"));
				dto.setClientFile(rs.getString("clientFile"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return dto;
	}
	
	public void deleteReferFile(String mode, long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (mode.equals("all")) {
				sql = "DELETE FROM referfile WHERE writeNum = ?";
			} else {
				sql = "DELETE FROM referfile WHERE referfileNum = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}

	}
	
}
	
	
