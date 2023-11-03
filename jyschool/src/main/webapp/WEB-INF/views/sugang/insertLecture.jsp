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
	max-width: 800px;
	padding-top: 15px;
}

.table-form td { padding: 7px 0; }
.table-form p { line-height: 200%; }
.table-form tr:first-child { border-top: 2px solid #212529; }
.table-form tr > td:first-child { width: 110px; text-align: center; background: #f8f8f8; }
.table-form tr > td:nth-child(2) { padding-left: 10px; }

.table-form input[type=text], .table-form input[type=file], .table-form textarea {
	width: 96%; }
</style>

<script type="text/javascript">
function sendLecture() {
    const f = document.lectureForm;
	let str;
	
    str = f.classNum.value.trim();
    if(!str) {
        alert("강좌 번호를 입력하세요.");
        f.classNum.focus();
        return;
    }

    str = f.className.value.trim();
    if(!str) {
        alert("강좌 제목을 입력하세요. ");
        f.className.focus();
        return;
    }
    
    str = f.classComment.value.trim();
    if(!str) {
        alert("강좌 설명을 입력하세요. ");
        f.classComment.focus();
        return;
    }
    
    str = f.classComment.value.trim();
    if(!str) {
        alert("강좌 설명을 입력하세요. ");
        f.classComment.focus();
        return;
    }
    
    str = f.classDegree.value.trim();
    if(!str) {
        alert("강좌 등급을 입력하세요. ");
        f.classDegree.focus();
        return;
    }
    
    str = f.price.value.trim();
    if(!str) {
        alert("강좌 가격을 입력하세요. ");
        f.price.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/sugang/${mode}_ok.do";
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
			<h2><i class="fas fa-chalkboard-teacher"></i> 강좌 등록 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<form name="lectureForm" enctype="multipart/form-data" method="post">
				<table class="table table-border table-form">
				
					<tr> 
						<td>강좌 번호</td>
						<td> 
							<input type="text" name="classNum" maxlength="100" class="form-control" value="${dto.classNum}">
						</td>
					</tr>
					
					<tr> 
						<td>강좌 제목</td>
						<td> 
							<input type="text" name="className" maxlength="100" class="form-control" value="${dto.className}">
						</td>
					</tr>
					
					<tr> 
						<td>강사</td>
						<td> 
							<p>${sessionScope.member.userName}</p>
							<input type="hidden" name="userId" value="${sessionScope.member.userId}">
						</td>
					</tr>
					
					<tr> 
						<td valign="top">강좌 설명</td>
						<td>
							<textarea name="classComment" class="form-control">${dto.classComment}</textarea>
						</td>
					</tr>
					
					<tr> 
						<td>강좌 등급</td>
						<td> 
							<input type="text" name="classDegree" maxlength="100" class="form-control" value="${dto.classDegree}">
						</td>
					</tr>
					
					<tr> 
						<td>가격</td>
						<td> 
							<input type="text" name="price" maxlength="100" class="form-control" value="${dto.price}">
						</td>
					</tr>
					
					<tr> 
						<td>강좌메인사진</td>
						<td> 
							<input type="file" name="selectFile1" accept="image/*" multiple class="form-control">
						</td>
					</tr>
					
					<tr> 
						<td>강좌설명사진</td>
						<td> 
							<input type="file" name="selectFile2" accept="image/*" multiple class="form-control">
						</td>
					</tr>
				</table>
					
				<table class="table">
					<tr> 
						<td align="center">
							<button type="button" class="btn" onclick="sendLecture();">${mode=='update'?'수정완료':'등록완료'}</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/sugang/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
						<c:if test="${mode =='update'}">
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