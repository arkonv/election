package ru.smartsarov.election.uik;

import static ru.smartsarov.election.Constants.JSON_INDENT;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

public class Uik {
	private String requestUikNumber;
	private String uikNumber;
	private String tikNumber;
	private String uikAddress;
	private String uikPhone;
	private String votingRoomAddress;
	private String votingRoomPhone;
	private BigDecimal lat;
	private BigDecimal lng;
	

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public String getUikAddress() {
		return uikAddress;
	}

	public void setUikAddress(String uikAddress) {
		this.uikAddress = uikAddress;
	}

	public String getUikPhone() {
		return uikPhone;
	}

	public void setUikPhone(String uikPhone) {
		this.uikPhone = uikPhone;
	}

	public String getVotingRoomAddress() {
		return votingRoomAddress;
	}

	public void setVotingRoomAddress(String votingRoomAddress) {
		this.votingRoomAddress = votingRoomAddress;
	}

	public String getVotingRoomPhone() {
		return votingRoomPhone;
	}

	public void setVotingRoomPhone(String votingRoomPhone) {
		this.votingRoomPhone = votingRoomPhone;
	}

	public String getUikNumber() {
		return uikNumber;
	}

	public void setUikNumber(String uikNumber) {
		this.uikNumber = uikNumber;
	}

	public String getTikNumber() {
		return tikNumber;
	}

	public void setTikNumber(String tikNumber) {
		this.tikNumber = tikNumber;
	}

	public String getRequestUikNumber() {
		return requestUikNumber;
	}

	public void setRequestUikNumber(String requestUikNumber) {
		this.requestUikNumber = requestUikNumber;
	}

	public String toJsonString() {
		Gson gson = new GsonBuilder().create();
		StringWriter strOut = new StringWriter();
		try (JsonWriter jsonWriter = new JsonWriter(strOut)) {
			jsonWriter.setIndent(JSON_INDENT);
			gson.toJson(this, getClass(), jsonWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strOut.toString();
	}
}