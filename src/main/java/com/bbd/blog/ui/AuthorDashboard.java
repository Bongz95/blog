package com.bbd.blog.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AuthorDashboard extends HBox{

	List<Text> txts;
	public AuthorDashboard() {
		txts = new ArrayList<>();
		for(int i = 0; i < 20; i++) txts.add(new Text("Hello World")); 
		
		init();
	}
	
	public void init() {
		ScrollPane sp = new ScrollPane();
		sp.setPrefWidth(450);
		VBox vb = new VBox();
		txts.stream().forEach(t -> vb.getChildren().add(new PostNode()));
		sp.setContent(vb);
		getChildren().add(sp);
	}
	
	

}
