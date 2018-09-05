package ru.smartsarov.election;

import static ru.smartsarov.election.Constants.CIKRF_UIK_INFO_URL;
import static ru.smartsarov.election.Constants.DUBNA_DB_NAME;
import static ru.smartsarov.election.Constants.DUBNA_TIK;
import static ru.smartsarov.election.Constants.DUBNA_UIKS;
import static ru.smartsarov.election.Constants.MOSCOW_REGION;
import static ru.smartsarov.election.Constants.NIZHNY_NOVGOROD_REGION;
import static ru.smartsarov.election.Constants.SAROV_DB_NAME;
import static ru.smartsarov.election.Constants.SAROV_TIK;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.smartsarov.election.db.SQLiteDB;
import ru.smartsarov.election.db.Uik;
import ru.smartsarov.election.db.UikMember;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class Election {
	
	// TODO доработать под конкретный УИК
	@GET
	@Path("/{region}/{uik}")
	public Response uikInfo(@PathParam("region") int region, @PathParam("uik") int uikNumber) {
		int[] uikNumbers = {uikNumber};
		return Response.status(Response.Status.OK).entity(uiksToJsonString(region, uikNumbers)).build();
	}

	// TODO распараллелить этот метод
/*	@GET
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

			List<UikHtml2> uikHtml2 = gson.fromJson(json, new TypeToken<List<UikHtml2>>(){}.getType());
			int[] uikNumbers = new int[uikHtml2.size()];

			for (int i = 0; i < uikHtml2.size(); i++) {
				String text = uikHtml2.get(i).getText();
				uikNumbers[i] = Integer.valueOf(text.substring(text.indexOf("№") + 1));
			}
			resp = uiksToJsonString(region, uikNumbers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).entity(resp).build();
	}*/

	@GET
	@Path("/{city}/uiks")
	public Response getUiks(@PathParam("city") String city) {
		
//		List<Uik> uiks = SQLiteDB.getEntityManager().createNamedQuery("Uik.findAll", Uik.class).getResultList();
//		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
//		//gson.serializeNulls();
//		String uiksString = gson.toJson(uiks, new TypeToken<List<Uik>>(){}.getType());
		
		String resp = null;
		String dbName = null;
    	try {
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

    		resp = SQLiteDB.getUiksFromDB(dbName);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return Response.status(Response.Status.OK).entity(resp).build();
	}
	
/*	@GET
	@Path("/{city}/uiks2db")
	public Response uiks2db(@PathParam("city") String city) {
		String resp = null;
		String cityUiks = null;
		String cityTik = null;
		int region;
		String vybboryIzbirkomUrl = null;
		String dbName = null;
				
		switch (city) {
		case "sarov":
			cityUiks = SAROV_UIKS;
			cityTik = SAROV_TIK;
			region = NIZHNY_NOVGOROD_REGION;
			vybboryIzbirkomUrl = UIK_VYBORY_IZBIRKOM_RU_NNOV;
			dbName = SAROV_DB_NAME;
			break;
		case "dubna":
			cityUiks = DUBNA_UIKS;
			cityTik = DUBNA_TIK;
			region = MOSCOW_REGION;
			vybboryIzbirkomUrl = UIK_VYBORY_IZBIRKOM_RU_MOSCOW_REGION;
			dbName = DUBNA_DB_NAME;
			break;
		default:
			resp = "Указан неверный город";
			return Response.status(Response.Status.OK).entity(resp).build();
		}
		
		// TODO ТИК, адрес из uikAddress
		// УИКи, адрес из votingRoomAddress
		
		try {
			String json = Jsoup.connect(cityUiks).ignoreContentType(true).get().text();
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			StringBuilder sb = new StringBuilder();
			List<UikMember> uikMembers = new ArrayList<>();
			
			// 1. Получаем id и title с vybory.izbirkom.ru/region
			List<Uik> uiks = gson.fromJson(json, new TypeToken<List<Uik>>(){}.getType());
			List<ru.smartsarov.election.db.GeoAddress> geoAddresses = new ArrayList<>(uiks.size());
			
//			// ТИК
//			String jsonTik = Jsoup.connect(cityTik).ignoreContentType(true).get().text();
//			Uik tik = gson.fromJson(jsonTik, Uik.class);
//			uiks.add(0, tik);
			
			int[] uikNumbers = new int[uiks.size()];

			// 2. Делаем post-запрос на http://www.cikrf.ru/services/lk_address/?do=result_uik
			for (int i = 0; i < uiks.size(); i++) {
				String title = uiks.get(i).getTitle();
				uikNumbers[i] = Integer.valueOf(title.substring(title.indexOf("№") + 1));
				uiks.get(i).setUikNumber(uikNumbers[i]);
				uiks.get(i).setCikUikHtml(Jsoup.connect(CIKRF_UIK_INFO_URL)
						.data("uik", String.valueOf(uikNumbers[i]))
						.data("subject", String.valueOf(region))
						.post().toString());

				uiks.get(i).setRequestUikNumber(String.valueOf(uikNumbers[i]));
				String vyboryIzbirkomUikUrl = vybboryIzbirkomUrl + uiks.get(i).getVyboryIzbirkomUikId();
				uiks.get(i).setVyboryIzbirkomUikUrl(vyboryIzbirkomUikUrl);
				uiks.get(i).setVyboryIzbirkomUikHtml(Jsoup.connect(vyboryIzbirkomUikUrl).get().select(".center-colm").outerHtml());
				
				parseCikUikHtml(uiks.get(i));
				parseVyboryIzbirkomUikHtml(uiks.get(i), i + 1, uikMembers);

				// TODO переделать ru.smartsarov.geocoder.GeoAddress
				// Собираем данные geocoder'а
				ru.smartsarov.geocoder.GeoAddress geo = ru.smartsarov.geocoder.Geocoder.geoAddress(uiks.get(i).getUikAddress(), 1).get(0);
				geoAddresses.add(new ru.smartsarov.election.db.GeoAddress(i + 1, geo.getRequestAddress(), geo.getFullAddress(), geo.getLat(), geo.getLng()));
				uiks.get(i).setGeoAddressId(i + 1);

				sb.append("insert into geo_address(request_address,full_address,lat,lng) values('" + geoAddresses.get(i).getRequestAddress().replace("'", "''") + "','" + geoAddresses.get(i).getFullAddress().replace("'", "''") + "'," + String.valueOf(geoAddresses.get(i).getLat()) + "," + String.valueOf(geoAddresses.get(i).getLng())).append(");");

				sb.append("insert into uik(uik_number,tik_number,request_uik_number,uik_address,uik_phone,voting_room_address,voting_room_phone,geo_address_id,email,title,expiry_date,cik_uik_html,vybory_izbirkom_uik_id,vybory_izbirkom_uik_url,vybory_izbirkom_uik_html)"
						+ " values(" + String.valueOf(uiks.get(i).getUikNumber()) + ",'" +
						uiks.get(i).getTikNumber().replace("'", "''") + "','" +
						uiks.get(i).getRequestUikNumber().replace("'", "''") + "','" +
						uiks.get(i).getUikAddress().replace("'", "''") + "','" +
						uiks.get(i).getUikPhone().replace("'", "''") + "','" +
						uiks.get(i).getVotingRoomAddress().replace("'", "''") + "','" +
						uiks.get(i).getVotingRoomPhone().replace("'", "''") + "'," +
						String.valueOf(uiks.get(i).getGeoAddressId()) + "," +
						(uiks.get(i).getEmail() == null ? "null" : ("'" + uiks.get(i).getEmail().replace("'", "''") + "'")) + ",'" +
						uiks.get(i).getTitle().replace("'", "''") + "'," +
						(uiks.get(i).getExpiryDate() == null ? "null" : ("'" + uiks.get(i).getExpiryDate().replace("'", "''") + "'")) + ",'" +
						uiks.get(i).getCikUikHtml().replace("'", "''") + "','" +
						uiks.get(i).getVyboryIzbirkomUikId().replace("'", "''") + "','" +
						uiks.get(i).getVyboryIzbirkomUikUrl().replace("'", "''") + "','" +
						uiks.get(i).getVyboryIzbirkomUikHtml().replace("'", "''") + "')").append(";");
			}
			
			// 4. Собираем sql-batch. Добавляем: UikMember
			for (UikMember u : uikMembers) {
				sb.append("insert into uik_member(uik_id,full_name,position,appointment) values(" + String.valueOf(u.getUikId()) + ",'" + u.getFullName() + "','" + u.getPosition() + "','" + u.getAppointment()).append("');");
			}
			
			// Записываем в таблицу uik_html
			List<String> sql = Arrays.asList(sb.toString().split(";"));
			resp = String.valueOf(SQLiteDB.execute(dbName, sb.toString()));
		} catch (IOException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			resp = e.getMessage();
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).entity(resp).build();
	}*/
	
	private String uiksToJsonString(int region, int[] uikNumbers) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		//gson.serializeNulls();
		String uiksString = gson.toJson(getUiks(region, uikNumbers), new TypeToken<List<Uik>>(){}.getType());
		return uiksString;
	}
	
	/*private void parseCikUikHtml(Uik uik) {
		Document doc = Jsoup.parse(uik.getCikUikHtml());
		if (!doc.select(".dotted p:eq(0)").text().equals(UIK_BAD_NUMBER_MESSAGE)) {
			String[] p = { doc.select(".dotted p:eq(1)").text(), doc.select(".dotted p:eq(2)").text(), doc.select(".dotted p:eq(3)").text(), doc.select(".dotted p:eq(4)").text(), doc.select(".dotted p:eq(5)").text() };
			uik.setTikNumber(p[0].substring(p[0].length() - 3, p[0].length()));
			uik.setUikAddress(getPTag(p[1]));
			uik.setUikPhone(getPTag(p[2]));
			uik.setVotingRoomAddress(getPTag(p[3]));
			uik.setVotingRoomPhone(getPTag(p[4]));
		}
	}
	
	private void parseVyboryIzbirkomUikHtml(Uik uik, int uikId, List<UikMember> uikMembers) {
		Document doc = Jsoup.parse(uik.getVyboryIzbirkomUikHtml());
		
		String email = doc.select("p:eq(6)").text();
		uik.setEmail((email.endsWith(":") ? null : email.substring(email.lastIndexOf(" ") + 1)));
		
		String ed = doc.select("p:eq(7)").text();
		uik.setExpiryDate(ed.substring(ed.lastIndexOf(" ") + 1));
		
		// uik_members
		//doc.select(".table.margtab p:eq(4)").text();
		
		Element table = doc.select("table").get(0); //select the third table.
		Elements rows = table.select("tr");

		for (int i = 1; i < rows.size(); i++) { //first row is the col names, so skip it.
		    Element row = rows.get(i);
		    Elements cols = row.select("td");

		    uikMembers.add(new UikMember(uikId, cols.get(1).text(), cols.get(2).text(), cols.get(3).text()));
		}
		
		// TODO получить geoaddress с сайта VyboryIzbirkom
	}*/
	
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
					uik.setUikNumber(Integer.valueOf(p[0].substring(p[0].indexOf("№") + 1, p[0].indexOf(" ", p[0].indexOf("№")))));
					uik.setTikNumber(p[0].substring(p[0].length() - 3, p[0].length()));
					uik.setUikAddress(getPTag(p[1]));
					uik.setUikPhone(getPTag(p[2]));
					uik.setVotingRoomAddress(getPTag(p[3]));
					uik.setVotingRoomPhone(getPTag(p[4]));
					
					/*GeoAddress geoAddress = Geocoder.geoAddress(uik.getUikAddress(), 1).get(0);
					uik.setLat(geoAddress.getLat());
					uik.setLng(geoAddress.getLng());
					//uik.getGeoAddressId()*/
				}
				
				uiks.add(uik);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return uiks;
	}
	
	private String getPTag(String p) {
		return p.substring(p.indexOf(":") + 2, p.length());
	}
}