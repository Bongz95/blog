package com.bbd.blog.repository;
import java.sql.*;


public class DB {
	
	private static final  String connectionString = "jdbc:sqlserver://localhost:1433;"
										            + "database=BLOG_ENGINE;"
										            + "user=2161;"
										            + "password=3mm@nu3l;";
	private static Connection conn;
	
	
	
	
	public static boolean executeUpdate(String sql) {
		
		if(establishConnection()) {
			try {
				Statement st = conn.createStatement();
				st.executeUpdate(sql);
				st.close();
				conn.close();
			} catch (SQLException e) {
				System.err.println("ERR: Unable to execute query");
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	public static ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		if(establishConnection()) {
			try {
				Statement st = conn.createStatement();
				rs = st.executeQuery(sql);
				
			} catch (SQLException e) {
				System.err.println("ERR: Unable to execute query");
				e.printStackTrace();
			}
		}
		
		return rs;
	}
	private static boolean establishConnection() {
		
		try {
			conn = DriverManager.getConnection(connectionString);
			return true;
		}catch(SQLException e) {
			System.err.print("ERR: Unable to establish database connection");
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static boolean closeConnection() {
		try {
			if(!conn.isClosed())
				conn.close();
			return true;
		} catch (SQLException e) {
			System.err.print("ERR: Unable to close database connection");
			e.printStackTrace();
		}
		return false;
	}

}
