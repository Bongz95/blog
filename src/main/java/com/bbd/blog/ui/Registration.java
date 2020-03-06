package com.bbd.blog.ui;

import com.bbd.blog.exceptions.UsernameException;
import com.bbd.blog.model.Role;
import com.bbd.blog.model.User;
import com.bbd.blog.security.Credentials;
import com.bbd.blog.service.Auth;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Registration extends VBox{
	private Stage stg;
	public Registration(Stage s) {
		this.stg = s;
		init();
	}
	
	public void init() {
		Text title = new Text("Account Registration");
		Text personal = new Text("Personal");
		Text account = new Text("Account");
		Text err = new Text();
		title.setStyle("-fx-fill: grey; -fx-font-weight: bolder; -fx-font-size: 15pt;");
		personal.setStyle("-fx-fill: grey; -fx-font-weight: bolder;");
		account.setStyle("-fx-fill: grey; -fx-font-weight: bolder;");
		err.setStyle("-fx-fill: #FF0404; -fx-font-weight: bolder;");
		TextField txtName = new TextField();
		TextField txtSurname = new TextField();
		ComboBox<Role> cmbRole = new ComboBox<>();
		cmbRole.setStyle(" -fx-border: none; -fx-background-color: grey; -fx-text-fill: #fff; -fx-font-weight: bold");
		TextField txtUsername = new TextField();
		PasswordField txtPwd = new PasswordField();
		PasswordField txtConPwd = new PasswordField();
		
		cmbRole.getItems().addAll(Role.AUTHOR, Role.READER);
		cmbRole.setPromptText("Select Role");
		txtName.setPromptText("Name");
		txtSurname.setPromptText("Surname");
		txtUsername.setPromptText("New username");
		txtPwd.setPromptText("New Password");
		txtConPwd.setPromptText("Confirm Password");
		
		Button btnRegister = new Button("Register");
		Button btnCancel = new Button("Cancel");
		btnRegister.setStyle("-fx-padding: 10px 20px; -fx-border: none; -fx-background-color: #00c96f; -fx-text-fill: #fff; -fx-font-weight: bold");
		btnCancel.setStyle("-fx-padding: 10px 20px; -fx-border: none; -fx-background-color: #FF0404; -fx-text-fill: #fff; -fx-font-weight: bold");
		btnRegister.setOnAction(e->{
			String name = txtName.getText(), 
					surname = txtSurname.getText(), 
					username = txtUsername.getText(),
					password = txtPwd.getText(),
					confirm = txtConPwd.getText();
			Role role = cmbRole.getValue();
			StringProperty strErr =new SimpleStringProperty();
			err.textProperty().bind(strErr);
			if(name.length() == 0 || surname.length() == 0 || username.length() == 0 || password.length() == 0 ||
					confirm.length() == 0) {
				strErr.setValue("Please fill in all required information");
			}else if(role == null) strErr.setValue("Please select role");
			else if(!password.equals(confirm)) strErr.setValue("Passwords don't match");
			else
				try {
					Auth.register(new User(-1, name, surname), role, new Credentials(username, password));
					stg.close();
				} catch (UsernameException e1) {
					// TODO Auto-generated catch block
					strErr.setValue("Username already exists");
					e1.printStackTrace();
				}
			
			
		});
		btnCancel.setOnAction(e->stg.close());
		HBox buttons = new HBox(btnRegister, btnCancel);
		buttons.setSpacing(10);
		buttons.setPadding(new Insets(0,0,0,175));
		
		getChildren().addAll(title, personal, txtName, txtSurname, cmbRole,account,txtUsername,txtPwd, txtConPwd, err, buttons);
		setPadding(new Insets(25));
		setSpacing(10);
		
	}

}
