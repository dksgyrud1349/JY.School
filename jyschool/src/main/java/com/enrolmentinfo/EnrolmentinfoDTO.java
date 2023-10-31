package com.enrolmentinfo;

import java.sql.Date;

public class EnrolmentinfoDTO {
	
	private long classNum;
	private Date startDate;
	private Date endDate;
	private String userId;
	private long classNum2;
	
	private String className;
	private long price;
	private String classcomment;
	private String classdegree;
	private Date c_reg_date;
	private String imagefilename1;
	private String imagefilename2;
	
	public long getClassNum() {
		return classNum;
	}
	public void setClassNum(long classNum) {
		this.classNum = classNum;
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getClasscomment() {
		return classcomment;
	}
	public void setClasscomment(String classcomment) {
		this.classcomment = classcomment;
	}
	public String getClassdegree() {
		return classdegree;
	}
	public void setClassdegree(String classdegree) {
		this.classdegree = classdegree;
	}
	public Date getC_reg_date() {
		return c_reg_date;
	}
	public void setC_reg_date(Date c_reg_date) {
		this.c_reg_date = c_reg_date;
	}
	public String getImagefilename1() {
		return imagefilename1;
	}
	public void setImagefilename1(String imagefilename1) {
		this.imagefilename1 = imagefilename1;
	}
	public String getImagefilename2() {
		return imagefilename2;
	}
	public void setImagefilename2(String imagefilename2) {
		this.imagefilename2 = imagefilename2;
	}
	
	
	
	
	
}
