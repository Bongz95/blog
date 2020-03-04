package com.bbd.blog.ui;

import com.bbd.blog.model.Post;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PostNode extends HBox{
	private Post post; 
	public PostNode() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		Circle iconBg = new Circle(25);
		iconBg.setFill(Color.GREY);
		Image img = new Image("https://cdn1.iconfinder.com/data/icons/social-media-3/512/615556-Pencil_Document-512.png");
		ImageView imgV = new ImageView(img);
		imgV.maxWidth(1);
		imgV.maxHeight(1);
		getChildren().add(iconBg);
	}

}
