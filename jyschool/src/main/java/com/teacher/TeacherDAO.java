package com.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.member.MemberDTO;
import com.util.DBConn;
import com.util.DBUtil;

public class TeacherDAO {
private Connection conn= DBConn.getConnection();
	
	public int dateCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select count(*) from member where teachChk = 1";
			
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
	
	public List<MemberDTO> listPhoto(int offset, int size){
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select username, tecimg, edu, TECRECORD "
					+ " from teacher t join member m on t.userid=m.userid "
					+ "where m.TEACHCHK = 1 "
					+ "order by username desc offset ? rows fetch first ? rows only ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				
				dto.setUserName(rs.getString("username"));
				dto.setTecImg(rs.getString("tecimg"));
				dto.setEdu(rs.getString("edu"));
				dto.setTecRecord(rs.getString("TECRECORD"));
				
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
