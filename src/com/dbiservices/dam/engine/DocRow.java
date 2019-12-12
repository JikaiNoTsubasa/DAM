package com.dbiservices.dam.engine;

import java.util.HashMap;

import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfId;

public class DocRow {

	private IDfId id;
	private HashMap<String, IDfAttr> attributes = new HashMap<>();
	public HashMap<String, IDfAttr> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, IDfAttr> attributes) {
		this.attributes = attributes;
	}
	public IDfId getId() {
		return id;
	}
	public void setId(IDfId id) {
		this.id = id;
	}
}
