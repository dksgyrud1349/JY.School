package com.sugangqna;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/sugangqna/*")
public class sugangQnaServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		// uri에 따른 구분
		if (uri.indexOf("list.do") != -1) {
			// 수강신청한 강좌 리스트
			lectureList(req, resp);

		} else if (uri.indexOf("list_ok.do") != -1) {
			// 강좌 게시판 리스트
			listSubmit(req, resp);

		} else if (uri.indexOf("write.do") != -1) {
			// 게시판 글 등록 폼
			writeForm(req, resp);

		} else if (uri.indexOf("write_ok.do") != -1) {
			// 게시판 등록
			writeSubmit(req, resp);

		} else if (uri.indexOf("article.do") != -1) {
			// 게시판 글 내용 보기
			article(req, resp);

		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);

		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);

		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);

		} else if (uri.indexOf("answer.do") != -1) {
			answerForm(req, resp);

		} else if (uri.indexOf("answer_ok.do") != -1) {
			answerSubmit(req, resp);

		} else if (uri.indexOf("answerDelete.do") != -1) {
			deleteAnswer(req, resp);
		}
	}

	protected void lectureList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수강신청한 강좌 리스트
		sugangQnaDAO dao = new sugangQnaDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();

		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		try {
			if (!info.getUserId().equals("admin")) {
				String page = req.getParameter("page");
				int current_page = 1;
				if (page != null) {
					current_page = Integer.parseInt(page);
				}

				// 데이터 개수
				int dataCount = dao.dataCount(info.getUserId());

				int size = 5;
				int total_page = util.pageCount(dataCount, size);

				if (current_page > total_page) {
					current_page = total_page;
				}

				// 게시물 가져오기
				int offset = (current_page - 1) * size;
				if (offset < 0) {
					offset = 0;
				}

				List<sugangQnaDTO> list = dao.listsugangQna(offset, size, info.getUserId());

				// 페이징 처리
				String listUrl = cp + "/sugangqna/list.do";
				String paging = util.paging(current_page, total_page, listUrl);

				// 포워딩할 JSP에 전달할 속성
				req.setAttribute("list", list); // 로그인한 아이디가 수강신청한 강좌
				req.setAttribute("page", current_page);
				req.setAttribute("total_page", total_page);
				req.setAttribute("dataCount", dataCount);
				req.setAttribute("size", size);
				req.setAttribute("paging", paging);

			} else {

				String page = req.getParameter("page");
				int current_page = 1;
				if (page != null) {
					current_page = Integer.parseInt(page);
				}

				// 데이터 개수
				int dataCount = dao.adminDataCount();

				int size = 5;
				int total_page = util.pageCount(dataCount, size);

				if (current_page > total_page) {
					current_page = total_page;
				}

				// 게시물 가져오기
				int offset = (current_page - 1) * size;
				if (offset < 0) {
					offset = 0;
				}

				List<sugangQnaDTO> list = dao.listSugangLecture(offset, size, "admin");

				// 페이징 처리
				String listUrl = cp + "/sugangqna/list.do";
				String paging = util.paging(current_page, total_page, listUrl);

				// 포워딩할 JSP에 전달할 속성
				req.setAttribute("list", list);
				req.setAttribute("page", current_page);
				req.setAttribute("total_page", total_page);
				req.setAttribute("dataCount", dataCount);
				req.setAttribute("size", size);
				req.setAttribute("paging", paging);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/sugangqna/list.jsp");
	}

	protected void listSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		sugangQnaDAO dao = new sugangQnaDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();

		try {
			long classNum = Long.parseLong(req.getParameter("classNum"));

			String page = req.getParameter("page");

			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}

			// 전체 데이터 개수
			int dataCount = dao.bbsDataCount(classNum);

			// 전체 페이지 수
			int size = 3;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int offset = (current_page - 1) * size;
			if (offset < 0)
				offset = 0;

			List<sugangQnaDTO> list = dao.qnaList(offset, size, classNum);

			sugangQnaDTO dto1 = dao.findByLectureName(classNum);

			String listUrl = cp + "/sugangqna/list_ok.do?classNum=" + classNum;
			String articleUrl = cp + "/sugangqna/article.do?classNum=" + classNum;

			String paging = util.paging(current_page, total_page, listUrl);

			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("classNum", classNum);
			req.setAttribute("paging", paging);
			req.setAttribute("className", dto1.getClassName());
			req.setAttribute("articleUrl", articleUrl);

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/sugangqna/qnalist.jsp");
	}

	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		long classNum = Long.parseLong(req.getParameter("classNum"));

		req.setAttribute("classNum", classNum);
		req.setAttribute("mode", "write");

		forward(req, resp, "/WEB-INF/views/sugangqna/write.jsp");
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		sugangQnaDAO dao = new sugangQnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		long classNum = Long.parseLong(req.getParameter("classNum"));

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
			return;
		}

		try {
			sugangQnaDTO dto = new sugangQnaDTO();

			dto.setQ_userId(info.getUserId());
			dto.setClassNum(classNum);

			// 파라미터
			dto.setQ_title(req.getParameter("q_title"));
			dto.setQ_content(req.getParameter("q_content"));

			dao.insertQuestion(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시판 글 보기
		sugangQnaDAO dao = new sugangQnaDAO();

		String cp = req.getContextPath();

		long classNum = Long.parseLong(req.getParameter("classNum"));
		long q_num = Long.parseLong(req.getParameter("q_num"));

		try {
			sugangQnaDTO dto = dao.findByQuestion(q_num);

			if (dto == null) {
				resp.sendRedirect(cp + "/sugangqna/list.do?classNum=" + classNum);
				return;
			}

			// JSP로 전달할 속성
			req.setAttribute("classNum", classNum);
			req.setAttribute("dto", dto);
			req.setAttribute("q_num", q_num);

			// 포워딩
			forward(req, resp, "/WEB-INF/views/sugangqna/article.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		sugangQnaDAO dao = new sugangQnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		long classNum = Long.parseLong(req.getParameter("classNum"));
		long q_num = Long.parseLong(req.getParameter("q_num"));

		try {
			sugangQnaDTO dto = dao.findByQuestion(q_num);
			// q_num, q_title, q_userId, q_date, q_content, m.username

			if (dto == null) {
				resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (!dto.getQ_userId().equals(info.getUserId()) && !info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
				return;
			}

			req.setAttribute("classNum", classNum);
			req.setAttribute("q_num", q_num);
			req.setAttribute("dto", dto); // q_num, q_title, q_userId, q_date, q_content, m.username
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/sugangqna/write.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
	}

	protected void answerForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 폼
		sugangQnaDAO dao = new sugangQnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		long classNum = Long.parseLong(req.getParameter("classNum"));
		long q_num = Long.parseLong(req.getParameter("q_num"));

		try {
			sugangQnaDTO dto = dao.findByQuestion(q_num);
			// q_num, q_title, q_userId, q_date, q_content, m.username

			if (dto == null) {
				resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
				return;
			}

			// 관리자가 아니면
			if (!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
				return;
			}

			req.setAttribute("classNum", classNum);
			req.setAttribute("q_num", q_num);
			req.setAttribute("dto", dto); // q_num, q_title, q_userId, q_date, q_content, m.username
			req.setAttribute("mode", "answer");

			forward(req, resp, "/WEB-INF/views/sugangqna/write.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
	}

	protected void answerSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 완료
		sugangQnaDAO dao = new sugangQnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		long classNum = Long.parseLong(req.getParameter("classNum"));
		long q_num = Long.parseLong(req.getParameter("q_num"));

		try {
			sugangQnaDTO dto = new sugangQnaDTO();
			dto.setA_title(req.getParameter("a_title"));
			dto.setA_content(req.getParameter("a_content"));
			dto.setA_userId(info.getUserId());
			dto.setQ_num(q_num);
			
			dao.answer(dto, "O");

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/sugangqna/article.do?classNum=" + classNum + "&q_num=" + q_num);
	}


	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		sugangQnaDAO dao = new sugangQnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		long classNum = Long.parseLong(req.getParameter("classNum"));
		long q_num = Long.parseLong(req.getParameter("q_num"));

		try {
			sugangQnaDTO dto = new sugangQnaDTO();
			dto.setQ_title(req.getParameter("q_title"));
			dto.setQ_content(req.getParameter("q_content"));

			dto.setQ_userId(info.getUserId());
			dto.setQ_num(q_num);
			// dto.setClassNum(classNum);

			// q_title = ?, q_content = ? WHERE q_num = ?
			dao.updateQuestion(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/sugangqna/article.do?classNum=" + classNum + "&q_num=" + q_num);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		sugangQnaDAO dao = new sugangQnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		long classNum = Long.parseLong(req.getParameter("classNum"));
		long q_num = Long.parseLong(req.getParameter("q_num"));

		try {
			sugangQnaDTO dto = dao.findByQuestion(q_num);
			// q_num, q_title, q_userId, q_date, q_content, m.username

			if (dto == null) {
				resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
				return;
			}

			if (!info.getUserId().equals(dto.getQ_userId()) && !info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
				return;
			}

			dao.deleteQuestion(q_num, info.getUserId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
	}
	
	protected void deleteAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		sugangQnaDAO dao = new sugangQnaDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		long q_num = Long.parseLong(req.getParameter("q_num"));
		long classNum = Long.parseLong(req.getParameter("classNum"));
		
		try {
			
			if (!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/sugangqna/list_ok.do?classNum=" + classNum);
				return;
			}
			
			dao.deleteAnswer(q_num);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/sugangqna/article.do?classNum=" + classNum + "&q_num=" + q_num);
	}
}
