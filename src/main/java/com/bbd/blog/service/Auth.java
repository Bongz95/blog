package com.bbd.blog.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bbd.blog.exceptions.AccessRevokedException;
import com.bbd.blog.exceptions.InvalidUserCredentialsException;
import com.bbd.blog.exceptions.UsernameException;
import com.bbd.blog.model.Admin;
import com.bbd.blog.model.Author;
import com.bbd.blog.model.Reader;
import com.bbd.blog.model.Role;
import com.bbd.blog.model.User;
import com.bbd.blog.repository.DB;
import com.bbd.blog.security.Credentials;
import com.bbd.blog.security.MessageHash;
import com.bbd.blog.ui.LoginPane;
import com.bbd.blog.ui.StageInstance;

import javafx.scene.Scene;

public class Auth {
	
	private static User user;
	
	public static User login(String username, String password) throws InvalidUserCredentialsException, AccessRevokedException {
		String sql = "SELECT USER_.user_id, USER_.role_id, USER_.user_name, USER_.user_surname, CREDS.username, CREDS.access_granted "
				+ "FROM (SELECT * FROM CREDENTIALS WHERE password = '"+MessageHash.generateHash(password)+"') "
				+ "AS CREDS "
				+ "INNER JOIN USER_ "
				+ "ON CREDS.user_id = USER_.user_id";
		ResultSet rs = DB.executeQuery(sql);
		
		try {
		
			if(rs.next() && username.equals(rs.getString("username"))) {
				if(! rs.getString("access_granted").equals("T")) throw new AccessRevokedException("Access Denied");
				int id = rs.getInt("user_id");
				String name = rs.getString("user_name"), surname = rs.getString("user_surname");
				int role_id = rs.getInt("role_id");
				switch(role_id) {
					case 1:
						user = new Admin(id, name, surname);
						System.out.println("Logged in as admin");
						break;
					case 2:
						user = new Author(id, name, surname);
						System.out.println("Logged in as author");
						break;
					case 3:
						user = new Reader(id, name, surname);
						System.out.println("Logged in as reader");
						break;
				}
				return user;
				
			}else throw new InvalidUserCredentialsException("Invalid Username or password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User getActiveUser() {
		return user;
	}
	public static void logout() {
		user = null;
		StageInstance.getInstance().setScene(new Scene(new LoginPane(), 1080,720));
	}
	public static boolean register(User user, Role role, Credentials cred) throws UsernameException {
		String sql1 = String.format("SELECT * FROM CREDENTIALS WHERE username = '%s'",cred.getUsername());
		try {
			if(DB.executeQuery(sql1).next()) throw new UsernameException("Username already exists");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = String.format("BEGIN TRANSACTION\r\n" + 
				"	DECLARE @UserId int;\r\n" + 
				"	INSERT INTO USER_(role_id, user_name, user_surname) VALUES ('%d','%s', '%s')\r\n" + 
				"	SELECT @UserID = scope_identity();\r\n" + 
				"	INSERT INTO CREDENTIALS VALUES (@UserId, '%s', '%s', 'T');\r\n" + 
				"COMMIT", role.getId(), user.getName(), user.getSurname(), cred.getUsername(), MessageHash.generateHash(cred.getPassword()));
		System.err.println(sql);
		DB.executeUpdate(sql);
		return true;
	}
}
