package com.sincheong;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/sugang/*")
@MultipartConfig
public class sincheongServlet extends MyServlet {

	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		// 이미지를 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "lecturephoto";

		if (uri.indexOf("list.do") != -1) {
			listForm(req, resp);

		} else if (uri.indexOf("list_ok.do") != -1) {
			listSubmit(req, resp);

		} else if (uri.indexOf("insert_ok.do") != -1) {
			// 강좌 저장
			lectureSubmit(req, resp);

		} else if (uri.indexOf("insert.do") != -1) {
			// 강좌 등록 폼
			insertLectureForm(req, resp);
			
		} else if(uri.indexOf("update.do") != -1) {
			// 강좌 수정
			updateLectureForm(req, resp);
			
		} else if(uri.indexOf("update_ok.do") != -1) {
			// 강좌 수정 완료
			updateLectureSubmit(req, resp);
			
		} else if(uri.indexOf("delete.do") != -1) {
			// 강좌 삭제
			deleteLectureForm(req, resp);
			
		} else if(uri.indexOf("delete_ok.do") != -1) {
			// 강좌 삭제
			deleteLectureSubmit(req, resp);
			
		} else if(uri.indexOf("sincheong.do") != -1) {
			// 수강신청
			sincheongForm(req, resp);
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

			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if(schType == null) {
				schType = "all";
				kwd = "";
			}
			
			// GET 방식인 경우 디코딩
			if(req.getMethod().equalsIgnoreCase("GET")) {
				kwd = URLDecoder.decode(kwd, "utf-8");
			}

			// 전체 데이터 개수
			int dataCount;
			if(kwd.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(schType, kwd);
			}

			// 전체 페이지 수
			int size = 10;
			int total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if (offset < 0) {
				offset = 0;
			}
			List<LectureDTO> list = null;
			if(kwd.length() == 0) {
				list = dao.listLecture(offset, size);
			} else {
				list = dao.listLecture(offset, size, schType, kwd);
			}
			
			String query = "";
			if(kwd.length() != 0) {
				query = "schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "utf-8");
			}
			
			List<LectureDTO> teacherList = dao.teacherList();

			// 페이징 처리
			String listUrl = cp + "/sugang/list.do";
			if(query.length() != 0) {
				listUrl += "?" + query;
			}
			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("teacherList", teacherList);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("paging", paging);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/sugang/list.jsp");
	}

	// ㄱㅇㅇ
	protected void listSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		sincheongDAO dao = new sincheongDAO();
		LectureDAO dao2 = new LectureDAO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");

		try {

			long classNum = Long.parseLong(req.getParameter("classNum"));
			List<LectureDTO> teacherList = dao2.teacherList();

			LectureDTO dto = dao.pickLecture(classNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/sugang/list.do?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("teacherList", teacherList);

			forward(req, resp, "/WEB-INF/views/sugang/sincheong.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/sugang/list.do?page=" + page);

	}

	protected void insertLectureForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 강좌 등록 폼
		String page = req.getParameter("page");

		req.setAttribute("page", page);
		forward(req, resp, "/WEB-INF/views/sugang/insertLecture.jsp");
	}

	protected void lectureSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	      // 게시물 등록
	      LectureDAO dao = new LectureDAO();
	      
	      HttpSession session = req.getSession();
	      SessionInfo info = (SessionInfo)session.getAttribute("member");
	      
	      String cp = req.getContextPath();
	      
	      if(req.getMethod().equalsIgnoreCase("GET")){
	         resp.sendRedirect(cp + "/sugang/list.do");
	         return;
	      }
	      
	      try {
	         String page = req.getParameter("page");
	         
	         if(dao.findByTeacher(info.getUserId()) != null) {
	            // 강사이면
	            LectureDTO dto = new LectureDTO();
	            
	            dto.setUserId(info.getUserId());
	            dto.setUsername(info.getUserName());
	            
	            // 파라미터
	            dto.setClassNum(Long.parseLong(req.getParameter("classNum")));
	            dto.setClassName(req.getParameter("className"));
	            dto.setClassComment(req.getParameter("classComment"));
	            dto.setPrice(Integer.parseInt(req.getParameter("price")));
	            dto.setClassDegree(req.getParameter("classDegree"));
	            
	            Part p1 = req.getPart("selectFile1");
	            Part p2 = req.getPart("selectFile2");
	            
	            Map<String, String> map1 = doFileUpload(p1, pathname);
	            Map<String, String> map2 = doFileUpload(p2, pathname);
 	            
	            if(map1 != null) {
	            	String saveFilename = map1.get("saveFilename");
	            	dto.setImageFilename1(saveFilename);
	            }
	            
	            if(map2 != null) {
	            	String saveFilename = map2.get("saveFilename");
	            	dto.setImageFilename2(saveFilename);
	            }
	            
	            dao.insertLecture(dto);
	            
	         } else {
	            resp.sendRedirect(cp + "/sugang/list.do?page=" + page);
	            return;
	         }
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	      resp.sendRedirect(cp + "/sugang/list.do");
	   }

	protected Map<String, String> doFileUpload(Part p, String pathname) throws ServletException, IOException {
		Map<String, String> map = null;

		try {
			File f = new File(pathname);
			if (!f.exists()) { // 폴더가 존재하지 않으면
				f.mkdirs();
			}

			String originalFilename = getOriginalFilename(p);
			if (originalFilename == null || originalFilename.length() == 0)
				return null;

			String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
			String saveFilename = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
			saveFilename += System.nanoTime();
			saveFilename += fileExt;

			String fullpath = pathname + File.separator + saveFilename;
			p.write(fullpath);

			map = new HashMap<>();
			map.put("originalFilename", originalFilename);
			map.put("saveFilename", saveFilename);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	private String getOriginalFilename(Part p) {
		try {
			for (String s : p.getHeader("content-disposition").split(";")) {
				if (s.trim().startsWith("filename")) {
					return s.substring(s.indexOf("=") + 1).trim().replace("\"", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	// 수정
	   protected void updateLectureForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   LectureDAO dao = new LectureDAO();
		   String cp = req.getContextPath();
		 
		   String page = req.getParameter("page");
		   long classNum = Long.parseLong(req.getParameter("classNum"));
		   
		   HttpSession session = req.getSession();
		   SessionInfo info = (SessionInfo)session.getAttribute("member");
		   List<LectureDTO> teacherList = dao.teacherList();
		   
		   /*
		   if(req.getMethod().equalsIgnoreCase("GET")){
			      resp.sendRedirect(cp + "/sugang/list.do");
			      return;
			   }
			*/
		   
		   try {
			
			   if(dao.findByTeacher(info.getUserId()) != null) {
				   // 게시물을 작성한 강사님 id 가져옴
				   LectureDTO dto = dao.findById(classNum);
				   
				   req.setAttribute("dto", dto);
				   req.setAttribute("classNum", classNum);
				   req.setAttribute("page", page);
				   req.setAttribute("mode", "update");
				   req.setAttribute("teacherList", teacherList);
				 
			   }else{
				   resp.sendRedirect(cp + "/sugang/list.do?page=" + page);
			   }
			   forward(req, resp, "/WEB-INF/views/sugang/insertLecture.jsp?classNum="+classNum);
	           return;
			   
		} catch (Exception e) {
			e.printStackTrace();
		}
		      resp.sendRedirect(cp + "/sugang/list.do?page="+page);
	   }
	   
	   protected void updateLectureSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   LectureDAO dao = new LectureDAO();
		   String cp = req.getContextPath();
		   String page = req.getParameter("page");
		   
		   try {
			LectureDTO dto = new LectureDTO();
			
			dto.setClassNum(Long.parseLong(req.getParameter("classNum")));
			dto.setUserId(req.getParameter("userId"));
			dto.setClassName(req.getParameter("className"));
			dto.setPrice(Integer.parseInt(req.getParameter("price")));
			dto.setClassComment(req.getParameter("classComment"));
			dto.setClassDegree(req.getParameter("classDegree"));
			dto.setImageFilename1(req.getParameter("imageFilename1"));
			dto.setImageFilename2(req.getParameter("imageFilename2"));
			
			dao.updateLecture(dto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		   
		   resp.sendRedirect(cp + "/sugang/list.do?page="+page);
	   }
	   
	   protected void deleteLectureForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   LectureDAO dao = new LectureDAO();
		   String cp = req.getContextPath();
		   
		   HttpSession session = req.getSession();
		   SessionInfo info = (SessionInfo)session.getAttribute("member");
		   
		   String page = req.getParameter("page");
		   List<LectureDTO> teacherList = dao.teacherList();
		   
		   try {
			   long classNum = Long.parseLong(req.getParameter("classNum"));
			   
			   if(dao.findByTeacher(info.getUserId()) != null) {
				   // 게시물을 작성한 강사님 id 가져옴
				   LectureDTO dto = dao.findById(classNum);
				   
				   req.setAttribute("dto", dto);
				   req.setAttribute("page", page);
				   req.setAttribute("teacherList", teacherList);
			   
			   } else {
				   resp.sendRedirect(cp + "/sugang/list.do?page=" + page);
			   }
			   
			   forward(req, resp, "/WEB-INF/views/sugang/sincheong.jsp?classNum="+classNum);
	           return;
	           
		} catch (Exception e) {
			e.printStackTrace();
		}
		   resp.sendRedirect(cp + "/sugang/list.do?page="+page);
	   }
	   
	   protected void deleteLectureSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   LectureDAO dao = new LectureDAO();
		   String cp = req.getContextPath();
		   String page = req.getParameter("page");
		   
		   try {
			
			   long classNum = Long.parseLong(req.getParameter("classNum"));

			   dao.deleteLecture(classNum);
			   
		} catch (Exception e) {
			e.printStackTrace();
		}
		   
		   resp.sendRedirect(cp + "/sugang/list.do?page="+page);
	   }
	   
	   protected void sincheongForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   sincheongDAO dao2 = new sincheongDAO();
		   LectureDAO dao = new LectureDAO();
		   String cp = req.getContextPath();
		   // long classNum2 = Long.parseLong(req.getParameter("classNum2"));	// 강좌번호
		   String rtnCode = "";
		   
		   HttpSession session = req.getSession();
		   SessionInfo info = (SessionInfo)session.getAttribute("member");
		   
		   try {
			   String page      = req.getParameter("page");
			   String classNum2 = req.getParameter("classNum2");
			   // 강사와 관리자가 아니면
			   
			   if(dao.findByTeacher2(info.getUserId()) == null && !"admin".equals(info.getUserId())) {
				   sincheongDTO dto2 = new sincheongDTO();

				   dto2.setUserId(info.getUserId());
				   dto2.setClassNum2(Long.parseLong(classNum2));
				   
				   dao2.insertLecture(dto2);
				   rtnCode = "S";
			   } else {
				   resp.sendRedirect(cp + "/sugang/list.do?page="+page);
				   return;
			   } 
			} catch (Exception e) {
				rtnCode = "F";
				e.printStackTrace();
			}
			   resp.sendRedirect(cp + "/main.do?rtnCode=" + rtnCode);
		   }
}
