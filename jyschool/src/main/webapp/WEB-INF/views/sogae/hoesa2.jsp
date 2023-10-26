<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
<div class="content">
			

<!-- sub_common -->
<div class="sub_common">
	<div class="sub_visual" style="background-image: url(&quot;/img/sub_visual00.jpg?ver&quot;);">
		<div class="text">
			<h6 class="mont">Ssangyong Institute of System Technology</h6>
			<h2>교육센터소개</h2>
			<i></i>
		</div>
	</div>
	<div class="sub_route">
		<div class="inner">
			<div class="home">
				<a href="/" class="mont">
					<img src="/img/sub_route_home.png" alt="home"> HOME
				</a>
				<i></i>
			</div>
			<ul><li><a href="/about/intro/index.jsp">교육센터소개</a></li><li><a href="/about/intro/index.jsp">센터소개</a></li></ul>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		var url = window.location.pathname;
		var urlPathname = url.substring( 0, url.substring(1).indexOf('/') + 2 );
		var fileName = url.substring(url.lastIndexOf('/') + 1);
		var isForDev = fileName == "index.jsp" || fileName == "read.jsp" || fileName == "edit.jsp" || fileName == "write.jsp" || fileName == "password.jsp";
		
		// sub_common
		$("#header .gnb ul.depth02 li a").each(function() {
			if ( $(this).attr("href") == url ){
				var idx = $(this).parent().parent().parent().index();
				var title = $(this).parent().parent().parent().find('>a').text(); // depth01
				var link = $(this).parent().parent().parent().find('>a').attr('href'); // depth01 링크
				var depth02 = $(this).parent().html(); // depth02

				$('.sub_visual').css("background-image", "url('/img/sub_visual0" + idx + ".jpg?ver')");
				$('.sub_visual .text h2').text( title );
				$('.sub_route ul').append('<li><a href="' + link + '">' + title + '</a></li>');
				$('.sub_route ul').append("<li>" + depth02 + "</li>");
			}else if ( isForDev ){
				if ( $(this).attr("href").indexOf( url.substring(0, url.lastIndexOf('/')+1 ) ) > -1 ){
					var idx = $(this).parent().parent().parent().index();
					var title = $(this).parent().parent().parent().find('>a').text(); // depth01
					var link = $(this).parent().parent().parent().find('>a').attr('href'); // depth01 링크
					var depth02 = $(this).parent().html(); // depth02

					$('.sub_visual').css("background-image", "url('/img/sub_visual0" + idx + ".jpg?ver')");
					$('.sub_visual .text h2').text( title );
					$('.sub_route ul').append('<li><a href="' + link + '">' + title + '</a></li>');
					$('.sub_route ul').append("<li>" + depth02 + "</li>");
				}
			}
		});
	});
