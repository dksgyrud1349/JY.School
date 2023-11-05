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
function sendOk() {
    const f = document.questionForm;
	let str;
	
    str = f.title.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.title.focus();
        return;
    }

    str = f.qcontent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.qcontent.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/beforeqna/${mode}_ok.do";
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
							<input type="text" name="title" maxlength="100" class="form-control" value="${dto.title}">
						</td>
					</tr>
					
					<tr> 
						<td>작성자</td>
						<td> 
							<p>${sessionScope.member.userName}</p>
						</td>
					</tr>
					
					<tr>
						<td>공개여부</td>
						<td> 
							<input type="radio" name="secret" id="secret1" class="form-check-input" 
								value="0" ${empty dto || dto.secret==0?"checked='checked'":"" }>
							<label class="form-check-label" for="secret1">공개</label>
							<input type="radio" name="secret" id="secret2" class="form-check-input"
								value="1" ${dto.secret==1?"checked='checked'":"" }>
							<label class="form-check-label" for="secret2">비공개</label>
						</td>
					</tr>
									
					<tr> 
						<td valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
						<td> 
							<textarea name="qcontent" class="form-control">${dto.qcontent}</textarea>
						</td>
					</tr>
				</table>
					
				<table class="table">
					<tr> 
						<td align="center">
							<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/beforeqna/list.do?classNum2=${classNum2}';">${mode=='update'?'수정취소':'등록취소'}</button>
							<input type="hidden" name="classNum2" value="${classNum2}">
							<c:if test="${mode=='update'}">
								<input type="hidden" name="qnum" value="${dto.qnum}">
								<input type="hidden" name="page" value="${page}">
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