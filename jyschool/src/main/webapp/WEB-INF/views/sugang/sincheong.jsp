<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>

<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
.body-main {
	max-width: 1200px;
}
.table-vo{
	width: 500px;
	height: 190px;
	float: right;
	margin-top:85px;
	margin-right: 30px;
}
.table-content{
	width: 1070px;
	height: 150px;
	padding: 0px;
	margin-left: 20px;
	margin-top: 330px;
}
.table-list th{ background: #f8f8f8;}
.table-list td {text-align: center;}
.table-list .left { text-align: left; padding-left: 5px; }

.table-list .num { width: 60px;}
.table-list .name { width: 100px;}
.table-list .date { width: 100px; }
.table-list .content{width: 100px;}
.box{margin: 30px; left: 200px; top: 250px;}
.img{position: absolute; width: 500px; height: 300px; margin-left: 15px;}

}
</style>
<script type="text/javascript">

</script>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div class="container body-container">
	    <div class="body-title">
			<h2><i class="fas fa-chalkboard-teacher"></i>${dto.className}</h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<table class="table">
				<tr>
	    			<td class="box"><img src = "rupi.jpg" class="img"></td>
					<td align="left">&nbsp;</td>
				</tr>
			</table>
			
			<table class="table table-border table-list table-vo" style="border-top: 2px solid gray;">
					<tr>
						<th class="subject">강의제목</th>
						<td>${dto.className}</td>
					</tr>
					<tr>
						<th class="name">강사이름</th>
						<td>${dto.username}</td>
					</tr>
					<tr>
						<th class="rank">난이도</th>
						<td>${dto.classDegree}</td>
					</tr>
					<tr>
						<th class="date">등록일</th>
						<td>${dto.c_reg_date}</td>
					</tr>
					<tr>
						<th class="price">가격</th>
						<td>${dto.price}</td>
						<input type="hidden" name="classNum" value="${dto.classNum}">
					</tr>
				
			</table>
			
			<table class="table table-border table-list table-content">
				<tr>
					<th class="content" style="border-top: 2px solid gray;"> 상세 설명 </th>
					<th style="border-top: 2px solid gray; background: white;">ddddd</th>
				</tr>
				<tr>
					<td> 이미지로 대체 </td>

				</tr>
			</table>
			
			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugang/list.do';" title="이전으로">강좌목록 <i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					<td width="100" style="float: right; margin-right: -85px;">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugang/update.do';" title="수정">수정하기</button>
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugang/list.do';" title="삭제">삭제하기</button>
					</td>
					<td align="right" width="100">

					</td>
				</tr>
			</table>	
	    </div>
	</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>