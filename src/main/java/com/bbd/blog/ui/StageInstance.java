package com.bbd.blog.ui;

import javafx.stage.Stage;

public class StageInstance {
	private static Stage instance;
	public static void setInstance(Stage s) {
		if(instance == null) instance = s;
	}
	public static Stage getInstance() {
		return instance;
	}

}
