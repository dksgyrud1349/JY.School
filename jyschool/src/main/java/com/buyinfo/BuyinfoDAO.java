package com.buyinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.enrolmentinfo.EnrolmentinfoDTO;
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
			sql = "SELECT COUNT(*) FROM buyinfo WHERE userId=? "
					+ " FROM enrolment e1 "
					+ " JOIN buyinfo b2 ON e1.classNum = b2.classNum "
					+ " JOIN member m1 ON b2.userid = m1.userid "
					+ " WHERE e1.userid = ? ";
					
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
	// 검색에서 데이터 개수 구하기
	public int dataCount(String schType, String kwd, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM buyinfo b2"
					+ ""
					+ " WHERE b2.userid=? ";
			if(schType.equals("all")) {  // 제목 또는 내용
				sql += " AND (INSTR(userId, ?) >= 1 OR INSTR(classNum2, ?) >= 1)";
			} else if(schType.equals("reg_date")) { // 날짜
				kwd = kwd.replaceAll("(\\-|\\/|\\.)","");
				sql += " AND TO_CHAR(reg_date, 'YYYYMMDD') = ?";
			} else { // 이름, 제목 내용
				sql += " AND INSTR(" + schType + " , ?) >= 1";
			}
		} catch (Exception e) {
			
		}
		
		return result;
	}
	// 게시글 리스트 
	public List<BuyinfoDTO> listBuyinfo(int offset, int size, String userId) {
		List<BuyinfoDTO> list = new ArrayList<BuyinfoDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder ();
		
		try {
			sb.append(" SELECT b2.userId, b2.buyDate, b2.startDate, b2.endDate, b2.price, b2.classNum, e1.classNum2");
			sb.append(" FROM buyinfo b2 ");
			sb.append(" JOIN enrolment e1 ON e1.classNum = b2.classNum ");
			sb.append(" WHERE b2.userId = ? ");
			sb.append(" ORDER BY b2.classNum DESC");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString ());
			
			pstmt.setString(1, userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BuyinfoDTO dto = new BuyinfoDTO();
				
				dto.setUserId(rs.getString("userId"));
				dto.setBuyDate(rs.getDate("buyDate"));
				dto.setStartDate(rs.getDate("startDate"));
				dto.setEndDate(rs.getDate("endDate"));
				dto.setPrice(rs.getLong("price"));
				dto.setClassNum(rs.getLong("classNum"));
				dto.setClassNum2(rs.getLong("classNum2"));
				
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
	// 검색에서 게시글 리스트
	public List<EnrolmentinfoDTO> listEnrolmentinfo(int offset, int size, String schType, String kwd, String userId) {
		List<EnrolmentinfoDTO> list = new ArrayList<EnrolmentinfoDTO>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuilder sb = new StringBuilder();
	    
	    try {
	    	sb.append("SELECT b2.buyDate, b2.startDate, b2.endDate, b2,price b2.userId, b2.classNum ");
	    	sb.append(" FROM buyinfo b2 ");
	    	sb.append(" JOIN enrolment e1 ON e1.classNum = b2.classNum ");
	    	sb.append(" WHERE b2.userId=? ");
	    	 if (schType.equals("all")) {
	    		 sb.append(" AND (INSTR(b2.classNum, ?) >= 1 OR INSTR(b2.userId, ?) >= 1) ");
	    	 } else if (schType.equals("reg_date")) {
		         kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
		         sb.append(" AND TO_CHAR(l1.c_reg_date, 'YYYYMMDD') = ? ");
	    	 } else {
	    		 sb.append(" AND INSTR(" + schType + ", ?) >= 1");
	    	 } 
	    	 sb.append(" ORDER BY b2.classNum DESC ");
		     sb.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");
		     
		     pstmt = conn.prepareStatement(sb.toString());

		        pstmt.setString(1, userId);
		        if (schType.equals("all")) {
		            pstmt.setString(2, kwd);
		            pstmt.setString(3, kwd);
		            pstmt.setInt(4, offset);
		            pstmt.setInt(5, size);
		        } else {
		            pstmt.setString(2, kwd);
		            pstmt.setInt(3, offset);
		            pstmt.setInt(4, size);
		        }
		        
		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		            BuyinfoDTO dto = new BuyinfoDTO();
		            dto.setBuyDate(rs.getDate("buyDate"));
					dto.setStartDate(rs.getDate("startDate"));
					dto.setEndDate(rs.getDate("endDate"));
					dto.setPrice(rs.getLong("price"));
					dto.setUserId(rs.getString("userId"));
					dto.setClassNum(rs.getLong("classNum"));
					dto.setClassNum2(rs.getLong("classNum2"));
					
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
	 public EnrolmentinfoDTO findById(long classNum) {
	      EnrolmentinfoDTO dto = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql;
	      
	      return dto;
	 }
}
