package ru.smartsarov.election.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "uik_members" database table.
 * 
 */
@Entity
@Table(name="\"uik_members\"")
@NamedQuery(name="UikMember.findAll", query="SELECT u FROM UikMember u")
public class UikMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"appointment\"", length=2000000000)
	private String appointment;

	@Column(name="\"full_name\"", length=2000000000)
	private String fullName;

	@Id
	@Column(name="\"id\"", nullable=false)
	private int id;

	@Column(name="\"position\"", length=2000000000)
	private String position;

	@Column(name="\"uik_id\"", insertable=false, updatable=false)
	private int uikId;

	//bi-directional many-to-one association to Uik
//	@ManyToOne
//	@JoinColumns({
//		})
//	private Uik uik;

	public UikMember() {
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