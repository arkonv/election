package ru.smartsarov.election.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

public class GeoAddressDelete implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fullAddress;

	private String lat;

	private String lng;

	private String requestAddress;

	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLng() {
		return lng;
	}


	public void setLng(String lng) {
		this.lng = lng;
	}


	public GeoAddressDelete() {
	}


	public String getFullAddress() {
		return this.fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getRequestAddress() {
		return this.requestAddress;
	}

	public void setRequestAddress(String requestAddress) {
		this.requestAddress = requestAddress;
	}
}