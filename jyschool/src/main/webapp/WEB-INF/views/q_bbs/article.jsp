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

.table-article tr>td { padding-left: 5px; padding-right: 5px; }
.file-item { padding: 7px; margin-bottom: 3px; border: 1px solid #ced4da; color: #777777; }

.reply { padding: 20px 0 10px; }
.reply .bold { font-weight: 600; }
.reply .form-header { padding-bottom: 7px; }

.reply-form  tr>td { padding: 2px 0 2px; }
.reply-form textarea { width: 100%; height: 75px; }
.reply-form button { padding: 8px 25px; }

.row-flex { display: flex; justify-content: space-between; }
.left-item {
	width:30px; margin-right: 1px;    padding:10px 10px;
    width:30px;
    text-align: center;
	font-weight: 600;
	color: #fff;
}
.right-item {
	flex-grow: 1;
    overflow: hidden;
    text-overflow: ellipsis;
    word-spacing: nowrap;
    box-sizing: border-box;
    padding: 10px 7px;
	font-weight: 600;
	color: #fff;
}
.left-question { background: #0d6efd; }
.right-question { background: #0d6efd; }

.left-answer { background: #198754; }
.right-answer { background: #198754; }
</style>

<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
	<script type="text/javascript">
		function deleteOk(mode) {
			let s = mode === "question" ? "질문" : "답변";
			
			if(confirm(s + "을 삭제 하시 겠습니까 ? ")) {
				let query = "writeNum=${dto.writeNum}&${query}&mode="+mode;
				let url = "${pageContext.request.contextPath}/q_bbs/delete.do?" + query;
				location.href = url;
			}
		}
	</script>
</c:if>

<c:if test="${sessionScope.member.userId=='admin'}">
	<script type="text/javascript">
		$(function(){
			let answer = "${dto.answer}";
			if(! answer) {
				$(".reply").show();
			}
		});
		
		$(function(){
			$(".btnSendAnswer").click(function(){	// 답변등록하는부분
				const f = document.answerForm;
				if(! f.answer.value.trim()) {
					f.answer.focus();
					return false;
				}
				
				f.action = "${pageContext.request.contextPath}/q_bbs/answer.do";
				f.submit();
			});
		});
		
		$(function(){
			$(".btnUpdateAnswer").click(function(){
				let mode = $(this).attr("data-mode");
				if(mode === "update") {
					$(".reply").show();
					$(this).text("답변 수정 취소")
					$(this).attr("data-mode", "cancel");
				} else {
					$(".reply").hide();
					$(this).attr("data-mode", "update");
					$(this).text("답변 수정")
				}
			});
		});
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
			<h2><i class="fa-solid fa-person-circle-question"></i> 질문과 답변 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<table class="table table-border table-article">
				<tbody>
					<tr style="border: none;">
						<td colspan="2" style="padding: 10px 0 0 0;">
							<div class="row-flex">
								<div class="left-item left-question">Q</div>
								<div class="right-item right-question">${dto.title}</div>
							</div>
						</td>						
					</tr>				
					<tr>
						<td width="50%">
							이름 : ${dto.userName}
						</td>
						<td align="right">
							문의일자 : ${dto.writeDate}
						</td>
					</tr>
					
					<tr>
						<td colspan="2" valign="top" height="200">
							${dto.contents}
						</td>
					</tr>
					
				</tbody>
			</table>			<!-- 테이블1)제목 : 질문과답변 / Qusetion 부분 -->
				
			<c:if test="${not empty dto.answer}">		<!-- 답변이 있다면 -->
				<table class="table table-border table-article">
					<tbody>
						<tr style="border: none;">
							<td colspan="2" style="padding: 0 0 0 0;">
								<div class="row-flex">
									<div class="left-item left-answer">A</div>
									<div class="right-item right-answer">${dto.title}</div>
								</div>
							</td>
						</tr>
						
						<tr>
							<td width="50%">
								담당자 : ${dto.answerName}				
							</td>
							<td align="right">
								답변일자 :  ${dto.reg_date}
							</td>
						</tr>
						
						<tr>
							<td colspan="2" valign="top" height="150">
								${dto.answer}
							</td>
						</tr>
					</tbody>
				</table>			<!-- 테이블2) Answer부분 -->
			</c:if>
				
			<table class="table table-border table-article">
				<tr>
					<td colspan="2">
						이전글 :
						<c:if test="${not empty prevDto}">	<!-- 이전글이 있다면 -->
							<c:choose>
								<c:when test="${prevDto.secret==1}">
									<c:if test="${sessionScope.member.userId==prevDto.userId || sessionScope.member.userId=='admin'}">
										<a href="${pageContext.request.contextPath}/q_bbs/article.do?writeNum=${prevDto.writeNum}&${query}">${prevDto.title}</a>
									</c:if>
									<c:if test="${sessionScope.member.userId!=prevDto.userId && sessionScope.member.userId!='admin'}">
										비밀글 입니다.
									</c:if>
									<i class="bi bi-file-lock2"></i>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath}/q_bbs/article.do?writeNum=${prevDto.writeNum}&${query}">${prevDto.title}</a>
								</c:otherwise>
							</c:choose>
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						다음글 :
						<c:if test="${not empty nextDto}">
							<c:choose>
								<c:when test="${nextDto.secret==1}">
									<c:if test="${sessionScope.member.userId==nextDto.userId || sessionScope.member.userId=='admin'}">
										<a href="${pageContext.request.contextPath}/q_bbs/article.do?writeNum=${nextDto.writeNum}&${query}">${nextDto.title}</a>
									</c:if>
									<c:if test="${sessionScope.member.userId!=nextDto.userId && sessionScope.member.userId!='admin'}">
										비밀글 입니다.
									</c:if>
									<i class="icofont-lock"></i>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath}/q_bbs/article.do?writeNum=${nextDto.writeNum}&${query}">${nextDto.title}</a>
								</c:otherwise>
							</c:choose>
						</c:if>
					</td>
				</tr>
			</table>				<!-- 테이블3) 이전글,다음글 -->
			
			<table class="table">
				<tr>
					<td width="50%">
						<c:if test="${sessionScope.member.userId==dto.userId && empty dto.answer}">
							<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/q_bbs/update.do?writeNum=${dto.writeNum}&page=${page}';">질문수정</button>
						</c:if>
						<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
							<button type="button" class="btn" onclick="deleteOk('question');">질문삭제</button>
						</c:if>
						<c:if test="${not empty dto.answer and sessionScope.member.userId==dto.answerId}">
							<button type="button" class="btn btnUpdateAnswer" data-mode="update">답변수정</button>
						</c:if>
						<c:if test="${not empty dto.answer && (sessionScope.member.userId==dto.answerId)}">
							<button type="button" class="btn" onclick="deleteOk('answer');">답변삭제</button>
						</c:if>
					</td>
					<td align="right">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/q_bbs/list.do?${query}';">리스트</button>
					</td>
				</tr>
			</table>		<!-- 4)테이블: 맨아래 삭제수정버튼 4개 -->

			<div class="reply" style="display: none;">	<!-- 답변달았으면 안보인데. display:none. -->
				<form name="answerForm" method="post">	<!-- 답변수정 누를시.. -->
					<div class='form-header'>
						<span class="bold">질문에 대한 답변</span>
					</div>
					
					<table class="table reply-form">
						<tr>
							<td>
								<textarea class='form-control' name="answer">${dto.answer}</textarea>
							</td>
						</tr>
						<tr>
						   <td align='right'>
						   		<input type="hidden" name="writeNum" value="${dto.writeNum}">	
						   		<input type="hidden" name="page" value="${page}">					   
						        <button type='button' class='btn btnSendAnswer'>답변 등록</button>
						    </td>
						 </tr>
					</table>
				</form>
			</div>

	    </div>
	</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>