package com.sincheong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;
import com.util.DBUtil;

public class sincheongDAO {
	private Connection conn = DBConn.getConnection();
	
	public LectureDTO pickLecture(long classNum) {
		LectureDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT classNum2, className, m.username, TO_CHAR(c_reg_date, 'YYYY-MM-DD') c_reg_date, classDegree, price"
					+ " FROM lecture le"
					+ " JOIN teacher t ON le.userId = t.userId"
					+ " JOIN member m ON t.userId = m.userId"
					+ " WHERE classNum2 = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, classNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			dto = new LectureDTO();
			dto.setClassNum(rs.getLong("classNum2"));
			dto.setClassName(rs.getString("className"));
			dto.setUsername(rs.getString("username"));
			dto.setC_reg_date(rs.getString("c_reg_date"));
			dto.setClassDegree(rs.getString("classDegree"));
			dto.setPrice(rs.getInt("price"));
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
