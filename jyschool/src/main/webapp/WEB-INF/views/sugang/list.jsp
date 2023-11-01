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
	max-width: 700px;
}

.table-list thead > tr:first-child{ background: #f8f8f8; }
.table-list th, .table-list td { text-align: center; }
.table-list .left { text-align: left; padding-left: 5px; }

.table-list .num { width: 60px; color: #787878; }
.table-list .subject { color: #787878; }
.table-list .name { width: 100px; color: #787878; }
.table-list .date { width: 100px; color: #787878; }
.table-list .hit { width: 70px; color: #787878; }
</style>
<script type="text/javascript">
function searchList() {
	const f = document.searchForm;
	f.submit();
}
</script>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div class="container body-container">
	    <div class="body-title">
			<h2><i class="fas fa-chalkboard-teacher"></i> 강좌목록 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<table class="table">
				<tr>
					<td width="50%">
						${dataCount}(${page}/${total_page} 페이지)
					</td>
					<td align="right">&nbsp;</td>
				</tr>
			</table>
			
			<table class="table table-border table-list">
				<thead>
					<tr>
						<th class="num">번호</th>
						<th class="subject">강의제목</th>
						<th class="name">강사이름</th>
						<th class="rank">난이도</th>
						<th class="date">등록일</th>
						<th class="price">가격</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<td>${dataCount - (page - 1) * size - status.index}</td>
							<td class="left">
								<c:forEach var="n" begin="1" end="${dataCount}">&nbsp;&nbsp;</c:forEach>
								<input type="hidden" name="classNum" value="${dto.classNum}">
								<a href="${pageContext.request.contextPath}/sugang/list_ok.do?page=${page}&classNum=${dto.classNum}">${dto.className}</a>
							</td>
							<td>${dto.username}</td>
							<td>${dto.classDegree}</td>
							<td>${dto.c_reg_date}</td>
							<td>${dto.price }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div class="page-navigation">
				${dataCount == 0 ? "등록된 강좌가 없습니다." : paging}
			</div>
			
			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugang/list.do';" title="새로고침"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					<td align="center">
						<form name="searchForm" action="${pageContext.request.contextPath}/sugang/list.do" method="post">
							<select name="schType" class="form-select">
								<option value="all"      ${schType=="all"?"selected":"" }>제목+내용</option>
								<option value="userName" ${schType=="username"?"selected":"" }>강사</option>
								<option value="c_reg_date"  ${schType=="c_reg_date"?"selected":"" }>등록일</option>
								<option value="className"  ${schType=="className"?"selected":"" }>강좌이름</option>
							</select>
							<input type="text" name="kwd" value="${kwd}" class="form-control">
							<button type="button" class="btn" onclick="searchList();">검색</button>
						</form>
					</td>
					<c:forEach var="tdto" items="${teacherList}">
						<c:if test="${sessionScope.member.userId==tdto.userId}">
							<td align="right" width="100">
								<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugang/insert.do';">강좌업로드</button>
							</td>
						</c:if>
					</c:forEach>
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