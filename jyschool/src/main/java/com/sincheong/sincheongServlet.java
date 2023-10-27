package com.sincheong;

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

@WebServlet("/sugang/*")
public class sincheongServlet extends MyServlet {

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
		
		if(uri.indexOf("list.do") != -1) {
			listForm(req, resp);
		} 
	}
	
	protected void listForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 강좌 리스트
		LectureDAO dao = new LectureDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 전체 데이터 개수
			int dataCount = dao.dataCount();
			
			// 전체 페이지 수
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			
			if(offset < 0) {
				offset = 0;
			}
			
			List<LectureDTO> list = dao.listLecture(offset, size);
			
			// 페이징 처리
			String listUrl = cp + "sugang/list.do";
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("paging", paging);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		//JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/sugang/list.jsp");
	}
}
