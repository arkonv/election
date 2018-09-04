package ru.smartsarov.election.db;

import static ru.smartsarov.election.Constants.JSON_INDENT;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonWriter;

@Entity
@Table(name="\"uik\"")
@NamedQuery(name="Uik.findAll", query="SELECT u FROM Uik u")
public class Uik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Expose(serialize = false, deserialize = false)
	@Id
	@Column(name="\"id\"", updatable=false)
	private int id;

	@SerializedName("id")
	@Expose
	@Column(name="\"vybory_izbirkom_uik_id\"")
	private String vyboryIzbirkomUikId;

	@SerializedName("text")
	@Expose
	@Column(name="\"title\"")
	private String title;
	
	@Expose
	@Column(name="\"cik_uik_html\"")
	private String cikUikHtml;

	@Expose
	@Column(name="\"email\"")
	private String email;

	@Expose
	@Column(name="\"expiry_date\"")
	private String expiryDate;

	@Expose
	@Column(name="\"geo_address_id\"")
	private int geoAddressId;

	@Expose
	@Column(name="\"request_uik_number\"")
	private String requestUikNumber;

	@Expose
	@Column(name="\"tik_number\"")
	private String tikNumber;

	@Expose
	@Column(name="\"uik_address\"")
	private String uikAddress;

	@Expose
	@Column(name="\"uik_number\"")
	private Integer uikNumber;

	@Expose
	@Column(name="\"uik_phone\"")
	private String uikPhone;

	@Expose
	@Column(name="\"voting_room_address\"")
	private String votingRoomAddress;

	@Expose
	@Column(name="\"voting_room_phone\"")
	private String votingRoomPhone;

	@Expose
	@Column(name="\"vybory_izbirkom_uik_html\"")
	private String vyboryIzbirkomUikHtml;

	@Expose
	@Column(name="\"vybory_izbirkom_uik_url\"")
	private String vyboryIzbirkomUikUrl;

	public Uik() {
	}

	public String getCikUikHtml() {
		return this.cikUikHtml;
	}

	public void setCikUikHtml(String cikUikHtml) {
		this.cikUikHtml = cikUikHtml;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getGeoAddressId() {
		return this.geoAddressId;
	}

	public void setGeoAddressId(int geoAddressId) {
		this.geoAddressId = geoAddressId;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRequestUikNumber() {
		return this.requestUikNumber;
	}

	public void setRequestUikNumber(String requestUikNumber) {
		this.requestUikNumber = requestUikNumber;
	}

	public String getTikNumber() {
		return this.tikNumber;
	}

	public void setTikNumber(String tikNumber) {
		this.tikNumber = tikNumber;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUikAddress() {
		return this.uikAddress;
	}

	public void setUikAddress(String uikAddress) {
		this.uikAddress = uikAddress;
	}

	public Integer getUikNumber() {
		return this.uikNumber;
	}

	public void setUikNumber(Integer uikNumber) {
		this.uikNumber = uikNumber;
	}

	public String getUikPhone() {
		return this.uikPhone;
	}

	public void setUikPhone(String uikPhone) {
		this.uikPhone = uikPhone;
	}

	public String getVotingRoomAddress() {
		return this.votingRoomAddress;
	}

	public void setVotingRoomAddress(String votingRoomAddress) {
		this.votingRoomAddress = votingRoomAddress;
	}

	public String getVotingRoomPhone() {
		return this.votingRoomPhone;
	}

	public void setVotingRoomPhone(String votingRoomPhone) {
		this.votingRoomPhone = votingRoomPhone;
	}

	public String getVyboryIzbirkomUikHtml() {
		return this.vyboryIzbirkomUikHtml;
	}

	public void setVyboryIzbirkomUikHtml(String vyboryIzbirkomUikHtml) {
		this.vyboryIzbirkomUikHtml = vyboryIzbirkomUikHtml;
	}

	public String getVyboryIzbirkomUikId() {
		return this.vyboryIzbirkomUikId;
	}

	public void setVyboryIzbirkomUikId(String vyboryIzbirkomUikId) {
		this.vyboryIzbirkomUikId = vyboryIzbirkomUikId;
	}

	public String getVyboryIzbirkomUikUrl() {
		return this.vyboryIzbirkomUikUrl;
	}

	public void setVyboryIzbirkomUikUrl(String vyboryIzbirkomUikUrl) {
		this.vyboryIzbirkomUikUrl = vyboryIzbirkomUikUrl;
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