package com.bbd.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
	private int id;
	private Category category;
	private Date datetime;
	private String title, text;
	private List<Comment> comments = new ArrayList<Comment>();
	public Post(int id, Category category, Date datetime, String title, String text) {
		super();
		this.id = id;
		this.category = category;
		this.datetime = datetime;
		this.title = title;
		this.text = text;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Post(int id, Category category, Date datetime, String text) {
		this.id = id;
		this.category = category;
		this.datetime = datetime;
		this.text = text;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
