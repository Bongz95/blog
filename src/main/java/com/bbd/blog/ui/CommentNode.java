package com.bbd.blog.ui;

import java.text.SimpleDateFormat;

import com.bbd.blog.model.Comment;
import com.bbd.blog.model.User;
import com.bbd.blog.service.UserService;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CommentNode extends VBox{
	Comment comment;
	public CommentNode(Comment comment) {
		this.comment = comment;
		init();
	}
	
	public void init() {
		User u = UserService.getUser(comment.getUserId());
		Text names = new Text(u.getName() + " " + u.getSurname());
		Text text = new Text(comment.getText());
		Text time = new Text(new SimpleDateFormat("dd MMM yyyy HH:mm a").format(comment.getDatetime()));
		
		names.setStyle("-fx-font-weight: bold;"); 
		time.setStyle("-fx-font-weight: lighter; -fx-fill:#717171; -fx-font-size: 8pt; "); 
		getChildren().addAll(names, text, time);
		setSpacing(10);
		setPadding(new Insets(10,10,10,10));
		setPrefWidth(500);
		
	}

}
