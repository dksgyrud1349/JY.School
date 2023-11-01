package com.buyinfo;

import java.sql.Date;

public class BuyinfoDTO {
	
	private Date buyDate;
	private Date startDate;
	private Date endDate;
	private long price;
	private long classNum;
	private String userId;
	
	private long classNum2;

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

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

	public long getClassNum2() {
		return classNum2;
	}

	public void setClassNum2(long classNum2) {
		this.classNum2 = classNum2;
	}
	
}
