package com.bbd.blog.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.bbd.blog.model.Post;
import com.bbd.blog.service.Auth;
import com.bbd.blog.service.CommentService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TimeLine extends HBox{

	Scene adScene;
	StringProperty pTitle = new SimpleStringProperty("");
	StringProperty pTime = new SimpleStringProperty("Published on ");
	StringProperty pText = new SimpleStringProperty("");
	List<Post>posts;
	DateFormat df = new SimpleDateFormat("dd MMM yyyy 'at' HH:mm a");
	Post viewPost;
	public TimeLine(List<Post> posts) {
		this.posts = posts;
		if(posts.size() > 0) {
			Post p = posts.get(0);
			viewPost = p;
			pTitle.setValue(p.getTitle());
			
			pTime.setValue(df.format(p.getDatetime()));
			pText.setValue(p.getText());
		}
		init();
	}
	
	public void init() {
		ScrollPane sp = new ScrollPane();
		sp.setPrefWidth(400);
		VBox vb = new VBox();
		posts.stream().forEach(p -> {
			PostNode pn = new PostNode(p);
			pn.setOnMouseClicked(e->{
				viewPost = p;
				pTitle.setValue(p.getTitle());
				pTime.setValue(df.format(p.getDatetime()));
				pText.setValue(p.getText());
			
			});
			vb.getChildren().add(pn);
		});
		

		
		VBox v = new VBox();
		Text t = new Text("  Recent posts");
		Button btnLogOut = new Button("Logout");
		btnLogOut.setStyle("-fx-background-color: #00c96f; -fx-text-fill:#fff; -fx-font-weight: bold; -fx-padding: 10px 10px");
		btnLogOut.setOnAction(e->{
			Auth.logout();
		});
		HBox top = new HBox(t, btnLogOut);
		top.setSpacing(210);
		t.setStyle("-fx-font-weight: bold; -fx-font-size: 15pt; -fx-fill: #717171"); 
		v.setPadding(new Insets(10,0,0,0));
		v.setSpacing(10);
		v.setPrefHeight(720);
		sp.setPrefHeight(720);
		v.getChildren().addAll(top, sp);
		sp.setContent(vb);
		getChildren().addAll(v, initPostScreen());
		
	}
	public VBox initPostScreen() {
		VBox root = new VBox();
		root.setPadding(new Insets(20));
		root.setSpacing(10);
		Text postTitle = new Text("Some really awesome title");
		postTitle.textProperty().bind(pTitle);
		postTitle.setStyle("-fx-font-size: 15pt; -fx-font-weight: bolder;");
		Text postTime = new Text("Published on 01 March 2020 10:23 am");
		postTime.textProperty().bind(pTime);
		Text postText = new Text();
		postText.textProperty().bind(pText);
		Button btnDelete = new Button("Delete");
		btnDelete.setStyle("-fx-background-color: #FF0404; -fx-text-fill:#fff; -fx-font-weight: bold; -fx-padding: 10px 10px");
		Button btnEdit = new Button("Edit");
		btnEdit.setStyle("-fx-background-color: grey; -fx-text-fill:#fff; -fx-font-weight: bold; -fx-padding: 10px 10px");
		Text likes = new Text("100 likes");
		likes.setStyle("-fx-font-style: italic; -fx-fill: #717171");
		Text comments = new Text("Comments");
		comments.setOnMouseClicked(e->{
			Stage stg = new Stage();
			Scene scn = new Scene(new CommentSection(CommentService.getComments(viewPost), viewPost, stg), 600, 600);
			stg.setScene(scn);
			stg.show();
		});
		comments.setStyle("-fx-font-weight: bold");
		HBox controls = new HBox(likes, comments);
		controls.setSpacing(20);
		controls.setPadding(new Insets(25, 0, 0, 0));
		root.getChildren().addAll(postTitle, postTime, postText, controls);

		return root;
	}

}
