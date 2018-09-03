package ru.smartsarov.geocoder;

import java.math.BigDecimal;

public class GeoAddress {
	private String requestAddress;
	private String fullAddress;
	private BigDecimal lat;
	private BigDecimal lng;
	
	public GeoAddress() {
		
	};
	
	public GeoAddress(String requestAddress, String fullAddress, BigDecimal lat, BigDecimal lng) {
		this.requestAddress = requestAddress;
		this.fullAddress = fullAddress;
		this.lat = lat;
		this.lng = lng;
	}
	
	public String getRequestAddress() {
		return requestAddress;
	}

	public void setRequestAddress(String requestAddress) {
		this.requestAddress = requestAddress;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

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
}