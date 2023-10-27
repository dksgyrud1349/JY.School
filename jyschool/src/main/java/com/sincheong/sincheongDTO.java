package com.sincheong;

public class sincheongDTO {
	private int classNum;  // 수강번호
	private int classNum2;  // 강좌번호
	private String userId;
	private String startDate;
	private String endDate;
	
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	public int getClassNum2() {
		return classNum2;
	}
	public void setClassNum2(int classNum2) {
		this.classNum2 = classNum2;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	
	
}
