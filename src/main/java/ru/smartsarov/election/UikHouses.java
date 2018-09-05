package ru.smartsarov.election;

import static ru.smartsarov.election.Constants.CIKRF_FIND_UIK_URL;
import static ru.smartsarov.election.Constants.CIKRF_UIK_ADDRESS_URL;
import static ru.smartsarov.election.Constants.DUBNA_ADDRESS;
import static ru.smartsarov.election.Constants.DUBNA_DB_NAME;
import static ru.smartsarov.election.Constants.DUBNA_STREETS;
import static ru.smartsarov.election.Constants.SAROV_ADDRESS;
import static ru.smartsarov.election.Constants.SAROV_DB_NAME;
import static ru.smartsarov.election.Constants.SAROV_STREETS;
import static ru.smartsarov.election.Constants.UIK_BAD_NUMBER_MESSAGE;
import static ru.smartsarov.election.Constants.UIK_BAD_NUMBER_MESSAGE2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.smartsarov.election.cik.Street;
import ru.smartsarov.election.cik.UikRequest;
import ru.smartsarov.election.db.SQLiteDB;
import ru.smartsarov.geocoder.GeoAddress;
import ru.smartsarov.geocoder.Geocoder;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class UikHouses {
	
	@GET
	@Path("/{city}/uiks_houses")
	public Response uiksHouses(@PathParam("city") String city) {
		String resp = null;
		
		String dbName = null;
		switch (city) {
		case "sarov":
			dbName = SAROV_DB_NAME;
			break;
		case "dubna":
			dbName = DUBNA_DB_NAME;
			break;
		default:
			resp = "Указан неверный город";
			return Response.status(Response.Status.OK).entity(resp).build();
		}
		
		try {
			resp = SQLiteDB.getUiksHousesFromDB(dbName);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).entity(resp).build();
	}

	@GET
	@Path("/{city}/uiks_houses2file")
	public Response uiksHouses2File(@PathParam("city") String city) throws UnsupportedEncodingException, URISyntaxException, IOException {
		String resp = null;
		String streetsCik = null;
		//String dbName = null;
		String address = null;
		
		switch (city) {
		case "sarov":
			//dbName = SAROV_DB_NAME;
			streetsCik = SAROV_STREETS;
			address = SAROV_ADDRESS;
			break;
		case "dubna":
			//dbName = DUBNA_DB_NAME;
			streetsCik = DUBNA_STREETS;
			address = DUBNA_ADDRESS;
			break;
		default:
			resp = "Указан неверный город";
			return Response.status(Response.Status.OK).entity(resp).build();
		}
		
		resp = getUiksGeoAddresses(streetsCik, address);

		// TODO ToDB
		
		return Response.status(Response.Status.OK).entity(resp).build();
	}

	@GET
	@Path("/{city}/uiksFile2DB")
	public Response uiksFile2DB(@PathParam("city") String city) {
		String resp = null;
		
		switch (city) {
		case "sarov":
		case "dubna":
			break;
		default:
			resp = "Указан неверный город";
			return Response.status(Response.Status.OK).entity(resp).build();
		}
		
		try {
			resp = "Rows inserted: " + String.valueOf(uiksFromFile2DB(city));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).entity(resp).build();
	}
	
	private int uiksFromFile2DB(String city) throws ClassNotFoundException, SQLException {
		Gson gson = new GsonBuilder().create();
		
		StringBuilder json = readFile("/" + city + ".txt");
		List<UikGeoAddress> uikGeoAddresses = gson.fromJson(json.toString(), new TypeToken<List<UikGeoAddress>>(){}.getType());
		int uiksCount = uikGeoAddresses.size();
	    Object[][] params = new Object[uiksCount][5];

		for (int i = 0; i < uiksCount; i++) {
			params[i][0] = uikGeoAddresses.get(i).getUikNumber();
			params[i][1] = uikGeoAddresses.get(i).getRequestAddress();
			params[i][2] = uikGeoAddresses.get(i).getFullAddress();
			params[i][3] = uikGeoAddresses.get(i).getLat();
			params[i][4] = uikGeoAddresses.get(i).getLng();
		}
		
		String insertSQL = "insert into uik_geo_address(uik_number,request_address,full_address,lat,lng) values(?, ?, ?, ?, ?)";
		Connection conn = SQLiteDB.getConnection(city + ".db");
		int[] rows = new QueryRunner().batch(conn, insertSQL, params);
		conn.commit();
		DbUtils.close(conn);

		return rows.length;
	}
	
	private StringBuilder readFile(String pathFile) {
		StringBuilder fileData = new StringBuilder();
		
		java.nio.file.Path path = null; 
		try {
			path = Paths.get(getClass().getClassLoader().getResource(pathFile).toURI());
		} catch (URISyntaxException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/*
		// 1.
		File file = new File(pathFile);
		StringBuilder fileContents = new StringBuilder((int)file.length());

	    //try (Scanner scanner = new Scanner(file)) {
	    try (Scanner scanner = new Scanner((Readable) new BufferedReader(new FileReader(file)))) {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine());
	        }
	        json = fileContents.toString();
	    } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    // 2.
	    try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
	    	lines.forEach(line -> data.append(line));
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // 3.
	    try {
			String content = new String(Files.readAllBytes(new File(pathFile).toPath()), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	    // 4.
	    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
	    	reader.lines().forEach(line -> fileData.append(line));
	    } catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return fileData;
	}
	
	private String getUiksGeoAddresses(String streetsCik, String address) throws URISyntaxException, UnsupportedEncodingException, IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Street> streets = null;
		List<UikGeoAddress> geo = new ArrayList<>();

		// TODO переименовать поля класса UikRequest в удобные наименования через аннотации Gson'а
		try {
			String jsonStreets = Jsoup.connect(streetsCik).ignoreContentType(true).get().text();
			streets = gson.fromJson(jsonStreets, new TypeToken<List<Street>>(){}.getType());
			
			for (Street street : streets) {
				String jsonHouses = Jsoup.connect(CIKRF_FIND_UIK_URL + street.getId()).ignoreContentType(true).get().text();
				street.addHouses(gson.fromJson(jsonHouses, new TypeToken<List<UikRequest>>(){}.getType()));
				
				for (int i = 0; i < street.getHouses().size(); i++) {
					String requestAddress = address + street.getText() + ", " + street.getHouses().get(i).getText();
					
					Document doc = Jsoup.connect(CIKRF_UIK_ADDRESS_URL + street.getHouses().get(i).getAAttr().getIntid() + "?do=result").ignoreContentType(true).get();
					Integer uikNumber = null;
					if (!doc.select(".dotted p:eq(0)").text().equals(UIK_BAD_NUMBER_MESSAGE) && !doc.select(".dotted p:eq(0)").text().equals(UIK_BAD_NUMBER_MESSAGE2)) {
						String p = doc.select(".dotted p:eq(1)").text();
						try {
						uikNumber = Integer.valueOf(p.substring(p.indexOf("№") + 1, p.indexOf(" ", p.indexOf("№"))));
						}
						// TODO Integer
						catch(NumberFormatException e) {
							e.getMessage();
						}
					}

					GeoAddress geoAddress = Geocoder.geoAddress(requestAddress, 1).get(0);
					geo.add(new UikGeoAddress(requestAddress, geoAddress.getFullAddress(), geoAddress.getLat(), geoAddress.getLng(), uikNumber));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String str = gson.toJson(geo, new TypeToken<List<UikGeoAddress>>(){}.getType());
		// TODO write to file
		//java.nio.file.Files.write(Paths.get(getClass().getClassLoader().getResource("houses.txt").toURI()), str.getBytes("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		
		return str;
	}
}