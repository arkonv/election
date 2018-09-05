package ru.smartsarov.election.db;

import java.util.ArrayList;
import java.util.List;

public class Addr {
	private Integer uikNumber;
	private List<GeoAddressDelete> houses = new ArrayList<>();
	
	public Addr() {
	}

	public Integer getUikNumber() {
		return uikNumber;
	}

	public void setUikNumber(Integer uikNumber) {
		this.uikNumber = uikNumber;
	}
	
	public void setHouses(List<GeoAddressDelete> houses) {
		this.houses = houses;
	}


	public List<GeoAddressDelete> getHouses() {
		return houses;
	}
}