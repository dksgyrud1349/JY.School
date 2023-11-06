<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>준영스쿨 코딩 공식 사이트 | 코딩은 역시 준영스쿨</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
</head>
<body>


<div class="container body-container">
	<div class="inner-page mx-auto" style="float: left;">
			<img onclick="location.href='${pageContext.request.contextPath}/member/member_hak.do';" style="cursor: pointer;"
			 	src="${pageContext.request.contextPath}/img/학생회원.png" alt="My Image" width="460px" height="auto">
	    </div>
	    <div class="inner-page mx-auto" style="margin-left: 600px ">
			<img onclick="location.href='${pageContext.request.contextPath}/member/teacher.do';" 
				style="cursor: pointer;" src="${pageContext.request.contextPath}/img/강사회원.png" alt="My Image" width="460px" height="auto">
	    </div>
</div>	
<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>