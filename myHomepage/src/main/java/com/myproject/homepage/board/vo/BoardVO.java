package com.myproject.homepage.board.vo;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

// VO(Value Object)
public class BoardVO {

	private int seq;
	private String title;
	private String writer;
	private String content;
	private Date regDate;
	private Date updateDate;
	private int cnt;
	private List<AttachFileVO> attachList;

	private MultipartFile[] uploadFile;

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public MultipartFile[] getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile[] uploadFile) {
		this.uploadFile = uploadFile;
	}
	public List<AttachFileVO> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<AttachFileVO> attachList) {
		this.attachList = attachList;
	}
	
	@Override
	public String toString() {
		return "BoardVO [seq=" + seq + ", title=" + title + ", writer=" + writer + ", content=" + content + ", regDate="
				+ regDate + ", updateDate=" + updateDate + ", cnt=" + cnt + ", attachList=" + attachList
				+ ", uploadFile=" + Arrays.toString(uploadFile) + "]";
	}
}
