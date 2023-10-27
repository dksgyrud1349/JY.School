package com.sincheong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class LectureDAO {
	private Connection conn = DBConn.getConnection();
	
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
	
	// 게시물 리스트
	public List<LectureDTO> listLecture(int offset, int size){
		
		List<LectureDTO> list = new ArrayList<LectureDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT classNum2, className, m.username, classDegree, TO_CHAR(c_reg_date, 'YYYY-MM-DD') c_reg_date, price\r\n"
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
}
