package com.bbd.blog.model;

public enum Category {
	FICTIONS(1),
	NEWS(2),
	FINANCE(3),
	SPORTS(4),
	BUSINESS(5),
	WORDPRESS(6),
	FOOD(7),
	WRITING(8),
	CARS(9),
	MOVIES(10);
	
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
