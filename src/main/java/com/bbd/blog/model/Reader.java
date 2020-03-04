package com.bbd.blog.model;

import java.util.ArrayList;
import java.util.List;

public class Reader extends User{

	private List<Comment> comments = new ArrayList<Comment>();
	private List<Post> readPost = new ArrayList<Post>();
	public Reader(int id, String name, String surname) {
		super(id, name, surname);
		// TODO Auto-generated constructor stub
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Post> getReadPost() {
		return readPost;
	}
	public void setReadPost(List<Post> readPost) {
		this.readPost = readPost;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

}
