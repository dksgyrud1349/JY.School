<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>준영스쿨 코딩 공식 사이트 | 코딩은 역시 준영스쿨</title>

<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
	
<main>


	<div class="container body-container">
	    <div class="inner-page mx-auto">
			<img src="${pageContext.request.contextPath}/img/sec012.jpg" alt="My Image" width="1160px" height="auto">
	    </div>
	</div>
	<div class="container body-container">
	    <div class="inner-page mx-auto">
			<img src="${pageContext.request.contextPath}/img/intro.png" alt="My Image" >
	    </div>
	</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/></body>
</html>