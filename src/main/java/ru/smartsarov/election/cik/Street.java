package ru.smartsarov.election.cik;

import java.util.ArrayList;
import java.util.List;

public class Street extends UikRequest {
	private static final long serialVersionUID = 7580125185615363602L;

	private List<UikRequest> houses;
	
	public Street() {
		this.houses = new ArrayList<>();
	}

	/*public Street(List<UikRequest> houses) {
		this.houses = houses;
	}*/
	
	public List<UikRequest> getHouses() {
		return houses;
	}

	public void setHouses(List<UikRequest> houses) {
		this.houses = houses;
	}
	
	public void addHouses(List<UikRequest> houses) {
		this.houses.addAll(houses);
	}
}