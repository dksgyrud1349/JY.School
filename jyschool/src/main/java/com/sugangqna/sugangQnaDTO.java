package com.sugangqna;

public class sugangQnaDTO {
	private long q_num;  // 질문번호
	private String q_title;  // 질문제목
	private String q_userId;  // 작성자
	private String q_date;  // 질문일자
	private String result;  // 결과
	private String q_content;  // 질문내용
	
	private String a_title;  // 답변제목
	private String a_userId;  // 답변자
	private String a_date;  // 답변일자
	private String a_content;  // 답변내용
	
	private long classNum;  // 수강번호
	
	private String className;  // 강좌 제목
	private String startDate;  // 시작 날짜
	private String endDate;    // 끝 날짜
	private String teacherName;  // 강사 이름
	private long lectureNumber;  // 강좌 번호

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public long getLectureNumber() {
		return lectureNumber;
	}

	public void setLectureNumber(long lectureNumber) {
		this.lectureNumber = lectureNumber;
	}

	public long getQ_num() {
		return q_num;
	}

	public void setQ_num(long q_num) {
		this.q_num = q_num;
	}

	public String getQ_title() {
		return q_title;
	}

	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}

	public String getQ_userId() {
		return q_userId;
	}

	public void setQ_userId(String q_userId) {
		this.q_userId = q_userId;
	}

	public String getQ_date() {
		return q_date;
	}

	public void setQ_date(String q_date) {
		this.q_date = q_date;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getQ_content() {
		return q_content;
	}

	public void setQ_content(String q_content) {
		this.q_content = q_content;
	}

	public String getA_title() {
		return a_title;
	}

	public void setA_title(String a_title) {
		this.a_title = a_title;
	}

	public String getA_userId() {
		return a_userId;
	}

	public void setA_userId(String a_userId) {
		this.a_userId = a_userId;
	}

	public String getA_date() {
		return a_date;
	}

	public void setA_date(String a_date) {
		this.a_date = a_date;
	}

	public String getA_content() {
		return a_content;
	}

	public void setA_content(String a_content) {
		this.a_content = a_content;
	}

	public long getClassNum() {
		return classNum;
	}

	public void setClassNum(long classNum) {
		this.classNum = classNum;
	}
}
