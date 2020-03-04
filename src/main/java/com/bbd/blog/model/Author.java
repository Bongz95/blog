package com.bbd.blog.model;

import java.util.ArrayList;
import java.util.List;

public class Author extends User{

	private List<Post> posts = new ArrayList<Post>();
	
	public Author(int id, String name, String surname) {
		super(id, name, surname);
	}

	public final List<Post> getPosts() {
		return posts;
	}

	public void addPost(Post post) {
		this.posts.add(post);
	}
	
	public Post getPost(int id) {
		for(Post p : posts) {
			if(p.getId() == id) return p;
		}
		return null;
	}

}
