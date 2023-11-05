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
	padding-top: 15px;
}

.table-article tr > td { 	padding-left: 5px; padding-right: 5px; }
</style>

<script type="text/javascript">
function deleteBoard(){
	if(confirm('게시글을 삭제 하시겠습니까? ')){
		let query = 'num=${dto.num}&${query}';
		let url = '${pageContext.request.contextPath}/bbs/delete.do?'+query;
		location.href = url;
	}
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
			<h2><i class="fa-regular fa-square"></i> 수강후기 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<table class="table table-border table-article">
				<thead>
					<tr>
						<td colspan="2" align="center">
							${dto.subject}
						</td>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td width="50%">
							이름 : ${dto.userName}
						</td>
						<td align="right">
							${dto.reg_date} | 조회 ${dto.hitCount}
						</td>
					</tr>
					
					<tr>
						<td colspan="2" valign="top" height="200">
							${dto.content}
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							이전글 : 
					<c:if test="${not empty prevDto}">
						<a href="${pageContext.request.contextPath}/bbs/article.do?${query}&num=${prevDto.num}">${prevDto.subject}</a>
					</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							다음글 : 
					<c:if test="${not empty nextDto}">
						<a href="${pageContext.request.contextPath}/bbs/article.do?${query}&num=${nextDto.num}">${nextDto.subject}</a>
					</c:if>
						</td>
					</tr>
				</tbody>
			</table>
			
			<table class="table">
				<tr>
					<td width="50%">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/bbs/update.do?num=${dto.num}&page=${page}';">수정</button>
						<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
					</td>
					<td align="right">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/bbs/list.do?${query}';">리스트</button>
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