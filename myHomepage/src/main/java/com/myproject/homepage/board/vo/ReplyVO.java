package com.myproject.homepage.board.vo;

import java.util.Date;

public class ReplyVO {
	private int rno;
	private int seq;
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;
	private int parent_id;
	private int depth;
	private String reply_password;
	
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getReplyer() {
		return replyer;
	}
	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}
	public Date getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getReply_password() {
		return reply_password;
	}
	public void setReply_password(String reply_password) {
		this.reply_password = reply_password;
	}
	
	@Override
	public String toString() {
		return "ReplyVO [rno=" + rno + ", seq=" + seq + ", reply=" + reply + ", replyer=" + replyer + ", replyDate="
				+ replyDate + ", updateDate=" + updateDate + ", parent_id=" + parent_id + ", depth=" + depth
				+ ", reply_password=" + reply_password + "]";
	}
}
