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
public class sugangQnaServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		// uri에 따른 구분
		if(uri.indexOf("list.do") != -1) {
			lectureList(req, resp);
		}
	}
	
	protected void lectureList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수강신청한 강좌 리스트
		sugangQnaDAO dao = new sugangQnaDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
				
		if(info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 데이터 개수
			int dataCount = dao.dataCount(info.getUserId());
			
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) {
				offset = 0;
			}
			
			List<sugangQnaDTO> list = dao.listsugangQna(offset, size, info.getUserId());
			
			// 페이징 처리
			String listUrl = cp + "/sugangqna/list.do";
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);  // 로그인한 아이디가 수강신청한 강좌
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/sugangqna/list.jsp");
	}

}
