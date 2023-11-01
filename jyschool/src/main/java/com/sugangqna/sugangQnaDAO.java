package com.sugangqna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class sugangQnaDAO {
	private Connection conn = DBConn.getConnection();
	
	// 데이터 개수
	public int dataCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select count(*)\r\n"
					+ "from enrolment en\r\n"
					+ "JOIN lecture le ON en.classNum2 = le.classNum2\r\n"
					+ "JOIN member m ON le.userid = m.userid\r\n"
					+ "WHERE en.userid = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return result;
	}
	
	// 수강신청한 강좌 리스트
	public List<sugangQnaDTO> listsugangQna(int offset, int size, String userId){
		List<sugangQnaDTO> list = new ArrayList<sugangQnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		// le.classname : 강좌 제목
		// startdate : 시작날짜
		// enddate : 끝날짜
		// m.username : 강사 이름
		// le.classNum2 : 강좌 번호
		try {
			sql = "select le.classname, TO_CHAR(startdate, 'YYYY-MM-DD') startdate, TO_CHAR(enddate, 'YYYY-MM-DD') enddate, m.username, le.classNum2\r\n"
					+ "from enrolment en\r\n"
					+ "JOIN lecture le ON en.classNum2 = le.classNum2\r\n"
					+ "JOIN member m ON le.userid = m.userid\r\n"
					+ "WHERE en.userid = ? "
					+ " ORDER BY le.classNum2 DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sugangQnaDTO dto = new sugangQnaDTO();
				
				dto.setClassName(rs.getString("classname"));
				dto.setStartDate(rs.getString("startdate"));
				dto.setEndDate(rs.getString("enddate"));
				dto.setTeacherName(rs.getString("username"));
				dto.setLectureNumber(rs.getLong("classNum2"));
				
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
