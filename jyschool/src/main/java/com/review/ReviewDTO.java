package com.review;

public class ReviewDTO {
	private long classNum;	// 수강번호
	private String userId;	
	private String subject;
	private String content;
	private String reg_date;
	
	private String userName;
	private int boardLikeCount;

	public long getClassNum() {
		return classNum;
	}

	public void setClassNum(long classNum) {
		this.classNum = classNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getBoardLikeCount() {
		return boardLikeCount;
	}

	public void setBoardLikeCount(int boardLikeCount) {
		this.boardLikeCount = boardLikeCount;
	}

}
