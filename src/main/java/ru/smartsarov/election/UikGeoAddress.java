package ru.smartsarov.election;

import java.math.BigDecimal;

import ru.smartsarov.geocoder.GeoAddress;

public class UikGeoAddress extends GeoAddress {
	private Integer uikNumber;

	public UikGeoAddress() {
	}

	public UikGeoAddress(String requestAddress, String fullAddress, BigDecimal lat, BigDecimal lng, Integer uikNumber) {
		super(requestAddress, fullAddress, lat, lng);
		this.uikNumber = uikNumber;
	}

	public Integer getUikNumber() {
		return uikNumber;
	}

	public void setUikNumber(Integer uikNumber) {
		this.uikNumber = uikNumber;
	}
}