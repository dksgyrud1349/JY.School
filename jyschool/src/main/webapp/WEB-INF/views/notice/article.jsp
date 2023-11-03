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

.table-article tr > td { padding-left: 5px; padding-right: 5px; }
.file-item { padding: 7px; margin-bottom: 3px; border: 1px solid #ced4da; color: #777777; }
</style>
<c:if test="${sessionScope.member.userId=='admin'}">
	<script type="text/javascript">
	function deleteNotice() {
	    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	        let query = "referNum=${dto.referNum}&${query}";
	        let url = "${pageContext.request.contextPath}/notice/delete.do?" + query;
	    	location.href = url;
	    }
	}
	</script>
</c:if>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div class="container body-container">
	    <div class="body-title">
			<h2><i class="fas fa-clipboard-list"></i> 공지사항 </h2>	<!-- 공지사항 이미지와 / 공지사항 -->
	    </div>
	    
	    <div class="body-main mx-auto">
			<table class="table table-border table-article">
				<thead>
					<tr>
						<td colspan="2" align="center">
							${dto.title}			<!-- 맨위 subject값을 받아와서 center에 준다 -->
						</td>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td width="50%">
							이름 : ${dto.userName}
						</td>
						<td align="right">
							${dto.writeDate} | 조회 ${dto.hitCount}
						</td>
					</tr>
					
					<tr style="border-bottom:none;">
						<td colspan="2" valign="top" height="200">
							${dto.content}
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<c:forEach var="vo" items="${listFile}" varStatus="status">
								<p class="file-item">
									<i class="fa-regular fa-folder-open"></i>								<!-- @@@@@@@@@@@@@@@@@2 -->
									<a href="${pageContext.request.contextPath}/notice/download.do?noticeFileNum=${vo.noticeFileNum}">${vo.clientFile}</a>
								</p>
							</c:forEach>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							이전글 :
							<c:if test="${not empty prevDto}">
								<a href="${pageContext.request.contextPath}/notice/article.do?${query}&referNum=${prevDto.referNum}">${prevDto.title}</a>
							</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							다음글 :
							<c:if test="${not empty nextDto}">
								<a href="${pageContext.request.contextPath}/notice/article.do?${query}&referNum=${nextDto.referNum}">${nextDto.title}</a>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>		<!-- 1테이블) 제목~이전글,다음글까지 리스트 메인틀 -->
			
			<table class="table">
				<tr>
					<td width="50%">
						<c:choose>
							<c:when test="${sessionScope.member.userId=='admin'}">	<!-- 관리자라면 수정누를시 update.do로 -->
								<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/update.do?referNum=${dto.referNum}&page=${page}&size=${size}';">수정</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn" disabled>수정</button>	<!-- 일반인은 수정누르지 못한다. -->
							</c:otherwise>
						</c:choose>
				    	
						<c:choose>
				    		<c:when test="${sessionScope.member.userId=='admin'}">	<!-- 관리자라면 삭제누를시 onclick -->
				    			<button type="button" class="btn" onclick="deleteNotice();">삭제</button>
				    		</c:when>
				    		<c:otherwise>
				    			<button type="button" class="btn" disabled>삭제</button>	<!-- 일반인은 삭제누르지 못한다. -->
				    		</c:otherwise>
				    	</c:choose>
					</td>
					<td align="right">												<!-- 오른쪽에 리스트 -->
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/list.do?${query}';">리스트</button>
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