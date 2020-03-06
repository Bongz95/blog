package com.bbd.blog.ui;

import java.util.Date;
import java.util.List;

import com.bbd.blog.model.Comment;
import com.bbd.blog.model.Post;
import com.bbd.blog.model.Reader;
import com.bbd.blog.service.Auth;
import com.bbd.blog.service.CommentService;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CommentSection extends VBox{
	List<Comment> comments;
	Post post;
	Stage stg;
	HBox button = new HBox();
	public CommentSection(List<Comment> comments, Post p, Stage s) {
		// TODO Auto-generated constructor stub
		this.comments = comments;
		this.post = p;
		stg = s;
		init();
	}
	
	public void init() {
		ScrollPane sp = new ScrollPane();
		VBox comments = new VBox();
		this.comments.stream().forEach(c -> {
			CommentNode cn = new CommentNode(c);
			comments.getChildren().add(cn);
		});
		sp.setContent(comments);
		this.getChildren().add(sp);
		setPadding(new Insets(25,25,25,25));
		setSpacing(10);
		if(Auth.getActiveUser() instanceof Reader) putCommentBox();
		
		Button btnCancel = new Button("Cancel");
		btnCancel.setOnAction(e->{
			stg.close();
		});
		button.setSpacing(10);
		button.getChildren().add(btnCancel);
		getChildren().add(button);
	}
	
	public void putCommentBox() {
		TextArea txtComment = new TextArea();
		txtComment.setPromptText("Type comment here...");
		Button btnComment = new Button("Comment");
		button.getChildren().add(btnComment);
		btnComment.setOnAction(e->{
			String txt = txtComment.getText();
			Comment c = new Comment(-1, txt, new Date(), post, Auth.getActiveUser().getId());
			CommentService.comment(c, (Reader)Auth.getActiveUser());
			stg.close();
			
		});
		getChildren().addAll(txtComment);
	}

}
