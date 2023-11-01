package com.reference;

public class ReferenceDTO {
	private long writeNum;
	private String Title;
	private String Content;
	private String writeDate;
	private String userId;
	private String userName;
	private long fileNum;
	private long referNum;
	private String saveFile;
	private String clientFile;
	private String[] saveFiles;
	private String[] clientFiles;
	private long gap;
	private long referfileNum;
	
	public long getReferfileNum() {
		return referfileNum;
	}
	public void setReferfileNum(long referfileNum) {
		this.referfileNum = referfileNum;
	}
	public long getGap() {
		return gap;
	}
	public void setGap(long gap) {
		this.gap = gap;
	}
	public long getWriteNum() {
		return writeNum;
	}
	public void setWriteNum(long writeNum) {
		this.writeNum = writeNum;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
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
	public long getFileNum() {
		return fileNum;
	}
	public void setFileNum(long fileNum) {
		this.fileNum = fileNum;
	}
	public long getReferNum() {
		return referNum;
	}
	public void setReferNum(long referNum) {
		this.referNum = referNum;
	}
	public String getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	public String getClientFile() {
		return clientFile;
	}
	public void setClientFile(String clientFile) {
		this.clientFile = clientFile;
	}
	public String[] getSaveFiles() {
		return saveFiles;
	}
	public void setSaveFiles(String[] saveFiles) {
		this.saveFiles = saveFiles;
	}
	public String[] getClientFiles() {
		return clientFiles;
	}
	public void setClientFiles(String[] clientFiles) {
		this.clientFiles = clientFiles;
	}
}
