<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<div class="header-top">
		<div class="header-left">
			&nbsp;
		</div>
		<div class="header-center">
			<h1 class="logo"><a href="${pageContext.request.contextPath}/"><img src="http://localhost:9090/jyschool/img/l준영.png"  width="250" height="auto" alt="My Image" ></a></h1>
		</div>
		<div class="header-right" >
            <c:if test="${empty sessionScope.member}">
                <a href="${pageContext.request.contextPath}/member/login.do" title="로그인"><i class="fa-solid fa-arrow-right-to-bracket"></i></a>
				&nbsp;
                <a href="${pageContext.request.contextPath}/member/member.do" title="회원가입"><i class="fa-solid fa-user-plus"></i></a>
            </c:if>
            <c:if test="${not empty sessionScope.member}">
            	<a href="#" title="알림"><i class="fa-regular fa-bell"></i></a>
            	&nbsp;
				<a href="${pageContext.request.contextPath}/member/logout.do" title="로그아웃"><i class="fa-solid fa-arrow-right-from-bracket"></i></a>
            </c:if>
            <c:if test="${sessionScope.member.userId == 'admin'}">
            	&nbsp;
				<a href="#" title="관리자"><i class="fa-solid fa-gear"></i></a>
            </c:if>
		</div>
	</div>

	<nav style="background-color: #592EFC">
		<ul class="main-menu" style="background-color: #592EFC">
			<li><a href="${pageContext.request.contextPath}/">홈</a></li>
			
			<li><a href="#">준영스쿨 특징</a>
			<ul class="sub-menu">
					<li><a href="#" aria-label="submenu">준영스쿨이란</a></li>
					<li><a href="#" aria-label="submenu">강사진 소개</a></li>
				</ul>
			</li>
			
			<li><a href="#">수강신청</a>
			</li>
			
			<li><a href="#">커뮤니티</a>
				<ul class="sub-menu">
					<li><a href='<c:url value="/bbs/list.do" />' aria-label="submenu">질문게시판</a></li>
					<li><a href="#" aria-label="submenu">자료실</a></li>
					<li><a href="#" aria-label="submenu">수강후기</a></li>
				</ul>
			</li>

			<li><a href="#">스터디룸</a>
				<ul class="sub-menu">
					<li><a href="#" aria-label="submenu">프로그래밍</a></li>
					<li><a href="#" aria-label="submenu">데이터베이스</a></li>
					<li><a href="#" aria-label="submenu">웹프로그래밍</a></li>
					<li><a href="#" aria-label="submenu">질문과 답변</a></li>
				</ul>
			</li>

			<li><a href="#">고객센터</a>
				<ul class="sub-menu">
					<li><a href="#" aria-label="submenu">자주하는 질문</a></li>
					<li><a href="#" aria-label="submenu">공지사항</a></li>
					<li><a href="#" aria-label="submenu">질문과 답변</a></li>
					<li><a href="#" aria-label="submenu">1:1 문의</a></li>
					<li><a href="#" aria-label="submenu">이벤트</a></li>
				</ul>
			</li>

			<li><a href="#">마이페이지</a>
				<ul class="sub-menu">
					<li><a href="${pageContext.request.contextPath}/member/pwd.do?mode=update" aria-label="submenu">정보수정</a></li>
					<li><a href="#" aria-label="submenu">수강정보</a></li>
					<li><a href="#" aria-label="submenu">구매내역</a></li>
				</ul>
			</li>

		</ul>
	</nav>
