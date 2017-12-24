package cn.edu.uzz.entity;

import java.sql.Date;

public class Rent {
	private String account;
	private String bookname;
	private String nowdate;
	private String willtime;
	public String getNowdate() {
		return nowdate;
	}
	public void setNowdate(String nowdate) {
		this.nowdate = nowdate;
	}
	public String getWilltime() {
		return willtime;
	}
	public void setWilltime(String willtime) {
		this.willtime = willtime;
	}
	private String picture;
	private int bookid;
	private int booktype;
	
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public int getBooktype() {
		return booktype;
	}
	public void setBooktype(int booktype) {
		this.booktype = booktype;
	}
	
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
	
	
}
