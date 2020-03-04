package com.bbd.blog.model;

public enum Role {
	ADMIN(1), AUTHOR(2), READER(3);
			
	public int id;
	private Role(int i) {
		id = i;
	}
	public int getId() {return id;}
}
