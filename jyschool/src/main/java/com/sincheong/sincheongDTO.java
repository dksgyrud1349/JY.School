package com.sincheong;

public class sincheongDTO {
	private long classNum;  // 수강번호
	private long classNum2;  // 강좌번호
	private String userId;
	private String startDate;
	private String endDate;
	
	public long getClassNum() {
		return classNum;
	}
	public void setClassNum(long classNum) {
		this.classNum = classNum;
	}
	public long getClassNum2() {
		return classNum2;
	}
	public void setClassNum2(long classNum2) {
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
