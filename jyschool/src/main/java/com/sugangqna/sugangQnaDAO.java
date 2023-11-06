package com.sugangqna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.util.DBUtil;

public class sugangQnaDAO {
	private Connection conn = DBConn.getConnection();

	// 수강 강좌 데이터 개수
	public int adminDataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			/*
			sql = "select count(*)\r\n" + "from enrolment en\r\n" + "JOIN lecture le ON en.classNum2 = le.classNum2\r\n"
					+ "JOIN member m ON le.userid = m.userid";
			*/
			sql = "SELECT COUNT(*) FROM LECTURE";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
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
	
	// 강사 본인이 수업하고 있는 강좌 데이터 개수
	public int teachDataCount(String teacher) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select count(*)\r\n"
					+ "from lecture\r\n"
					+ "where userid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher);
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

	// 데이터 개수
	public int dataCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select count(*)\r\n" + "from enrolment en\r\n" + "JOIN lecture le ON en.classNum2 = le.classNum2\r\n"
					+ "JOIN member m ON le.userid = m.userid\r\n" + "WHERE en.userid = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
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

	// 게시판 데이터 개수
	public int bbsDataCount(long classNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select count(*)\r\n" + "from qna\r\n" + "where classnum=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, classNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
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

	public List<sugangQnaDTO> listSugangLecture(int offset, int size, String admin) {
		List<sugangQnaDTO> listSugangLecture = new ArrayList<sugangQnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			/*
			sql = "select le.classname, TO_CHAR(startdate, 'YYYY-MM-DD') startdate, TO_CHAR(enddate, 'YYYY-MM-DD') enddate, m.username, le.classNum2, en.classnum, en.userid\r\n"
					+ "from enrolment en\r\n" + "JOIN lecture le ON en.classNum2 = le.classNum2\r\n"
					+ "JOIN member m ON le.userid = m.userid\r\n" + "ORDER BY le.classNum2 DESC";
			*/
			
			sql = "select className\r\n"
					+ "from lecture OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				/*
				sugangQnaDTO dto = new sugangQnaDTO();
				dto.setClassName(rs.getString("classname")); // 강좌 이름
				dto.setStartDate(rs.getString("startdate")); // 시작 날짜
				dto.setEndDate(rs.getString("enddate")); // 종료 날짜
				dto.setTeacherName(rs.getString("username")); // 강사 이름
				dto.setLectureNumber(rs.getLong("classNum2")); // 강좌 번호
				dto.setClassNum(rs.getLong("classnum")); // 수강 번호
				dto.setQ_userId(rs.getString("userid")); // 학생 아이디
				*/
				
				sugangQnaDTO dto = new sugangQnaDTO();
				dto.setClassName(rs.getString("className"));

				listSugangLecture.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return listSugangLecture;
	}
	
	public List<sugangQnaDTO> listSugangLecture2(int offset, int size, String teacher) {
		List<sugangQnaDTO> listSugangLecture = new ArrayList<sugangQnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			/*
			sql = "select le.classname, TO_CHAR(startdate, 'YYYY-MM-DD') startdate, TO_CHAR(enddate, 'YYYY-MM-DD') enddate, m.username, le.classNum2, en.classnum, en.userid\r\n"
					+ "from enrolment en\r\n"
					+ "join lecture le on en.classNum2 = le.classNum2\r\n"
					+ "join member m on le.userid = m.userid\r\n"
					+ "where le.userid = ?\r\n"
					+ "order by le.classnum2 desc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			*/
			sql = "select className\r\n"
					+ "from lecture\r\n"
					+ "where userId = ? OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, teacher);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sugangQnaDTO dto = new sugangQnaDTO();
				/*
				dto.setClassName(rs.getString("classname")); // 강좌 이름
				dto.setStartDate(rs.getString("startdate")); // 시작 날짜
				dto.setEndDate(rs.getString("enddate")); // 종료 날짜
				dto.setTeacherName(rs.getString("username")); // 강사 이름
				dto.setLectureNumber(rs.getLong("classNum2")); // 강좌 번호
				dto.setClassNum(rs.getLong("classnum")); // 수강 번호
				dto.setQ_userId(rs.getString("userid")); // 학생 아이디
				*/
				dto.setClassName(rs.getString("className"));
				listSugangLecture.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return listSugangLecture;
	}

	// 수강신청한 강좌 리스트
	public List<sugangQnaDTO> listsugangQna(int offset, int size, String userId) {
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
			sql = "select le.classname, TO_CHAR(startdate, 'YYYY-MM-DD') startdate, TO_CHAR(enddate, 'YYYY-MM-DD') enddate, m.username, le.classNum2, en.classnum\r\n"
					+ "from enrolment en\r\n" + "JOIN lecture le ON en.classNum2 = le.classNum2\r\n"
					+ "JOIN member m ON le.userid = m.userid\r\n" + "WHERE en.userid = ? "
					+ " ORDER BY le.classNum2 DESC " + " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				sugangQnaDTO dto = new sugangQnaDTO();

				dto.setClassName(rs.getString("classname"));
				dto.setStartDate(rs.getString("startdate"));
				dto.setEndDate(rs.getString("enddate"));
				dto.setTeacherName(rs.getString("username"));
				dto.setLectureNumber(rs.getLong("classNum2"));
				dto.setClassNum(rs.getLong("classnum"));

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

	// 게시판 글 등록
	public void insertQuestion(sugangQnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "INSERT INTO qna(q_num, q_title, q_userId, q_date, q_content, classNum, result_state) VALUES (QNA_SEQ.NEXTVAL, ?, ?, SYSDATE, ?, ?, 'X')";
			// 질문번호, 질문제목, 작성자 아이디, 질문일자, 질문내용, 수강번호
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getQ_title());
			pstmt.setString(2, dto.getQ_userId());
			pstmt.setString(3, dto.getQ_content());
			pstmt.setLong(4, dto.getClassNum());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}

	// 해당 게시물 보기
	public sugangQnaDTO findByQuestion(long q_num) {  // q_num : 질문번호
		sugangQnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select q_num, q_title, q_userId, q_date, q_content, m.username, a_content, a_title, result_state, a_date\r\n"
					+ "from qna q \r\n"
					+ "JOIN member m ON q.q_userId = m.userId \r\n"
					+ "where q_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, q_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new sugangQnaDTO();
				
				dto.setQ_num(rs.getLong("q_num"));
				dto.setQ_title(rs.getString("q_title"));
				dto.setQ_userId(rs.getString("q_userId"));
				dto.setQ_date(rs.getString("q_date"));
				dto.setQ_content(rs.getString("q_content"));
				dto.setQ_userName(rs.getString("username"));
				dto.setA_content(rs.getString("a_content"));
				dto.setA_title(rs.getString("a_title"));
				dto.setResult_state(rs.getString("result_state"));
				dto.setA_date(rs.getString("a_date"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		
		return dto;
	}

	// 게시판 글 수정
	public void updateQuestion(sugangQnaDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {

			sql = "UPDATE qna SET q_title = ?, q_content = ? WHERE q_num = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getQ_title());
			pstmt.setString(2, dto.getQ_content());
			pstmt.setLong(3, dto.getQ_num());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}
	
	public void deleteAnswer(long q_num) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE qna SET a_title = null, a_content = null, result_state = 'X', a_userid = null, a_date = null where q_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, q_num);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
	}

	// 답변
	public void answer(sugangQnaDTO dto, String result_state) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE qna SET a_content = ?, result_state = ?, a_title = ?, a_userId = ?, a_date = SYSDATE WHERE q_num = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getA_content());
			pstmt.setString(2, result_state);
			pstmt.setString(3, dto.getA_title());
			pstmt.setString(4, dto.getA_userId());
			pstmt.setLong(5, dto.getQ_num());

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pstmt);
		}
	}

	// 게시판 글 삭제
	public void deleteQuestion(long q_num, String q_userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (q_userId.equals("admin")) {
				sql = "DELETE FROM qna WHERE q_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, q_num);
				pstmt.executeUpdate();

			} else {
				sql = "DELETE FROM qna WHERE q_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, q_num);
				// pstmt.setString(2, q_userId);
				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.close(pstmt);
		}
	}

	// 게시판 글 가져오기
	public List<sugangQnaDTO> qnaList(int offset, int size, long classNum) {
		List<sugangQnaDTO> list = new ArrayList<sugangQnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select q_num, q_content, TO_CHAR(q_date, 'YYYY-MM-DD') q_date, en.classNum, q_title, q_userId, result_state, le.classname, me.username\r\n"
					+ "from qna q\r\n"
					+ "join enrolment en on q.classNum = en.classNum\r\n"
					+ "join lecture le on en.classNum2 = le.classNum2\r\n"
					+ "join member me on q_userId = me.userId\r\n"
					+ "WHERE en.classnum = ? "
					+ " ORDER BY q_num desc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, classNum);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				sugangQnaDTO dto = new sugangQnaDTO();

				dto.setQ_num(rs.getLong("q_num"));
				dto.setQ_content(rs.getString("q_content"));
				dto.setQ_date(rs.getString("q_date"));
				dto.setClassNum(rs.getLong("classNum"));
				dto.setQ_title(rs.getString("q_title"));
				dto.setQ_userId(rs.getString("q_userId"));
				dto.setResult_state(rs.getString("result_state"));
				dto.setClassName(rs.getString("classname"));
				dto.setQ_userName(rs.getString("username"));

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

	public sugangQnaDTO findByLectureName(long classNum) {
		sugangQnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select classNum, le.className\r\n" + "from enrolment en\r\n"
					+ "JOIN LECTURE le ON en.classNum2 = le.classNum2\r\n" + "WHERE en.classNum = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, classNum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new sugangQnaDTO();
				dto.setClassNum(rs.getLong("classNum"));
				dto.setClassName(rs.getString("className"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}

		return dto;
	}
	
	// info.getuserid로 선생님 여부 확인
	public sugangQnaDTO isTeacher(String teacher) {
		sugangQnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select userId\r\n"
					+ "from lecture\r\n"
					+ "where userId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dto = new sugangQnaDTO();
				dto.setT_userId(rs.getString("userId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
		return dto;
	}
	
	// 답변 여부
	public sugangQnaDTO isAnswer(long q_num) {
		sugangQnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select result_state\r\n"
					+ "from qna\r\n"
					+ "where q_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, q_num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dto = new sugangQnaDTO();
				dto.setResult_state(rs.getString("result_state"));
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
