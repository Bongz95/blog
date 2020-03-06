package com.bbd.blog.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bbd.blog.model.Admin;
import com.bbd.blog.model.Author;
import com.bbd.blog.model.Reader;
import com.bbd.blog.model.User;
import com.bbd.blog.repository.DB;

public class UserService {

	public static User getUser(int id) {
		String sql = String.format("SELECT * FROM USER_ WHERE user_id = '%d'", id);
		System.err.println(id);
		ResultSet rs = DB.executeQuery(sql);
		User user =null;
		try {
			if(rs.next()) {
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return user;
	}

}
