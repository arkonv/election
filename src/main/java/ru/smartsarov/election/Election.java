package ru.smartsarov.election;

import static ru.smartsarov.election.Constants.CIKRF_UIK_INFO_URL;
import static ru.smartsarov.election.Constants.DUBNA_UIKS;
import static ru.smartsarov.election.Constants.MOSCOW_REGION;
import static ru.smartsarov.election.Constants.NIZHNY_NOVGOROD_REGION;
import static ru.smartsarov.election.Constants.SAROV_UIKS;
import static ru.smartsarov.election.Constants.UIK_BAD_NUMBER_MESSAGE;
import static ru.smartsarov.election.Constants.UIK_VYBORY_IZBIRKOM_RU_MOSCOW_REGION;
import static ru.smartsarov.election.Constants.UIK_VYBORY_IZBIRKOM_RU_NNOV;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.smartsarov.election.db.SQLiteDB;
import ru.smartsarov.election.db.UikHtml;
import ru.smartsarov.election.uik.Uik;
import ru.smartsarov.geocoder.GeoAddress;
import ru.smartsarov.geocoder.Geocoder;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class Election {	
	@GET
	@Path("/conn")
	public Response connectDB() {
		String resp = null;
		try {
			SQLiteDB.getConnection();
			return Response.status(Response.Status.OK).entity(resp).build();
		/*} catch (ValidationException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}*/
		} catch (Exception e) {
			resp = e.getMessage();
			e.printStackTrace();
			// log error
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/{region}/{uik}")
	public Response uikInfo(@PathParam("region") int region, @PathParam("uik") int uikNumber) {
		int[] uikNumbers = {uikNumber};
		return Response.status(Response.Status.OK).entity(getUiksString(region, uikNumbers)).build();
	}

	// TODO распараллелить этот метод
	@GET
	@Path("/{city}/uiks")
	//public Response uikSarov() {
	public Response getUiks(@PathParam("city") String city) {
		String resp = null;
		String cityUiks = null;
		int region;
		
		switch (city) {
		case "sarov":
			cityUiks = SAROV_UIKS;
			region = NIZHNY_NOVGOROD_REGION;
//			vybboryIzbirkomUrl = UIK_VYBORY_IZBIRKOM_RU_NNOV;
//			dbName = "sarov.db";
			break;
		case "dubna":
			cityUiks = DUBNA_UIKS;
			region = NIZHNY_NOVGOROD_REGION;
//			vybboryIzbirkomUrl = UIK_VYBORY_IZBIRKOM_RU_MOSCOW_REGION;
//			dbName = "dubna.db";
			break;
		default:
			resp = "Неправильно указан город";
			return Response.status(Response.Status.OK).entity(resp).build();
		}
		
		try {
			String json = Jsoup.connect(cityUiks).ignoreContentType(true).get().text();
			Gson gson = new GsonBuilder().create();

			List<UikHtml> uikHtml = gson.fromJson(json, new TypeToken<List<UikHtml>>(){}.getType());
			int[] uikNumbers = new int[uikHtml.size()];

			for (int i = 0; i < uikHtml.size(); i++) {
				String text = uikHtml.get(i).getText();
				uikNumbers[i] = Integer.valueOf(text.substring(text.indexOf("№") + 1));
			}
			resp = getUiksString(region, uikNumbers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).entity(resp).build();
	}
	
	@GET
	@Path("/dubna/uiks")
	public Response uikDubna() {
		String resp = null;
		try {
			String json = Jsoup.connect(DUBNA_UIKS).ignoreContentType(true).get().text();
			Gson gson = new GsonBuilder().create();

			List<UikHtml> uikHtml = gson.fromJson(json, new TypeToken<List<UikHtml>>(){}.getType());
			int[] uikNumbers = new int[uikHtml.size()];

			for (int i = 0; i < uikHtml.size(); i++) {
				String text = uikHtml.get(i).getText();
				uikNumbers[i] = Integer.valueOf(text.substring(text.indexOf("№") + 1));
			}
			resp = getUiksString(MOSCOW_REGION, uikNumbers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).entity(resp).build();
	}

	
	@GET
	@Path("/{city}/uiks/html")
	public Response getUiksHtml(@PathParam("city") String city) {
		String resp = null;
		
		String cityUiks = null;
		int region;
		String vybboryIzbirkomUrl = null;
		String dbName = null;
				
		switch (city) {
		case "sarov":
			cityUiks = SAROV_UIKS;
			region = NIZHNY_NOVGOROD_REGION;
			vybboryIzbirkomUrl = UIK_VYBORY_IZBIRKOM_RU_NNOV;
			dbName = "sarov.db";
			break;
		case "dubna":
			cityUiks = DUBNA_UIKS;
			region = NIZHNY_NOVGOROD_REGION;
			vybboryIzbirkomUrl = UIK_VYBORY_IZBIRKOM_RU_MOSCOW_REGION;
			dbName = "dubna.db";
			break;
		default:
			resp = "Неправильно указан город";
			return Response.status(Response.Status.OK).entity(resp).build();
		}
		
		try {
			String json = Jsoup.connect(cityUiks).ignoreContentType(true).get().text();
			Gson gson = new GsonBuilder().create();
			StringBuilder sb = new StringBuilder();
			
			List<UikHtml> uikHtml = gson.fromJson(json, new TypeToken<List<UikHtml>>(){}.getType());
			int[] uikNumbers = new int[uikHtml.size()];

			for (int i = 0; i < uikHtml.size(); i++) {
				String text = uikHtml.get(i).getText();
				uikNumbers[i] = Integer.valueOf(text.substring(text.indexOf("№") + 1));
				uikHtml.get(i).setUikNumber(uikNumbers[i]);
				uikHtml.get(i).setCikHtml(Jsoup.connect(CIKRF_UIK_INFO_URL)
						.data("uik", String.valueOf(uikHtml.get(i).getUikNumber()))
						.data("subject", String.valueOf(region))
						.post().toString());
				String vyboryIzbirkomUikUrl = vybboryIzbirkomUrl + uikHtml.get(i).getVyboryIzbirkomUikId();
				uikHtml.get(i).setVyboryIzbirkomUikUrl(vyboryIzbirkomUikUrl);
				uikHtml.get(i).setVyboryIzbirkomUikHtml(Jsoup.connect(vyboryIzbirkomUikUrl).get().select(".center-colm").outerHtml());
				
				sb.append("insert into uik_html(uik_number,cik_uik_html,vybory_izbirkom_uik_id,vybory_izbirkom_uik_url,vybory_izbirkom_uik_html)"
						+ " values(" + String.valueOf(uikHtml.get(i).getUikNumber()).replace("'", "''") + ",'" + uikHtml.get(i).getCikHtml().replace("'", "''") + "','" + uikHtml.get(i).getVyboryIzbirkomUikId().replace("'", "''") + "','" + uikHtml.get(i).getVyboryIzbirkomUikUrl().replace("'", "''") + "','" + uikHtml.get(i).getVyboryIzbirkomUikHtml().replace("'", "''") + "')").append(";");
			}

			// Записываем в таблицу uik_html
			//List<String> sql = Arrays.asList(sb.toString().split(";"));
			resp = String.valueOf(SQLiteDB.execute(dbName, sb.toString()));
		} catch (IOException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			resp = e.getMessage();
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).entity(resp).build();
	}
	
	private String getUiksString(int region, int[] uikNumbers) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//gson.serializeNulls();
		String uiksString = gson.toJson(getUiks(region, uikNumbers), new TypeToken<List<Uik>>(){}.getType());
		return uiksString;
	}
	
	private List<Uik> getUiks(int region, int[] uikNumber) {
		List<Uik> uiks = new ArrayList<>(uikNumber.length);

		for (int i = 0; i < uikNumber.length; i++) {
			Uik uik = new Uik();
			try {
				Document doc = Jsoup.connect(CIKRF_UIK_INFO_URL)
						.data("uik", String.valueOf(uikNumber[i]))
						.data("subject", String.valueOf(region))
						.post();

				 uik.setRequestUikNumber(String.valueOf(uikNumber[i]));
				 
				if (!doc.select(".dotted p:eq(0)").text().equals(UIK_BAD_NUMBER_MESSAGE)) {
					String[] p = { doc.select(".dotted p:eq(1)").text(), doc.select(".dotted p:eq(2)").text(), doc.select(".dotted p:eq(3)").text(), doc.select(".dotted p:eq(4)").text(), doc.select(".dotted p:eq(5)").text() };
					uik.setUikNumber(p[0].substring(p[0].indexOf("№") + 1, p[0].indexOf(" ", p[0].indexOf("№"))));
					uik.setTikNumber(p[0].substring(p[0].length() - 3, p[0].length()));
					uik.setUikAddress(getPTag(p[1]));
					uik.setUikPhone(getPTag(p[2]));
					uik.setVotingRoomAddress(getPTag(p[3]));
					uik.setVotingRoomPhone(getPTag(p[4]));
					
					GeoAddress geoAddress = Geocoder.geoAddress(uik.getUikAddress(), 1).get(0);
					uik.setLat(geoAddress.getLat());
					uik.setLng(geoAddress.getLng());
				}
				
				uiks.add(uik);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return uiks;
	}
	
	@GET
	@Path("/getUikFromFile")
	public Response getUikFromFile() {
		String resp = null;		
		getUiksFromFile("/db/sarov.txt");
		
		return Response.status(Response.Status.OK).entity(resp).build();
	}
	
	private List<Uik> getUiksFromFile(String pathFile) {
		List<Uik> uiks = new ArrayList<>();
		//String json = null;
		Gson gson = new GsonBuilder().create();
	    StringBuilder json = new StringBuilder();
		
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
	    	//json.append(reader.lines());
	    	reader.lines().forEach(line -> json.append(line));
	    } catch (IOException e) {
			e.printStackTrace();
		}

		List<UikGeoAddress> uikGeoAddress = gson.fromJson(json.toString(), new TypeToken<List<UikGeoAddress>>(){}.getType());
		int[] uikNumbers = new int[uikGeoAddress.size()];

		for (int i = 0; i < uikGeoAddress.size(); i++) {
			//String text = geoAddressWithUik.get(i).getText();
			//uikNumbers[i] = Integer.valueOf(text.substring(text.indexOf("№") + 1));
		}
		//resp = getUiksString(NIZHNY_NOVGOROD_REGION, uikNumbers);
		
		return uiks;
	}
	
	private String getPTag(String p) {
		return p.substring(p.indexOf(":") + 2, p.length());
	}
}