<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
div, ul, li {-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;padding:0;margin:0}
a {text-decoration:none;}

.quickmenu {position:absolute;width:90px;top:50%;margin-top:-50px;right:10px;background:#fff;z-index: 9999}
.quickmenu ul {position:relative;float:left;width:100%;display:inline-block;*display:inline;border:1px solid #ddd;}
.quickmenu ul li {float:left;width:100%;border-bottom:1px solid #ddd;text-align:center;display:inline-block;*display:inline;}
.quickmenu ul li a {position:relative;float:left;width:100%;height:30px;line-height:30px;text-align:center;color:#999;font-size:9.5pt;}
.quickmenu ul li a:hover {color:#000;}
.quickmenu ul li:last-child {border-bottom:0;}

.content {position:relative;min-height:1000px;}
</style>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	  var currentPosition = parseInt($(".quickmenu").css("top"));
	  $(window).scroll(function() {
	    var position = $(window).scrollTop(); 
	    $(".quickmenu").stop().animate({"top":position+currentPosition+"px"},1000);
	  });
	});
</script>
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
<div class="quickmenu">
  <ul>
    <li><a href="#">등급별혜택</a></li>
    <li><a href="#">1:1문의</a></li>
    <li><a href="#">후기</a></li>
  </ul>
</div>
	<nav style="background-color: #592EFC">
		<ul class="main-menu" style="background-color: #592EFC">
			<li><a href="${pageContext.request.contextPath}/">홈</a></li>
			
			<li><a href="#">준영스쿨</a>
			<ul class="sub-menu">
					<li><a href='<c:url value="/sogae/sogae.do" />' aria-label="submenu">준영스쿨이란</a></li>
					<li><a href="#" aria-label="submenu">강사진 소개</a></li>
				</ul>
			</li>
			
			<li><a href='<c:url value="/sugang/list.do"/>'>수강신청</a>
			</li>
			
			<li><a href="#">커뮤니티</a>
				<ul class="sub-menu">
					<li><a href='<c:url value="/bbs/list.do" />' aria-label="submenu">질문게시판</a></li>
					<li><a href="#" aria-label="submenu">자료실</a></li>
					<li><a href="#" aria-label="submenu">수강후기</a></li>
					<li><a href="#" aria-label="submenu">궁금게시판</a></li>
				</ul>
			</li>

		
			<li><a href="#">고객센터</a>
				<ul class="sub-menu">
					<li><a href="#" aria-label="submenu">공지사항</a></li>
					<li><a href="#" aria-label="submenu">질문과 답변</a></li>
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
