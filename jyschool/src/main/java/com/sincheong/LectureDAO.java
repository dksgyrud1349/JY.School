package com.sincheong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class LectureDAO {
	private Connection conn = DBConn.getConnection();

	// 강좌 등록(강사)
	public void insertLecture(LectureDTO dto) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		try {
			
			sql = "INSERT INTO lecture(classNum2, className, price, classComment, classDegree, c_reg_date, userId, imagefilename1, imagefilename2) values (?, ?, ?, ?, ?, SYSDATE, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getClassNum());
			pstmt.setString(2, dto.getClassName());
			pstmt.setInt(3, dto.getPrice());
			pstmt.setString(4, dto.getClassComment());
			pstmt.setString(5, dto.getClassDegree());
			pstmt.setString(6, dto.getUserId());
			pstmt.setString(7, dto.getImageFilename1());
			pstmt.setString(8, dto.getImageFilename2());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public List<LectureDTO> teacherList(){
		List<LectureDTO> list = new ArrayList<LectureDTO>();
		LectureDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT USERID\r\n"
					+ "FROM MEMBER\r\n"
					+ "WHERE TEACHCHK = '1'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dto = new LectureDTO();
				dto.setUserId(rs.getString("USERID"));
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
	
	// 강좌 수정(관리자)
	public void updateLecture(LectureDTO dto, String userId) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(userId.equals("admin")) {
				LectureDTO ldto = findByTeacher(userId);
				if(ldto != null) {  // 강사이면
					sql = "UPDATE LECTURE SET CLASSNAME = ?, PRICE = ?, CLASSCOMMENT = ?, CLASSDEGREE = ?, USERID = '" + ldto.getUserId() + "' WHERE classNum2 = ?";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, dto.getClassName());
					pstmt.setInt(2, dto.getPrice());
					pstmt.setString(3, dto.getClassComment());
					pstmt.setString(4, dto.getClassDegree());
					pstmt.setLong(5, dto.getClassNum());
					
					pstmt.executeUpdate();
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	// 강좌 삭제(관리자)
	public void deleteLecture(long num, String userId) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(userId.equals("admin")) {  // 관리자만 삭제할 수 있다.
				sql = "DELETE FROM lecture WHERE num = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, num);
				pstmt.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	// 강사 정보
	public LectureDTO findByTeacher(String userId) {
		LectureDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select teachchk, userid, username\r\n"
					+ "from member\r\n"
					+ "where userid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new LectureDTO();
				dto.setTeachchk(rs.getString("teachchk"));
				dto.setUserId(rs.getString("userid"));
				dto.setUsername(rs.getString("username"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return dto;
	}

	// 데이터 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM lecture";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
	public int dataCount(String schType, String kwd) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) "
					+ " FROM lecture le "
					+ " JOIN member m ON le.userId = m.userId ";
			
			if(schType.equals("all")) {
				sql += " WHERE INSTR(className, ?) >= 1 OR INSTR(classComment, ?) >= 1 ";
			} else if(schType.equals("c_reg_date")) {
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(c_reg_date, 'YYYYMMDD') = ? ";
			} else {
				sql += " WHERE INSTR(" + schType + ", ?) >= 1 ";
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
	
	// 게시물 리스트
	public List<LectureDTO> listLecture(int offset, int size){
		
		List<LectureDTO> list = new ArrayList<LectureDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT classNum2, className, m.username, classDegree, TO_CHAR(c_reg_date, 'YYYY-MM-DD') c_reg_date, price, m.TEACHCHK, t.userId\r\n"
					+ "FROM lecture le\r\n"
					+ "JOIN teacher t ON le.userid = t.userid\r\n"
					+ "JOIN member m ON t.userid = m.userid"
					+ " ORDER BY classNum2 DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setClassNum(rs.getLong("classNum2"));
				dto.setClassName(rs.getString("className"));
				dto.setUsername(rs.getString("username"));
				dto.setClassDegree(rs.getString("classDegree"));
				dto.setC_reg_date(rs.getString("c_reg_date"));
				dto.setPrice(rs.getInt("price"));
				dto.setTeachchk(rs.getString("TEACHCHK"));
				dto.setUserId(rs.getString("userId"));
				
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
	
	// 검색 리스트
	public List<LectureDTO> listLecture(int offset, int size, String schType, String kwd){
		List<LectureDTO> list = new ArrayList<LectureDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "SELECT classNum2, className, m.username, classDegree, TO_CHAR(c_reg_date, 'YYYY-MM-DD') c_reg_date, price, m.TEACHCHK, t.userId\r\n"
					+ "FROM lecture le\r\n"
					+ "JOIN teacher t ON le.userid = t.userid\r\n"
					+ "JOIN member m ON t.userid = m.userid ";
			if(schType.equals("all")) {
				sql += " WHERE INSTR(className, ?) >= 1 OR INSTR(classComment, ?) >= 1 ";
			} else if(schType.equals("c_reg_date")) {
				kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(c_reg_date, 'YYYYMMDD') = ? ";
			} else {
				sql += " WHERE INSTR(" + schType + ", ?) >= 1 ";
			}
			sql += " ORDER BY classNum2 DESC ";
			sql += " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			if(schType.equals("all")) {
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
			
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setClassNum(rs.getLong("classNum2"));
				dto.setClassName(rs.getString("className"));
				dto.setUsername(rs.getString("username"));
				dto.setClassDegree(rs.getString("classDegree"));
				dto.setC_reg_date(rs.getString("c_reg_date"));
				dto.setPrice(rs.getInt("price"));
				dto.setTeachchk(rs.getString("TEACHCHK"));
				dto.setUserId(rs.getString("userId"));
				
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
	
	// 게시글 가져오기
		public LectureDTO findById(Long classNum) {
			LectureDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = " SELECT userId, className, classDegree, price, classComment FROM lecture WHERE classNum2 = ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, classNum);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					dto = new LectureDTO();
					
					dto.setClassNum(rs.getLong("classNum2"));
					dto.setUserId(rs.getString("userId"));
					dto.setClassName(rs.getString("className"));
					dto.setClassDegree(rs.getString("classDegree"));
					dto.setPrice(rs.getInt("price"));
					dto.setClassComment(rs.getString("classComment"));
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				DBUtil.close(rs);
				DBUtil.close(pstmt);
			}
			
			return dto;
		}
}
