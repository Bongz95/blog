package com.bbd.blog.ui;

import com.bbd.blog.exceptions.AccessRevokedException;
import com.bbd.blog.exceptions.InvalidUserCredentialsException;
import com.bbd.blog.model.Author;
import com.bbd.blog.model.Reader;
import com.bbd.blog.model.User;
import com.bbd.blog.service.Auth;
import com.bbd.blog.service.PostService;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPane extends BorderPane {
	private PasswordField txtPassword;
	private TextField txtUsername;
	private Button btnLogin;
	private Text txtErr;
	public LoginPane() {
		init();
	}
	
	private void init() {
		txtPassword = new PasswordField();
		txtPassword.setPromptText("Password");
		txtUsername = new TextField();
		txtUsername.setPromptText("Username");
		btnLogin = new Button("Login");
		btnLogin.setAlignment(Pos.CENTER_RIGHT);
		btnLogin.setStyle("-fx-padding: 10px 20px; -fx-border: none; -fx-background-color: #FF0404; -fx-text-fill: #fff; -fx-font-weight: bold");
		btnLogin.setOnAction(e->login());
		Button btnRegister  = new Button("Register");
		btnRegister.setStyle("-fx-padding: 10px 20px; -fx-border: none; -fx-background-color: #00c96f; -fx-text-fill: #fff; -fx-font-weight: bold");
		btnRegister.setOnAction(e->{
			Stage stg = new Stage();
			stg.setScene(new Scene(new Registration(stg), 400, 800));
			stg.show();
		});
		txtErr = new Text();
		txtErr.setStyle("-fx-fill: #FF0404; -fx-font-weight: bolder;");
		GridPane inputs = new GridPane();
		
		Text txtWelcome = new Text("Welcome to the BBD Blogger");
		txtWelcome.setStyle("-fx-fill: #9B9B9B; -fx-font-size: 8pt");
		Text txtSignIn = new Text("Log into your account");
		txtSignIn.setStyle("-fx-font-weight: bold; -fx-font-size: 15pt");
		inputs.add(txtWelcome, 0, 0, 3, 1);
		inputs.add(txtSignIn, 0, 1, 3, 1);
		inputs.add(txtUsername, 0, 2, 3, 1);
		inputs.add(txtPassword, 0, 3, 3, 1);
		inputs.add(new HBox(btnLogin), 0, 4, 1,1);
		inputs.add(new HBox(btnRegister), 2, 4, 2,1);
		inputs.add(txtErr, 0, 5, 3, 1);
		inputs.setVgap(15);
		inputs.setHgap(10);
		inputs.setStyle("-fx-background-color: #fff; -fx-padding: 50px 100px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		
		HBox hBox = new HBox();
		hBox.getChildren().add(inputs);
		hBox.setPadding(new Insets(150,25,250,150));
		
		VBox vbox = new VBox();
		vbox.setPrefHeight(720);
		vbox.setPrefWidth(400);
		vbox.setStyle("-fx-background-color: #fff");
		
		ImageView bbdLogo = new ImageView();
		Image logo = new Image("https://www.studentbrands.co.za/wp-content/uploads/2016/08/this-bbd-logo.png");
		
		bbdLogo.setImage(logo);
		bbdLogo.setFitHeight(150);
		bbdLogo.setFitWidth(350);
		vbox.setPadding(new Insets(260,25,260,25));
		vbox.getChildren().add(bbdLogo);
		
		setLeft(vbox);
		setCenter(hBox);
		setStyle("-fx-background-color: #88B0EC");
		
		
	}
	
	public void login() {
		String username = txtUsername.getText(), password = txtPassword.getText();
		try {
			User u = Auth.login(username, password);
			if(u instanceof Author) {
				Scene s = new Scene(new AuthorDashboard(PostService.getPosts((Author)u)), 1080,720);
				StageInstance.getInstance().setScene(s);
			}else if(u instanceof Reader) {
				Scene s = new Scene(new TimeLine(PostService.getAllPosts()), 1080,720);
				StageInstance.getInstance().setScene(s);
			}
		} catch (InvalidUserCredentialsException | AccessRevokedException e) {
			// TODO Auto-generated catch block
			if(e instanceof InvalidUserCredentialsException)
				txtErr.setText("Invalid username and password.");
			if(e instanceof AccessRevokedException)
				txtErr.setText("Your access to the system has been revoked, contact system admin.");
		}
		
	}
}
