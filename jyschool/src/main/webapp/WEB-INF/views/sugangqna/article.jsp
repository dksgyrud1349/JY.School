<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	pageContext.setAttribute("cn", "\n");
	pageContext.setAttribute("br", "<br/>");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>

<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />

<style type="text/css">
.body-main {
	max-width: 700px;
	padding-top: 15px;
}

.table-article tr>td {
	padding-left: 5px;
	padding-right: 5px;
}

.file-item {
	padding: 7px;
	margin-bottom: 3px;
	border: 1px solid #ced4da;
	color: #777777;
}
</style>
<c:if test="${sessionScope.member.userId==dto.q_userId || sessionScope.member.userId=='admin'}">
	<script type="text/javascript">
		function deleteQuestion() {
			if (confirm("게시글을 삭제하시겠습니까 ? ")) {
				
				let url = "${pageContext.request.contextPath}/sugangqna/delete.do?q_num=${dto.q_num}&classNum=${classNum}";
				location.href = url;
			}
		}
	</script>
</c:if>
<c:if test="${sessionScope.member.userId == 'admin' || sessionScope.member.userId == teacher.t_userId}">
	<script type="text/javascript">
		function deleteAnswer(){
			if(confirm("답변을 삭제하시겠습니까? ")){
				let url = "${pageContext.request.contextPath}/sugangqna/answerDelete.do?q_num=${dto.q_num}&classNum=${classNum}";
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
				<h2>
					<i class="fa-regular fa-square"></i> 질문과 답변
				</h2>
			</div>

			<div class="body-main mx-auto">
				<table class="table table-border table-article">
					<thead>
						<tr>
							<td colspan="2" align="center"> Q : ${dto.q_title}</td>
						</tr>
					</thead>

					<tbody>
						<tr>
							<td width="50%">이름 : ${dto.q_userName}</td>
							<td align="right">${dto.q_date}</td>
						</tr>

						<tr>
							<td colspan="2" valign="top" height="200">${fn:replace(dto.q_content, cn, br)}</td>
						</tr>
					</tbody>
					<c:choose>
						<c:when test="${sessionScope.member.userId == 'admin' && dto.result_state == 'O'}">
							<tfoot>
								<tr>
									<td colspan="2" align="center">A : ${dto.a_title}</td>
								</tr>
						
								<tr>
									<td width="50%">이름 : ${sessionScope.member.userId}</td>
									<td align="right">${dto.a_date}</td>
								</tr>
						
								<tr>
									<td colspan="2" valign="top" height="200">${fn:replace(dto.a_content, cn, br)}</td>
								</tr>
							</tfoot>
						</c:when>
						<c:when test="${dto.result_state == 'O'}">
							<tfoot>
								<tr>
									<td colspan="2" align="center">A : ${dto.a_title}</td>
								</tr>
						
								<tr>
									<td width="50%">이름 : ${sessionScope.member.userId}</td>
									<td align="right">${dto.a_date}</td>
								</tr>
						
								<tr>
									<td colspan="2" valign="top" height="200">${fn:replace(dto.a_content, cn, br)}</td>
								</tr>
							</tfoot>
						</c:when>
					</c:choose>
					
				</table>

				<table class="table">
					<tr>
						<td width="50%">
							<c:choose>
								<c:when test="${sessionScope.member.userId == dto.q_userId}">
									<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugangqna/update.do?classNum=${classNum}&q_num=${dto.q_num}';">수정하기</button>
								</c:when>
								<c:when test="${sessionScope.member.userId == teacher.t_userId || sessionScope.member.userId == 'admin'}">
									<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugangqna/answer.do?classNum=${classNum}&q_num=${dto.q_num}';">답변</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn" disabled>수정하기</button>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${sessionScope.member.userId==dto.q_userId || sessionScope.member.userId=='admin'}">
									<button type="button" class="btn" onclick="deleteQuestion();">게시글삭제하기</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn" disabled>게시글삭제하기</button>
								</c:otherwise>
							</c:choose>
							<c:if test="${result.result_state == 'O' && sessionScope.member.userId == teacher.t_userId || sessionScope.member.userId == 'admin'}">
								<button type="button" class="btn" onclick="deleteAnswer();">답변삭제</button>
							</c:if>
						</td>
						<td align="right">
							<button type="button" class="btn"
								onclick="location.href='${pageContext.request.contextPath}/sugangqna/list_ok.do?classNum=${classNum}';">리스트</button>
						</td>
					</tr>
				</table>

			</div>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>