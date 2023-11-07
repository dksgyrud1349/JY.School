package com.buyinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class BuyinfoDAO { 
	private Connection conn = DBConn.getConnection();
	
	// 전체 데이터 개수
	public int dataCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*)  "
				+ " FROM lecture l1 "
				+ " JOIN enrolment e1 on l1.classnum2 = e1.classNum2 "
				+ " JOIN pay p1 ON e1.classnum = p1.classnum "
				+ " where e1.userid = ? ";
					
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
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
	
	// 게시글 리스트 
	public List<BuyinfoDTO> listBuyinfo(int offset, int size, String userId) {
		List<BuyinfoDTO> list = new ArrayList<BuyinfoDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT l1.classNum2, className, allpay, p1.price, "
					+ "	TO_CHAR(startDate, 'YYYY-MM-DD') startDate, "
					+ " TO_CHAR(endDate, 'YYYY-MM-DD') endDate, "
					+ " TO_CHAR(payDate, 'YYYY-MM-DD') payDate "
					+ " FROM lecture l1 "
					+ " JOIN enrolment e1 on l1.classnum2 = e1.classNum2 "
					+ " JOIN pay p1 ON e1.classnum = p1.classnum "
					+ " where e1.userid = ? "
					+ " ORDER BY e1.classNum DESC"
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BuyinfoDTO dto = new BuyinfoDTO();
				
				dto.setClassNum2(rs.getLong("classNum2"));
				dto.setClassName(rs.getString("className"));
				dto.setStartDate(rs.getString("startDate"));
				dto.setEndDate(rs.getString("endDate"));
				dto.setPayDate(rs.getString("payDate"));
				dto.setPrice(rs.getLong("price"));
				dto.setAllpay(rs.getLong("allpay"));
		
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
