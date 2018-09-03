package ru.smartsarov.election.cik;

import java.io.Serializable;
import java.util.Iterator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UikRequest implements Serializable, Iterable<UikRequest> {

	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("text")
	@Expose
	private String text;
	@SerializedName("a_attr")
	@Expose
	private Aattr aAttr;
	@SerializedName("children")
	@Expose
	private Boolean children;
	private final static long serialVersionUID = 2326643019323295056L;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Aattr getAAttr() {
		return aAttr;
	}

	public void setAAttr(Aattr aAttr) {
		this.aAttr = aAttr;
	}

	public Boolean getChildren() {
		return children;
	}

	public void setChildren(Boolean children) {
		this.children = children;
	}

	@Override
	public Iterator<UikRequest> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}