package com.qbbs;

public class QbbsDTO {
	private long writeNum;	// 글번호
	private int secret;		// 비밀여부
	private String title;	// 제목
	private String content;	// 질문내용
	private String writeDate;	// 작성일자
	private String comment;	// 답글
	private String reg_date;// 답글등록일(답변날짜)
	private String userId;	// 아이디
	
	private String userName;
	private String answerId;// 답변자 아이디
	private String answerName;// 답변자 이름
	private String answer;	// 답글내용
	
	
	public long getWriteNum() {
		return writeNum;
	}
	public void setWriteNum(long writeNum) {
		this.writeNum = writeNum;
	}
	public int getSecret() {
		return secret;
	}
	public void setSecret(int secret) {
		this.secret = secret;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getAnswerName() {
		return answerName;
	}
	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}


}
