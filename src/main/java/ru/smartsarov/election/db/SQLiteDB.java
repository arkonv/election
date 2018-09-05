package ru.smartsarov.election.db;

import static ru.smartsarov.election.Constants.JSON_INDENT;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.smartsarov.election.UikGeoAddress;
import ru.smartsarov.election.uik.UikWithMembers;

public class SQLiteDB {
	private static final String DRIVER_DEFAULT = "org.sqlite.JDBC";
	private static final String URL_DEFAULT = "jdbc:sqlite::resource:db/dubna.db";
	
	/*@PersistenceContext(unitName = "election")
	private static EntityManager entityManager;
	
	@PersistenceUnit(unitName = "election")
    private static EntityManagerFactory entityManagerFactory;

	public static EntityManager getEntityManager() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}*/

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
	
	public static String getUiksFromDB(String dbName) throws ClassNotFoundException, SQLException {
		/*"select uik_number,tik_number,uik_address,uik_phone,voting_room_address,voting_room_phone,email,title,expiry_date,vybory_izbirkom_uik_url, geo_address.full_address, geo_address.lat, geo_address.lng, uik_member.full_name, uik_member.position, uik_member.appointment " + 
		"from uik " + 
		"join geo_address on uik.geo_address_id = geo_address.id " + 
		"join uik_member on uik.id = uik_member.uik_id " + 
		"order by uik_number ";*/
		
		String queryUiks =		
			" select "
			+ "uik.id,"
			+ "uik.uik_number as uikNumber,"
			+ "uik.tik_number as tikNumber,"
			+ "uik.uik_address as uikAddress,"
			+ "uik.uik_phone as uikPhone,"
			+ "uik.voting_room_address as votingRoomAddress,"
			+ "uik.voting_room_phone as votingRoomPhone,"
			+ "uik.email,"
			+ "uik.title,"
			+ "uik.expiry_date as expiryDate,"
			+ "uik.vybory_izbirkom_uik_url as vyboryIzbirkomUikUrl,"
			+ "geo_address.full_address as fullAddress,"
			+ "geo_address.lat,"
			+ "geo_address.lng " + 
			" from uik " + 
			" join geo_address on uik.geo_address_id = geo_address.id " + 
			" order by uik_number ";
		
		String queryUikMembers = 
				" select " +
				" uik.uik_number as uikNumber, " +
				" uik_member.full_name as fullName, " +
				" uik_member.position, " +
				" uik_member.appointment " + 
				" from uik_member " + 
				" join uik on uik.id = uik_member.uik_id " +
				" where uik.id = ? " +
				" order by uik_member.id";
		
		/*Statement stmt = conn.createStatement();
		List<UikWithMembers> uiks = (List<UikWithMembers>) stmt.executeQuery(queryUiks);
		*/

		// https://www.baeldung.com/apache-commons-dbutils
		// Use the BeanListHandler implementation to convert all ResultSet rows into a List of Person JavaBeans
		// TODO написать обработчик типа java.lang.Double для lat и lng через gson TypeConverter
		ResultSetHandler<List<UikWithMembers>> h = new BeanListHandler<UikWithMembers>(UikWithMembers.class);
		List<UikWithMembers> uiks = null;

		Connection conn = getConnection(dbName);
		
		try {
			QueryRunner run = new QueryRunner();

			// Execute the SQL statement and return the results in a List of UikWithMember objects generated by the BeanListHandler.
			uiks = run.query(conn, queryUiks, h);
			ResultSetHandler<List<UikMember>> h1 = new BeanListHandler<UikMember>(UikMember.class);

			for (UikWithMembers u : uiks) {
				u.setUikMembers(run.query(conn, queryUikMembers, h1, String.valueOf(u.getId())));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(conn);
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(uiks, new TypeToken<List<UikWithMembers>>(){}.getType());
			
		//return execQuery(dbName, queryUiks, true);
	}
	
	public static String getUiksHousesFromDB(String dbName) throws ClassNotFoundException, SQLException {
		String queryUiks =
			" select " +
			" distinct uik_number as uikNumber " +
			" from uik_geo_address " +
			" where uik_number is not null " +
			" order by uik_number";

		String queryGeoAddress =
				" select distinct " + 
				" full_address as fullAddress," +
				" cast (lat as text) as lat, " +
				" cast (lng as text) as lng " +
				" from uik_geo_address " + 
				" where uik_number = ?";
		
		// TODO убрать временное решение по фильтрации нераспознанных адресов геокодером Яндекса
		if (dbName.equals("dubna.db")) {
			queryGeoAddress =
					" select distinct " + 
					" full_address as fullAddress," +
					" cast (lat as text) as lat, " +
					" cast (lng as text) as lng " +
					" from uik_geo_address " + 
					" where full_address != 'Россия, Московская область, Дубна' and full_address not like 'Россия, Московская область, городской округ Чехов%' " +
					" and uik_number = ?";
		}

		// https://www.baeldung.com/apache-commons-dbutils
		List<Addr> uiks = null;
		
		Connection conn = getConnection(dbName);
		QueryRunner run = new QueryRunner();

		try {
			uiks = run.query(conn, queryUiks, new BeanListHandler<Addr>(Addr.class));
			for (Addr u : uiks) {
				u.setHouses(run.query(conn, queryGeoAddress, new BeanListHandler<GeoAddressDelete>(GeoAddressDelete.class), u.getUikNumber()));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(conn);
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(uiks, new TypeToken<List<Addr>>(){}.getType());
	}
}