package ru.smartsarov.election.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="\"geo_address\"")
@NamedQuery(name="GeoAddress.findAll", query="SELECT g FROM GeoAddress g")
public class GeoAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"full_address\"", length=2000000000)
	private String fullAddress;

	@Id
	@Column(name="\"id\"")
	private int id;

	@Column(name="\"lat\"", precision=10, scale=10)
	private BigDecimal lat;

	@Column(name="\"lng\"", precision=10, scale=10)
	private BigDecimal lng;

	@Column(name="\"request_address\"", length=2000000000)
	private String requestAddress;

	//bi-directional one-to-one association to Uik
//	@OneToOne(mappedBy="geoAddress")
//	private Uik uik;

	public GeoAddress() {
	}

	public GeoAddress(int id, String requestAddress, String fullAddress, BigDecimal lat, BigDecimal lng) {
		this.fullAddress = fullAddress;
		this.id = id;
		this.lat = lat;
		this.lng = lng;
		this.requestAddress = requestAddress;
	}

	public String getFullAddress() {
		return this.fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getLat() {
		return this.lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLng() {
		return this.lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public String getRequestAddress() {
		return this.requestAddress;
	}

	public void setRequestAddress(String requestAddress) {
		this.requestAddress = requestAddress;
	}

//	public Uik getUik() {
//		return this.uik;
//	}
//
//	public void setUik(Uik uik) {
//		this.uik = uik;
//	}

}