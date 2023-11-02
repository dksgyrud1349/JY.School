package com.enrolmentinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;
// 데이터카운트 findbyid 글보기 게시글리스트
public class EnrolmentinfoDAO {
	private Connection conn = DBConn.getConnection();
	
	
	public int dataCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM enrolment WHERE userId=? ";
			
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
	
	public int dataCount(String schType, String kwd, String userId) {

		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql= "SELECT COUNT(*) FROM enrolment e1 "
					+ ""
					+ " WHERE e1.userId=? ";
			if(schType.equals("all")) { // 제목 또는 내용
				sql += " AND (INSTR(userId, ?) >= 1 OR INSTR(classNum2, ?) >= 1)";
			} else if(schType.equals("reg_date")) { // 날짜
				kwd = kwd.replaceAll("(\\-|\\/|\\.)","");
				sql += " AND TO_CHAR(reg_date, 'YYYYMMDD') = ?";
			} else { // 이름, 제목 내용
				sql += " AND INSTR(" + schType + " , ?) >= 1";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, kwd);
			if(schType.equals("all")) {
				pstmt.setString(3,  kwd);
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
	
	
	// 게시글 리스트
	public List<EnrolmentinfoDTO> listEnrolmentinfo(int offset, int size, String userId) {
		List<EnrolmentinfoDTO> list = new ArrayList<EnrolmentinfoDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder ();
		
		
		try {
			sb.append(" SELECT e1.userId, e1.classNum, e1.startDate, e1.endDate, e1.classNum2, className ");
			sb.append(" FROM enrolment e1 ");
			sb.append(" JOIN lecture l1 ON e1.classNum2 = l1.classNum2 ");
			sb.append(" WHERE e1.userId = ? ");
			sb.append(" ORDER BY classnum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString ());
			
			
			pstmt.setString(1, userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				EnrolmentinfoDTO dto = new EnrolmentinfoDTO();
				
				dto.setUserId(userId);
				dto.setClassNum(rs.getLong("classNum"));
				dto.setStartDate(rs.getDate("startDate"));
				dto.setEndDate(rs.getDate("endDate"));
				dto.setClassNum2(rs.getLong("classNum2"));
				dto.setClassName(rs.getString("className"));
				
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
	
	// 검색에서 게시글 리스트
	public List<EnrolmentinfoDTO> listEnrolmentinfo(int offset, int size, String schType, String kwd, String userId) {
	    List<EnrolmentinfoDTO> list = new ArrayList<EnrolmentinfoDTO>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuilder sb = new StringBuilder();

	    try {
	        sb.append("SELECT e1.classNum, e1.startDate, e1.endDate, e1.userId, e1.classNum2 ");
	        sb.append(" FROM enrolment e1 ");
	        sb.append(" JOIN lecture l1 ON e1.classNum2 = l1.classNum2 ");
	        sb.append(" WHERE e1.userId=? ");
	        if (schType.equals("all")) {
	            sb.append(" AND (INSTR(e1.classNum, ?) >= 1 OR INSTR(e1.userId, ?) >= 1)");
	        } else if (schType.equals("reg_date")) {
	            kwd = kwd.replaceAll("(\\-|\\/|\\.)", "");
	            sb.append(" AND TO_CHAR(l1.c_reg_date, 'YYYYMMDD') = ? ");
	        } else {
	            sb.append(" AND INSTR(" + schType + ", ?) >= 1");
	        }

	        sb.append(" ORDER BY e1.classNum DESC ");
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
	            EnrolmentinfoDTO dto = new EnrolmentinfoDTO();
	            dto.setClassNum(rs.getLong("classNum"));
	            dto.setStartDate(rs.getDate("startDate"));
	            dto.setEndDate(rs.getDate("endDate"));
	            dto.setUserId(rs.getString("userId"));
	            dto.setClassNum2(rs.getLong("classNum2"));
	        

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
	// 게시글 가져오기
	  public EnrolmentinfoDTO findById(long classNum) {
	      EnrolmentinfoDTO dto = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql;

	      try {
	    	  sql = "SELECT e1.classNum, e1.startDate, e1.endDate, e1.userId, e1.classNum2 " 
	    			  + " FROM enrolment e1 " 
	    			  + " JOIN lecture l1 ON e1.classNum2 = l1.classNum2 " 
	                  + " WHERE e1.classNum = ? ";
	        	
	          pstmt = conn.prepareStatement(sql);
	          pstmt.setLong(1, classNum);

	          rs = pstmt.executeQuery();

	          if (rs.next()) {
	               dto = new EnrolmentinfoDTO();
	                
	               dto.setClassNum(rs.getLong("classNum"));
	               dto.setStartDate(rs.getDate("startDate"));
	               dto.setEndDate(rs.getDate("endDate"));
	               dto.setUserId(rs.getString("userId"));
	               dto.setClassNum2(rs.getLong("classNum2"));
	            }
	            
	       } catch (SQLException e) {
	           e.printStackTrace();
	       } finally {
	           DBUtil.close(rs);
	           DBUtil.close(pstmt);
	       }

	       return dto;
	    }
	
}
