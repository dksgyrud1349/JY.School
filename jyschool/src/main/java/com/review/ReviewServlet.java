package com.review;

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

@WebServlet("/review/*")
public class ReviewServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("main.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);			
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
		
	}
		
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReviewDAO dao = new ReviewDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
			
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dataCount();
			
			// 전체페이지수 구하기
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			
			// 전체페이지보다 표시할 페이지가 큰경우
			if(total_page < current_page) {
				current_page = total_page;
			}
			
			// 데이터 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			
			List<ReviewDTO> list = dao.listGuest(offset, size);
			// 페이징
			for(ReviewDTO dto : list) {
				dto.setContent(dto.getContent().replaceAll(">", "&gt;"));
				dto.setContent(dto.getContent().replaceAll("<", "&lt;"));
				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			}
			
			
			String strUrl = cp + "/review/main.do";
			String paging = util.paging(current_page, total_page, strUrl);
			
			// 포워딩할 jsp에 전달할 속성(attribute).
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("dataCount", dataCount);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/review/guest.jsp");
	}
	
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 ReviewDAO dao = new ReviewDAO();
	      
	      HttpSession session = req.getSession();
	      SessionInfo info = (SessionInfo) session.getAttribute("member");
	      
	      String cp = req.getContextPath();

			if(info == null) { 
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			
	      try {
	         ReviewDTO dto = new ReviewDTO();
	        
	         dto.setUserId(info.getUserId());
	         dto.setContent(req.getParameter("content"));
	         dto.setSubject(req.getParameter("subject"));

	         dao.insertGuest(dto);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }

	      resp.sendRedirect(cp + "/review/main.do");
	      
	   }
	
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 방명록 삭제
		ReviewDAO dao = new ReviewDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		if(info == null) { // 로그인되지 않은 경우
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
		
		try {
			long num = Long.parseLong(req.getParameter("num"));

			dao.deleteGuest(num, info.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/review/main.do?page=" + page);
	}
	
}


