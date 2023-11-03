package com.notice;

public class NoticeDTO {
	private long referNum;
	private int Notice;
	private String Title;
	private String Content;
	private String writeDate;
	private int hitCount;
	private String userId;
	private String userName;
	
	private long noticeFileNum;
	private String saveFile;
	private String clientFile;
	
	private String[] saveFiles;			// DB에 없는것을 임시로 만들고 DB에 있는컬럼에다 뿌려주면 된다.
	private String[] clientFiles;
	private long gap;
	
	public long getReferNum() {
		return referNum;
	}
	public void setReferNum(long referNum) {
		this.referNum = referNum;
	}
	public int getNotice() {
		return Notice;
	}
	public void setNotice(int notice) {
		Notice = notice;
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
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
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
	public long getNoticeFileNum() {
		return noticeFileNum;
	}
	public void setNoticeFileNum(long noticeFileNum) {
		this.noticeFileNum = noticeFileNum;
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
	public long getGap() {
		return gap;
	}
	public void setGap(long gap) {
		this.gap = gap;
	}
	
}
	