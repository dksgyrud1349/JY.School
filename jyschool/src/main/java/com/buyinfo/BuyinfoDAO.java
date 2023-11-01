package com.buyinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			sql = "SELECT COUNT(*) FROM buyinfo WHERE userId=? ";
			
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
		
		return result;
	}
	// 게시글 리스트 
	public List<BuyinfoDTO> listBuyinfo(int offset, int size, String userId) {
		List<BuyinfoDTO> list = new ArrayList<BuyinfoDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder ();
		
		
		
		return list;
	}
	// 검색에서 게시글 리스트
	public List<EnrolmentinfoDTO> listEnrolmentinfo(int offset, int size, String schType, String kwd, String userId) {
		List<EnrolmentinfoDTO> list = new ArrayList<EnrolmentinfoDTO>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    StringBuilder sb = new StringBuilder();
	    
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
