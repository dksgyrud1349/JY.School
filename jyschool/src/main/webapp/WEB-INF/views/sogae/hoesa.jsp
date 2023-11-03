<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
    	<meta name="keyword" content="시원스쿨,시원스쿨영어,시원스쿨환급,시원스쿨환급반,시원스쿨챌린지,시원스쿨100일,시원스쿨교재,시원스쿨그래머인유즈,시원스쿨기초영어,시원스쿨기초영어법,시원스쿨닷컴,시원스쿨비즈니스영어,시원스쿨비지니스영어,시원스쿨시원펜,시원스쿨아이패드,시원스쿨여행영어,시원스쿨영문법,시원스쿨영어,시원스쿨영어회화,시원스쿨왕초보영어,시원스쿨왕초보탈출,시원스쿨인강,시원스쿨쿠폰,시원스쿨탭,시원스쿨패밀리탭,시원스쿨펜,시원스쿨할인,시원스쿨회화,시원스쿨후기,시원영어,시원펜,이시원영어,시원스쿨태블릿,시원스쿨왕초보,시원스쿨레벨테스트,SIWONSCHOOL,영어회화,영어인강,영어공부혼자하기,그래머인유즈,영어회화인강,영어아이패드,영어공부,여행영어,영어회화강의,아이패드영어,GRAMMARINUSE,영어강의,영어,기초영어인강,성인영어인강,영어인강추천,생활영어회화,비즈니스영어회화,영어온라인수강,영어회화태블릿,그래머인유즈베이직,영어아이패드패키지,영어온라인강의,영어회화탭,일일영어회화,영어스피킹,회화인강,비즈니스영어,기초영어강의,해외여행회화,영어회화독학,직장인영어인강,문법인강,온라인회화,해외여행영어회화,비지니스영어강의,성인영어회화,영어말하기,실생활영어,영어초보,왕기초영어회화,즐거운영어공부,하루영어,성인영어인터넷강의,영어스피킹공부,영어회화공부,그래머인유즈강의,온라인영어,그래머인유즈인강,비즈니스회화,비지니스영어,영어숙어,영어스쿨,영어학습교재,비지니스영어회화,스피킹영어,영어단어어플,온라인영어회화,영어회화문법,기초영어,영어공부유튜브,영어스피킹어플,외국어배우기,기본영어회화,베이직그래머인유즈,영어면접,영어면접인강,영어추천,무료레벨테스트,성인영어회화추천,영단어사전,영어인터넷강의,왕초보영어배우기,직장인영어문법,오늘의영어회화,영어회화어플추천,해외여행영어,여행영어어플,영어회화여행,영어회화책,초보영어배우기,BASICGRAMMARINUSE,GRAMMARINUSEINTERMEDIATE,영어독해강의,리얼스쿨,영어회화초급,관광영어강의">
	<meta name="description" content="17년 노하우의 시원스쿨 영어로 2023년 영어 왕초보 탈출! 하루 1강 습관을 바꾸는 힘! 지금 시원스쿨에서 경험하세요">
	<meta property="og:keywords" content="시원스쿨,시원스쿨영어,시원스쿨환급,시원스쿨환급반,시원스쿨챌린지,시원스쿨100일,시원스쿨교재,시원스쿨그래머인유즈,시원스쿨기초영어,시원스쿨기초영어법,시원스쿨닷컴,시원스쿨비즈니스영어,시원스쿨비지니스영어,시원스쿨시원펜,시원스쿨아이패드,시원스쿨여행영어,시원스쿨영문법,시원스쿨영어,시원스쿨영어회화,시원스쿨왕초보영어,시원스쿨왕초보탈출,시원스쿨인강,시원스쿨쿠폰,시원스쿨탭,시원스쿨패밀리탭,시원스쿨펜,시원스쿨할인,시원스쿨회화,시원스쿨후기,시원영어,시원펜,이시원영어,시원스쿨태블릿,시원스쿨왕초보,시원스쿨레벨테스트,SIWONSCHOOL,영어회화,영어인강,영어공부혼자하기,그래머인유즈,영어회화인강,영어아이패드,영어공부,여행영어,영어회화강의,아이패드영어,GRAMMARINUSE,영어강의,영어,기초영어인강,성인영어인강,영어인강추천,생활영어회화,비즈니스영어회화,영어온라인수강,영어회화태블릿,그래머인유즈베이직,영어아이패드패키지,영어온라인강의,영어회화탭,일일영어회화,영어스피킹,회화인강,비즈니스영어,기초영어강의,해외여행회화,영어회화독학,직장인영어인강,문법인강,온라인회화,해외여행영어회화,비지니스영어강의,성인영어회화,영어말하기,실생활영어,영어초보,왕기초영어회화,즐거운영어공부,하루영어,성인영어인터넷강의,영어스피킹공부,영어회화공부,그래머인유즈강의,온라인영어,그래머인유즈인강,비즈니스회화,비지니스영어,영어숙어,영어스쿨,영어학습교재,비지니스영어회화,스피킹영어,영어단어어플,온라인영어회화,영어회화문법,기초영어,영어공부유튜브,영어스피킹어플,외국어배우기,기본영어회화,베이직그래머인유즈,영어면접,영어면접인강,영어추천,무료레벨테스트,성인영어회화추천,영단어사전,영어인터넷강의,왕초보영어배우기,직장인영어문법,오늘의영어회화,영어회화어플추천,해외여행영어,여행영어어플,영어회화여행,영어회화책,초보영어배우기,BASICGRAMMARINUSE,GRAMMARINUSEINTERMEDIATE,영어독해강의,리얼스쿨,영어회화초급,관광영어강의">
	<meta property="og:description" content="17년 노하우의 시원스쿨 영어로 2023년 영어 왕초보 탈출! 하루 1강 습관을 바꾸는 힘! 지금 시원스쿨에서 경험하세요">
	<meta property="og:type" content="website">
	<meta property="og:url" content="http://www.siwonschool.com/?s=siwon_info">
	<meta property="og:site_name" content="시원스쿨 영어 공식 사이트 | 영어는 역시 시원스쿨">
	<meta property="og:title" content="시원스쿨 영어 공식 사이트 | 영어는 역시 시원스쿨">
	<meta property="og:description" content="17년 노하우의 시원스쿨 영어로 2023년 영어 왕초보 탈출! 하루 1강 습관을 바꾸는 힘! 지금 시원스쿨에서 경험하세요"/>
	<meta property="og:image" content="http://siwon-cdn.siwonschool.com/pc/www/header/200x200.png">
	<meta name="twitter:image" content="http://siwon-cdn.siwonschool.com/pc/www/header/400x400.png">
	<meta name="twitter:card" content="summary">
	<meta name="twitter:url" content="http://www.siwonschool.com/?s=siwon_info">
	<meta name="twitter:description" content="17년 노하우의 시원스쿨 영어로 2023년 영어 왕초보 탈출! 하루 1강 습관을 바꾸는 힘! 지금 시원스쿨에서 경험하세요">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="apple-mobile-web-app-title" content="시원스쿨 기초영어">
	<meta name="viewport" content="width=1200">
	<meta name="referrer" content="no-referrer-when-downgrade" />
	<title>준영스쿨 코딩 공식 사이트 | 코딩은 역시 준영스쿨</title>

	<link href="//siwon-cdn.siwonschool.com/pc/www/header/favicon.ico" rel="shortcut icon">
	<link rel="canonical" href="http://www.siwonschool.com">
	<link rel="apple-touch-icon-precomposed"  href="//siwon-cdn.siwonschool.com/pc/www/header/56x56.png" />
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="//siwon-cdn.siwonschool.com/pc/www/header/114x114.png" />

	<!-- webfont -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
	<link href="//spoqa.github.io/spoqa-han-sans/css/SpoqaHanSans-kr.css?t=1" rel="stylesheet">

	<!-- css -->
	<link href="/common/css/comm.css?1698297305" rel="stylesheet">
	<link href="/common/css/layout.css?1698297305" rel="stylesheet">
	<link href="/common/css/table.css?1698297305" rel="stylesheet">
	<link href="/common/css/layerpop_mov.css?1698297305" rel="stylesheet">
	<link href="/common/css/template.css?1698297305" rel="stylesheet">
	<link href="/common/css/video_js.css?v2" rel="stylesheet">
	<link href="//assets.siwonschool.com/vendor/swiper/swiper.min.css" rel="stylesheet">
	<link href="//assets.siwonschool.com/css/keyframes.css?1698297305" rel="stylesheet">
	<link href="/common/css/eng_style_v5.css?1698297305" rel="stylesheet">
	<link href="/common/css/core.min.css?1698297305" rel="stylesheet">
	<link href="/common/css/module/siwonschool.css?1698297305" rel="stylesheet">
	<!-- script -->
	<script src="//assets.siwonschool.com/vendor/jquery/jquery-3.5.1.min.js"></script>
	<script src="//assets.siwonschool.com/vendor/jquery/jquery-migrate-3.3.2.min.js"></script>
	<script src="//assets.siwonschool.com/vendor/jquery/jquery-ui.min.js"></script>
	<script src="//assets.siwonschool.com/vendor/swiper/swiper.min.js"></script>
	<script src="//assets.siwonschool.com/vendor/bxslider/jquery.bxslider.min.js"></script>
	<script src="//assets.siwonschool.com/vendor/countdown/jquery.countdown.min.js"></script>
	<script src="//assets.siwonschool.com/vendor/number/jquery.number.min.js"></script>
	<script src="//assets.siwonschool.com/vendor/etc/jquery.cookie.js"></script>
	<script src="//assets.siwonschool.com/vendor/etc/jquery.lazy.min.js"></script>
	<script src="//assets.siwonschool.com/vendor/etc/lodash.min.js"></script>
	<!-- <script src="/common/js/flashView.js"></script> -->
	<script src="/common/js/video.js"></script>
	<script src="/common/js/imgLiquid-min.js"></script>
	<script src="//assets.siwonschool.com/js/pc/comm.js?1698297305"></script>
	<script src="//assets.siwonschool.com/vendor/etc/lazysizes.min.js?1698297305" async=""></script>

	<script src="/common/js/eng_script_v4.js?1698297305"></script>
            <script src="/module/siwonschool/siwonschool.js?date=1698297305"></script>
    
			<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
	
	<!-- LiveLog TrackingCheck Script Start -->
	<script>
		var LLscriptPlugIn = new function () { this.load = function(eSRC,fnc) { var script = document.createElement('script'); script.type = 'text/javascript'; script.charset = 'utf-8'; script.onreadystatechange= function () { if((!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete') && fnc!=undefined && fnc!='' ) { eval(fnc); }; }; script.onload = function() { if(fnc!=undefined && fnc!='') { eval(fnc); }; }; script.src= eSRC; document.getElementsByTagName('head')[0].appendChild(script); }; }; LoadURL = "MAgyCDI2CDI4CDUI"; LLscriptPlugIn.load('//livelog.co.kr/js/plugShow.php?'+LoadURL, 'sg_check.playstart()');
	</script>
	<!-- LiveLog TrackingCheck Script End -->

	<!-- 기초영어 랜딩 내 스크립트 추가 요청의 건(150332) -->
	<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/adfit/static/kp.js"></script>
	<script type="text/javascript">
		kakaoPixel('1291054235488043797').pageView();
	</script>
	<!-- //기초영어 랜딩 내 스크립트 추가 요청의 건(150332) -->

	<!-- 인사이드 -->
	<script async src="//siwonschool.api.useinsider.com/ins.js?id=10002572"></script>
	<!-- 인사이드 -->

	<div id="fb-root"></div>
	<script>(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) return;
			js = d.createElement(s); js.id = id;
			js.src = 'https://connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v2.11&appId=114542389173096';
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));</script>

	<script>
		membersite = "member.siwonschool.com";
	</script>

    <!-- Global site tag (gtag.js) - Google Ads: 10976905257 -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=AW-10976905257"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'AW-10976905257');
    </script>
</head>
<script>
 $(document).ready(function(){

 	/* header renew(2019.06.03 open) */
	if( $.cookie('bnr_header') != '1'){
		$('.bnr_wrap').show();
	} else {
		$('.bnr_wrap').hide();
	}

	$('#siwon_header .bnr_wrap button').click(function(){
		$(this).parents('.bnr_wrap').hide();
		 $.cookie('bnr_header','1');
	});

	 $('#siwon_header .bt_myalert').click(function(){
		 var $target = $('#siwon_header .my_alarm');
		 if( !$target.hasClass('on'))
			 $target.addClass('on');
		 else
			 $target.removeClass('on');
	 });

	 $(document).on('click',function(e){
		 if( !$(e.target).hasClass('my_alarm') && !$(e.target).parent().hasClass('bt_myalert') ) {
			 $('#siwon_header .my_alarm').removeClass('on');
		 }
	 });

	var headerGnbOffset = $( '#header_gnb' ).offset();
	var headerGnbHeight = $( '#header_gnb' ).height()+1;
	$( window ).scroll( function() {
	  if ( $( document ).scrollTop() > headerGnbOffset.top ){
		  $( '#header_gnb' ).addClass( 'fixed' );
		  $( '#siwon_header' ).css('paddingBottom',headerGnbHeight);
	  }else{
		  $( '#header_gnb' ).removeClass( 'fixed' );
		  $( '#siwon_header' ).css('paddingBottom',0);
	  }
	});

	 /* 전체 메뉴 */
	 var allMenu = {
		 open : function(){
			 $('.bt_allview').addClass('on');
			 $('#allmenu').fadeIn(200);
		 },
		 close : function(){
			 $('.bt_allview').removeClass('on');
			 $('#allmenu').fadeOut(200);
		 }
	 };
	 $('.bt_allview').on('click',function(){
		 if( !$(this).hasClass('on') ){
			 allMenu.open();
		 } else {
			 allMenu.close();
		 }
	 });
	 $(document).scroll(function(){
		 if( $('.bt_allview').hasClass('on') ) allMenu.close();
	 });
	 $('#allmenu').after().on('click',function(e){
		 if( !$(e.target).hasClass('menu_dep1'))
			 allMenu.close();
	 });

	 /* gnb 배너영역 */
	 var cardOp = ( $('.ban_card_sl li').length >= 2 )? true : false;
	 var cardSlider = new Swiper('.ban_card_sl .swiper-container', {
		 effect: 'fade',
		 preventInteractionOnTransition: true,
		 loop: true,
		 autoplay: cardOp,
	 });
	 if(cardOp) $('.ban_card_sl .swiper-navigation').addClass('show');

	 /* gnb 수강신청 배너영역 */
	 var gnbSl = new Swiper('.gnb_sl .swiper-container', {
		 preventInteractionOnTransition: true,
		 loop: true,
		 autoplay: true,
		 speed: 1500,
		 pagination: {
			 el: '.gnb_sl .swiper-pagination',
			 type: 'bullets',
			 clickable: true,
		 },
	 });

	$('.menu_dep2.has_sl').siblings('a').mouseover(function(){
		setTimeout(function(){
			gnbSl.update();
		},300);
		gnbSl.autoplay.start();
	});
	$('.menu_dep2.has_sl').siblings('a').mouseleave(function(){
		gnbSl.autoplay.stop();
	});
});
</script>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<body>
<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
<style>
/* 공통 */
.w1200{width:1200px;position:relative;left:50%;transform:translateX(-50%)}
.w1300{width:1300px;position:relative;left:50%;transform:translateX(-50%)}

/* animation */
@keyframes fade_up{
	from {
		opacity:0;
		transform:translateY(30%);
	}
	to {
		opacity:1;
		transform:translateY(0);
	}
}

@keyframes bg_flow{
	from {
		background-position-x:0;
	}
	to {
		background-position-x:-1000%;
	}
}

@keyframes penAction {
	0%,25%,40%,90%,100%{transform:translate3d(0,0,0)}
	20%,35%{transform:translate3d(10px,-10px,0)}
}

@keyframes penSoundAction {
	0%{opacity:0}
	50%{opacity:1}
	100%{opacity:0}
}

@keyframes typingAni {
  0%{width:0%}
  50%{width:100%}
  100%{width:100%}
}

@keyframes cursor{
  0%{opacity:0;}
  50%{opacity:1;}
  100%{opacity:0;}
}

/* 사이트 */
.sec01{position:relative;min-height:1080px}
.sec01 .bg{width:100%;max-width:2000px;touch-action:none;user-select:none;pointer-events:none}
.sec01 .cont{display:flex;flex-direction:column;align-items:center;position:absolute;top:0;left:0;width:100%;height:100%;background:rgba(0,0,0,0.6)}
.sec01 .cont .tit{margin-top:327px}
.sec01 .cont .tit .tit2{position:absolute;top:0;left:0;opacity:0}
.sec01.active .cont .tit .tit2{animation:fade_up 1s ease-in-out 1 forwards}
.sec01 .cont .bt{margin-top:46px}
.sec01 .cont .txt{margin-top:209px}
.sec02{padding:185px 0 170px}
.sec02 ul{display:flex;flex-wrap:wrap;width:1100px;margin:0 auto;}
.sec02 ul li{width:50%;opacity:0}
.sec02 ul li .txt{margin-top:43px}
.sec02 ul li:nth-child(2){padding-top:122px}
.sec02 ul li:nth-child(3){padding-top:23px}
.sec02 ul li:nth-child(4){padding-top:157px}
.sec02.active ul li:nth-child(1){animation:fade_up 1s ease-in-out 1 forwards}
.sec02.active ul li:nth-child(2){animation:fade_up 1s ease-in-out 1 forwards;animation-delay: 0.3s}
.sec02.active ul li:nth-child(3){animation:fade_up 1s ease-in-out 1 forwards;animation-delay: 0.6s}
.sec02.active ul li:nth-child(4){animation:fade_up 1s ease-in-out 1 forwards;animation-delay: 0.9s}
.sec02 ul li:nth-child(even){padding-left:60px;box-sizing:border-box}
.sec03 .box {height:calc( 100vmin - 59px )/*height:965px*/;background:url('//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec03_bg_2.jpg') no-repeat top center;background-size:cover;text-align:center}
.sec03 .swiper-container{height:inherit}
.sec03 .swiper-container .swiper-slide{display:flex;justify-content:center;align-items:center}

.sec11{min-height:1073px;padding-top:200px;box-sizing:border-box;background:#010101 url('//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec11_bg.jpg') no-repeat -96px 0}
.sec11 h3{padding-bottom:45px}
.sec11 .typing{margin-top:34px}
.sec11 .typing span{display:block;position:relative;max-width:270px;width:0;height:38px;margin-top:2px;color:#fff;font-size:30px;white-space:nowrap;overflow:hidden;letter-spacing:-0.03em}
.sec11 .typing span:after{position:absolute;display:block;content:"";width:10px;height:2px;bottom:0;right:0;background:#fff;animation:cursor 0.5s step-end infinite;content:''t}
.sec11.active .typing span{animation:typingAni 6s steps(32, end) infinite}
</style>
<script src="//cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.7/ScrollMagic.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.7/plugins/debug.addIndicators.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/gsap/2.1.3/TweenMax.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.5/plugins/animation.gsap.js"></script>
<script>
$(document).ready(function(){

	setTimeout(function(){
		$('.sec01').addClass('active');		
	},100);


	var sec03Sl = new Swiper(".sec03 .swiper-container", {
		direction: "vertical",
		allowTouchMove:false,
	});

	/*
	var nowgoSl = new Swiper('.ai_swiper', {
		slidesPerView: 1,
		speed: 2000,
		mousewheel: true,
		loop: true,
		allowTouchMove: false,
		autoplay: {
			delay: 0,
		},
	});
	*/

	var sec10Sl = new Swiper(".sec10 .sl .swiper-container", {
		allowTouchMove:false,
	});

	let controller = new ScrollMagic.Controller({
	triggerHook: 'onLeave',
	});

	/*
	var tween1 = 
	TweenMax.to('#animate1 .box', 0.5, {
	autoAlpha:0.6
	});
	*/

	var sec02Scene = new ScrollMagic.Scene({
		triggerElement: "#sec02_trigger",
		//duration: "100%"
	})
	.on('start',function(e){
		if ( e.scrollDirection == 'FORWARD' ){
			$('.sec02').addClass('active')
		}
	})
	.addTo(controller);

	var sec03Scene = new ScrollMagic.Scene({
		triggerElement: "#sec03_trigger",
		duration: 2500,
		offset: -59,
		triggerHook: 0
	})
	.setPin('.sec03')
	.on('progress',function(e){
		let length = $('.sec03 .swiper-slide').length;
			//progress / 3
		//개선 필요
		if ( e.progress >= 0 && e.progress < '0.33' ){
			sec03Sl.slideTo(0);
		}else if ( e.progress > '0.33' && e.progress <= '0.66' ) {
			sec03Sl.slideTo(1);
		}else if ( e.progress > '0.66' && e.progress <= 1 ){
			sec03Sl.slideTo(2);
		}
	})
	.addTo(controller);

	

	/*
	var sec07Scene = new ScrollMagic.Scene({
		triggerElement: "#sec07_trigger",
		triggerHook: 0.4
	})
	.on('start',function(e){
		if ( e.scrollDirection == 'FORWARD' ){
			$('.sec07').addClass('active')
		}
	})
	.addTo(controller);
	*/

	

	var sec11Scene = new ScrollMagic.Scene({
		triggerElement: "#sec11_trigger",
		triggerHook: 0.1
	})
	.on('start',function(e){
		if ( e.scrollDirection === 'FORWARD' ){
			$('.sec11').addClass('active')
		}
	})
	.addTo(controller);

});//jq
</script>

<div id="siwon_container" class="event_section">


	<div class="w2000">

		<div class="sec01" id="sec01_trigger">
			<video id="visual_bg" class="bg" poster="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/video_poster.jpg" preload="auto" autoplay loop playsinline muted>
				<source src="//www-ndrm.siwonschool.com/eng/siwon_info_visual.mp4?1698297305" type="video/mp4"></source>
			</video>
			<div class="cont">
				<div class="position tit">
					<img src="http://localhost:9090/jyschool/img/jschool.png" alt="" aria-hidden="true" class="tit1">
					<img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec01_tit2.png" alt="" aria-hidden="true" class="tit2">
				</div>
				<button class="bt" onclick="playYoutubeComm('3RHUKHv3ug4');"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec01_bt.png" alt=""></button>
				<img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec01_txt.png" alt="" class="txt">
			</div>
		</div>

		<div class="sec02" id="sec02_trigger">
			<ul data-effect>
				<li>
					<p class="tit"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec02_tit01.png" alt="영어회화는 공부하는 게 아닙니다"></p>
					<p class="txt"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec02_txt01.png" alt="" aria-hidden="true"></p>
				</li>
				<li>
					<p class="tit"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec02_tit02.png" alt="영어는 운동을 배우는 것과 같습니다"></p>
					<p class="txt"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec02_txt02.png" alt="" aria-hidden="true"></p>
				</li>
				<li>
					<p class="tit"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec02_tit03.png" alt="한국에서는 영어를 잘하는 게 어렵습니다"></p>
					<p class="txt"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec02_txt03.png" alt="" aria-hidden="true"></p>
				</li>
				<li>
					<p class="tit"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec02_tit04.png" alt="제대로 된 영어 습관을 만드는 것이 핵심입니다"></p>
					<p class="txt"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec02_txt04.png" alt="" aria-hidden="true"></p>
				</li>
			</ul>
		</div>

		<div class="sec03" id="sec03_trigger">
			<div class="box">
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec03_sl1_2.png" alt="올바른 “영어 학습 습관”을 만들어 드립니다"></div>
						<div class="swiper-slide"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec03_sl2_2.png" alt="영어가 습관처럼 들리고, 습관처럼 입에서 나오게 만들어 드립니다."></div>
						<div class="swiper-slide"><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec03_sl3_2.png" alt="배우는 것에 만족하지 마세요. 실력이 변화하는 것에 만족하셔야 합니다."></div>
					</div>
				</div>
			</div>
		</div>


		<div class="sec11" id="sec11_trigger">
			<div class="w1100">
				<h3><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec11_tit.png" alt=""></h3>
				<p><img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec11_txt.png" alt=""></p>
				<p class="typing">
					<img src="//siwon-cdn.siwonschool.com/www/siwonschool/siwon_info/sec11_typing.png" alt="">
					<span>오늘부터 도전하세요.</span>
				</p>
			</div>
		</div>

	</div>
</div>
<!-- //siwon_container --><style>
	.customer_satisfaction_survey{padding:125px 0 65px}
	.customer_satisfaction_survey *{box-sizing:border-box}
	.customer_satisfaction_survey .w1000{position:relative;left:inherit;max-width:1100px;width:100%!important;margin:0 auto!important}
	.customer_satisfaction_survey .survey_title{padding:15px 41px 13px;font-size:17px;line-height:100%;color:#fff;background-color:#8a8a8a}
	.customer_satisfaction_survey .survey_rate{display:flex;justify-content:space-between;padding:33px 41px;background-color:#fff;border:1px solid #ddd;border-bottom:0}
	.customer_satisfaction_survey .survey_rate_check{flex:0 0 auto;display:flex;align-items:center;font-size:15px;color:#111}
	.customer_satisfaction_survey .survey_rate_check label{margin-right:25px;cursor:pointer}
	.customer_satisfaction_survey .survey_rate_check label:last-child{margin-right:0}
	.customer_satisfaction_survey .survey_rate_check input{position:static!important;margin-right:2px;vertical-align:inherit;cursor:pointer;-webkit-appearance:radio!important}
	.customer_satisfaction_survey .survey_rate_message{flex:1 0 auto;display:flex;align-items:center;padding-left:75px;font-size:15px}
	.customer_satisfaction_survey .survey_rate_message input{flex:1 1 auto;padding:0 12px;width:calc(100% - 100px);height:32px;font-size:inherit;border:1px solid #ddd;border-right:0}
	.customer_satisfaction_survey .survey_rate_message button{flex:0 0 auto;width:100px;height:32px;font-size:inherit;color:#fff;background-color:#111;border:0}
	.customer_satisfaction_survey .survey_result{display:flex;padding:19px 41px 17px;line-height:100%;background-color:#f3f3f3;border:1px solid #ddd;border-top:0}
	.customer_satisfaction_survey .survey_result_notice{flex:0 0 auto;font-size:15px;color:#111}
	.customer_satisfaction_survey .survey_result_update{flex:1 1 auto;font-size:14px;color:#7e7e7e;text-align:right}
	.customer_satisfaction_survey .survey_result_update_date{margin-right:38px}

	/* 일아시아 new 만족도조사 - pc */
	.asia_customer_satisfaction_survey *{box-sizing:border-box}
	.asia_customer_satisfaction_survey .w1000{position:relative;left:inherit;max-width:1100px;width:100%!important;margin:0 auto!important}
	.asia_customer_satisfaction_survey .bgcolor{position:absolute;width:50%;height:80px}
	.asia_customer_satisfaction_survey .bg_01{background:#fbfbfb}
	.asia_customer_satisfaction_survey .bg_02{right:0;background:#eee}
	.asia_customer_satisfaction_survey .survey_rate{display:flex;justify-content:space-between;align-items:center;background-color:#fbfbfb}
	.asia_customer_satisfaction_survey .survey_rate .survey_title{flex:0 0 auto;width:305px;font-size:15px;font-weight:700;color:#888;background-color:#fbfbfb}
	.asia_customer_satisfaction_survey .survey_rate .survey_title .survey_result_update_average{font-weight:400;font-size:12px}
	.asia_customer_satisfaction_survey .survey_rate_check{flex:0 0 auto;display:flex;align-items:center;width:322px;font-size:15px;color:#111;padding:0 38px;text-indent:-999px}
	.asia_customer_satisfaction_survey .survey_rate_check .text{display:block;background:url(//siwon-cdn.siwonschool.com/asia/comm/survey_icon.png)no-repeat top;width:51px;height:52px;cursor:pointer;text-indent:-9999px}
	.asia_customer_satisfaction_survey .survey_rate_check label[for='survey_rate5'] .text{background-position:top left}
	.asia_customer_satisfaction_survey .survey_rate_check label[for='survey_rate4'] .text{background-position-x:-51px;margin-left:2px}
	.asia_customer_satisfaction_survey .survey_rate_check label[for='survey_rate3'] .text{background-position-x:-98px}
	.asia_customer_satisfaction_survey .survey_rate_check label[for='survey_rate2'] .text{background-position-x:-147px}
	.asia_customer_satisfaction_survey .survey_rate_check label[for='survey_rate1'] .text{background-position-x:-196px}
	.asia_customer_satisfaction_survey .survey_rate_check input:checked ~ .text{background-position-y:bottom}
	.asia_customer_satisfaction_survey .survey_rate_check input{position:static!important;margin-right:2px;vertical-align:inherit;cursor:pointer;-webkit-appearance:radio!important}
	.asia_customer_satisfaction_survey .survey_rate_check input[type=radio]{display:none}
	.asia_customer_satisfaction_survey .survey_rate .survey_result{display:flex;width:224px;height:80px;flex:0 0 auto;align-items:center;background-color:#eee}
	.asia_customer_satisfaction_survey .survey_rate .survey_result_notice{padding-left:40px;font-size:14px;color:#111}
	.asia_customer_satisfaction_survey .survey_rate .survey_rate_message{display:flex;align-items:center;width:100%;height:80px;font-size:15px;background:#eee}
	.asia_customer_satisfaction_survey .survey_rate .survey_rate_message .AsiacustomerMessage{display:inline-block;padding:0 12px;width:187px;height:40px;line-height:40px;font-size:inherit;background:#fff;border:1px solid #ddd;border-right:0;text-overflow:ellipsis;white-space: nowrap;word-wrap:break-word;overflow:hidden}
	.asia_customer_satisfaction_survey .survey_rate .survey_rate_message button{flex:0 0 auto;width:62px;height:40px;font-size:12px;line-height:40px;color:#fff;background-color:#666;border:0}

	@media all and (max-width: 1023px) {
		.customer_satisfaction_survey{padding:30px 0 0;word-break:keep-all}
		.customer_satisfaction_survey.japan,.customer_satisfaction_survey.china,.customer_satisfaction_survey.vietnam,.customer_satisfaction_survey.thai,.customer_satisfaction_survey.indonesia{padding:0}
		.customer_satisfaction_survey .survey_title{padding:8px 38px;line-height:30px}
		.customer_satisfaction_survey .survey_rate{display:block;padding:22px 38px}
		.customer_satisfaction_survey .survey_rate_check{display:block}
		.customer_satisfaction_survey .survey_rate_check label{display:inline-block;margin:0 25px 16px 0}
		.customer_satisfaction_survey .survey_rate_check label:last-child{margin-right:0}
		.customer_satisfaction_survey .survey_rate_message{margin-top:4px;padding-left:0}
		.customer_satisfaction_survey .survey_rate_message button{width:80px}
		.customer_satisfaction_survey .survey_result{display:flex;padding:19px 20px 17px;line-height:100%;background-color:#f3f3f3;border:1px solid #ddd;border-top:0}
		.customer_satisfaction_survey .survey_result_notice{display:none}
		.customer_satisfaction_survey .survey_result_update{flex:1 1 auto;font-size:13px;text-align:center}
		.customer_satisfaction_survey .survey_result_update_date{margin-right:12px}
		
		/* 일아 만족도 조사 - mo */
		.asia_customer_satisfaction_survey_mo .survey_title{padding:9.375vw 0 4.688vw;text-align:center;letter-spacing:-1.6px;font-size:3.75vw;line-height:3.75vw;font-weight:700;color:#888;background:#fbfbfb}
		.asia_customer_satisfaction_survey_mo .survey_info{display:flex;padding:0 6.25vw 6.25vw;background:#fbfbfb}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_check{width:25vw;padding:0 1.563vw 0 0;box-sizing:content-box;background:#fff}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_check select{width:100%;height:9.375vw;padding:0 15px;font-size:3.438vw;line-height: 3.438vw;background:url(//img.siwonschool.com/japan/m/comm/arw_d6.png) no-repeat 105% 50%;background-size:auto 1.094vw;border:2px solid #ddd}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_message{display:flex;align-items:center}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_message .AsiacustomerMessage{flex:1 1 auto;width:43.906vw;height:9.375vw;line-height: 8.5vw;font-size:3.438vw;padding-left:3.125vw;color:#999;border:2px solid #ddd;border-right:0;box-sizing:border-box;background: #fff;text-overflow:ellipsis;white-space:nowrap;word-wrap:normal;overflow:hidden}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_message button{flex:0 0 auto;width:17.031vw;height:9.375vw;font-size:3.438vw;background:#111;color:#fff}
		.asia_customer_satisfaction_survey_mo .survey_result{padding:2.344vw 4.688vw;font-size:2.813vw;color:#999;background:#eee;text-align:center}
		.asia_customer_satisfaction_survey_mo .survey_result .survey_result_notice{padding-right:5.469vw;font-weight:500;letter-spacing:-1px}
		.asia_customer_satisfaction_survey_mo .survey_result .survey_result_update_average{display:block;letter-spacing:-.5px;padding-top:0.781vw}
	}
	@media all and (min-width: 1024px) {
		/* 일아 만족도 조사 mo - ipad pro 이상 */
		.asia_customer_satisfaction_survey_mo .survey_title{padding:9.375vw 0 4.688vw;text-align:center;letter-spacing:-1.6px;font-size:3.75vw;line-height:3.75vw;font-weight:700;color:#888;background:#fbfbfb}
		.asia_customer_satisfaction_survey_mo .survey_info{display:flex;padding:0 6.25vw 6.25vw;background:#fbfbfb}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_check{width:25vw;padding:0 1.563vw 0 0;box-sizing:content-box;background:#fff}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_check select{width:100%;height:9.375vw;padding:0 15px;font-size:3.438vw;line-height: 3.438vw;background:url(//img.siwonschool.com/japan/m/comm/arw_d6.png) no-repeat 105% 50%;background-size:auto 1.094vw;border:2px solid #ddd}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_message{display:flex;align-items:center}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_message .AsiacustomerMessage{flex:1 1 auto;width:43.906vw;height:9.375vw;line-height: 8.5vw;font-size:3.438vw;padding-left:3.125vw;color:#999;border:2px solid #ddd;border-right:0;box-sizing:border-box;background: #fff;text-overflow:ellipsis;white-space:nowrap;word-wrap:normal;overflow:hidden}
		.asia_customer_satisfaction_survey_mo .survey_info .survey_rate_message button{flex:0 0 auto;width:17.031vw;height:9.375vw;font-size:3.438vw;background:#111;color:#fff}
		.asia_customer_satisfaction_survey_mo .survey_result{padding:2.344vw 4.688vw;font-size:2.813vw;color:#999;background:#eee;text-align:center}
		.asia_customer_satisfaction_survey_mo .survey_result .survey_result_notice{padding-right:5.469vw;font-weight:500;letter-spacing:-1px}
		.asia_customer_satisfaction_survey_mo .survey_result .survey_result_update_average{display:block;letter-spacing:-.5px;padding-top:0.781vw}
	}

</style>
<script type="text/javascript">

var rsrc_area  = $("#rsrc_area");
var page_rsrcF = $("form[name=page_rsrcF]",rsrc_area);
var rsrc_sno   = $("input[name=page_sno]",page_rsrcF).val();
var today      = $("input[name=today]",page_rsrcF).val();
var lastUpdate = $("input[name=lastUpdate]",page_rsrcF).val();

function sendSurvey(form) {

	var type = $("[name=surveyRate]").attr('type');
	if(type == "select"){
		var selector =	"[name=surveyRate]";
	}else{
		var selector =	"[name=surveyRate]:checked";
	}

	if($(selector,page_rsrcF).length>0){

		var param = page_rsrcF.serialize();
		var place_chk= "p";
		if( place_chk=="m" && "basics"!="basics" ){
			var url="/m/";
		}else{
			var url="/"
		}

		$.ajax({
			url: url,
			data: param,
			type: "POST",
			dataType: "JSON",
			success: function(data){
				if(data.msg=="ok"){
					alert("제출되었습니다.");
					$.cookie("page_rsrc_"+rsrc_sno,today,{expires:30});
					rsrc_area.hide();
				}else{
					alert(data.msg);
					location.href();
				}
			},
			error : function(request,status,error){
				alert("code:"+request.status+"\n"+"\n"+"error:"+error);
			}
		});

	}else{
		
		alert("만족도를 선택해주세요.");
		return false;
	}
}

// 일아시아 입력창 닫기
function surveyPopup() {
	var customerMessage = $('textarea[name="customerMessage"]').val();
	
	$(".AsiacustomerMessage").text(customerMessage);
	$("input[name=customerMessage]",page_rsrcF).val(customerMessage);
	layerPopClose();
}


$(document).ready(function(){
	var place_chk    = "p";
	var rsrc_sno     = $("input[name=page_sno]").val();
	var myLastUpdate = $.cookie("page_rsrc_"+rsrc_sno);
	var lastUpdate   = $("input[name=lastUpdate]").val();

	if(!myLastUpdate || myLastUpdate<lastUpdate){
//		var width = $(".inner_container").css("width").replace('px','');
//		rsrc_area.children().first().attr("class","w"+width);
		rsrc_area.show();
	}


	//일아시아 - 의견 입력창 클릭시 레이어 팝업 노출
	var cst_message = $(".AsiacustomerMessage");
		cst_message.click(function(){
			layerPopId('layer_survey_common');
	});
});
</script>

<!-- 만족도 조사 의견 레이어 팝업 -->
<style>
	.wrap_layer_popup#layer_survey_common{width:800px;height:auto;left:50%;margin-left:-400px;background:#fff}
	.wrap_layer_popup#layer_survey_common .pop_close{position:absolute;top:-50px;right:0;z-index:10;overflow:hidden}
	.wrap_layer_popup#layer_survey_common .pop_cont{padding: 30px 40px}
	.wrap_layer_popup#layer_survey_common .pop_cont textarea{padding:30px;width:100%;height:200px;font-size:20px;color:#111;background-color:#f6f6f6;border:0;box-sizing:border-box;resize:none;vertical-align:middle}
	.wrap_layer_popup#layer_survey_common .pop_cont .btn{display:block;margin:20px auto 0;width:300px;height:60px;font-size:20px;font-weight:normal;color:#fff;text-align:center;background-color:#111}
	
	@media all and (max-width: 1023px) {
		.wrap_layer_popup#layer_survey_common{width:90.625vw;height:auto;margin-left:-45.313vw;background:#fff}
		.wrap_layer_popup#layer_survey_common .pop_close{position:absolute;width:6.25vw;height:auto;top:-7.813vw;right:0;z-index:10;overflow:hidden}
		.wrap_layer_popup#layer_survey_common .pop_close img{width:100%}
		.wrap_layer_popup#layer_survey_common .pop_cont{padding:3.906vw 4.375vw}
		.wrap_layer_popup#layer_survey_common .pop_cont textarea{padding:4.688vw;width:100%;height:31.25vw;font-size:3.125vw;color:#111;background-color:#f6f6f6;border:0;resize:none;vertical-align:middle}
		.wrap_layer_popup#layer_survey_common .pop_cont .btn{display:block;margin-top:3.125vw;width:46.875vw;height:9.375vw;font-size:3.125vw;font-weight:400;color:#fff;text-align:center;background-color:#111}
	}
</style>
<input type="hidden" name="Log_no" value="">
<input type="hidden" name="tcode" value="">
<script type="text/javascript">
$(document).ready(function(){
	/* 온라인 상담 신청 */
	$(".r_quick_menu .q1").click(function more() {
		$(".advice_form").show();
	});
	$(".close").click(function more() {
		$(".advice_form").hide();
	});
	/*개인정보수집 더보기 버튼*/
	$(".more").click( function() {
		$(".privacy").slideToggle(500);
    });
});
    if($("input[name=tcode]").val().length!=0){
        setPrecode($("input[name=tcode]").val());
    }
</script>

				<!-- Google Tag Manager -->
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
            new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
        j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
        'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
    })(window,document,'script','dataLayer','GTM-KQDV8F3');</script>
<!-- End Google Tag Manager -->



<!-- 네이버 프리미엄로그분석 Start -->
<!-- 공통 적용 스크립트 , 모든 페이지에 노출되도록 설치. 단 전환페이지 설정값보다 항상 하단에 위치해야함 -->
<script type="text/javascript" src="//wcs.naver.net/wcslog.js"> </script>
<script type="text/javascript">
// AccoutId 적용
if (!wcs_add) var wcs_add={};
wcs_add["wa"] = "s_15f4ba48667f";

// 유입추적함수(최상위 도메인을 인자로 삽입)
if (!_nasa) var _nasa={};
wcs.inflow("siwonschool.com");

// 로그 수집 함수 호출
wcs_do(_nasa);
</script>
<!-- 네이버 프리미엄로그분석 End -->

<!-- 네이버 애널리틱스 Start -->
<script type="text/javascript" src="//wcs.naver.net/wcslog.js"></script>
<script type="text/javascript">
if(!wcs_add) var wcs_add = {};
wcs_add["wa"] = "4b1ce83a0dbbe8";
wcs_do();
</script>
<!-- 네이버 애널리틱스 End -->

<!-- 다음 CTS -->
<script type="text/javascript">
    var roosevelt_params = {
        retargeting_id:'QW-sexfte1XdNuTQxQjiTA00',
        tag_label:'QmGcyITUSSOZ9dd3VithKA'
    };
</script>
<script type="text/javascript" src="//adimg.daumcdn.net/rt/roosevelt.js"></script>
<!-- // 다음 CTS -->




<!-- DDN 스크립트 Start -->
<script type="text/javascript">
	var roosevelt_params = {
		retargeting_id:'afjqMd5MAFArKuVx-7sflQ00',
		tag_label:'c190YrXCT0mU6YryAcPGBw'
	};
</script>
<script type="text/javascript" src="//adimg.daumcdn.net/rt/roosevelt.js"></script>
<!-- DDN 스크립트 End -->


<!-- GA 추적코드 업데이트 버전 Start -->
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-25007883-1', 'auto', {'allowLinker': true});
ga('require', 'linker');
ga('linker:autoLink', ['member.siwonschool.com']);
ga('require', 'linkid', 'linkid.js');
ga('require', 'displayfeatures');
var dimensionValue = '211.238.142.100';
ga('set', 'dimension2', dimensionValue);
ga('send', 'pageview');
</script>
<!-- GA 추적코드 업데이트 버전 End -->

<!-- Facebook Pixel Code -->
<script>
	!function(f,b,e,v,n,t,s)
	{if(f.fbq)return;n=f.fbq=function(){n.callMethod?
		n.callMethod.apply(n,arguments):n.queue.push(arguments)};
		if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
		n.queue=[];t=b.createElement(e);t.async=!0;
		t.src=v;s=b.getElementsByTagName(e)[0];
		s.parentNode.insertBefore(t,s)}(window, document,'script',
		'https://connect.facebook.net/en_US/fbevents.js');
	fbq('init', '316769026274878');
	fbq('track', 'PageView');
</script>
<noscript><img height="1" width="1" style="display:none"
			   src="https://www.facebook.com/tr?id=316769026274878&ev=PageView&noscript=1"
	/></noscript>
<!-- End Facebook Pixel Code -->



<!-- Facebook Pixel Code Linkedin-->
<script type="text/javascript">
_linkedin_partner_id = "498683";
window._linkedin_data_partner_ids = window._linkedin_data_partner_ids || [];
window._linkedin_data_partner_ids.push(_linkedin_partner_id);
</script><script type="text/javascript">
(function(){var s = document.getElementsByTagName("script")[0];
var b = document.createElement("script");
b.type = "text/javascript";b.async = true;
b.src = "https://snap.licdn.com/li.lms-analytics/insight.min.js";
s.parentNode.insertBefore(b, s);})();
</script>
<noscript>
<img height="1" width="1" style="display:none;" alt="" src="https://dc.ads.linkedin.com/collect/?pid=498683&fmt=gif" />
</noscript>
<!-- End Facebook Pixel Code -->



<!-- Tracking Script Start 2.0 -->
<script type="text/javascript" async="true">

var dspu = "GN3c2l3b24x";      // === (필수)광고주key (변경하지마세요) ===
var dspu,dspt,dspo,dspom;
function loadanalJS_dsp(b,c){var d=document.getElementsByTagName("head")[0],a=document.createElement("sc"+"ript");a.type="text/javasc"+"ript";null!=c&&(a.charset="UTF-8");
a.src=b;a.async="true";d.appendChild(a)}function loadanal_dsp(b){loadanalJS_dsp(("https:"==document.location.protocol?"https://":"http://")+b,"UTF-8");document.write("<span id=dsp_spn style=display:none;></span>");}
loadanal_dsp("tk.realclick.co.kr/tk_comm.js?dspu="+dspu+"&dspt="+dspt+"&dspo="+dspo+"&dspom="+dspom);
</script>
<!-- Tracking Script End 2.0 -->


<!-- RealClick 리얼타겟팅 Checking Script V.20130115 Start-->
<script type='text/javascript'>

function loadrtgJS(b,c){var d=document.getElementsByTagName("head")[0],a=document.createElement("script");a.type="text/javascript";null!=c&&
(a.charset="euc-kr");a.src=b;a.async="true";d.appendChild(a)}function load_rtg(b){loadrtgJS(("https:"==document.location.protocol?" https://":" http://")+b,"euc-kr")}
load_rtg("event.realclick.co.kr/rtarget/rtget.js?rtcode=siwon1");

</script>
<!-- RealClick 리얼타겟팅 Checking Script V.20130115 End -->


<!-- DSP 리타겟팅 Checking Script V.201603 Start-->
<script type="text/javascript" async="true">
function dsp_loadrtgJS(b,c){var d=document.getElementsByTagName("head")[0],a=document.createElement("script");a.type="text/javascript";null!=c&&(a.charset="euc-kr");a.src=b;a.async="true";d.appendChild(a)}function dsp_load_rtg(b){dsp_loadrtgJS(("https:"==document.location.protocol?" https://":" http://")+b,"euc-kr")}dsp_load_rtg("realdmp.realclick.co.kr/rtarget/rtget.js?dsp_adid=siwon1");
</script>
<!-- DSP 리타겟팅 Checking Script V.201603 End-->

<!--EDN-->
<iframe id="hfrADCheck" src="//adcheck.about.co.kr/mad/prd/view?shopid=siwon12" scrolling="no" frameborder="0" width="0" height="0"></iframe>
<!--EDN-->

<!-- N2S 스크립트 광고  수집용 Start -->
<!--<script language="javascript" src="//web.n2s.co.kr/js/_n2s_sp_log_siwon1234.js"></script>-->
<!-- N2S 스크립트 광고  수집용 End -->

<!-- Withpang Tracker v3.0 [공용] start -->
<script type="text/javascript">
<!--
	function mobRf(){
  		var rf = new EN();
  		rf.sendRf();
	}
  //-->
</script>
<script async="true" src="https://cdn.megadata.co.kr/js/enliple_min2.js" onload="mobRf()"></script>
<!-- Withpang Tracker v3.0 [공용] end -->


<script type="text/javascript" src="//static.tagmanager.toast.com/tag/view/138"></script>
<script type="text/javascript">
window.ne_tgm_q = window.ne_tgm_q || [];
window.ne_tgm_q.push(
{
    tagType: 'visit',	
    device:'web'/*web, mobile, tablet*/,
    pageEncoding:'utf-8'
});
</script>
<!-- 에이플러스 스트립트(리마케팅 광고를 위한 모수 수집용) -->
<script type="text/javascript" src="//mtag.mman.kr/aud.mezzo/tracking?e_version=2&t_tag_no=591&t_adver_no=169&t_brand_no=277&d_adid={ADID/IDFA}"></script>
<!-- 에이플러스 스트립트(리마케팅 광고를 위한 모수 수집용) -->




<!-- 최적의 광고소재가 송출될 수 있도록 쇼핑몰에서 활동한 유저 행동패턴 수집 -->
<script type="text/javascript">
	var adn_param = adn_param || [];
	adn_param.push([{
		ui:'100324',
		ut:'Home'
	}]);
</script>
<script type="text/javascript" async src="//fin.rainbownine.net/js/adn_tags_1.0.0.js"></script>
<!-- //최적의 광고소재가 송출될 수 있도록 쇼핑몰에서 활동한 유저 행동패턴 수집 -->



<!-- Dable 스크립트 시작 / 문의 support@dable.io -->
<script>
(function(d,a,b,l,e,_) {
d[b]=d[b]||function(){(d[b].q=d[b].q||[]).push(arguments)};e=a.createElement(l);
e.async=1;e.charset='utf-8';e.src='//static.dable.io/dist/dablena.min.js';
_=a.getElementsByTagName(l)[0];_.parentNode.insertBefore(e,_);
})(window,document,'dablena','script');
dablena('init', 'Siwonschool KR');
dablena('track', 'PageView');
</script>
<!-- Dable 스크립트 종료 / 문의 support@dable.io -->




<!--기초영어 DA 6월 신규 광고 매체 운영 리타겟팅 코드-->
<!--기초영어 DA 6월 신규 광고 매체 운영 리타겟팅 코드-->



<script>
	window.appier_q = window.appier_q || [];
	window.appier_q.push(
		{"t": "register", "content": { "id": "b444", "site": "siwonschool.com" }},
		{"t":"pv_track","action_id": "46a68f46cddff56","track_id":"b4bd248ff58fb97","isCountReload": true,"counter": 0},
		{"t":"pv_track","action_id": "63fa04337078f56","track_id":"b4bd248ff58fb97","isCountReload": false,"counter": 1})
</script>


<script src="//jscdn.appier.net/aa.js?id=siwonschool.com" defer></script>



<!-- 기초영어 YouTube 광고 성과 측정 위한 스크립트 삽입 요청 -->
<!-- Global site tag (gtag.js) - Google Ads: 780531434 -->
<script async src="https://www.googletagmanager.com/gtag/js?id=AW-780531434"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag(){dataLayer.push(arguments);}
	gtag('js', new Date());
	gtag('config', 'AW-780531434');
</script>



<!-- Screenview 리타겟팅Script Start-->
<script>var rGroupNeo = new function(){this.playGoods = function(eSRC,fnc) {var script = document.createElement('script');script.type = 'text/ja'+'vasc'+'ript';script.charset = 'utf-8';script.onreadystatechange = function() {if((!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete') && fnc!=undefined && fnc!='' ) { eval(fnc); };};script.onload = function() {if(fnc!=undefined && fnc!='') { eval(fnc); };};script.src= eSRC;document.getElementsByTagName('head')[0].appendChild(script);};};</script>
<script>rGroupNeo.playGoods('//nscreen.neoebiz.co.kr/xCData.php?adCode=MTE3CDg4CDg3CDkwCDExMwg');</script>


<!--	/ 크리테오 			-->

<!-- Criteo 홈페이지 태그 -->
<script type="text/javascript" src="//static.criteo.net/js/ld/ld.js" async="true"></script>
<script type="text/javascript">
	window.criteo_q = window.criteo_q || [];
	var deviceType = /iPad/.test(navigator.userAgent) ? "t" : /Mobile|iP(hone|od)|Android|BlackBerry|IEMobile|Silk/.test(navigator.userAgent) ? "m" : "d";
	window.criteo_q.push(
		{ event: "setAccount", account: 7216}, // 이 라인은 업데이트하면 안됩니다
		{ event: "setEmail", email: "" }, // 빈 문자 일수 있습니다
		{ event: "setSiteType", type: deviceType},
		{ event: "viewHome"});
</script>
<!-- END Criteo 홈페이지 태그 -->


<!-- 아프리카 에드벌룬 CPS 연동-->


<!-- 바른웹 CPS 연동-->



<!-- Global site tag (gtag.js) - Google Ads: 845255144 -->
<script async src="https://www.googletagmanager.com/gtag/js?id=AW-845255144"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag(){dataLayer.push(arguments);}
	gtag('js', new Date());

	gtag('config', 'AW-845255144');
</script>

<!-- Global site tag (gtag.js) - Google Ads: 1065086927 SA계정 -->
<script async src="https://www.googletagmanager.com/gtag/js?id=AW-1065086927"></script>
<script>
window.dataLayer = window.dataLayer || [];
function gtag(){dataLayer.push(arguments);}
gtag('js', new Date());

gtag('config', 'AW-1065086927');
</script>

<!-- WIDERPLANET  SCRIPT START 2021.2.3 -->
<div id="wp_tg_cts" style="display:none;"></div>
<script type="text/javascript">
	var wptg_tagscript_vars = wptg_tagscript_vars || [];
	wptg_tagscript_vars.push(
		(function() {
			return {
				wp_hcuid:"",   /*고객넘버 등 Unique ID (ex. 로그인  ID, 고객넘버 등 )를 암호화하여 대입. *주의 : 로그인 하지 않은 사용자는 어떠한 값도 대입하지 않습니다.*/
				ti:"19531",	/*광고주 코드 */
				ty:"Home",	/*트래킹태그 타입 */
				device:"web"	/*디바이스 종류  (web 또는  mobile)*/

			};
		}));
</script>
<script type="text/javascript" async src="//cdn-aitg.widerplanet.com/js/wp_astg_4.0.js"></script>
<!-- // WIDERPLANET  SCRIPT END 2021.2.3 -->

<!-- 틱톡광고 픽셀 -->
<script>
    !function (w, d, t) {
        w.TiktokAnalyticsObject=t;var ttq=w[t]=w[t]||[];ttq.methods=["page","track","identify","instances","debug","on","off","once","ready","alias","group","enableCookie","disableCookie"],ttq.setAndDefer=function(t,e){t[e]=function(){t.push([e].concat(Array.prototype.slice.call(arguments,0)))}};for(var i=0;i<ttq.methods.length;i++)ttq.setAndDefer(ttq,ttq.methods[i]);ttq.instance=function(t){for(var e=ttq._i[t]||[],n=0;n<ttq.methods.length;n++
        )ttq.setAndDefer(e,ttq.methods[n]);return e},ttq.load=function(e,n){var i="https://analytics.tiktok.com/i18n/pixel/events.js";ttq._i=ttq._i||{},ttq._i[e]=[],ttq._i[e]._u=i,ttq._t=ttq._t||{},ttq._t[e]=+new Date,ttq._o=ttq._o||{},ttq._o[e]=n||{};n=document.createElement("script");n.type="text/javascript",n.async=!0,n.src=i+"?sdkid="+e+"&lib="+t;e=document.getElementsByTagName("script")[0];e.parentNode.insertBefore(n,e)};

        ttq.load('CGLPSG3C77UECB7PKI4G');
        ttq.page();
    }(window, document, 'ttq');
</script>
<!-- 틱톡광고 픽셀 -->

<!-- Enliple Tracker Start -->
<script type="text/javascript">
    (function(a,g,e,n,t){a.enp=a.enp||function(){(a.enp.q=a.enp.q||[]).push(arguments)};n=g.createElement(e);n.async=!0;n.defer=!0;n.src="https://cdn.megadata.co.kr/dist/prod/enp_tracker_self_hosted.min.js";t=g.getElementsByTagName(e)[0];t.parentNode.insertBefore(n,t)})(window,document,"script");
    enp('create', 'common', 'siwon12', { device: 'W' });  // W:웹, M: 모바일, B: 반응형
    enp('send', 'common', 'siwon12');
</script>
<!-- Enliple Tracker End -->



<!-- 카카오 픽셀 -->
<script type="text/javascript" charset="UTF-8" src="//t1.daumcdn.net/kas/static/kp.js"></script>
<script type="text/javascript">
    kakaoPixel('4770848325887149055').pageView('방문');
</script>

<!-- 와이즈몹 -->
<!-- WiseMob 리타겟팅Script Start-->
<script src="https://ad.api.stax.kr/tracker/site/?code=daf5eef60f39ffc97cc9ca79c19c8f0c" async></script>
<!-- WiseMob 리타겟팅Script End-->				<span itemscope="" itemtype="http://schema.org/Organization">
		 <link itemprop="url" href="http://www.siwonschool.com">
		 <a itemprop="sameAs" href="https://story.kakao.com/ch/siwonschool?utm_campaign=siwonschool&utm_source=viral&utm_medium=free&utm_content=banner&utm_term=SNS_main_be_kakaostory_n"></a>
		 <a itemprop="sameAs" href="http://blog.naver.com/siwonschool0?utm_campaign=siwonschool&utm_source=viral&utm_medium=free&utm_content=banner&utm_term=SNS_main_be_blog_n"></a>
		 <a itemprop="sameAs" href="https://www.facebook.com/siwonschool?utm_campaign=siwonschool&utm_source=viral&utm_medium=free&utm_content=banner&utm_term=SNS_main_be_facebook_n"></a>
		 <a itemprop="sameAs" href="https://www.youtube.com/user/siwonschool?utm_campaign=siwonschool&utm_source=viral&utm_medium=free&utm_content=banner&utm_term=SNS_main_be_youtube_n"></a>
		 <a itemprop="sameAs" href="https://www.instagram.com/siwonschool/?utm_campaign=siwonschool&utm_source=viral&utm_medium=free&utm_content=banner&utm_term=SNS_main_be_instagram_n"></a>
		 <a itemprop="sameAs" href="http://post.naver.com/siwonschool0?utm_campaign=siwonschool&utm_source=viral&utm_medium=free&utm_content=banner&utm_term=SNS_main_be_post_n"></a>
		</span>
		
				<a href="#siwon_header" id="body_top" class="top"><img src="//siwon-cdn.siwonschool.com/www/comm/bt_top_comm.png" alt="맨위로"></a>

	</body>
</html>
<input type="hidden" id="fid" value="">



 