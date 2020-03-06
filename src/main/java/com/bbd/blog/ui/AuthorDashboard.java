package com.bbd.blog.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bbd.blog.model.Author;
import com.bbd.blog.model.Category;
import com.bbd.blog.model.Post;
import com.bbd.blog.service.Auth;
import com.bbd.blog.service.CommentService;
import com.bbd.blog.service.PostService;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AuthorDashboard extends HBox{
	Scene adScene;
	StringProperty pTitle = new SimpleStringProperty("");
	StringProperty pTime = new SimpleStringProperty("Published on ");
	StringProperty pText = new SimpleStringProperty("");
	List<Post>posts;
	DateFormat df = new SimpleDateFormat("dd MMM yyyy 'at' HH:mm a");
	Post viewPost;
	public AuthorDashboard(List<Post> posts) {
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
		

		VBox sidePanel = new VBox();
		sidePanel.setPrefHeight(720);
		sidePanel.setPrefWidth(50);
		sidePanel.setStyle("-fx-background-color: #717171");
		ImageView newPostIc = new ImageView(new Image("file:img\\plus.png"));
		newPostIc.setFitHeight(35);
		newPostIc.setFitWidth(35);
		Button btnNew = new Button();
		btnNew.setOnAction(e->{
			newPost();
		});
		btnNew.setGraphic(newPostIc);
		btnNew.setStyle("-fx-background-color: #717171");
		sidePanel.getChildren().add(btnNew);
		sidePanel.setPadding(new Insets(7.5));
		VBox v = new VBox();
		Text t = new Text("  Your posts");
		Button btnLogOut = new Button("Logout");
		btnLogOut.setStyle("-fx-background-color: #00c96f; -fx-text-fill:#fff; -fx-font-weight: bold; -fx-padding: 10px 10px");
		btnLogOut.setOnAction(e->{
			Auth.logout();
		});
		HBox top = new HBox(t, btnLogOut);
		top.setSpacing(230);
		t.setStyle("-fx-font-weight: bold; -fx-font-size: 15pt; -fx-fill: #717171"); 
		v.setPadding(new Insets(10,0,0,0));
		v.setSpacing(10);
		v.setPrefHeight(720);
		sp.setPrefHeight(720);
		
		v.getChildren().addAll(top, sp);
		sp.setContent(vb);
		getChildren().addAll(sidePanel, v, initPostScreen());
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
		HBox buttons = new HBox(btnEdit, btnDelete);
		buttons.setSpacing(25);
		buttons.setPadding(new Insets(0,0,0,300));
		HBox controls = new HBox(likes, comments, buttons);
		controls.setSpacing(20);
		controls.setPadding(new Insets(25, 0, 0, 0));
		root.getChildren().addAll(postTitle, postTime, postText, controls);
		
		btnDelete.setOnAction(e->{
			PostService.deletePost(viewPost.getId());
			Scene s = new Scene(new AuthorDashboard(PostService.getPosts((Author)Auth.getActiveUser())), 1080,720);
			StageInstance.getInstance().setScene(s);
		});
		btnEdit.setOnAction(e->{
			editPost(viewPost);
		});
		return root;
	}
	
	public void editPost(Post p) {
		Stage editStage = new Stage();
		
		GridPane gPane = new GridPane();
		Text edit = new Text("Edit your post");
		edit.setStyle("-fx-font-weight: bold; -fx-fill: #717171; -fx-font-size: 12pt");
		Label lblTitle = new Label("Title");
		Label lblText = new Label("Text");
		TextField txtTitle = new TextField();
		TextArea txtText = new TextArea();
		txtTitle.setText(p.getTitle());
		txtText.setText(p.getText());
		Button btnDone = new Button("Done");
		Button btnCancel = new Button("Cancel");
		btnDone.setOnAction(e->{
			p.setText(txtText.getText());
			p.setTitle(txtTitle.getText());
			PostService.edit(p);
			pTitle.setValue(txtTitle.getText());
			pText.setValue(txtText.getText());
			editStage.close();
		});
		btnCancel.setOnAction(e->editStage.close());
		btnCancel.setStyle("-fx-background-color: #FF0404; -fx-text-fill:#fff; -fx-font-weight: bold; -fx-padding: 10px 10px");
		btnDone.setStyle("-fx-background-color: #717171; -fx-text-fill:#fff; -fx-font-weight: bold; -fx-padding: 10px 10px");
		gPane.add(edit, 1, 0);
		gPane.add(lblTitle, 0, 1);
		gPane.add(txtTitle, 1, 1, 3, 1);
		gPane.add(lblText, 0, 2);
		gPane.add(txtText, 1, 2, 3, 10);
		gPane.setVgap(10);
		gPane.setHgap(10);
		gPane.setPadding(new Insets(25, 80,25,80));
		HBox buttons = new HBox(btnDone, btnCancel);
		buttons.setPadding(new Insets(0,0,0,485));
		buttons.setSpacing(10);
		VBox root = new VBox(gPane, buttons);
		Scene s = new Scene(root, 800,600);
		editStage.setScene(s);
		editStage.show();
	}
	
	public void newPost() {
		Stage newStage = new Stage();
		
		GridPane gPane = new GridPane();
		Text edit = new Text("Edit your post");
		edit.setStyle("-fx-font-weight: bold; -fx-fill: #717171; -fx-font-size: 12pt");
		Label lblTitle = new Label("Title");
		Label lblText = new Label("Text");
		TextField txtTitle = new TextField();
		TextArea txtText = new TextArea();
		Button btnPublish = new Button("Publish");
		Button btnCancel = new Button("Cancel");
		btnPublish.setOnAction(e->{
			Post p = new Post(-1, Category.WORDPRESS, new Date(), txtTitle.getText(), txtText.getText());
			PostService.post(p, (Author)Auth.getActiveUser());
			newStage.close();
			Scene s = new Scene(new AuthorDashboard(PostService.getPosts((Author)Auth.getActiveUser())), 1080,720);
			StageInstance.getInstance().setScene(s);
		});
		btnCancel.setOnAction(e->newStage.close());
		btnCancel.setStyle("-fx-background-color: #FF0404; -fx-text-fill:#fff; -fx-font-weight: bold; -fx-padding: 10px 10px");
		btnPublish.setStyle("-fx-background-color: #717171; -fx-text-fill:#fff; -fx-font-weight: bold; -fx-padding: 10px 10px");
		gPane.add(edit, 1, 0);
		gPane.add(lblTitle, 0, 1);
		gPane.add(txtTitle, 1, 1, 3, 1);
		gPane.add(lblText, 0, 2);
		gPane.add(txtText, 1, 2, 3, 10);
		gPane.setVgap(10);
		gPane.setHgap(10);
		gPane.setPadding(new Insets(25, 80,25,80));
		HBox buttons = new HBox(btnPublish, btnCancel);
		buttons.setPadding(new Insets(0,0,0,485));
		buttons.setSpacing(10);
		VBox root = new VBox(gPane, buttons);
		Scene s = new Scene(root, 800,600);
		newStage.setScene(s);
		newStage.show();
	}
	
	
	
	
	

}
