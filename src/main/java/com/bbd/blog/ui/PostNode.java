package com.bbd.blog.ui;

import java.text.SimpleDateFormat;

import com.bbd.blog.model.Post;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PostNode extends HBox{
	private Post post; 
	public PostNode(Post post) {
		// TODO Auto-generated constructor stub
		this.post = post;
		init();
	}
	
	public void init() {
		Image img = new Image("file:img/post.png");
		ImageView imgV = new ImageView(img);
		imgV.setFitHeight(50);
		imgV.setFitWidth(50);
		
		VBox vbox = new VBox();
		Text postTitle = new Text(post.getTitle());
		postTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 10pt;");
		Text postTime = new Text("Published on " + new SimpleDateFormat("dd MMM yyyy 'at' HH:mm a").format(post.getDatetime()));
		postTime.setStyle("-fx-font-weight: lighter; -fx-fill: grey");
		vbox.getChildren().addAll(postTitle, postTime);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(5);
		
		getChildren().addAll(imgV, vbox);
		setPadding(new Insets(10,10,0,10));
		vbox.setStyle("-fx-border-style: none; -fx-border-width: 0 0 0.5 0; -fx-border-color: grey;");
		vbox.setPrefWidth(375);
		setPrefWidth(400);
		setStyle("-fx-background-color: #fff");
		
		this.setOnMouseEntered(e->{
			setStyle("-fx-background-color: #c9c9c9");
		});
		this.setOnMouseExited(e->{
			setStyle("-fx-background-color: #fff");
		});
		
		
	}
	public Post getPost() {
		return post;
	}

}
