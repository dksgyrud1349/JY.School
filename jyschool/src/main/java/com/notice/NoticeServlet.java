package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.DBUtil;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;
/*
 * 리스트에서 sb.append사용 / 뜻? : 
 * DB에서 테이블만들고 컬럼 부족할시 // DB에 없는것을 임시로 만들고 DB에 있는컬럼에다 뿌려주면 된다.
 * dao.은 dao에서 찾기
 * findById는 어디서 값을받고 저장하는 역할?
 * [write,update]submit 에서 Map을통해 파일을 업로드 해야한다.
 * (resp.sendRedirect) : 가장마지막에 페이지 들어가기위해 항상 쓴다. writeForm관리자글쓰기는 if문을 통해 사용
 * dto.setnum(rs.getLong("num")); dto에서 num값을 가져와서. 넘겨준다?
 * DB에 넣고빼고 관련있는것은 DAO만
 * (req.getParameter("refernum") // jsp : name값이 refernum
 * list.jsp --> <option value="userName" ${schType=="userName"?"selected":"" }>작성자</option>
 	userName값이 DB에 없더라도 option value="" 값을 줬으므로 "작성자" 쪽 내용은 <td>${dto.userName}</td>로 선언하고 dto에서 게터세터 한다
 * dto선언할때 맨 앞자리에 대문자를 쓰지말자	
 * (Servlet-writeForm = write.jsp로 이동 // Servlet-article = article.jsp로 이동)
    req.setAttribute() 후에 forward()로 write.jsp, article.jsp로 이동한다.
 * 에러찾을때 console외에 F12번도 활용하자
 * DAO의 insert를 잘못만졌더니 --> 글올리기-등록하기가 눌리지않았다.
 * getAttribute: 속성 가져올떄 // setAttribute: 속성 설정할때
 * not null = insert 할때 꼭 있어야함
 * */

