<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

.table-form td { padding: 7px 0; }
.table-form p { line-height: 200%; }
.table-form tr:first-child { border-top: 2px solid #212529; }
.table-form tr > td:first-child { width: 110px; text-align: center; background: #f8f8f8; }
.table-form tr > td:nth-child(2) { padding-left: 10px; }

.table-form input[type=text], .table-form input[type=file], .table-form textarea {
	width: 96%; }
.form-check-input { width: 1em; height: 1em; vertical-align: middle; background-color: #fff; border: 1px solid rgba(0,0,0,.25); margin-top: 7px; margin-bottom: 7px; }
.form-check-input:checked { background-color: #0d6efd; border-color: #0d6efd; }
.form-check-label { cursor: pointer; vertical-align: middle; margin-top: 7px; margin-bottom: 7px; }	
</style>

<script type="text/javascript">

function sendqna() {
    const f = document.questionForm;
	let str;
	
    str = f.q_title.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.q_title.focus();
        return;
    }

    str = f.q_content.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.q_content.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/sugangqna/${mode}_ok.do";
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
			<h2><i class="fa-solid fa-person-circle-question"></i> 질문과 답변 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<form name="questionForm" method="post">
				<table class="table table-border table-form">
					<tr> 
						<td>제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
						<td>
							<c:choose>
								<c:when test="${sessionScope.member.userId == 'admin'}">
									<input type="text" name="q_title" maxlength="100" class="form-control" value="${dto.q_title}" readonly>
								</c:when>
								<c:otherwise>
									<input type="text" name="q_title" maxlength="100" class="form-control" value="${dto.q_title}">
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					
					<tr> 
						<td>작성자</td>
						<td> 
							<p>${dto.q_userName}</p>
						</td>
					</tr>			
					<tr> 
						<td valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
						<td>
							<c:choose>
								<c:when test="${sessionScope.member.userId == 'admin'}">
									<textarea name="q_content" class="form-control" readonly>${dto.q_content}</textarea>
								</c:when>
								<c:otherwise>
									<textarea name="q_content" class="form-control">${dto.q_content}</textarea>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					
					<c:if test="${sessionScope.member.userId == 'admin' }">
						<tr> 
							<td>제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
							<td>
								<input type="text" name="a_title" maxlength="100" class="form-control" value="${dto.a_title}">
							</td>
						</tr>
					
						<tr> 
							<td>작성자</td>
							<td> 
								<p>${sessionScope.member.userId}</p>
							</td>
						</tr>			
						<tr> 
							<td valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
							<td>
								<textarea name="a_content" class="form-control">${dto.a_content}</textarea>
							</td>
					</tr>
					</c:if>
				</table>
					
				<table class="table">
					<tr> 
						<td align="center">
							<button type="button" class="btn" onclick="sendqna();">${mode == 'update' ? '수정하기' : mode == 'answer' ? '답변하기':'등록하기'}</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugangqna/list_ok.do?classNum=${classNum}';">${mode == 'update' ? '수정취소' : mode == 'answer' ? '답변취소':'등록취소'}</button>
							<input type="hidden" name="classNum" value="${classNum}">
							<c:if test="${mode=='update'}">
								<input type="hidden" name="q_num" value="${dto.q_num}">
								<input type="hidden" name="page" value="${page}">
								<input type="hidden" name="classNum" value="${classNum}">
							</c:if>
							<c:if test="${mode=='answer'}">
								<input type="hidden" name="q_num" value="${dto.q_num}">
								<input type="hidden" name="page" value="${page}">
								<input type="hidden" name="classNum" value="${classNum}">
							</c:if>
						</td>
					</tr>
				</table>
		
			</form>

	    </div>
	</div>

</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>