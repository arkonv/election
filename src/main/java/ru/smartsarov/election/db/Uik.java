package ru.smartsarov.election.db;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "uik" database table.
 * 
 */
@Entity
@Table(name="\"uik\"")
@NamedQuery(name="Uik.findAll", query="SELECT u FROM Uik u")
public class Uik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"cik_uik_id\"", length=2000000000)
	private String cikUikId;

	@Column(name="\"cik_uik_url\"", length=2000000000)
	private String cikUikUrl;

	@Column(name="\"email\"", length=2000000000)
	private String email;

	@Column(name="\"geo_address_id\"", nullable=false)
	private int geoAddressId;

	@Id
	@Column(name="\"id\"", unique=true, nullable=false)
	private int id;

	@Column(name="\"request_uik_number\"", length=2000000000)
	private String requestUikNumber;

	@Column(name="\"tik_number\"", length=2000000000)
	private String tikNumber;

	@Column(name="\"title\"", length=2000000000)
	private String title;

	@Column(name="\"uik_address\"", length=2000000000)
	private String uikAddress;

	@Column(name="\"uik_number\"", length=2000000000)
	private String uikNumber;

	@Column(name="\"uik_phone\"", length=2000000000)
	private String uikPhone;

	@Column(name="\"voting_room_address\"", length=2000000000)
	private String votingRoomAddress;

	@Column(name="\"voting_room_phone\"", length=2000000000)
	private String votingRoomPhone;

	//bi-directional one-to-one association to GeoAddress
//	@OneToOne
//	@JoinColumns({
//		})
//	private GeoAddress geoAddress;
//
//	//bi-directional one-to-one association to UikHtml
//	@OneToOne
//	@JoinColumns({
//		})
//	private UikHtml uikHtml;
//
//	//bi-directional many-to-one association to UikMember
//	@OneToMany(mappedBy="uik")
//	private List<UikMember> uikMembers;
//
//	public Uik() {
//	}

	public String getCikUikId() {
		return this.cikUikId;
	}

	public void setCikUikId(String cikUikId) {
		this.cikUikId = cikUikId;
	}

	public String getCikUikUrl() {
		return this.cikUikUrl;
	}

	public void setCikUikUrl(String cikUikUrl) {
		this.cikUikUrl = cikUikUrl;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getUikNumber() {
		return this.uikNumber;
	}

	public void setUikNumber(String uikNumber) {
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

//	public GeoAddress getGeoAddress() {
//		return this.geoAddress;
//	}
//
//	public void setGeoAddress(GeoAddress geoAddress) {
//		this.geoAddress = geoAddress;
//	}
//
//	public UikHtml getUikHtml() {
//		return this.uikHtml;
//	}
//
//	public void setUikHtml(UikHtml uikHtml) {
//		this.uikHtml = uikHtml;
//	}
//
//	public List<UikMember> getUikMembers() {
//		return this.uikMembers;
//	}
//
//	public void setUikMembers(List<UikMember> uikMembers) {
//		this.uikMembers = uikMembers;
//	}
//
//	public UikMember addUikMember(UikMember uikMember) {
//		getUikMembers().add(uikMember);
//		uikMember.setUik(this);
//
//		return uikMember;
//	}
//
//	public UikMember removeUikMember(UikMember uikMember) {
//		getUikMembers().remove(uikMember);
//		uikMember.setUik(null);
//
//		return uikMember;
//	}

}