@MultipartConfig
@WebServlet("/notice/*")
public class NoticeServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathname;
	
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();	//요청의 URI를 문자열로 반환
		String cp = req.getContextPath();	//웹 애플리케이션의 컨텍스트 경로를 반환
		
		HttpSession session = req.getSession();	//현재 요청에 대한 세션 객체를 가져옵니다
		SessionInfo info = (SessionInfo) session.getAttribute("member");  //세션에서 "member"라는 이름으로 저장된 객체를 가져와 SessionInfo 타입으로 캐스팅
		
		if(uri.indexOf("list.do") == -1 && info == null) { // list가아니면 로그인으로 간다?
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
		
		// 파일을 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "notice";

		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {		//req:클라이언트가 네이버에 보낸거 resp:네이버가 회원가입 등 화면뿌려준것
			list(req, resp);	
		} else if (uri.indexOf("write.do") != -1) { //get:DAO등 데이터를 가져오고싶다. set:DAO등 데이터를 보내준다.
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if (uri.indexOf("deleteList.do") != -1) {
			deleteList(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		NoticeDAO dao = new NoticeDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		try {	// 페이지 번호
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String schType = req.getParameter("schType");
			String kwd = req.getParameter("kwd");
			if (schType == null) {
				schType = "all";
				kwd = "";
			}
			
			// GET 방식이면 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				kwd = URLDecoder.decode(kwd, "utf-8");
			}
			
			// 한페이지 표시할 데이터 개수
			String pageSize = req.getParameter("size");			//HTTP 요청에서 "size" 파라미터 값을 가져옵니다.
			int size = pageSize == null ? 10 : Integer.parseInt(pageSize);	//가져온 값은 pageSize라는 문자열 변수에 저장후/ (Integer)정수로 변환
			// 전체 데이터 개수, 페이지											--만약 "size" 파라미터가 존재하지 않거나 문제가 발생시,기본값=10
			int dataCount, total_page;
			
			if (kwd.length() != 0) {	// 검색이 맞는경우
				dataCount = dao.dataCount(schType, kwd);
			} else {
				dataCount = dao.dataCount();
			}
			total_page = util.pageCount(dataCount, size);
			
			if (current_page > total_page) {	// 현재페이지>전체페이지인경우 // 누군가 순간 삭제시 현재페이지=전체페이지로 수정한다.
				current_page = total_page;
			}
			
			// 게시글 가져오기
			int offset = (current_page -1) * size;
			if(offset <0) offset = 0;
			
			List<NoticeDTO> list;
			if (kwd.length() != 0) {
				list = dao.listNotice(offset, size, schType, kwd);
			} else {
				list = dao.listNotice(offset, size);
			}
			
			// 공지글
			List<NoticeDTO> listNotice = null;
			listNotice = dao.listNotice();
			for (NoticeDTO dto : listNotice) {
				
				dto.setWriteDate(dto.getWriteDate().substring(0, 10));
			}
			
			long gap;
			Date curDate = new Date();	// 현재날짜
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (NoticeDTO dto : list) {
				Date date = sdf.parse(dto.getWriteDate());	// 받아온날짜
				// gap = (curDate.getTime() - date.getTime()) / (1000*60*60*24); // 일자
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60); // 시간
				dto.setGap(gap);
				
				dto.setWriteDate(dto.getWriteDate().substring(0, 10));
			}
			
			String query = "";
			String listUrl;
			String articleUrl;
			
			listUrl = cp + "/notice/list.do?size=" + size;
			articleUrl = cp + "/notice/article.do?page=" + current_page + "&size=" + size;
			if (kwd.length() != 0) {
				query = "schType=" + schType + "&kwd=" + URLEncoder.encode(kwd, "utf-8");
				
				listUrl += "&" + query;
				articleUrl += "&" + query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩 jsp에 전달할 데이터
			req.setAttribute("list", list);
			req.setAttribute("listNotice", listNotice);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("schType", schType);	// 4) 서버에서 넘어와서 받은 결과값을 포워딩으로 넘겨준다?
			req.setAttribute("kwd", kwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/notice/list.jsp");	// 5) 포워딩 --> 포워딩한뒤로 어디로가나>?? (list.jsp?)
	}	
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼(글올리기 들어가기)
		HttpSession session = req.getSession();	// 현재 요청(request)에 대한 세션을 가져오는 메서드
		SessionInfo info = (SessionInfo) session.getAttribute("member"); 
												//세션에서 "member"라는 이름으로 저장된속성을 가져와서 SessionInfo 타입의 객체로 형변환
		String cp = req.getContextPath();	//현재 웹 애플리케이션의 컨텍스트 경로를 반환 (주소참조할때 길이줄이기위해)
		String size = req.getParameter("size");	// HTTP 요청에서 size"라는 이름의 파라미터 값을 가져오는 
	//	String page = req.getParameter("page");
		// admin만 글을 등록(resp.sendRedirect)
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do?size=" + size);
			return;
		}
		
		req.setAttribute("mode", "write");
		req.setAttribute("size", size);
		forward(req, resp, "/WEB-INF/views/notice/write.jsp"); // forward 메서드를 호출하여 "/WEB-INF/views/bbs/write.jsp"로 전달
	} 
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 쓰고 저장되기
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}
		
		// admin만 글을 등록
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		String size = req.getParameter("size");
		try {
			NoticeDTO dto = new NoticeDTO();
			
			dto.setUserId(info.getUserId());
			if (req.getParameter("notice") != null) {
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			}
			dto.setTitle(req.getParameter("title")); // name그자체 : 다를시 null로반환
			dto.setContent(req.getParameter("content"));
			
			
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] clientFiles = map.get("originalFilenames");
				dto.setSaveFiles(saveFiles);
				dto.setClientFiles(clientFiles);	// DTO에서 임시컬럼(saveFiles) 만들고 기존컬럼에다 뿌려주기(files는 임시컬럼인데??)
			}
			
			dao.insertNotice(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/list.do?size=" + size);
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		String cp = req.getContextPath();	// 현재 웹 애플리케이션의 컨텍스트 경로를 가져오는 코드( 가져올 주소 줄이기 )
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;
		
		NoticeDAO dao = new NoticeDAO();
		
		try {
			long referNum = Long.parseLong(req.getParameter("referNum"));
			
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
			
			// 조회수
			dao.updateHitCount(referNum);
			
			// 게시물 가져오기
			NoticeDTO dto = dao.findById(referNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/notice/list.do?" + query);
				return;
			}
			
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			// 이전글/다음글
			NoticeDTO prevDto = dao.findByPrev(dto.getReferNum(), schType, kwd);
			NoticeDTO nextDto = dao.findByNext(dto.getReferNum(), schType, kwd);
			
			// 파일
			List<NoticeDTO> listFile = dao.listNoticeFile(referNum);	// dao listNotice 불러옴
			System.out.println(listFile);
			req.setAttribute("dto", dto);
			req.setAttribute("prevDto", prevDto);
			req.setAttribute("nextDto", nextDto);
			req.setAttribute("listFile", listFile);	// 게시물에 첨부된 파일 목록
			req.setAttribute("query", query);	// 검색 쿼리 또는 페이지 이동을 위한 정보
			req.setAttribute("page", page);		// 현재 페이지 번호
			req.setAttribute("size", size);		// 페이지 당 게시물 수
			
			forward(req, resp, "/WEB-INF/views/notice/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/list.do?" + query);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼(수정 들어가기)
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		
		try {
			long referNum = Long.parseLong(req.getParameter("referNum"));
			
			NoticeDTO dto = dao.findById(referNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&size=" + size);
				return;
			}
			
			// 파일
			List<NoticeDTO> listFile = dao.listNoticeFile(referNum);
			
			req.setAttribute("dto", dto);
			req.setAttribute("listFile", listFile);
			req.setAttribute("page", page);
			req.setAttribute("size", size);
			
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/notice/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&size=" + size);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료되기
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}
		
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		
		try {
			NoticeDTO dto = new NoticeDTO();
			
			dto.setReferNum(Long.parseLong(req.getParameter("referNum")));
			if (req.getParameter("notice") != null) {
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			}
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFile");
				String[] clientFiles = map.get("clientFile");
				dto.setSaveFiles(saveFiles);
				dto.setClientFiles(clientFiles);
			}
			
			dao.updateNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&size=" + size);
	}
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정에서 파일만 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		String page = req.getParameter("page");	// 주소, 리스트 페이지?
		String size = req.getParameter("size");	// 주소, 리스트 최대값?
		
		try {
			long referNum = Long.parseLong(req.getParameter("referNum"));
			long noticeFileNum = Long.parseLong(req.getParameter("noticeFileNum"));
			NoticeDTO dto = dao.findByFileId(noticeFileNum);
			if (dto != null) {
				// 파일 삭제
				FileManager.doFiledelete(pathname, dto.getSaveFile());
				
				// 테이블 파일정보 삭제
				dao.deleteNoticeFile("one", noticeFileNum);	//  one???????
			}
			
			//다시 수정화면으로
			resp.sendRedirect(cp + "/notice/update.do?referNum=" + referNum + "&page=" + page + "&size=" + size);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/list.do?page=" + page + "&size=" + size);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;
		
		try {
			long referNum = Long.parseLong(req.getParameter("referNum"));
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
			
			NoticeDTO dto = dao.findById(referNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/notice/list.do?" + query);
				return;
			}
			
			// 파일삭제
			List<NoticeDTO> listFile = dao.listNoticeFile(referNum);
			for (NoticeDTO vo : listFile) {
				FileManager.doFiledelete(pathname, vo.getSaveFile());
			}
			dao.deleteNoticeFile("all", referNum);
			
			dao.deleteNotice(referNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/list.do?" + query);
	}
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 다운로드
		NoticeDAO dao = new NoticeDAO();
		boolean b = false;
		
		try {
			long noticefileNum = Long.parseLong(req.getParameter("noticeFileNum"));
			
			NoticeDTO dto = dao.findByFileId(noticefileNum);
			if (dto != null) {
				b = FileManager.doFiledownload(dto.getSaveFile(), dto.getClientFile(), pathname, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일다운로드가 실패했습니다.');history.back();</script>");
		}
	}
	
	protected void deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// delete랑 차이점?
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		if (!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do");
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
			
			String[] nn = req.getParameterValues("referNum");
			long referNum[] = null;
			referNum = new long[nn.length];
			for (int i = 0; i < nn.length; i++) {
				referNum[i] = Long.parseLong(nn[i]);
			}
			
			NoticeDAO dao = new NoticeDAO();
			
			// 파일 삭제
			for (int i=0; i<referNum.length; i++) {
				List<NoticeDTO> listFile = dao.listNoticeFile(referNum[i]);
				for (NoticeDTO vo : listFile) {
					FileManager.doFiledelete(pathname, vo.getSaveFile());
				}
				dao.deleteNoticeFile("all", referNum[i]);
			}
			
			// 게시글 삭제
			dao.deleteNoticeList(referNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/list.do?" + query);
	}
}
