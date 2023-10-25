package com.bbs;

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
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/bbs/*")
public class BoardServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// post 방식이라 인코딩 해야하지만 MyServlet에 이임 ㅣㅇㅆ음
		// 세션 정보(로그인 정보)
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(info == null) {
			// 로그인이 안된 경우
			forward(req,resp,"/WEB-INF/views/member/login.jsp");
			return;
		}
		
		// uri에 따른 작업 구분
		String uri = req.getRequestURI();
		if(uri.indexOf("list.do") != -1) {
			list(req,resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req,resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req,resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req,resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req,resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req,resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req,resp);
		}
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시글 리스트
		
		BoardDAO dao = new BoardDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		try {
			// 페이지 번호
			String page = req.getParameter("page");
			int current_page =1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if(schType == null) {
				schType = "all";
				kwd = "";
			}
			
			// GET 방식이면 디코딩
			if(req.getMethod().equalsIgnoreCase("GET")) {
				kwd = URLDecoder.decode(kwd, "utf-8");
			}
			
			// 전체 데이터 개수
			int dataCount;
			
			if(kwd.length()==0) {
				// 검색이 아닌 경우
				dataCount = dao.dataCount();
			}else {
				// 검색인 경우
				dataCount = dao.dataCount(schType,kwd);
			}
			
			// 전체 페이지 수
			int size = 10; // 한페이지에 출력할 목록 개수
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) { // 나는 보고 있는데 누가 지워서 실제는 없으면 성립
				current_page = total_page;
			}
			
			// 게시글 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<BoardDTO> list = null;
			
			if(kwd.length() == 0) {
				// 검색이 아닐 때
				list = dao.listBoard(offset, size);
			}else {
				// 검색일 때
				list = dao.listBoard(offset, size, schType, kwd);
			}
			
			String query = "";
			if(kwd.length() != 0) { // 검색 상태이면
				query = "schType=" + schType + "&kwd="
						+URLEncoder.encode(kwd, "utf-8");
			}
			
			// 페이징 처리
			String listUrl = cp + "/bbs/list.do"; // 글리스트 주소
			String articleUrl = cp + "/bbs/article.do?page=" + current_page; // 글보기 주소
			if(query.length() !=0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 속성(attribute)
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("total_page", total_page);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("schType", schType);
			req.setAttribute("kwd", kwd);
		} catch (Exception e) {
		}
			
		forward(req,resp,"/WEB-INF/views/bbs/list.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		req.setAttribute("mode", "write");
		forward(req,resp,"/WEB-INF/views/bbs/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글등록
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) { // 침입방지
			resp.sendRedirect(cp + "/");
			return;
		}
		
		BoardDAO dao = new BoardDAO();
		
		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			BoardDTO dto = new BoardDTO();
			dto.setUserId(info.getUserId());
			dto.setUserName(req.getParameter("username"));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dao.insertBoard(dto);
			
		} catch (Exception e) {
		}
		resp.sendRedirect(cp+"/bbs/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		BoardDAO dao = new BoardDAO();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			long num = Long.parseLong(req.getParameter("num"));
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			
			if(schType == null) {
				schType = "all";
				kwd = "";
			}
			
			kwd = URLDecoder.decode(kwd, "utf-8");
			
			if(kwd.length()!=0) {
				query += "&schType=" + schType + "&kwd="
						+ URLEncoder.encode(kwd,"utf-8");
			}
			
			dao.updateHitcount(num);
			
			BoardDTO dto = dao.findById(num);
			if(dto == null) {
				// 게시글이 없으면 다시 리스트로
				resp.sendRedirect(cp+"/bbs/list.do?"+query);
				return;
			}
			// content의 엔터를 <br>로
						dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
						// 이전글, 다음글
						BoardDTO prevDto = dao.findByPrevBoard(num, schType, kwd);
						BoardDTO nextDto = dao.findByNextBoard(num, schType, kwd);
						
						// 포워딩할 JSP에 전달할 속성
						req.setAttribute("dto", dto);
						req.setAttribute("prevDto", prevDto);
						req.setAttribute("nextDto", nextDto);
						req.setAttribute("page", page);
						req.setAttribute("query", query);
						
						// 포워딩
						forward(req, resp, "/WEB-INF/views/bbs/article.jsp");
						return;
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					// 예외가 발생하면 list.do로
					resp.sendRedirect(cp+"/bbs/list.do?"+query);
		
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 수정 폼
		BoardDAO dao = new BoardDAO();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			long num = Long.parseLong(req.getParameter("num"));
			
			// 테이블에서 해당 게시글 가져오기
			BoardDTO dto = dao.findById(num);
			
			// 게시글이 없으면
			if(dto == null) {
				resp.sendRedirect(cp + "/bbs/list.do?page=" +page);
				return;
			}
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			if(!dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/bbs/list.do?page=" +page);
				return;
			}
			
			// JSP에 넘길 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			forward(req,resp, "/WEB-INF/views/bbs/write.jsp");
			return;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/bbs/list.do?page="+page);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 수정 완료
		BoardDAO dao = new BoardDAO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			BoardDTO dto = new BoardDTO();
			dto.setNum(Long.parseLong(req.getParameter("num")));
			dto.setUserName(req.getParameter("username"));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dao.updateBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/bbs/list.do?page="+page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 삭제
		BoardDAO dao = new BoardDAO();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			long num = Long.parseLong(req.getParameter("num"));
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			
			if(schType == null) {
				schType = "all";
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");
			
			if(kwd.length() != 0) {
				query += "&schType="+schType+"&kwd="
							+URLEncoder.encode(kwd,"utf-8");
			}
			
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			BoardDTO dto = dao.findById(num);
			if(!dto.getUserId().equals(info.getUserId())|!dto.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/bbs/list.do?page=" +page);
				return;
			}
			
			dao.deleteBoard(num);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/bbs/list.do?"+query);
	}

	protected void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 리스트
	}
	
}
