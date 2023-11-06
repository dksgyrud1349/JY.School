package com.beforeqna;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.sincheong.LectureDAO;
import com.sincheong.LectureDTO;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/beforeqna/*")
public class BeforeQnaServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri = req.getRequestURI();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			viewPage(req, resp, "redirect:/member/login.do");
			return;
		}
		
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("answer.do") != -1) {
			answerSubmit(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		}
	}
	
	// 게시물 리스트
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BeforeQnaDAO dao = new BeforeQnaDAO();
		MyUtil util = new MyUtil();		
		
		try {
			String classNum2 = req.getParameter("classNum2");
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			
			// 검색
			String kwd = req.getParameter("kwd");
			if (kwd == null) {
				kwd = "";
			}

			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				kwd = URLDecoder.decode(kwd, "utf-8");
			}

			// 전체 데이터 개수
			int dataCount;
			if (kwd.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(kwd);
			}
			
			// 전체 페이지 수
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<BeforeQnaDTO> list = null;
			if (kwd.length() == 0) {
				list = dao.listQuestion(offset, size);
			} else {
				list = dao.listQuestion(offset, size, kwd);
			}

			String query = "";
			if (kwd.length() != 0) {
				query = "kwd=" + URLEncoder.encode(kwd, "utf-8");
			}			

			String cp = req.getContextPath();
			String listUrl = cp + "/beforeqna/list.do?classNum2="+classNum2;
			String articleUrl = cp + "/beforeqna/article.do?classNum2="+classNum2+"&page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			LectureDAO lectureDAO = new LectureDAO();
			
			LectureDTO dto = lectureDAO.findById(Long.parseLong(classNum2));

			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("kwd", kwd);
			
			req.setAttribute("lectureSubject", dto.getClassName());
			req.setAttribute("classNum2", classNum2);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		viewPage(req, resp, "beforeqna/list.jsp");	
	}
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String classNum2 = req.getParameter("classNum2");
		
		req.setAttribute("classNum2", classNum2);
		req.setAttribute("mode", "write");
		viewPage(req, resp, "beforeqna/write.jsp");		
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BeforeQnaDAO dao = new BeforeQnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String classNum2 = req.getParameter("classNum2");
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2);
			return;
		}
		
		try {
			BeforeQnaDTO dto = new BeforeQnaDTO();

			// userId는 세션에 저장된 정보
			dto.setUserId(info.getUserId());
			
			// 파라미터
			dto.setQcontent(req.getParameter("qcontent"));		
			dto.setClassNum2(Long.parseLong(classNum2));			
			dto.setTitle(req.getParameter("title"));
			dto.setSecret(Integer.parseInt(req.getParameter("secret")));

			dao.insertQuestion(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2);
	}		
	
	// 글보기 
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BeforeQnaDAO dao = new BeforeQnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String classNum2 = req.getParameter("classNum2");
		String page = req.getParameter("page");
		
		String query = "classNum2="+classNum2+"&page=" + page;
		
		try {
			long qnum = Long.parseLong(req.getParameter("qnum"));
			String kwd = req.getParameter("kwd");
			if (kwd == null) {
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");

			if (kwd.length() != 0) {
				query += "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}

			// 게시물 가져오기
			BeforeQnaDTO dto = dao.findById(qnum);
			if (dto == null) {
				viewPage(req, resp, "redirect:/beforeqna/list.do?"+ query);
				return;
			}
			
			if( dto.getSecret() == 1 ) {
				if(dto.getUserId().equals(info.getUserId()) && ! info.getUserId().equals("admin")) {
					viewPage(req, resp, "redirect:/beforeqna/list.do?" + query);
					return;					
				}
			}
			
			dto.setQcontent(dto.getQcontent().replaceAll("\n", "<br>"));
			if(dto.getAcontent() != null) {
				dto.setAcontent(dto.getAcontent().replaceAll("\n", "<br>"));
			}		
			
			BeforeQnaDTO prevDto = dao.findByPrev(dto.getQnum(), kwd);
			BeforeQnaDTO nextDto = dao.findByNext(dto.getQnum(), kwd);

			LectureDAO lectureDAO = new LectureDAO();
			
			LectureDTO lectureDto = lectureDAO.findById(Long.parseLong(classNum2));
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			
			req.setAttribute("prevDto", prevDto);
			req.setAttribute("nextDto", nextDto);
			
			req.setAttribute("lectureSubject", lectureDto.getClassName());
			req.setAttribute("classNum2", classNum2);
			
			// 포워딩
			viewPage(req, resp, "beforeqna/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		viewPage(req, resp, "redirect:/beforeqna/list.do?" +query);			

	}		

	// 수정 폼
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BeforeQnaDAO dao = new BeforeQnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String page = req.getParameter("page");
		String classNum2 = req.getParameter("classNum2");
		
		try {
			long qnum = Long.parseLong(req.getParameter("qnum"));
			BeforeQnaDTO dto = dao.findById(qnum);

			if (dto == null) {
				viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2+"&page=" + page);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (! dto.getUserId().equals(info.getUserId())) {
				viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2+"&page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("classNum2", classNum2);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");

			viewPage(req, resp, "beforeqna/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2+"&page=" + page);
	}
	
	// 수정 완료
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BeforeQnaDAO dao = new BeforeQnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String classNum2 = req.getParameter("classNum2");
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2);
			return;
		}
		
		String page = req.getParameter("page");
		try {
			BeforeQnaDTO dto = new BeforeQnaDTO();
			
			dto.setQnum(Long.parseLong(req.getParameter("qnum")));
			dto.setSecret(Integer.parseInt(req.getParameter("secret")));
			dto.setTitle(req.getParameter("title"));
			dto.setQcontent(req.getParameter("qcontent"));

			dto.setUserId(info.getUserId());

			dao.updateQuestion(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2+"&page="+page);		
	}
	
	
	protected void answerSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 답변 완료
		BeforeQnaDAO dao = new BeforeQnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String classNum2 = req.getParameter("classNum2");
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2);
			return;
		}

		if (! info.getUserId().equals("admin")) {
			viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2);
			return;
		}
		
		String page = req.getParameter("page");
		try {
			BeforeQnaDTO dto = new BeforeQnaDTO();
			
			dto.setQnum(Long.parseLong(req.getParameter("qnum")));
			dto.setAcontent(req.getParameter("acontent"));
			dto.setAuserId(info.getUserId());
			dao.updateAnswer(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		viewPage(req, resp, "redirect:/beforeqna/list.do?classNum2="+classNum2+"&page="+page);
	}		
	
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		BeforeQnaDAO dao = new BeforeQnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String classNum2 = req.getParameter("classNum2");
		
		String page = req.getParameter("page");
		String query = "classNum2="+classNum2+"&page=" + page;

		try {
			long qnum = Long.parseLong(req.getParameter("qnum"));
			String mode = req.getParameter("mode");
			
			String kwd = req.getParameter("kwd");
			if (kwd == null) {
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");

			if (kwd.length() != 0) {
				query += "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}
			
			if(mode.equals("answer") && info.getUserId().equals("admin")) {
				// 답변 삭제
				BeforeQnaDTO dto = new BeforeQnaDTO();
				dto.setQnum(qnum);
				dto.setAcontent("");
				dto.setAuserId("");

				dao.updateAnswer(dto);

			} else if(mode.equals("question")) {
				// 질문 삭제
				dao.deleteQuestion(qnum, info.getUserId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		viewPage(req, resp, "redirect:/beforeqna/list.do?" + query);
	}		
	
}
