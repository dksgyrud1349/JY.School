package com.beforeqna;

public class BeforeQnaDTO {
	// 질문자
	private long qnum;	// 질문 번호	
	private String qcontent;
	private String qdate;
	private String userId;
	private String userName;
	
	// 답변자
	private String acontent;
	private String adate;
	private String auserId;
	private String auserName;
	
	//
	private int secret; // 공개 비공개
	private String title;
	private long classNum2;
	private long classNum;
	
	
	
	
	public String getAuserName() {
		return auserName;
	}
	public void setAuserName(String auserName) {
		this.auserName = auserName;
	}
	public long getClassNum() {
		return classNum;
	}
	public void setClassNum(long classNum) {
		this.classNum = classNum;
	}
	public long getQnum() {
		return qnum;
	}
	public void setQnum(long qnum) {
		this.qnum = qnum;
	}
	public String getQcontent() {
		return qcontent;
	}
	public void setQcontent(String qcontent) {
		this.qcontent = qcontent;
	}
	public String getQdate() {
		return qdate;
	}
	public void setQdate(String qdate) {
		this.qdate = qdate;
	}
	public String getAcontent() {
		return acontent;
	}
	public void setAcontent(String acontent) {
		this.acontent = acontent;
	}
	public String getAdate() {
		return adate;
	}
	public void setAdate(String adate) {
		this.adate = adate;
	}
	public String getAuserId() {
		return auserId;
	}
	public void setAuserId(String aserId) {
		this.auserId = aserId;
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
	public long getClassNum2() {
		return classNum2;
	}
	public void setClassNum2(long classnum2) {
		this.classNum2 = classnum2;
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
	
}
