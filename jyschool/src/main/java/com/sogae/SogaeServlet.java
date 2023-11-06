package com.sogae;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/sogae/*")
public class SogaeServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		if (uri.indexOf("sogae.do") != -1) {
			hoesasogae(req, resp);
		} else if (uri.indexOf("login_ok.do") != -1) {
			hoesasogae22(req, resp);

		}
	}

	protected void hoesasogae(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼
		String path = "/WEB-INF/views/sogae/hoesa.jsp";
		forward(req, resp, path);
	}

	protected void hoesasogae22(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

}
