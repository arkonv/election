package ru.smartsarov.election.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "uik_html" database table.
 * 
 */
@Entity
@Table(name="\"uik_html\"")
@NamedQuery(name="UikHtml.findAll", query="SELECT u FROM UikHtml u")
public class UikHtml implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"cik_uik_html\"", length=2000000000)
	private String cikUikHtml;

	@Id
	@Column(name="\"id\"")
	private int id;

	@Column(name="\"uik_number\"", nullable=false)
	private int uikNumber;

	@Column(name="\"vybory_izbirkom_uik_html\"", length=2000000000)
	private String vyboryIzbirkomUikHtml;

	@Column(name="\"vybory_izbirkom_uik_id\"", length=2000000000)
	private String vyboryIzbirkomUikId;

	@Column(name="\"vybory_izbirkom_uik_url\"", length=2000000000)
	private String vyboryIzbirkomUikUrl;

	//bi-directional one-to-one association to Uik
//	@OneToOne(mappedBy="uikHtml")
//	private Uik uik;

	public UikHtml() {
	}

	public String getCikUikHtml() {
		return this.cikUikHtml;
	}

	public void setCikUikHtml(String cikUikHtml) {
		this.cikUikHtml = cikUikHtml;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUikNumber() {
		return this.uikNumber;
	}

	public void setUikNumber(int uikNumber) {
		this.uikNumber = uikNumber;
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

//	public Uik getUik() {
//		return this.uik;
//	}
//
//	public void setUik(Uik uik) {
//		this.uik = uik;
//	}

}