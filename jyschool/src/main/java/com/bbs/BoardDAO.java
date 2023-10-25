package com.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class BoardDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertBoard(BoardDTO dto)throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "insert into bbs(num, userid, subject, content, reg_date, hitcount) "
					+ " values(bbs_seq.NEXTVAL, ?, ?, ?, sysdate, 0)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
		
	}
	
	public List<BoardDTO> listBoard(int offset, int size){
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" select num, b1.userid, userName, subject, content, hitcount, TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date");
			sb.append(" from bbs b1");
			sb.append(" join member1 m1 ON b1.userId=m1.userId");
			sb.append(" order by num desc");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getLong("num"));
				dto.setUserId(rs.getString("userid"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setUserName(rs.getString("username"));
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
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select NVL(count(*),0) from bbs";
			pstmt = conn.prepareStatement(sql);
			
			rs =pstmt.executeQuery();
			if(rs.next()) {
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
	
	public int dataCount(String schType, String kwd) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select count(*) from bbs b1 join member1 m1 ON b1.userId=m1.userId ";
			if(schType.equals("all")) { // 제목 또는 내용
				sql += " where  instr(subject,?) >= 1 or instr(content, ?) >= 1";
			}else if (schType.equals("reg_date")) { // 날짜
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sql += " where to_char(reg_date, 'YYYYMMDD') = ? ";
			}else { // 이름, 제목, 내용 // 아무거나 검색란에 넣은거 들어갈수 있게 schType
				sql += " where instr(" + schType + ", ?) >= 1 ";
			}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, kwd);
			if(schType.equals("all")) {
				pstmt.setString(2, kwd);
			}
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
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
	
	public List<BoardDTO> listBoard(int offset, int size, String schType, String kwd){
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("select num, b1.userid, username, subject, hitCount, ");
			sb.append("  TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date ");
			sb.append(" FROM bbs b1");
			sb.append(" join member1 m1 ON b1.userId=m1.userId");
			if(schType.equals("all")) {
				sb.append(" where instr(subject, ?) >=1 or instr(content, ?)>=1 ");
			}else if(schType.equals("reg_date")) {
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" where to_char(reg_date, 'YYYYMMDD') = ?");
			}else {
				sb.append(" where instr(" + schType + ", ?)>=1");
			}
			
			sb.append(" order by num DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(schType.equals("all")) {
				pstmt.setString(1, kwd);
				pstmt.setString(2, kwd);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			}else {
				pstmt.setString(1, kwd);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getLong("num"));
				dto.setUserName(rs.getString("username"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
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
	
	public void updateHitcount(long num) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "update bbs set hitCount = hitCount + 1 where num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num); // 시퀀스는 더 크기가 크니까 롱형으로...
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public BoardDTO findById(long num) {
		BoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select num, b1.userid, username, subject, content, reg_date, hitCount from bbs b1 "
					+ " join member1 m1 ON b1.userId=m1.userId "
					+ "where num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new BoardDTO();
				
				dto.setNum(rs.getLong("num"));
				dto.setUserId(rs.getString("userid"));
				dto.setUserName(rs.getString("username"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setHitCount(rs.getInt("hitCount"));
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
	public BoardDTO findByPrevBoard(long num, String schType, String kwd) {
		BoardDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if(kwd.length() != 0) {
				sb.append(" SELECT num, subject ");
				sb.append(" FROM bbs b1  join member1 m1 ON b1.userId=m1.userId");
				sb.append(" WHERE num > ? ");
				if (schType.equals("all")) {
					sb.append("   AND ( INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (schType.equals("reg_date")) {
					kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(reg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + schType + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY num ASC ");
				sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setLong(1, num);
				pstmt.setString(2, kwd);
				if(schType.equals("all")) {
					pstmt.setString(3, kwd);
				}
			} else {
				sb.append("SELECT num, subject FROM bbs b1 join member1 m1 ON b1.userId=m1.userId");
				sb.append(" WHERE num > ? ");
				sb.append(" ORDER BY num ASC ");
				sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setLong(1, num);
			}
        	
			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new BoardDTO();
				dto.setNum(rs.getLong("num"));
				dto.setSubject(rs.getString("subject"));
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
	public BoardDTO findByNextBoard(long num, String schType, String kwd) {
		BoardDTO dto = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if(kwd.length() != 0) {
				sb.append(" SELECT num, subject ");
				sb.append(" FROM bbs b1 join member1 m1 ON b1.userId=m1.userId ");
				sb.append(" WHERE num < ? ");
				if (schType.equals("all")) {
					sb.append("   AND ( INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (schType.equals("reg_date")) {
					kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(reg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + schType + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY num DESC ");
				sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setLong(1, num);
				pstmt.setString(2, kwd);
				if(schType.equals("all")) {
					pstmt.setString(3, kwd);
				}
			} else {
				sb.append("SELECT num, subject FROM bbs b1 ");
				sb.append( "join member1 m1 ON b1.userId=m1.userId ");
				sb.append(" WHERE num < ? ");
				sb.append(" ORDER BY num DESC ");
				sb.append(" FETCH  FIRST  1  ROWS  ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setLong(1, num);
			}
        	
			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new BoardDTO();
				dto.setNum(rs.getLong("num"));
				dto.setSubject(rs.getString("subject"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
    
		return dto;
	}
	
	public void updateBoard(BoardDTO dto) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "update bbs set subject=?, content=?, reg_date=sysdate  where num = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setLong(3, dto.getNum());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public void deleteBoard(long num) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "delete from bbs where num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}

}
