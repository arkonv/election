package ru.smartsarov.geocoder;

import static ru.smartsarov.geocoder.Constants.JSON_INDENT;
import static ru.smartsarov.geocoder.Constants.YANDEX_GEOCODER_URL;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import ru.smartsarov.geocoder.data.YaGeocoderResponse;


@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class Geocoder
{
	/*@GET
	@Path
    public Response index()
    {
		InputStream is = this.getClass().getResourceAsStream("/static/index.html");
    	return Response.status(Response.Status.OK).entity(is).build();
    }*/
    
    // TODO создать отдельную ф. geocoder, принимающую адрес, возвращающую класс GeoAddress. С приёмом массива адресов через POST
    
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * 
	 * <p>Для создания java класса из json необходимо использовать
	 * онлайн-сервис http://www.jsonschema2pojo.org
	 */
	@GET
    @Path("/{address}/{count}")
    public static Response geocoder(@PathParam("address") String address, @PathParam("count") Integer count)
    {
		String url = YANDEX_GEOCODER_URL + address;
		String resp = null;
		try {
			resp = Jsoup.connect(url).ignoreContentType(true).get().body().text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().create();
		YaGeocoderResponse yandexGeo = gson.fromJson(resp, YaGeocoderResponse.class);
		int found = count == null ? Integer.valueOf(yandexGeo.response.geoObjectCollection.metaDataProperty.geocoderResponseMetaData.found) : count;
		//List<GeoAddress> geoAddresses = new ArrayList<>(yandexGeo.response.geoObjectCollection.featureMember.size());
		GeoAddresses geoAddresses = new GeoAddresses();
		/*Type listType = new TypeToken<List<String>>() {
		}.getType();
		List<String> target = new LinkedList<String>();
		target.add("blah");

		Gson gson = new Gson();
		String json = gson.toJson(target, listType);
		List<String> target2 = gson.fromJson(json, listType);*/

		//for (int i = 0; i < geoAddresses.size(); i++) {
		for (int i = 0; i < found; i++) {
			GeoAddress geoAddress = new GeoAddress();
			String[] pos = yandexGeo.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.split(" ");

			geoAddress.setRequestAddress(yandexGeo.response.geoObjectCollection.metaDataProperty.geocoderResponseMetaData.request);
			geoAddress.setFullAddress(yandexGeo.response.geoObjectCollection.featureMember.get(0).geoObject.metaDataProperty.geocoderMetaData.text);
			geoAddress.setLat(new BigDecimal(pos[1]));
			geoAddress.setLng(new BigDecimal(pos[0]));

			geoAddresses.addAddress(geoAddress);
		}

		/*String requestAddress = yandexGeo.response.geoObjectCollection.metaDataProperty.geocoderResponseMetaData.request;	
		String fullAddress = yandexGeo.response.geoObjectCollection.featureMember.get(0).geoObject.metaDataProperty.geocoderMetaData.text;
		String[] pos = yandexGeo.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.split(" ");
		GeoAddress geoAddress = new GeoAddress(requestAddress, fullAddress, new BigDecimal(pos[1]), new BigDecimal(pos[0]));*/

		StringWriter strOut = new StringWriter();
		try(JsonWriter jsonWriter = new JsonWriter(strOut)) {
			jsonWriter.setIndent(JSON_INDENT);
			gson.toJson(geoAddresses, GeoAddresses.class, jsonWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		records.add(record);
		gson.toJson(records, ResultSetToJson.RESULT_TYPE, jsonWriter);*/

		return Response.status(Response.Status.OK).entity(strOut.toString()).build();
    }
	
	@GET
    @Path("/{address}")
    public static Response geocoder(@PathParam("address") String address)
    {
		return geocoder(address, null);
    }
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * 
	 * <p>Для создания java класса из json необходимо использовать
	 * онлайн-сервис http://www.jsonschema2pojo.org
	 */
    public static List<GeoAddress> geoAddress(String address, Integer count)
    {
		String url = YANDEX_GEOCODER_URL + address;
		String resp = null;
		try {
			resp = Jsoup.connect(url).ignoreContentType(true).get().body().text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().create();
		YaGeocoderResponse yandexGeo = gson.fromJson(resp, YaGeocoderResponse.class);
		int found = count == null ? Integer.valueOf(yandexGeo.response.geoObjectCollection.metaDataProperty.geocoderResponseMetaData.found) : count;
		//List<GeoAddress> geoAddresses = new ArrayList<>(yandexGeo.response.geoObjectCollection.featureMember.size());
		GeoAddresses geoAddresses = new GeoAddresses();
		/*Type listType = new TypeToken<List<String>>() {
		}.getType();
		List<String> target = new LinkedList<String>();
		target.add("blah");

		Gson gson = new Gson();
		String json = gson.toJson(target, listType);
		List<String> target2 = gson.fromJson(json, listType);*/

		//for (int i = 0; i < geoAddresses.size(); i++) {
		for (int i = 0; i < found; i++) {
			GeoAddress geoAddress = new GeoAddress();
			String[] pos = yandexGeo.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.split(" ");

			geoAddress.setRequestAddress(yandexGeo.response.geoObjectCollection.metaDataProperty.geocoderResponseMetaData.request);
			geoAddress.setFullAddress(yandexGeo.response.geoObjectCollection.featureMember.get(0).geoObject.metaDataProperty.geocoderMetaData.text);
			geoAddress.setLat(new BigDecimal(pos[1]));
			geoAddress.setLng(new BigDecimal(pos[0]));

			geoAddresses.addAddress(geoAddress);
		}

		/*String requestAddress = yandexGeo.response.geoObjectCollection.metaDataProperty.geocoderResponseMetaData.request;	
		String fullAddress = yandexGeo.response.geoObjectCollection.featureMember.get(0).geoObject.metaDataProperty.geocoderMetaData.text;
		String[] pos = yandexGeo.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.split(" ");
		GeoAddress geoAddress = new GeoAddress(requestAddress, fullAddress, new BigDecimal(pos[1]), new BigDecimal(pos[0]));*/

		/*StringWriter strOut = new StringWriter();
		try(JsonWriter jsonWriter = new JsonWriter(strOut)) {
			jsonWriter.setIndent(JSON_INDENT);
			gson.toJson(geoAddresses, GeoAddresses.class, jsonWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		records.add(record);
		gson.toJson(records, ResultSetToJson.RESULT_TYPE, jsonWriter);*/
		

		return geoAddresses.getGeoAddresses();
    }
    
    private static class GeoAddresses {
		private List<GeoAddress> geoAddresses = new ArrayList<>();
		
		public List<GeoAddress> getGeoAddresses() {
			return geoAddresses;
		}

		public void setGeoAddresses(List<GeoAddress> geoAddresses) {
			this.geoAddresses = geoAddresses;
		}

		public void addAddress(GeoAddress e) {
			// TODO проверка на null geoAddresses
			geoAddresses.add(e);
		}
	}
}