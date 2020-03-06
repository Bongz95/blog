package com.bbd.blog.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bbd.blog.model.Category;
import com.bbd.blog.model.Comment;
import com.bbd.blog.model.Post;
import com.bbd.blog.model.Reader;
import com.bbd.blog.repository.DB;

public class CommentService {
	
	public static boolean comment(Comment comment, Reader reader) {
		String sql = String.format("INSERT INTO COMMENT(user_id, comment_text, comment_datetime, post_id) "
				+ "OUTPUT INSERTED.comment_id,INSERTED.comment_datetime VALUES (%d,'%s', CURRENT_TIMESTAMP, %d);",reader.getId(), comment.getText(), comment.getPost().getId());
		
		ResultSet rs = DB.executeQuery(sql);
		try {
			if(rs.next()) {
				int id = rs.getInt("comment_id");
				comment.setDatetime(rs.getDate("comment_datetime"));
				comment.setId(id);
				reader.addComment(comment);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static List<Comment> getComments(Reader reader){
		List<Comment> list = new ArrayList<Comment>();
		
		String sql = String.format("SELECT * FROM (SELECT * FROM COMMENT WHERE user_id = %d AND comment_deleted = 'F') as comments"
				+ " INNER JOIN POST on comments.post_id = POST.post_id", reader.getId());
		ResultSet rs = DB.executeQuery(sql);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("comment_id");
				Date d = new Date(rs.getTimestamp("post_datetime").getTime());
				String text = rs.getString("comment_text");
				int user_id = rs.getInt("user_id");
				int post_id =  rs.getInt("post_id");
				Date post_date = new Date(rs.getTimestamp("comment_datetime").getTime());
				Category cat = Category.match(rs.getInt("cat_id"));
				String title = rs.getString("post_title"), post_text = rs.getString("post_text");
				Post p = new Post(post_id, cat, post_date, title, post_text);
				Comment c = new Comment(id, text, d, p, user_id);
				reader.addComment(c);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	public static List<Comment> getComments(Post post){
		List<Comment> list = new ArrayList<Comment>();
		
		String sql = String.format("SELECT * FROM (SELECT * FROM COMMENT WHERE post_id = '%d' AND comment_deleted = 'F') as comments"
				+ " INNER JOIN POST on comments.post_id = POST.post_id", post.getId());
		ResultSet rs = DB.executeQuery(sql);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("comment_id");
				Date d = new Date(rs.getTimestamp("post_datetime").getTime());
				String text = rs.getString("comment_text");
				int user_id = rs.getInt("user_id");
				
				int post_id =  rs.getInt("post_id");
				Date post_date = new Date(rs.getTimestamp("comment_datetime").getTime());
				Category cat = Category.match(rs.getInt("cat_id"));
				String title = rs.getString("post_title"), post_text = rs.getString("post_text");
				Post p = new Post(post_id, cat, post_date, title, post_text);
				Comment c = new Comment(id, text, d, p, user_id);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static List<Comment> getAllComments(){
		List<Comment> list = new ArrayList<Comment>();
			
		String sql = "SELECT * FROM (SELECT * FROM COMMENT WHERE comment_deleted = 'F') as comments"
					+ " INNER JOIN POST on comments.post_id = POST.post_id";
		ResultSet rs = DB.executeQuery(sql);
			
		try {
			while(rs.next()) {
				int id = rs.getInt("comment_id");
				Date d = rs.getDate("comment_datetime");
				String text = rs.getString("comment_text");
				int user_id = rs.getInt("user_id");
				int post_id =  rs.getInt("post_id");
				Date post_date = rs.getDate("post_datetime");
				Category cat = Category.match(rs.getInt("cat_id"));
				String title = rs.getString("post_title"), post_text = rs.getString("post_text");
				Post p = new Post(post_id, cat, post_date, title, post_text);
				Comment c = new Comment(id, text, d, p, user_id);					
				list.add(c);
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			return list;
	}
	
	public static void deleteComment(int id) {
		String sql = String.format("UPDATE COMMENT SET comment_deleted = 'T' WHERE comment_id = '%d'", id);
		DB.executeUpdate(sql);
	}

}
