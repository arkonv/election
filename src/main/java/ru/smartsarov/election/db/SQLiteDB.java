package ru.smartsarov.election.db;

import static ru.smartsarov.election.Constants.JSON_INDENT;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class SQLiteDB {
	private static final String DRIVER_DEFAULT = "org.sqlite.JDBC";
	private static final String URL_DEFAULT = "jdbc:sqlite::resource:db/dubna.db";
	
//	@PersistenceContext(unitName = "election")
//	private static EntityManager entityManager;
	
	@PersistenceUnit(unitName = "election")
    private static EntityManagerFactory entityManagerFactory;

	public static EntityManager getEntityManager() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

//	public void setEntityManager(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}

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
	
	public static String getUiks() {
		String query =
			" select uik_number,tik_number,uik_address,uik_phone,voting_room_address,voting_room_phone,geo_address_id,email,title,expiry_date,vybory_izbirkom_uik_url, geo_address.lat, geo_address.lng, uik_member.full_name, uik_member.position, uik_member.appointment " + 
			"from uik " + 
			"join geo_address on uik.geo_address_id = geo_address.id " + 
			"join uik_member on uik.id = uik_member.uik_id " + 
			"order by uik_number ";
		return execQuery(query, true);
	}
	
	private static String execQuery(String query, boolean closeWriter) {
		StringWriter strOut = new StringWriter();
		String indent = JSON_INDENT;
		//PrintWriter writer = new PrintWriter(System.out);
	    //String dbProps = "/database.properties";

	    ResultSetToJson.queryToJson(strOut, /*dbProps,*/ query, indent, closeWriter);
	    
        return strOut.toString();
	}
}