</script>
<!-- sub_common // -->

			<div class="intro_con01">
				<div class="inner">
					<div class="sub_tit">
						<h3>센터소개</h3>
						<h4 class="mont"><b>CENTER</b> INTRODUCTION</h4>
					</div>
					<div class="img_box">
						<img src="/jyschool/img/intro_con01_img01.png" alt="img">
					</div>
					<div class="txt_box">
						<h4 class="mont">SINCE 1985.</h4>
						<div class="desc">
							<h5>미래 IT인재의 요람 <span>쌍용교육센터</span>는,</h5>
							<h6>10년 이상의 실무경력을 갖춘 <b>우수한 강사진</b>과 <b>최신교육시설</b>, <br class="pc_br">
							<b>실무중심의 커리큘럼</b>으로 현장 맞춤형 인재를 양성합니다.</h6>

							<div class="hr"></div>
							<p>
								쌍용교육센터는 1985년부터 다양한 컴퓨터 교육서비스를 제공하며 IT분야로 취업을 <br class="pc_br">
								원하는 많은 취업 준비생을 취업의 길로 이끌어 온 IT전문 교육기관입니다.
							</p>
							<p>
								오랜 기간 동안 축적된 교육운영 노하우를 바탕으로 체계적이고 안정적인 환경에서 <br class="pc_br">
								전문인재 양성을 하여 6만 명 이상의 수료생을 배출하였고, <br class="pc_br">
								현업에서 쌍용교육센터 출신 IT전문가들이 다방면에서 역량을 발휘하고 있습니다.
							</p>
							<p>
								쌍용교육센터는 전 직원 모두가 한마음이 되어 훈련생 여러분을 정직하게 교육시키고, <br class="pc_br">
								양질의 교육을 제공하여 여러분의 취업과 자기계발을 위하여 도움이 되도록 최선을 <br class="pc_br">
								다하겠습니다.
							</p>
							<p><b>세상의 중심에서 역량을 펼쳐 나아갈 여러분의 도전을 응원합니다.</b></p>
						</div>
					</div>
				</div>
			</div>

			<div class="intro_con02">
				<div class="inner">
					<div class="txt_box">
						<h4 class="mont">SSANGYONG Institute of <br class="pc_br">System Technology</h4>
						<h6>
							IT 인재양성의 범위가 다른 <br>
							<b>쌍용교육센터</b>
						</h6>
					</div>
					<div class="chart_box">
						<img src="/img/intro_con02_chart.png" alt="chart">
						<div class="text01">
							<h6 class="mont">1985</h6>
							<i></i>
							<p>‘쌍용컴퓨터교육센터’ 설립</p>
						</div>
						<div class="text02">
							<h6 class="mont">2005</h6>
							<i></i>
							<p>교육 특화를 위하여 ‘쌍용교육센터’로 <br>사업자 및 CI변경</p>
						</div>
						<div class="text03">
							<h6 class="mont">2020</h6>
							<i></i>
							<p>최우수훈련기관 <br>6만 여명 이상 수료생 배출 <br>NCS기반 교육 진행</p>
						</div>
					</div>
				</div>
			</div>

			<div class="intro_con03">
				<div class="inner">
					<div class="top_box">
						<h3 class="mont">VISION&amp;MISSION</h3>
						<div class="hr"></div>
						<h4>‘세상을 변화시키는 IT전문가 양성으로 사회에 공헌한다.’</h4>
						<p>쌍용교육센터는 빠르게 변화하는 IT기술의 흐름을 파악하여 경쟁력 있는 IT전문가 양성을 통하여 사회공헌에 이바지 하고자 합니다. </p>
						<ul>
							<li>
								<div class="box">
									<div class="img_box"><img src="/img/intro_con03_icon01.png" alt="icon"></div>
									<h6>고객만족도 <br><b class="mont">BEST</b></h6>
								</div>
							</li>
							<li>
								<div class="box">
									<div class="img_box"><img src="/img/intro_con03_icon02.png" alt="icon"></div>
									<h6>인재역량 <br><b class="mont">BEST</b></h6>
								</div>
							</li>
							<li>
								<div class="box">
									<div class="img_box"><img src="/img/intro_con03_icon03.png" alt="icon"></div>
									<h6>교육품질 <br><b class="mont">BEST</b></h6>
								</div>
							</li>
						</ul>
					</div>
					<div class="dl_box">
						<dl>
							<dt><img src="/img/intro_con03_img01.jpg" alt="img"></dt>
							<dd>
								<h5>취업교육</h5>
								<i></i>
								<p>
									정부지원IT전문 위탁교육 및 <br class="pc_br">
									취업지원 서비스 제공 <br class="pc_br">
									고용노동부/교육부/중소기업청/ <br class="pc_br">
									한국산업인력공단/미래창조과학부 <br class="pc_br">
									등
								</p>
							</dd>
						</dl>
						<dl>
							<dt><img src="/img/intro_con03_img02.jpg" alt="img"></dt>
							<dd>
								<h5>정부지원 IT교육</h5>
								<i></i>
								<p>
									IT교육 컨설팅 및 기업·기관 교육 <br class="pc_br">
									아웃소싱, 재직자 대상 교육서비스
								</p>
							</dd>
						</dl>
						<dl>
							<dt><img src="/img/intro_con03_img03.jpg" alt="img"></dt>
							<dd>
								<h5>SI/SM 사업</h5>
								<i></i>
								<p>
									교육관련 SW 및 HW 솔루션사업
								</p>
							</dd>
						</dl>
						<dl>
							<dt><img src="/img/intro_con03_img04.jpg" alt="img"></dt>
							<dd>
								<h5>교육 컨설팅</h5>
								<i></i>
								<p>
									KT, 한국정보인증, 신세기캐피탈 등 <br class="pc_br">
									다수 SI/SM 사업진행
								</p>
							</dd>
						</dl>
					</div>
				</div>
			</div>

		</div>
		
		<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>