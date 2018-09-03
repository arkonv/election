package ru.smartsarov.election.db;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UikHtml implements Serializable {
	private final static long serialVersionUID = 8965219466178306522L;

	@SerializedName("id")
	@Expose
	private String vyboryIzbirkomUikId;

	@SerializedName("text")
	@Expose
	private String text;

	private Integer uikNumber;

	private String cikHtml;

	private String vyboryIzbirkomUikUrl;

	private String vyboryIzbirkomUikHtml;

	public String getVyboryIzbirkomUikUrl() {
		return vyboryIzbirkomUikUrl;
	}

	public void setVyboryIzbirkomUikUrl(String vyboryIzbirkomUikUrl) {
		this.vyboryIzbirkomUikUrl = vyboryIzbirkomUikUrl;
	}

	public String getVyboryIzbirkomUikHtml() {
		return vyboryIzbirkomUikHtml;
	}

	public void setVyboryIzbirkomUikHtml(String vyboryIzbirkomUikHtml) {
		this.vyboryIzbirkomUikHtml = vyboryIzbirkomUikHtml;
	}

	public String getId() {
		return vyboryIzbirkomUikId;
	}

	public void setId(String id) {
		this.vyboryIzbirkomUikId = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getVyboryIzbirkomUikId() {
		return vyboryIzbirkomUikId;
	}

	public void setVyboryIzbirkomUikId(String vyboryIzbirkomUikId) {
		this.vyboryIzbirkomUikId = vyboryIzbirkomUikId;
	}

	public Integer getUikNumber() {
		return uikNumber;
	}

	public void setUikNumber(Integer uikNumber) {
		this.uikNumber = uikNumber;
	}

	public String getCikHtml() {
		return cikHtml;
	}

	public void setCikHtml(String cikHtml) {
		this.cikHtml = cikHtml;
	}

}