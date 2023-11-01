package com.reference;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/reference/*")
public class ReferenceServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (uri.indexOf("list.do") == -1 && info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "referenceroom";

		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("deleteList.do") != -1) {
			deleteList(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		}
	}

	// 게시물 리스트
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReferenceDAO dao = new ReferenceDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();

		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}

			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if (schType == null) {
				schType = "all";
				kwd = "";
			}
			if (req.getMethod().equalsIgnoreCase("GET")) {
				kwd = URLDecoder.decode(kwd, "utf-8");
			}

			// 한페이지 표시할 데이터 개수
			String pageSize = req.getParameter("size");
			int size = pageSize == null ? 10 : Integer.parseInt(pageSize);

			int dataCount, total_page;

			if (kwd.length() != 0) {
				dataCount = dao.dataCount(schType, kwd);
			} else {
				dataCount = dao.dataCount();
			}
			total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}

			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<ReferenceDTO> list;
			if (kwd.length() != 0) {
				list = dao.listNotice(offset, size, schType, kwd);
			} else {
				list = dao.listNotice(offset, size);
			}	


			String query = "";
			String listUrl;
			String articleUrl;

			listUrl = cp + "/reference/list.do?size=" + size;
			articleUrl = cp + "/reference/article.do?page=" + current_page + "&size=" + size;
			if (kwd.length() != 0) {
				query = "schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "utf-8");

				listUrl += "&" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			req.setAttribute("list", list);

			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("schType", schType);
			req.setAttribute("kwd", kwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/reference/list.jsp");
	}
		
	
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String size = req.getParameter("size");

		// admin만 작성 가능
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/reference/list.do?size=" + size);
			return;
		}

		req.setAttribute("mode", "write");
		req.setAttribute("size", size);
		forward(req, resp, "/WEB-INF/views/reference/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/reference/list.do");
			return;
		}
		
		// admin만 작성 가능
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/reference/list.do");
			return;
		}
		
		ReferenceDAO dao = new ReferenceDAO();
		
		String size = req.getParameter("size");
		try {
			ReferenceDTO dto = new ReferenceDTO();
			
			dto.setUserId(info.getUserId());
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));

			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] clientFiles = map.get("originalFilenames");
				dto.setSaveFiles(saveFiles);
				dto.setClientFiles(clientFiles);
			}

			dao.insertReference(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/reference/list.do?size=" + size);		
	}
	
	// 자료글 보기
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;
		
		ReferenceDAO dao = new ReferenceDAO();
		
		try {
			long writeNum = Long.parseLong(req.getParameter("writeNum"));

			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if (schType == null) {
				schType = "all";
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");

			if (kwd.length() != 0) {
				query += "&schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}
			
			// 자료글 훔쳐오기
			ReferenceDTO dto = dao.findById(writeNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/reference/list.do?" + query);
				return;
			}

			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));	
			
			ReferenceDTO prevDto = dao.findByPrev(dto.getWriteNum(), schType, kwd);
			ReferenceDTO nextDto = dao.findByNext(dto.getWriteNum(), schType, kwd);
			
			
			// 파일
			List<ReferenceDTO> listReferFile = dao.listReferFile(writeNum);
			
			
			req.setAttribute("dto", dto);
			req.setAttribute("prevDto", prevDto);
			req.setAttribute("nextDto", nextDto);
			req.setAttribute("listReferFile", listReferFile);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			req.setAttribute("size", size);

			forward(req, resp, "/WEB-INF/views/reference/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/reference/list.do?" + query);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/reference/list.do");
			return;
		}	
	
		ReferenceDAO dao = new ReferenceDAO();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");

		try {
			long writeNum = Long.parseLong(req.getParameter("writeNum"));

			ReferenceDTO dto = dao.findById(writeNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/reference/list.do?page=" + page + "&size=" + size);
				return;
			}		
			
			
			// 파일
			List<ReferenceDTO> listReferFile = dao.listReferFile(writeNum);
			
			
			req.setAttribute("dto", dto);
			req.setAttribute("listReferFile", listReferFile);
			req.setAttribute("page", page);
			req.setAttribute("size", size);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/reference/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/reference/list.do?page=" + page + "&size=" + size);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/reference/list.do");
			return;
		}
		
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/reference/list.do");
			return;
		}	
	
		ReferenceDAO dao = new ReferenceDAO();
	
		String page = req.getParameter("page");
		String size = req.getParameter("size");

		try {
			ReferenceDTO dto = new ReferenceDTO();
			
			dto.setWriteNum(Long.parseLong(req.getParameter("writeNum")));
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));

			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFile");
				String[] clientFiles = map.get("clientFile");
				dto.setSaveFiles(saveFiles);
				dto.setClientFiles(clientFiles);
			}

			dao.updateReference(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/reference/list.do?page=" + page + "&size=" + size);
	}

	// 삭제
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/reference/list.do");
			return;
		}
	
		ReferenceDAO dao = new ReferenceDAO();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;

		try {
			long writeNum = Long.parseLong(req.getParameter("writeNum"));
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if (schType == null) {
				schType = "all";
				kwd = "";
			}
			kwd = URLDecoder.decode(kwd, "utf-8");

			if (kwd.length() != 0) {
				query += "&schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}

			ReferenceDTO dto = dao.findById(writeNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/reference/list.do?" + query);
				return;
			}
			
			
			// 파일삭제
			List<ReferenceDTO> listFile = dao.listReferFile(writeNum);
			for (ReferenceDTO vo : listFile) {
				FileManager.doFiledelete(pathname, vo.getSaveFile());
			}
			dao.deleteReferFile("all", writeNum);
			

			// 게시글 삭제
			dao.deleteReference(writeNum);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/reference/list.do?" + query);
	}
	
	protected void deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/reference/list.do");
			return;
		}	

		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "size=" + size + "&page=" + page;

		String schType = req.getParameter("schType");
		String kwd = req.getParameter("kwd");

		try {
			if (kwd != null && kwd.length() != 0) {
				query += "&schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "UTF-8");
			}

			String[] nn = req.getParameterValues("writeNums");
			long writeNums[] = null;
			writeNums = new long[nn.length];
			for (int i = 0; i < nn.length; i++) {
				writeNums[i] = Long.parseLong(nn[i]);
			}
	
			ReferenceDAO dao = new ReferenceDAO();
			
			
			// 파일 삭제
			for (int i = 0; i < writeNums.length; i++) {
				List<ReferenceDTO> listFile = dao.listReferFile(writeNums[i]);
				for (ReferenceDTO vo : listFile) {
					FileManager.doFiledelete(pathname, vo.getSaveFile());
				}
				dao.deleteReferFile("all", writeNums[i]);
			}
			
			
			// 게시글 삭제
			dao.deleteReferenceList(writeNums);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/reference/list.do?" + query);
	}

	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정에서 파일만 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/reference/list.do");
			return;
		}
		
		ReferenceDAO dao = new ReferenceDAO();

		String page = req.getParameter("page");
		String size = req.getParameter("size");

		try {
			long writeNum = Long.parseLong(req.getParameter("writeNum"));
			long referfileNum = Long.parseLong(req.getParameter("referfileNum"));
			ReferenceDTO dto = dao.findByFileId(referfileNum);
			if (dto != null) {
				// 파일삭제
				FileManager.doFiledelete(pathname, dto.getSaveFile());
				
				// 테이블 파일 정보 삭제
				dao.deleteReferFile("one", referfileNum);
			}

			// 다시 수정 화면으로
			resp.sendRedirect(cp + "/reference/update.do?num=" + writeNum + "&page=" + page + "&size=" + size);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/referenece/list.do?page=" + page + "&size=" + size);
	}	
	
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 다운로드
		ReferenceDAO dao = new ReferenceDAO();
		boolean b = false;

		try {
			long referfileNum = Long.parseLong(req.getParameter("referfileNum"));

			ReferenceDTO dto = dao.findByFileId(referfileNum);
			if (dto != null) {
				b = FileManager.doFiledownload(dto.getSaveFile(), dto.getClientFile(), pathname, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일다운로드가 실패 했습니다.');history.back();</script>");
		}
	}
	
	
	
}
