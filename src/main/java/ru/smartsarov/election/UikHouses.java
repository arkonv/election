package ru.smartsarov.election;

import static ru.smartsarov.election.Constants.CIKRF_FIND_UIK_URL;
import static ru.smartsarov.election.Constants.SAROV_ADDRESS;
import static ru.smartsarov.election.Constants.SAROV_HOUSES;
import static ru.smartsarov.election.Constants.SAROV_STREETS;
import static ru.smartsarov.election.Constants.UIK_BAD_NUMBER_MESSAGE;
import static ru.smartsarov.election.Constants.UIK_BAD_NUMBER_MESSAGE2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ru.smartsarov.election.cik.Street;
import ru.smartsarov.election.cik.UikRequest;
import ru.smartsarov.geocoder.GeoAddress;
import ru.smartsarov.geocoder.Geocoder;

@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class UikHouses {

	@GET
	@Path("/sarov/uiks_houses")
	public Response uikSarov() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String uiks = gson.toJson(getSarovUiksGeoAddresses(), new TypeToken<List<UikGeoAddress>>(){}.getType());

		return Response.status(Response.Status.OK).entity(uiks).build();
	}

	private List<UikGeoAddress> getSarovUiksGeoAddresses() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Street> streets = null;
		List<UikGeoAddress> geo = new ArrayList<>();

		// TODO переименовать поля класса UikRequest в удобные наименования через аннотации Gson'а
		try {
			String jsonStreets = Jsoup.connect(SAROV_STREETS).ignoreContentType(true).get().text();
			streets = gson.fromJson(jsonStreets, new TypeToken<List<Street>>(){}.getType());
			
			for (Street street : streets) {
				String jsonHouses = Jsoup.connect(CIKRF_FIND_UIK_URL + street.getId()).ignoreContentType(true).get().text();
				street.addHouses(gson.fromJson(jsonHouses, new TypeToken<List<UikRequest>>(){}.getType()));
				
				for (int i = 0; i < street.getHouses().size(); i++) {
					String requestAddress = SAROV_ADDRESS + street.getText() + ", " + street.getHouses().get(i).getText();
					
					Document doc = Jsoup.connect(SAROV_HOUSES + street.getHouses().get(i).getAAttr().getIntid() + "?do=result").ignoreContentType(true).get();
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
					UikGeoAddress g = new UikGeoAddress(requestAddress, geoAddress.getFullAddress(), geoAddress.getLat(), geoAddress.getLng(), uikNumber);
					geo.add(g);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return geo;
	}
}