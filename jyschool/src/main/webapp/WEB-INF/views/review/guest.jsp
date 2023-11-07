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

.guest-form { clear: both; border: #d5d5d5 solid 1px; padding: 15px; min-height: 50px; }
.guest-form > .form-header { padding-bottom: 7px; }
.guest-form > .form-header > .bold { font-weight: bold; }
.guest-form > .form-body { padding: 10px 0 3px; }
.guest-form > .form-footer { padding-top: 3px; text-align: right; }
.guest-form textarea { width: 100%; height: 75px; }
.guest-form button { padding: 8px 25px; }

.list-header { padding-top: 25px; padding-bottom: 7px; }
.list-header .item-count { color: #3EA9CD; font-weight: 700; }

.list-content { width: 100%; }
.item-header {
	display: flex;
	padding: 10px 5px;
	background-color: #f8f8f8;
	border: 1px solid #ced4da;
	justify-content: space-between; 
	align-items: center;
	margin-top: 5px;
}
.item-header .item-header-left { font-weight: 600; padding-left: 5px; display: flex; align-items: center; }
.item-header .item-header-right { padding-right: 5px; display: flex; align-items: center; }
.item-body { padding: 5px; min-height: 50px; word-break: break-all; } 

.item-delete, .item-notify { cursor: pointer; padding-left: 5px; }
.item-delete:hover, .item-notify:hover { font-weight: 500; color: #2478FF; }

textarea::placeholder{
	opacity: 1; /* 파이어폭스에서 뿌옇게 나오는 현상 제거 */
	color: #333;
	text-align: center;
	line-height: 60px;
}
</style>
<script type="text/javascript">
function sendGuest() {
	const uid = "${sessionScope.member.userId}";
	if(! uid) {
		location.href = "${pageContext.request.contextPath}/member/login.do";
		return;
	}
	
	const f = document.guestForm;
	let str;
	
	str = f.content.value.trim();
	if(!str) {
		alert("내용을 입력 하세요 !!!");
		f.content.focus();
		return;
	}
	
	f.action = "${pageContext.request.contextPath}/review/write_ok.do";
	f.submit();
}

function deleteGuest(num) {
	let url = "${pageContext.request.contextPath}/review/delete.do?num=" + num + "&page=${page}";
	
	if(confirm("게시글을 삭제 하시겠습니까 ?")) {
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
			<h2><i class="far fa-edit"></i> 수강후기 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<form name="guestForm" method="post">
				<div class="guest-form">
					<div class="form-header">
						<span class="bold">후기 작성</span><span> - 후기 한번 야무지게 작성해 보세요~</span>
						<span class="bold">수강 종료후에 등록 가능해요~</span><span></span>
						
					</div>
					<div class="form-body">
						<textarea name="content" id="content" class="form-control" placeholder="${empty sessionScope.member ? '로그인 후 등록 가능합니다.':''}"></textarea>
					</div>
					<div class="form-footer">
						<button type="button" class="btn" onclick="sendGuest();"> 등록하기 </button>
					</div>
				</div>
			</form>
			
			<c:if test="${dataCount != 0}">
				<div class="wrap-inner">
					<div class="list-header">
						<span class="item-count">게시글 ${dataCount}개</span>
						<span class="item-desc">[목록, ${page}/${total_page} 페이지]</span>
					</div>
					
					<div class="list-content">
						<c:forEach var="dto" items="${list}">
							<div class="item-content">
								<div class="item-header">
									<div class="item-header-left">${dto.userName}</div>
									<div class="item-header-right">
										${dto.reg_date}
										<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
											|<span class="item-delete" onclick="deleteGuest('${dto.classNum}');">삭제</span>
										</c:if> 
									</div>
								</div>
								<div class="item-body">${dto.content}</div>
							</div>
						</c:forEach>
					</div>
					
					<div class="page-navigation">
						${paging}
					</div>
				</div>
			</c:if>
	    </div>
	</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>