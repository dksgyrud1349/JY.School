package com.sincheong;

public class LectureDTO {
	private long classNum;  // 강좌 번호
	private String className;  // 강좌 이름
	private int price;  // 가격
	private String classComment;  // 강좌 설명
	private String classDegree;  // 강좌 등급
	private String c_reg_date;  // 등록일 
	private String userId;  // 아이디(강사)
	private String username;  // 강사 이름
	private String teachchk;	
	
	private String imageFilename1;
	private String imageFilename2;
	
	public String getImageFilename1() {
		return imageFilename1;
	}
	public void setImageFilename1(String imageFilename1) {
		this.imageFilename1 = imageFilename1;
	}
	public String getImageFilename2() {
		return imageFilename2;
	}
	public void setImageFilename2(String imageFilename2) {
		this.imageFilename2 = imageFilename2;
	}
	public String getTeachchk() {
		return teachchk;
	}
	public void setTeachchk(String teachchk) {
		this.teachchk = teachchk;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getClassNum() {
		return classNum;
	}
	public void setClassNum(long classNum) {
		this.classNum = classNum;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getClassComment() {
		return classComment;
	}
	public void setClassComment(String classComment) {
		this.classComment = classComment;
	}
	public String getClassDegree() {
		return classDegree;
	}
	public void setClassDegree(String classDegree) {
		this.classDegree = classDegree;
	}
	public String getC_reg_date() {
		return c_reg_date;
	}
	public void setC_reg_date(String c_reg_date) {
		this.c_reg_date = c_reg_date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
