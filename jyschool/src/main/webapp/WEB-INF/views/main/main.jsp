<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>spring</title>
<style type="text/css">
    /* slider */
    .slider__wrap {
        width: 100%;
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    /* 이미지 보이는 부분 */
    .slider__img {
        position: relative;
        width: 1000px;
        height: 100%;
        overflow: hidden;
    }
    /* 이미지 감싸고 있는 부모 : 움직이는 부분 */
    .slider__inner {
        display: flex;
        flex-wrap: wrap;
        /* 총 이미지 6개 */
        width: 6000px;
        height: 100%;
    }
    /* 개별적인 이미지 */
    .slider {
        position: relative;
        width: 1000px;
        height: 100%;
    }
    .slider img{
        width: 100%;
    }
    .slider::before {
        position: absolute;
        left: 5px;
        top: 5px;
        background: rgba(0, 0, 0, 0.4);
        color: #fff;
        padding: 5px 10px;
    }
    .slider:nth-child(1)::before {content: '이미지1';}
    .slider:nth-child(2)::before {content: '이미지2';}
    .slider:nth-child(3)::before {content: '이미지3';}
    .slider:nth-child(4)::before {content: '이미지4';}
    .slider:nth-child(5)::before {content: '이미지5';}
    .slider:nth-child(6)::before {content: '이미지1';}

    @media (max-width: 800px) {
        .slider__img {
            width: 400px;
            height: 300px;
        }
    }

    .slider__btn a {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        width: 50px;
        height: 50px;
        background: rgba(0,0,0,0.4);
        text-align: center;
        line-height: 50px;
        transition: all 0.2s;
        display: block;
        color: #fff;
    }
    .slider__btn a:hover {
        border-radius: 50%;
        background: rgba(255,255,255,0.4);
        color:#000;
    }
    .slider__btn a.prev {
        left: 150px;
    }
    .slider__btn a.next {
        right: 150px;
    }

    .slider__dot {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        bottom: 20px;
    }
    .slider__dot .dot {
        width: 20px;
        height: 20px;
        background: rgba(0,0,0,0.4);
        display: inline-block;
        border-radius: 50%;
        text-indent: -9999px;
        transition: all 0.3s;
        margin: 2px;
    }
    .slider__dot .dot.active {
        background: rgba(255,255,255,0.9);
    }
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
	
<main>
   <section id="sliderType01">
        <div class="slider__wrap">
            <div class="slider__img">
                <div class="slider__inner">
                    <div class="slider" role="group" aria-label="1/5"><img src="${pageContext.request.contextPath}/img/l준영.png" alt="이미지1"></div>
                    <div class="slider" role="group" aria-label="2/5"><img src="${pageContext.request.contextPath}/img/l준영.png" alt="이미지2"></div>
                    <div class="slider" role="group" aria-label="3/5"><img src="${pageContext.request.contextPath}/img/sec012.jpg" alt="이미지3"></div>
                    <div class="slider" role="group" aria-label="4/5"><img src="../assets/img/effect_bg09-min.jpg" alt="이미지4"></div>
                    <div class="slider" role="group" aria-label="5/5"><img src="../assets/img/effect_bg10-min.jpg" alt="이미지5"></div>
                </div>
            </div>
            <div class="slider__btn">
                <a href="#" class="prev" role="button" aria-label="왼쪽 이미지">prev</a>
                <a href="#" class="next" role="button" aria-label="오른쪽 이미지">next</a>
            </div>
            <div class="slider__dot">
                <!-- <a href="#" class="dot active">이미지1</a>
                <a href="#" class="dot">이미지2</a>
                <a href="#" class="dot">이미지3</a>
                <a href="#" class="dot">이미지4</a>
                <a href="#" class="dot">이미지5</a> -->
            </div>
        </div>
    </section>

	<div class="container body-container">
	    <div class="inner-page mx-auto">
			<img src="${pageContext.request.contextPath}/img/sec012.jpg" alt="My Image" width="1160px" height="auto">
	    </div>
	</div>
	<div class="container body-container">
	    <div class="inner-page mx-auto">
			<img src="${pageContext.request.contextPath}/img/intro.png" alt="My Image" >
	    </div>
	</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>
<script type="text/javascript">
const sliderWrap = document.querySelector(".slider__wrap");
const sliderImg = document.querySelector(".slider__img");       // 보여지는 영역
const sliderInner = document.querySelector(".slider__inner");   // 움직이는 영역
const slider = document.querySelectorAll(".slider");            // 이미지
const sliderDot = document.querySelector(".slider__dot");       //닷 메뉴

// 이미지 갯수에 따라 닷메뉴 생성되도록 만들어야함

let currentIndex = 0;                       // 현재 이미지
let sliderCount = slider.length;            // 이미지 갯수
let sliderWidth = sliderImg.offsetWidth;    // 이미지 가로값
let dotIndex = "";

// 초기값 설정 함수 init()
function init(){
    // <a href="#" class="dot active">이미지1</a>

    slider.forEach(() => {dotIndex += "<a href='#' class='dot'>이미지1</a>";});
    sliderDot.innerHTML = dotIndex;

    // 첫 번째 닷 버튼에 활성화 표시(active)
    sliderDot.firstChild.classList.add("active");
}
init();

// 이미지 이동
function gotoSlider(num){
    sliderInner.style.transition = "all 400ms";
    sliderInner.style.transform = "translateX("+ -sliderWidth * num +"px)";
    currentIndex = num;

    // 두번째 이미지 == 두번째 닷에 클래스 추가
    // 닷 메뉴 클래스 모두 삭제 -> 해당 이미지의 닷 메뉴에 클래스 추가
    let dotActive = document.querySelectorAll(".slider__dot .dot");
    dotActive.forEach(el => el.classList.remove("active"));
    dotActive[num].classList.add("active");
}

// 버튼 클릭했을 때
document.querySelectorAll(".slider__btn a").forEach((btn, index) => {
    btn.addEventListener("click", () => {
        let prevIndex = (currentIndex + (sliderCount -1)) % sliderCount;
        let nextIndex = (currentIndex + 1) % sliderCount;

        if(btn.classList.contains("prev")){
            gotoSlider(prevIndex);
        } else {
            gotoSlider(nextIndex);
        }
    });
})

// 닷 버튼을 클릭했을 때 해당 이미지로 이동
document.querySelectorAll(".slider__dot .dot").forEach((dot, index) => {
    dot.addEventListener("click", () => {
        gotoSlider(index);
    });
})
</script>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/></body>
</html>