package com.q_bbs;

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


@WebServlet("/q_bbs/*")
public class Q_bbsServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");	// do?
			return;
		}
		
		// uri에 따른 작업 구분
				if (uri.indexOf("list.do") != -1) {
					list(req, resp);
				} else if (uri.indexOf("write.do") != -1) {
					writeForm(req, resp);
				} else if (uri.indexOf("write_ok.do") != -1) {
					writeSubmit(req, resp);
				} else if (uri.indexOf("article.do") != -1) {	// 글보기
					article(req, resp);
				} else if (uri.indexOf("update.do") != -1) {
					updateForm(req, resp);
				} else if (uri.indexOf("update_ok.do") != -1) {
					updateSubmit(req, resp);
				} else if (uri.indexOf("answer.do") != -1) {
					answerSubmit(req, resp);
				} else if (uri.indexOf("delete.do") != -1) {
					delete(req, resp);
				}
			}
				
			protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 게시물 리스트
				Q_bbsDAO dao = new Q_bbsDAO();
				MyUtil util = new MyUtil();
				
				try {
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
					
					List<Q_bbsDTO> list = null;
					if (kwd.length() == 0) {	//검색아님
						list = dao.listQuestion(offset, size);
					} else {					//검색
						list = dao.listQuestion(offset, size, kwd);
					}

					String query = "";
					if (kwd.length() != 0) {
						query = "kwd=" + URLEncoder.encode(kwd, "utf-8");
					}

					// 페이징 처리
					String cp = req.getContextPath();
					String listUrl = cp + "/q_bbs/list.do"; // ?size=\" + size	
					String articleUrl = cp + "/q_bbs/article.do?page=" + current_page;
					if (query.length() != 0) {
						listUrl += "?" + query;
						articleUrl += "&" + query;
					}

					String paging = util.paging(current_page, total_page, listUrl);

					// 포워딩할 JSP에 전달할 속성
					req.setAttribute("list", list);
					req.setAttribute("page", current_page);
					req.setAttribute("total_page", total_page);
					req.setAttribute("dataCount", dataCount);
					req.setAttribute("size", size);
					req.setAttribute("articleUrl", articleUrl);
					req.setAttribute("paging", paging);
					req.setAttribute("kwd", kwd);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				// JSP로 포워딩
				//forward(req, resp, "/WEB-INF/views/q_bbs/list.jsp");
				viewPage(req, resp, "q_bbs/list.jsp");
			}
			
			
			protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 글쓰기 폼
				req.setAttribute("mode", "write");
				//forward(req, resp, "/WEB-INF/views/q_bbs/write.jsp");
				viewPage(req, resp, "q_bbs/write.jsp");
			}
			
			protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 글 저장
				Q_bbsDAO dao = new Q_bbsDAO();
				
				HttpSession session = req.getSession();
				SessionInfo info = (SessionInfo) session.getAttribute("member");
				
				if (req.getMethod().equalsIgnoreCase("GET")) {
					// forward(req, resp, "/WEB-INF/views/q_bbs/list.do");
					viewPage(req, resp, "redirect:/q_bbs/list.do");
					return;
				}
				
				try {
					Q_bbsDTO dto = new Q_bbsDTO();
					
					// userId는 세션에 저장된 정보
					dto.setUserId(info.getUserId());
					
					// 파라미터
					dto.setSecret(Integer.parseInt(req.getParameter("secret")));
					dto.setTitle(req.getParameter("title"));
					dto.setContents(req.getParameter("contents"));
					dao.insertQuestion(dto);	// jsp에서 parameter(name)값을 받아 dao의 insertQuestion(dto)로 넘겨주기
					
				} catch (Exception e) {
					e.printStackTrace();	
				}
				
				// forward(req, resp, "/WEB-INF/views/q_bbs/list.do");
				viewPage(req, resp, "redirect:/q_bbs/list.do");
			}
			
			protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 글보기
				Q_bbsDAO dao = new Q_bbsDAO();
				
				HttpSession session = req.getSession();
				SessionInfo info = (SessionInfo) session.getAttribute("member");
				
				String page = req.getParameter("page");
				String query = "page=" + page; 
				
				try {
					long writeNum = Long.parseLong(req.getParameter("writeNum"));
					String kwd = req.getParameter("kwd");
					if (kwd == null) {
						kwd = "";
					}
					kwd = URLDecoder.decode(kwd, "utf-8");

					if (kwd.length() != 0) {
						query += "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
					}

					// 게시물 가져오기
					Q_bbsDTO dto = dao.findById(writeNum);
					if (dto == null) {
						//forward(req, resp, "/WEB-INF/views/q_bbs/list.do?" + query);
						viewPage(req, resp, "redirect:/q_bbs/list.do?" + query);
						return;
					}
					
					if( dto.getSecret() == 1) {		// 게시글쓴사람도 아니고 관리자도 아니면 글을 보게만든다.
						if(dto.getUserId().equals(info.getUserId()) && ! info.getUserId().equals("admin")) {
							//forward(req, resp, "/WEB-INF/views/q_bbs/list.do?" + query);
							viewPage(req, resp, "redirect:/q_bbs/list.do?" + query);
							return;
						}
					}
					
					dto.setContents(dto.getContents().replaceAll("\n", "<br>"));
					if(dto.getAnswer() != null) {
						dto.setAnswer(dto.getAnswer().replaceAll("\n", "<br>"));
					}

					// 이전글 다음글
					Q_bbsDTO prevDto = dao.findByPrev(dto.getWriteNum(), kwd);
					Q_bbsDTO nextDto = dao.findByNext(dto.getWriteNum(), kwd);

					// JSP로 전달할 속성
					req.setAttribute("dto", dto);
					req.setAttribute("page", page);
					req.setAttribute("query", query);
					req.setAttribute("prevDto", prevDto);
					req.setAttribute("nextDto", nextDto);

					// 포워딩
					// forward(req, resp, "/WEB-INF/views/q_bbs/article.jsp");
					viewPage(req, resp, "q_bbs/article.jsp");
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
				//forward(req, resp, "/WEB-INF/views/q_bbs/list.do?" + query);	//? 붙임
				viewPage(req, resp, "redirect:/q_bbs/list.do?" + query);
			}
			
			protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 수정 폼
				Q_bbsDAO dao = new Q_bbsDAO();
				HttpSession session = req.getSession();
				SessionInfo info = (SessionInfo) session.getAttribute("member");
				
				String page = req.getParameter("page");
				
				try {
					long writeNum = Long.parseLong(req.getParameter("writeNum"));
					Q_bbsDTO dto = dao.findById(writeNum);

					if (dto == null) {
						// forward(req, resp, "/WEB-INF/views/q_bbs/list.do?page=" + page);
						viewPage(req, resp, "redirect:/q_bbs/list.do?page=" + page);
						return;
					}

					// 게시물을 올린 사용자가 아니면
					if (! dto.getUserId().equals(info.getUserId())) {
						// forward(req, resp, "/WEB-INF/views/q_bbs/list.do?page=" + page);
						viewPage(req, resp, "redirect:/q_bbs/list.do?page=" + page);
						return;
					}

					req.setAttribute("dto", dto);
					req.setAttribute("page", page);
					req.setAttribute("mode", "update");
					
					// forward(req, resp, "/WEB-INF/views/q_bbs/write.jsp");
					viewPage(req, resp, "q_bbs/write.jsp");
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
				// forward(req, resp, "/WEB-INF/views/q_bbs/list.do?page=" + page);
				viewPage(req, resp, "redirect:/q_bbs/list.do?page=" + page);
			}
			
			protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 수정 완료
				Q_bbsDAO dao = new Q_bbsDAO();
				HttpSession session = req.getSession();
				SessionInfo info = (SessionInfo) session.getAttribute("member");
				
				if (req.getMethod().equalsIgnoreCase("GET")) {
					//forward(req, resp, "/WEB-INF/views/q_bbs/list.do");
					viewPage(req, resp, "redirect:/q_bbs/list.do");
					return;
				}
				
				String page = req.getParameter("page");
				try {
					Q_bbsDTO dto = new Q_bbsDTO();
					
					dto.setWriteNum(Long.parseLong(req.getParameter("writeNum")));
					dto.setSecret(Integer.parseInt(req.getParameter("secret")));
					dto.setTitle(req.getParameter("title"));
					dto.setContents(req.getParameter("contents"));

					dto.setUserId(info.getUserId());

					dao.updateQuestion(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// forward(req, resp, "/WEB-INF/views/q_bbs/list.do?page=" + page);
				viewPage(req, resp, "redirect:/q_bbs/list.do?page=" + page);
			}
			
			protected void answerSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 답변 완료
				Q_bbsDAO dao = new Q_bbsDAO();
				HttpSession session = req.getSession();
				SessionInfo info = (SessionInfo) session.getAttribute("member");
				
				if (req.getMethod().equalsIgnoreCase("GET")) {
					// forward(req, resp, "/WEB-INF/views/q_bbs/list.do");
					viewPage(req, resp, "redirect:/q_bbs/list.do");
					return;
				}
				
				if (! info.getUserId().equals("admin")) {
					// forward(req, resp, "/WEB-INF/views/q_bbs/list.do");
					viewPage(req, resp, "redirect:/q_bbs/list.do");
					return;
				}
				
				
				String page = req.getParameter("page");
				try {
					Q_bbsDTO dto = new Q_bbsDTO();
					
					dto.setWriteNum(Long.parseLong(req.getParameter("writeNum")));
					dto.setAnswer(req.getParameter("answer"));
					dto.setAnswerId(info.getUserId());

					dao.updateAnswer(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//forward(req, resp, "/WEB-INF/views/q_bbs/list.do?page=" + page);
				viewPage(req, resp, "redirect:/q_bbs/list.do?page=" + page );	//"&writeNum=" + writeNum
			}
			
			protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				// 삭제
				Q_bbsDAO dao = new Q_bbsDAO();
				HttpSession session = req.getSession();
				SessionInfo info = (SessionInfo) session.getAttribute("member");
				
				String page = req.getParameter("page");
				String query = "page=" + page;
				
				try {
					long writeNum = Long.parseLong(req.getParameter("writeNum"));
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
						Q_bbsDTO dto = new Q_bbsDTO();
						dto.setWriteNum(writeNum);
						dto.setAnswer("");
						dto.setAnswerId("");
						dao.updateAnswer(dto);
					} else if(mode.equals("question")) {
						// 질문 삭제
						dao.deleteQuestion(writeNum, info.getUserId());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				// forward(req, resp, "/WEB-INF/views/q_bbs/list.do?" + query);
				viewPage(req, resp, "redirect:/q_bbs/list.do?" + query);
			}
			
		}