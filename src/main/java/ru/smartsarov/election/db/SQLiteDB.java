package ru.smartsarov.election.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {
	private static final String DRIVER_DEFAULT = "org.sqlite.JDBC";
	private static final String URL_DEFAULT = "jdbc:sqlite::resource:db/dubna.db";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_DEFAULT);

		Connection conn = DriverManager.getConnection(URL_DEFAULT);
		conn.setAutoCommit(false);
		return conn;
	}
	
	public static Connection getConnection(String dbName) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_DEFAULT);

		Connection conn = DriverManager.getConnection("jdbc:sqlite::resource:db/" + dbName);
		conn.setAutoCommit(false);
		return conn;
	}

	public static int execute(String sql) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = null;
		int result;
		
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			conn.commit();
		} finally {
			if (stmt != null)
				stmt.close();
			conn.close();
		}
		
		return result;
	}
	
	public static int execute(String dbName, String sql) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection(dbName);
		Statement stmt = null;
		int result;
		
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			conn.commit();
		} finally {
			if (stmt != null)
				stmt.close();
			conn.close();
		}
		
		return result;
	}
	
	/*public static void execute(List<String> sql) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();

			for (String s : sql) {
				stmt.executeUpdate(s);
			}
			conn.commit();
		} finally {
			if (stmt != null)
				stmt.close();
			conn.close();
		}
	}*/
}