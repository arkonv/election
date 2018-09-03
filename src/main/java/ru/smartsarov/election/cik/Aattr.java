package ru.smartsarov.election.cik;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aattr implements Serializable {

	@SerializedName("intid")
	@Expose
	private String intid;
	@SerializedName("levelid")
	@Expose
	private String levelid;
	private final static long serialVersionUID = 9040837082457950412L;

	public String getIntid() {
		return intid;
	}

	public void setIntid(String intid) {
		this.intid = intid;
	}

	public String getLevelid() {
		return levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}
}