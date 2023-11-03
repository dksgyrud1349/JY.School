package com.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected static final String VIEW_PREFIX = "/WEB-INF/views/";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		execute(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		// 포워딩을 위한 메소드
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void viewPage(HttpServletRequest req, HttpServletResponse resp, String uri) throws ServletException, IOException {
		// 리다이렉트 또는 포워딩
		if(uri.startsWith("redirect:")) {
			// uri가 "redirect:/bbs/list.do" 인 경우 => "cp/bbs/list.do" 로 리다이렉트
			String cp = req.getContextPath();
			uri = cp + uri.substring("redirect:".length());
			resp.sendRedirect(uri);
		} else {
			// uri가 "bbs/list.jsp" 인 경우 => "/WEB-INF/views/bbs/list.jsp" 로 포워딩
			RequestDispatcher rd = req.getRequestDispatcher(VIEW_PREFIX + uri);
			rd.forward(req, resp);
		}
	}
	// JSON으로 응답
	protected void respJson(HttpServletRequest req, HttpServletResponse resp, String jsonStr) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().write(jsonStr);
	}
	
	protected abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
