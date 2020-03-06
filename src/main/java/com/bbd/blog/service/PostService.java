package com.bbd.blog.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bbd.blog.exceptions.OperationDeniedException;
import com.bbd.blog.model.Admin;
import com.bbd.blog.model.Author;
import com.bbd.blog.model.Category;
import com.bbd.blog.model.Post;
import com.bbd.blog.repository.DB;

public class PostService {

	public static boolean post(Post post, Author author) {
		String sql = String.format("INSERT INTO POST(user_id, post_title, post_text, post_datetime, cat_id) "
				+ "OUTPUT INSERTED.post_id,INSERTED.post_datetime VALUES (%d, '%s','%s', CURRENT_TIMESTAMP, %d);",author.getId(), post.getTitle(), post.getText(), post.getCategory().getId());
		
		ResultSet rs = DB.executeQuery(sql);
		try {
			if(rs.next()) {
				int id = rs.getInt("post_id");
				post.setDatetime(rs.getDate("post_datetime"));
				post.setId(id);
				author.addPost(post);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static List<Post> getPosts(Author author){
		List<Post> list = new ArrayList<Post>();
		
		String sql = String.format("SELECT * FROM POST WHERE user_id = '%d' AND post_deleted = 'F'", author.getId());
		ResultSet rs = DB.executeQuery(sql);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("post_id");
				Category cat = Category.match(rs.getInt("cat_id"));
				Date d = new Date(rs.getTimestamp("post_datetime").getTime());
				String title = rs.getString("post_title"), text = rs.getString("post_text");
				Post p = new Post(id, cat, d, title, text);
				author.addPost(p);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<Post> getAllPosts(){
		
		List<Post> list = new ArrayList<Post>();
		
		String sql = "SELECT * FROM POST WHERE post_deleted = 'F'";
		ResultSet rs = DB.executeQuery(sql);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("post_id");
				Category cat = Category.match(rs.getInt("cat_id"));
				Date d = new Date(rs.getTimestamp("post_datetime").getTime());
				String title = rs.getString("post_title"), text = rs.getString("post_text");
				Post p = new Post(id, cat, d, title, text);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void deletePost(int id) {
		String sql = String.format("UPDATE POST SET post_deleted = 'T' WHERE post_id = '%d'", id);
		DB.executeUpdate(sql);
	}
	
	public static void edit(Post p) {
		String sql = String.format("UPDATE POST SET post_title = '%s', post_text = '%s' WHERE post_id = '%d'", p.getTitle(), p.getText(), p.getId());
		DB.executeUpdate(sql);
		System.err.println("Post edit complete");
	}
	
	

}
