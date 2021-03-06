package ru.smartsarov.election.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="\"uik_member\"")
@NamedQuery(name="UikMember.findAll", query="SELECT u FROM UikMember u")
public class UikMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Expose(serialize = false, deserialize = false)
	@Id
	@Column(name="\"id\"", nullable=false)
	private int id;
	
	// TODO доделать
	@Expose
	//@Column(name="\"uik_id\"")
	private Integer uikNumber;
	
	@Expose
	@Column(name="\"full_name\"", length=2000000000)
	private String fullName;
	
	@Expose
	@Column(name="\"position\"", length=2000000000)
	private String position;
	
	@Expose
	@Column(name="\"appointment\"", length=2000000000)
	private String appointment;
	
	@Expose(serialize = false, deserialize = false)
	@Column(name="\"uik_id\"")
	private int uikId;

	//bi-directional many-to-one association to Uik
//	@ManyToOne
//	@JoinColumns({
//		})
//	private Uik uik;

	public UikMember() {
	}
	
	public UikMember(int uikId, String fullName, String position, String appointment) {
		this.appointment = appointment;
		this.fullName = fullName;
		this.position = position;
		this.uikId = uikId;
	}

	public String getAppointment() {
		return this.appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getUikId() {
		return this.uikId;
	}

	public void setUikId(int uikId) {
		this.uikId = uikId;
	}

//	public Uik getUik() {
//		return this.uik;
//	}
//
//	public void setUik(Uik uik) {
//		this.uik = uik;
//	}

}