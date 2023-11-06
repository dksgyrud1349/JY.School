package com.sincheong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			
			sql = " SELECT classNum2, le.userId, className, m.username, TO_CHAR(c_reg_date, 'YYYY-MM-DD') c_reg_date, classDegree, price, classComment, imagefilename1, imageFilename2 "
					+ " FROM lecture le"
					+ " JOIN teacher t ON le.userId = t.userId "
					+ " JOIN member m ON t.userId = m.userId "
					+ " WHERE classNum2 = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, classNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			dto = new LectureDTO();
			dto.setClassNum(rs.getLong("classNum2"));
			dto.setUserId(rs.getString("userId"));
			dto.setClassName(rs.getString("className"));
			dto.setUsername(rs.getString("username"));
			dto.setC_reg_date(rs.getString("c_reg_date"));
			dto.setClassDegree(rs.getString("classDegree"));
			dto.setPrice(rs.getInt("price"));
			dto.setClassComment(rs.getString("classComment"));
			dto.setImageFilename1(rs.getString("imagefilename1"));		
			dto.setImageFilename2(rs.getString("imageFilename2"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	  }finally {
		  DBUtil.close(rs);
		  DBUtil.close(pstmt);
	  }
		
		return dto;
	}
	
	// 수강신청(학생만)
	public void insertLecture(sincheongDTO dto) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = " INSERT INTO enrolment (classNum, startDate, endDate, classNum2, userId) values (enrolment_seq.NEXTVAL, SYSDATE, SYSDATE + 180, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getClassNum2());
			pstmt.setString(2, dto.getUserId());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			DBUtil.close(pstmt);
		}
		
	}
	
	public List<sincheongDTO> insertedenrolment(long classNum2) {
		//SincheongDTO dto = null;
		List<sincheongDTO> list = new ArrayList<sincheongDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = " SELECT e.classNum2, e.userId\r\n"
					+ " FROM lecture lec\r\n"
					+ " JOIN enrolment e ON lec.classNum2 = e.classNum2"
					+ " WHERE e.classNum2 = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, classNum2);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//dto = new SincheongDTO();
				//dto.setClassNum2(rs.getLong("classNum2"));
				//dto.setUserId(rs.getString("userId"));
				sincheongDTO dto = new sincheongDTO();
				dto.setClassNum2(rs.getLong("classNum2"));
				dto.setUserId(rs.getString("userId"));
				
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		//return dto;
		return list;
	}
	
}
