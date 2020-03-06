package com.bbd.blog.model;

import java.util.Date;

public class Comment {
	private int id;
	private String text;
	private Date datetime;
	private Post post;
	private int userId;

	public Comment(int id, String text, Date datetime, Post post, int userId) {
		super();
		this.id = id;
		this.text = text;
		this.datetime = datetime;
		this.post = post;
		this.userId = userId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
