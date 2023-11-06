<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />
<style type="text/css">
.body-main {
	max-width: 800px;
}

.list-header {
	padding-top: 25px;
	padding-bottom: 10px;
	display: flex;
	justify-content: space-between;
}

.list-header .list-header-left {
	padding-left: 5px;
	display: flex;
	align-items: center;
}

.list-header .list-header-right {
	padding-right: 5px;
	display: flex;
	align-items: center;
}

.list-content {
	list-style: none;
	width: 100%;
	margin-bottom: 5px;
	display: grid;
	grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
	gap: 10px;
	justify-content: center;
	text-align: center;
}

.items {
	text-align: center;
}

.item {
	display: inline-block;
	padding: 5px 5px 0;
	width: 100%;
	height: 300px;
	position: relative;
	margin: 10px;
	border: 1px solid #ced4da;
}

.item img {
	position: absolute;
	top: 50%;
	border-radius: 5px;
	left: 0;
	width: 100%;
	height: 300px;
	display: block;
	transform: translate(0, -50%);
}

.caption {
	width: inherit;
	height: inherit;
	background-color: rgba(0, 0, 0, 0.3);
	position: absolute;
	top: 0;
	left: 0;
	color: #fff;
	padding: 20px;
	box-sizing: border-box;
	opacity: 0;
	transition: 0.5s;
}

.caption a {
	color: #fff;
	background-color: #333;
	text-decoration: none;
	padding: 7px;
	border-radius: 3px;
}

.caption a:hover {
	background-color: #fff;
	color: #000;
}

.item:hover .caption {
	opacity: 1;
}

.card-title {
	font-size: 14px;
	font-weight: 500;
	padding: 10px 2px;
	width: 175px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	position: relative;
	top: 290px;
}
</style>

</head>
<body>

	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	</header>

	<main>
		<div class="container body-container">
			<div class="body-title">
				<h2>
					<i class="far fa-image"></i> 강사진 소개
				</h2>
			</div>

			

				<ul class="list-content">
					<c:forEach var="dto" items="${list}">
						<li class="item ">
							<img
								src="${pageContext.request.contextPath}/uploads/photo/${dto.tecImg}">
								
							<div class="caption">
								<p>${dto.tecRecord }</p>
							</div>
							<p class="card-title">${dto.userName}</p>
						</li>
						
					</c:forEach>
				</ul>

				<div class="page-navigation">${ dataCount == 0 ? "등록된 게시물이 없습니다." : paging }
				</div>

			</div>
		</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
	</footer>
	<script src="script/custom.js"></script>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>