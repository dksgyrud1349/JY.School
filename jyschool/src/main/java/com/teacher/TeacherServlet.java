package com.teacher;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.MemberDTO;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@WebServlet("/teacher/*")
@MultipartConfig
public class TeacherServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		
		if(uri.indexOf("list.do") != -1) {
			list(req,resp);
		}
		
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시글 리스트
		TeacherDAO dao = new TeacherDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page!= null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dateCount();
			
			int size = 8;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = ( current_page -1) * size;
			if(offset<0) offset = 0;
			
			List<MemberDTO> list = dao.listPhoto(offset, size);
			
			String listUrl = cp + "/teacher/list.do";
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
					
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		forward(req,resp, "/WEB-INF/views/teacher/list.jsp");
	}

}
