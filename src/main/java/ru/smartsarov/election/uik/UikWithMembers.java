package ru.smartsarov.election.uik;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.Expose;

import ru.smartsarov.election.db.Uik;
import ru.smartsarov.election.db.UikMember;

public class UikWithMembers extends Uik {
	private static final long serialVersionUID = 1L;

	@Expose
	private List<UikMember> uikMembers;

	@Expose
	private /* BigDecimal */String lat;

	@Expose
	private /* BigDecimal */String lng;

	public UikWithMembers() {
		super();

		uikMembers = new LinkedList<>();
	}

	public List<UikMember> getUikMembers() {
		return uikMembers;
	}

	public void setUikMembers(List<UikMember> uikMembers) {
		this.uikMembers = uikMembers;
	}

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

}