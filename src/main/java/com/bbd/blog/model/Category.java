package com.bbd.blog.model;

public enum Category {

	FINANCE(1),
	SPORTS(2),
	BUSINESS(3),
	WORDPRESS(4),
	FOOD(5),
	WRITING(6),
	CARS(7),
	MOVIES(8);
	
	int id;
	public int getId() {
		return id;
	}
	public static Category match(int i) {
		return Category.values()[i-1];
	}
	private Category(int i) {
		this.id = i;
	}
	
}
