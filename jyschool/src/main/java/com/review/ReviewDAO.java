package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bbs.BoardDTO;
import com.util.DBConn;
import com.util.DBUtil;

public class ReviewDAO {
	private Connection conn = DBConn.getConnection();

	public void insertBoard(BoardDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO review(classNum, userId, subject, content )"
					+ "VALUES (?, ?, ?, ? )";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql= "SELECT COUNT(*) FROM review ";
			pstmt = conn.prepareStatement(sql);
			
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
	
	// 게시글 리스트
	public List<ReviewDTO> listReview(int offset, int size) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" select classNum, r1.userid, subject, content ");
			sb.append(" from review r1");
			sb.append(" join member1 m1 ON b1.userId=m1.userId");
			sb.append(" order by classNum desc");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				
				dto.setClassNum(rs.getLong("classNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setSubject(rs.getString("subject"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return list;
	}
	
	// 게시글 가져오기
	public ReviewDTO findById(long classnum) {
		ReviewDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT classNum, subject, content "
					+ " FROM review "
					+ " WHERE classNum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, classnum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ReviewDTO();
				
				dto.setClassNum(rs.getLong("classnum"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return dto;
	}
	
	
}

	



