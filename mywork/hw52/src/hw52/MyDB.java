package hw52;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
	/*
	 * CS108 Student: This file will be replaced when we test your code. So, do not add any of your
	 * assignment code to this file. Also, do not modify the public interface of this file.
	 * Only change the public MyDBInfo constants so that it works with the database login credentials 
	 * that we emailed to you.
	 */
	public class MyDBInfo {
		
		public static final String MYSQL_USERNAME = "root";
		public static final String MYSQL_PASSWORD = "dd";
		public static final String MYSQL_DATABASE_SERVER = "localhost";
		public static final String MYSQL_DATABASE_NAME = "c_cs108_psyoung";

	}
	
	public static MyDB getInstance() {
		if (db == null) {
			db = new MyDB();
		}
		return db;
	}
	
	public Connection getConn() {
		return con;
	}
	
	private MyDB() {
		if (con == null) {
			conDB();
		}
	}
	
	private static MyDB db = null;
	private Connection con = null;
	
	private void conDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER + "/" + MyDBInfo.MYSQL_DATABASE_NAME;
			con = DriverManager.getConnection(url, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("CS108 student: Update the MySQL constants to correct values!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("CS108 student: Add the MySQL jar file to your build path!");
		}
	}
		
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
}
